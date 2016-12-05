package jp.leopanda.htmlEditHelper.parts;

/**
 * レイアウト用入力変数
 * @author LeoPanda
 *
 */
public class LayoutVariables {
  public LayoutType layoutType; //レイアウトの種類
  public int xIndent;// レイアウト間横インデント
  public int yIndent;// レイアウト間縦インデント
  public int defaultIndent; // デフォルトの字下げ
  public int maxCols;// レイアウトの最大カラム数
  public int layoutWidth; //レイアウト領域の幅

  public LayoutVariables setVariables(LayoutType layoutTyle,int xIndent, int yIndent, int defaultIndent, int maxCols,int layoutWidth) {
    this.layoutType = layoutTyle;
    this.xIndent = xIndent;
    this.yIndent = yIndent;
    this.defaultIndent = defaultIndent;
    this.maxCols = maxCols;
    this.layoutWidth = layoutWidth;
    return this;
  }
}
