package jp.leopanda.htmlEditHelper.parts;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;

import jp.leopanda.htmlEditHelper.enums.AttributeName;
import jp.leopanda.htmlEditHelper.enums.Error;
import jp.leopanda.htmlEditHelper.enums.TagName;

/**
 * PhotoLayout用DIVタグ変換関数
 * 
 * @author LeoPanda
 *
 */
public class LayoutCalc {
  // 固定文字列
  private static final int TEXT_ALIGN = 15;// テキストのみDIVの字下げ幅
  private static final int TEXT_WIDTH = 320;// テキストのみDIVの幅

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
   * @param generateClass String 生成後DIVタグのクラス名
   */
  public LayoutCalc(String generateClass,LayoutVariables variables) {
    this.generateClassName = generateClass;
    this.variables = variables;
  }

  /**
   * 入力変数のセッター
   * 
   * @param variables パネルから入力される変数群
   */
  public void setVariables(LayoutVariables variables) {
    this.variables = variables;
  }

  /**
   * レイアウト用にDIVタグを整形する
   * 
   * @param source String 対象のHTMLソース
   * @return String 整形後のHTMLソース
   */
  public String genLayoutedSource(String source) {
    HTML sourceHtml = new HTML(source);
    NodeList<Element> sourceNode = sourceHtml.getElement().getElementsByTagName(TagName.DIV.text);
    updateHtmlNode(sourceNode, setLayout(getDivElements(sourceNode)));
    return sourceHtml.getHTML();
  }

  // ソースHTMLをDIV配列へ変換する
  private DivTag[] getDivElements(NodeList<Element> sourceNode) {
    errorString = "";
    int nodeLength = sourceNode.getLength();
    DivTag[] divTags = new DivTag[nodeLength];
    for (int i = 0; i < sourceNode.getLength(); i++) {
      final int refIndex = i;
      divTags[i] = new DivTag(sourceNode.getItem(i), TEXT_ALIGN, new ErrorListener() {
        @Override
        public void onError(Error error, String text) {
          errorString += String.valueOf(refIndex + 1) + "番目の<div>:" + text + error.text + "\n";
        }
      });
    }
    if (errorString.length() > 0) {
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
  private void updateHtmlNode(NodeList<Element> divNode, DivTag[] divTags) {
    NumFormatter numFormatter = new NumFormatter(1);//数値フォーマットの小数点以下桁数を１桁にセットする
    for (int i = 0; i < divNode.getLength(); i++) {
      divNode.getItem(i).setAttribute(AttributeName.CLASS.text, generateClassName);
      divNode.getItem(i).setAttribute(AttributeName.STYLE.text,
          new StyleFormatter().getStyleAttributes(divTags[i]));
      divNode.getItem(i).setAttribute(AttributeName.WIDTH.text,
          numFormatter.getString(divTags[i].getWidth()));
      divNode.getItem(i).setAttribute(AttributeName.HEIGHT.text,
          numFormatter.getString(divTags[i].getHeight()));
      NodeList<Element> childImgNode = divNode.getItem(i).getElementsByTagName(TagName.IMG.text);
      if (childImgNode.getLength() > 0) {
        childImgNode.getItem(0).setAttribute(AttributeName.WIDTH.text,
            numFormatter.getString(divTags[i].getWidth()));
        childImgNode.getItem(0).setAttribute(AttributeName.HEIGHT.text,
            numFormatter.getString(divTags[i].getHeight()));
      }
    }
  }

  // 高さと幅のエレメントが指定されていない文字のみのDIVタグに情報を補完する
  private DivTag[] compleEmptyAttribute(DivTag[] divTags) {
    for (int i = 0; i < divTags.length; i++) {
      if (divTags[i].getWidth() == 0) {
        divTags[i].setWidth(TEXT_WIDTH);
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


}
