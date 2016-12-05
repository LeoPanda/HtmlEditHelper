package jp.leopanda.htmlEditHelper.parts;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;

/**
 * PhotoLayout用DIVタグ変換関数
 * 
 * @author LeoPanda
 *
 */
public class LayoutCalc {
  // 固定文字列
  private final String comma = ",";
  private final String delimiter = ";";
  private final String stylePrefix = "clear: both; text-defaultIndent: left;";
  private final String sizeUnit = "px";// divのサイズ単位
  private final int textAlign = 15;// テキストのみDIVの字下げ幅
  private final int textWidth = 320;// テキストのみDIVの幅

  private String generateClassName;// 変更後生成クラス名
  private LayoutVariables variables; // パネルから入力される可変変数

  private ErrorListener errorListener;
  private String errorString;
  public void setErrorListener(ErrorListener errorListener) {
    this.errorListener = errorListener;
  }
  /**
   * コンストラクタ
   * 
   * @param generateClass
   *          String 生成後DIVタグのクラス名
   */
  public LayoutCalc(String generateClass) {
    this.generateClassName = generateClass;
  }

  /**
   * 入力変数のセッター
   * 
   * @param variables
   *          パネルから入力される変数群
   */
  public void setVariables(LayoutVariables variables) {
    this.variables = variables;
  }

  /**
   * レイアウト用にDIVタグを整形する
   * 
   * @param source
   *          String 対象のHTMLソース
   * @return
   */
  public String genLayoutedSource(String source) {
    HTML sourceHtml = new HTML(source);
    NodeList<Element> sourceNode = sourceHtml.getElement()
        .getElementsByTagName(TagName.DIV.text);
    updateHTMLNode(sourceNode, setLayout(getDivElements(sourceNode)));
    return sourceHtml.getHTML();
  }

  // ソースHTMLをDIV配列へ変換する
  private DivTag[] getDivElements(NodeList<Element> sourceNode) {
    DivTag divTags[] = new DivTag[sourceNode.getLength()];
    errorString = "";
    for (int i = 0; i < sourceNode.getLength(); i++) {
      final int refIndex = i;
      Element divElement = sourceNode.getItem(i);
      divTags[i] = new DivTag(divElement, textAlign, new ErrorListener() {
        @Override
        public void onError(Error error, String text) {
          errorString += String.valueOf(refIndex + 1) + "番目の<div>:" + text + error.text + "\n";
        }
      });
    }
    if(errorString.length()>0){
      errorListener.onError(Error.DIVTAG_GETERROR, "\n" + errorString);
    }
    return divTags;
  }

  // 指定された入力にしたがって各DIVのレイアウト情報を書き換える
  private DivTag[] setLayout(DivTag[] divTags) {
    switch (variables.layoutType) {
    case GRID:
      divTags = new GridLayoutor(compleEmptyAttribute(divTags), variables).doLayout();
      break;
    case VHCOMBINE:
      divTags = new CombineLayoutor(compleEmptyAttribute(divTags), variables, 0).doLayout();
      break;
    case HVCOMBINE:
      divTags = new CombineLayoutor(compleEmptyAttribute(divTags), variables, divTags.length - 1)
          .doLayout();
      break;
    default:
      break;
    }
    return divTags;
  }

  // レイアウト情報をHTMLへ書き込む
  private void updateHTMLNode(NodeList<Element> divNode, DivTag[] divTags) {
    for (int i = 0; i < divNode.getLength(); i++) {
      divNode.getItem(i).setAttribute(AttributeName.CLASS.text, generateClassName);
      divNode.getItem(i).setAttribute(AttributeName.STYLE.text, getStyleAttributes(divTags[i]));
      divNode.getItem(i).setAttribute(AttributeName.WIDTH.text,
          getNumString(divTags[i].getWidth()));
      divNode.getItem(i).setAttribute(AttributeName.HEIGHT.text,
          getNumString(divTags[i].getHeight()));
      NodeList<Element> childImgNode = divNode.getItem(i).getElementsByTagName(TagName.IMG.text);
      if (childImgNode.getLength() > 0) {
        childImgNode.getItem(0).setAttribute(AttributeName.WIDTH.text,
            getNumString(divTags[i].getWidth()));
        childImgNode.getItem(0).setAttribute(AttributeName.HEIGHT.text,
            getNumString(divTags[i].getHeight()));
      }
    }
  }

  // 高さと幅のエレメントが指定されていない文字のみのDIVタグに情報を補完する
  private DivTag[] compleEmptyAttribute(DivTag[] divTags) {
    for (int i = 0; i < divTags.length; i++) {
      if (divTags[i].getWidth() == 0) {
        divTags[i].setWidth(textWidth);
      }
      if (divTags[i].getHeight() == 0) {
        float maxHeight = 0;
        int firstColOfRow = i - i % variables.maxCols;
        // 同一行にレイアウトされるDIVの最大高さをセットする
        for (int j = firstColOfRow; j < firstColOfRow + variables.maxCols; j++) {
          if (j < divTags.length) {
            maxHeight = divTags[j].getHeight() > maxHeight ? divTags[j].getHeight() : maxHeight;
          }
        }
        divTags[i].setHeight(maxHeight);
        divTags[i].setAspectRatio(divTags[i].getWidth() / maxHeight);
      }
    }
    return divTags;
  }

  // DIVタグのStyle属性を設定する
  private String getStyleAttributes(DivTag divTag) {
    String widthElement = getStyleElement(AttributeName.WIDTH.text, divTag.getWidth());
    String heightElement = getStyleElement(AttributeName.HEIGHT.text, divTag.getHeight());
    String marginLeftElement = getStyleElement(AttributeName.MARGINLEFT.text,
        divTag.getMarginLeft());
    String marginBottomElement = getStyleElement(AttributeName.MARGINBOTTOM.text,
        divTag.getMarginBottom());
    String transformElement = getTransformElement(divTag);
    return stylePrefix + widthElement + heightElement + transformElement + marginLeftElement
        + marginBottomElement;
  }

  // style属性の各要素を設定する
  private String getStyleElement(String elementName, float value) {
    return value == 0 ? "" : elementName + ":" + getNumString(value) + sizeUnit + delimiter;
  }

  // styleのトランスフォームエレメントを設定する
  private String getTransformElement(DivTag divTag) {
    if (divTag.getTranslateX() + divTag.getTranslateY() == 0) {
      return "";
    }
    String transform = "transform:translate(" +
        getNumString(divTag.getTranslateX()) + sizeUnit +
        comma +
        getNumString(divTag.getTranslateY()) + sizeUnit +
        ")" + delimiter;
    return transform;
  }

  // 浮動小数点数値を文字列に変換する
  private String getNumString(float value) {
    int decimalLength = 1;//小数点以下の桁数
    decimalLength = (int) Math.pow(10,decimalLength);
    String retString;
    int integerPart = (int) value;
    if (value == integerPart) {
      retString = String.valueOf(integerPart);
    } else {
      int decimalPart = (int) ((value - integerPart) * decimalLength);
      float formatedValue = integerPart + ((float) decimalPart / decimalLength);
      retString = String.valueOf(formatedValue);
    }
    return retString;
  }

}
