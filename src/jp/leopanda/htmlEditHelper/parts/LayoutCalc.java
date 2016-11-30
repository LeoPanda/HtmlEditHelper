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
        .getElementsByTagName(TagName.Div.text);
    updateHTMLNode(sourceNode, setLayout(getDivElements(sourceNode)));
    return sourceHtml.getHTML();
  }

  // ソースHTMLをDIV配列へ変換する
  private DivTag[] getDivElements(NodeList<Element> sourceNode) {
    DivTag divTags[] = new DivTag[sourceNode.getLength()];
    for (int i = 0; i < sourceNode.getLength(); i++) {
      Element divElement = sourceNode.getItem(i);
      divTags[i] = new DivTag(divElement, textAlign);
    }
    return divTags;
  }

  // 指定された入力にしたがって各DIVのレイアウト情報を書き換える
  private DivTag[] setLayout(DivTag[] divTags) {
    switch (variables.layoutType) {
    case Grid:
      divTags = new GridLayoutor(compleEmptyAttribute(divTags), variables).doLayout();
      break;
    case VerticalHorizontals:
      divTags = new CombineLayoutor(compleEmptyAttribute(divTags), variables, 0).doLayout();
      break;
    case HorizontalsVertical:
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
      divNode.getItem(i).setAttribute(AttributeName.Class.text, generateClassName);
      divNode.getItem(i).setAttribute(AttributeName.Style.text, getStyleAttributes(divTags[i]));
      divNode.getItem(i).setAttribute(AttributeName.Width.text,
          getNumString(divTags[i].getWidth()));
      divNode.getItem(i).setAttribute(AttributeName.Height.text,
          getNumString(divTags[i].getHeight()));
      NodeList<Element> childImgNode = divNode.getItem(i).getElementsByTagName(TagName.Img.text);
      if (childImgNode.getLength() > 0) {
        childImgNode.getItem(0).setAttribute(AttributeName.Width.text,
            getNumString(divTags[i].getWidth()));
        childImgNode.getItem(0).setAttribute(AttributeName.Height.text,
            getNumString(divTags[i].getHeight()));
      }
    }
  }

  // 高さと幅のエレメントが指定されていないDIVタグに情報を補完する
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

  // DIVタグのStyleを設定する
  private String getStyleAttributes(DivTag divTag) {
    String widthElement = getStyleElement(AttributeName.Width.text, divTag.getWidth());
    String heightElement = getStyleElement(AttributeName.Height.text, divTag.getHeight());
    String marginLeftElement = getStyleElement(AttributeName.MarginLeft.text,
        divTag.getMarginLeft());
    String marginBottomElement = getStyleElement(AttributeName.MarginBottom.text,
        divTag.getMarginBottom());
    String transformElement = getTransformElement(divTag);
    return stylePrefix + widthElement + heightElement + transformElement + marginLeftElement
        + marginBottomElement;
  }

  // styleの各要素を設定する
  private String getStyleElement(String name, float value) {
    return value == 0 ? "" : name + ":" + getNumString(value) + sizeUnit + delimiter;
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

  // 浮動小数点数値を小数点付き文字列に変換する
  private String getNumString(float value) {
    String retString;
    int integerPart = (int) value;
    if (value == integerPart) {
      retString = String.valueOf(integerPart);
    } else {
      int decimalPart = (int) ((value - integerPart) * 10);
      float formatedValue = integerPart + ((float) decimalPart / 10);
      retString = String.valueOf(formatedValue);
    }
    return retString;
  }

}
