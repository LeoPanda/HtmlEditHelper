package jp.leopanda.htmlEditHelper.enums;

/**
 * タグの種類
 * @author LeoPanda
 *
 */
public enum TagName {
  DIV("div"), IMG("img");
  public String text;

  TagName(String text) {
    this.text = text;
  }

}