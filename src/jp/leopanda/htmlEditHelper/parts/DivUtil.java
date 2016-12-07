package jp.leopanda.htmlEditHelper.parts;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author LeoPanda
 *
 */
public class DivUtil {
  // 固定文字列
  private final String delimiter = ";";
  private final String stylePrefix = "clear: both; text-defaultIndent: left;";
  private final String sizeUnit = "px";// divのサイズ単位

  private String generateClass = null;// 変更後生成クラス名
  private int xIndent = 0;// レイアウト間横インデント
  private int yIndent = 0;// レイアウト間縦インデント
  private int defaultIndent = 0; // デフォルトの字下げ
  private int maxCols = 0;// レイアウトの最大カラム数
  private int textAlign = 0;// テキストのみDIVの字下げ幅
  private int textWidth = 0;// テキストのみDIVの幅

  public DivUtil(String generateClass) {
    this.generateClass = generateClass;
  }

  public void setValues(int xIndent, int yIndent, int defaultIndent, int maxCols, int textAlign,int textWidth) {
    this.xIndent = xIndent;
    this.yIndent = yIndent;
    this.defaultIndent = defaultIndent;
    this.maxCols = maxCols;
    this.textAlign = textAlign;
    this.textWidth = textWidth;
  }

  // タグの属性
  private enum Attribute {
    Width("width"), Height("height"), MarginLeft("margin-left"), MarginBottom("margin-bottom");
    public String word;

    Attribute(String word) {
      this.word = word;
    }
  }

  // タグの種類
  private enum TagName {
    Div("div"), Img("img");
    public String word;

    TagName(String word) {
      this.word = word;
    }

  }

  /**
   * レイアウト用にDIVタグを整形する
   * 
   * @param source
   * @return
   */
  public String genLayoutDiv(String source) {
    HTML sourceHtml = new HTML(source);
    NodeList<Element> divNode = sourceHtml.getElement()
        .getElementsByTagName(TagName.Div.word);
    setLayoutToNode(divNode, setTransLate(loadInitialSpec(divNode)));
    return sourceHtml.getHTML();
  }

  // ソースHTMLのDIV諸元を読み取って配列に保存する
  private DivSpec[] loadInitialSpec(NodeList<Element> divNode) {
    DivSpec divSpec[] = new DivSpec[divNode.getLength()];
    for (int i = 0; i < divNode.getLength(); i++) {
      Element divElement = divNode.getItem(i);
      divSpec[i] = getOriginalDivSpec(divElement, i % maxCols);
    }
    return divSpec;
  }

  // DIVの移動設定をセットする
  private DivSpec[] setTransLate(DivSpec[] divSpec) {
    int transLateX = defaultIndent;
    for (int i = 0; i < divSpec.length; i++) {
      divSpec = compleEmptyDiv(divSpec, i);
      int col = divSpec[i].getCol();
      if (col > 0) {
        transLateX += divSpec[i - 1].getWidth() + xIndent;
      } else {
        transLateX = 0;
      }
      divSpec[i].setTranslateX(transLateX);
      divSpec[i].setMarginBottom(col < maxCols - 1 ? -1 * divSpec[i].getHeight() : yIndent);
    }
    return divSpec;
  }

  // レイアウト情報をHTMLへ書き込む
  private void setLayoutToNode(NodeList<Element> divNode, DivSpec[] divSpec) {
    for (int i = 0; i < divNode.getLength(); i++) {
      divNode.getItem(i).setAttribute("class", generateClass);
      divNode.getItem(i).setAttribute("style", setStyle(divSpec[i]));
    }
  }

  // 検出できなかったDIVの高さと幅を補完する
  private DivSpec[] compleEmptyDiv(DivSpec[] divSpec, int i) {
    if(divSpec[i].getWidth() == 0 ){
      divSpec[i].setWidth(textWidth);
    }
    if (divSpec[i].getHeight() == 0) {
      int maxHeight = 0;
      int firstColOfRow = i - i % maxCols;
      // 同一行にレイアウトされるDIVの最大高さをセットする
      for (int j = firstColOfRow; j < firstColOfRow + maxCols; j++) {
        maxHeight = divSpec[j].getHeight() > maxHeight ? divSpec[j].getHeight() : maxHeight;
      }
      divSpec[i].setHeight(maxHeight);
    }
    return divSpec;
  }

  // DIV単体の諸元を取り出す
  private DivSpec getOriginalDivSpec(Element divElement, int col) {
    int width = 0;
    int height = 0;
    String orgDivWidth = "";
    String orgDivHeight = "";
    int marginLeft = 0;
    NodeList<Element> childImgNode = divElement.getElementsByTagName(TagName.Img.word);
    if (childImgNode.getLength() > 0) {
      orgDivWidth = childImgNode.getItem(0).getAttribute(Attribute.Width.word);
      orgDivHeight = childImgNode.getItem(0).getAttribute(Attribute.Height.word);
    } else {
      orgDivWidth = divElement.getAttribute(Attribute.Width.word);
      orgDivHeight = divElement.getAttribute(Attribute.Height.word);
      marginLeft = textAlign;
    }
    width = orgDivWidth.isEmpty() ? 0 : Integer.valueOf(orgDivWidth);
    height = orgDivHeight.isEmpty() ? 0 : Integer.valueOf(orgDivHeight);
    return new DivSpec(col, width, height, marginLeft);
  }

  // DIVタグのStyleを設定する
  private String setStyle(DivSpec divSpec) {
    String width = setStyleAttribute(Attribute.Width.word + ":", divSpec.getWidth());
    String height = setStyleAttribute(Attribute.Height.word + ":", divSpec.getHeight());
    String marginLeft = setStyleAttribute(Attribute.MarginLeft.word + ":", divSpec.getMarginLeft());
    String marginBottom = setStyleAttribute(Attribute.MarginBottom.word + ":",
        divSpec.getMarginBottom());
    String transform = divSpec.getTranslateX() == 0 ? ""
        : "transform:translateX(" + String.valueOf(divSpec.getTranslateX())
            + sizeUnit + ")" + delimiter;
    return stylePrefix + width + height + transform + marginLeft + marginBottom;
  }

  // styleの各要素を設定する
  private String setStyleAttribute(String name, int value) {
    return value == 0 ? "" : name + String.valueOf(value) + sizeUnit + delimiter;
  }

}
