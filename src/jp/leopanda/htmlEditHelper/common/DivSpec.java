package jp.leopanda.htmlEditHelper.common;

/**
 * 生成するDivの諸元を保持する
 * 
 * @author LeoPanda
 *
 */
public class DivSpec {
  private int col = 0; // レイアウトのカラム位置
  private int width = 0;
  private int height = 0;
  private int translateX = 0;
  private int marginBottom = 0;
  private int marginLeft = 0;

  public DivSpec(int col, int width, int height, int marginLeft) {
    this.col = col;
    this.width = width;
    this.height = height;
    this.marginLeft = marginLeft;
  }

  public int getCol() {
    return col;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getTranslateX() {
    return translateX;
  }

  public int getMarginBottom() {
    return marginBottom;
  }

  public int getMarginLeft() {
    return this.marginLeft;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setTranslateX(int translateX) {
    this.translateX = translateX;
  }

  public void setMarginBottom(int marginBottom) {
    this.marginBottom = marginBottom;
  }
}
