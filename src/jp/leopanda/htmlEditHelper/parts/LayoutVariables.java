package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.htmlEditHelper.enums.LayoutType;

/**
 * レイアウト用入力変数
 * 
 * @author LeoPanda
 *
 */
public class LayoutVariables {
  public LayoutType layoutType; // レイアウトの種類
  public int indentX;// レイアウト間横インデント
  public int indentY;// レイアウト間縦インデント
  public int defaultIndent; // デフォルトの字下げ
  public int maxCols;// レイアウトの最大カラム数
  public int layoutWidth; // レイアウト領域の幅

  /**
   * コンストラクタ
   * @param layoutTyle LayoutType レイアウトの種類
   * @param indentX int 横方向インデント
   * @param indentY int 縦方向インデント
   * @param defaultIndent int デフォルトの横方向インデント
   * @param maxCols int グリッドタイプレイアウトの最大カラム数
   * @param layoutWidth レイアウト領域の幅
   * @return この変数セット
   */
  public LayoutVariables setVariables(LayoutType layoutTyle, int indentX, int indentY,
      int defaultIndent, int maxCols, int layoutWidth) {
    this.layoutType = layoutTyle;
    this.indentX = indentX;
    this.indentY = indentY;
    this.defaultIndent = defaultIndent;
    this.maxCols = maxCols;
    this.layoutWidth = layoutWidth;
    return this;
  }
}
