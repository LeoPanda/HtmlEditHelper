package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.panelFrame.filedParts.ListElement;

/**
 * リストボックス用の選択値エレメントを自動生成する
 * 
 * @author LeoPanda
 *
 */
public class ElementValue implements ListElement {
  private String name;

  public ElementValue() {}

  ElementValue(String name) {
    this.name = name;
  }

  /**
   * エレメント値の取得
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * 連続する数値エレメントを生成する
   * 
   * @param min int 連続数値の下限値
   * @param max int 連続数値の上限値
   * @return 連続数値エレメント
   */
  public ElementValue[] getSerialNumberElements(int min, int max) {
    ElementValue[] elements = new ElementValue[max - min];
    for (int i = 0; i <= max - min; i++) {
      elements[i] = new ElementValue(String.valueOf(min + i));
    }
    return elements;
  }

}
