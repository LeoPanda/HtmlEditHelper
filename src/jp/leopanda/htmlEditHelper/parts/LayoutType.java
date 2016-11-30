package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.panelFrame.filedParts.ListElement;

/**
 * @author LeoPanda
 *
 */
public enum LayoutType implements ListElement{
  Grid("グリッド"),VerticalHorizontals("縦＋横列"),HorizontalsVertical("横列＋縦");
  public String text;
  LayoutType(String text){
    this.text = text;
  }
  /* 
   * 
   */
  @Override
  public String getName() {
    return this.text;
  }
}
