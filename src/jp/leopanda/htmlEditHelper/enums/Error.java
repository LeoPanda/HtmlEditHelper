package jp.leopanda.htmlEditHelper.enums;

/**
 * 個別エラーメッセージ
 * 
 * @author LeoPanda
 *
 */
public enum Error {
  NO_ATTRIBUTE("属性がみつかりませんでした。"),
  NOT_NUMERIC_ATTRIBUTE("属性が数値ではありませんでした。"),
  DIVTAG_GETERROR("DIVタグの読み込みでエラーを検出しました。");
  public String text;

  Error(String text) {
    this.text = text;
  }

}
