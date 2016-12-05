package jp.leopanda.htmlEditHelper.parts;

/**
 * タグの種類
 * @author LeoPanda
 *
 */
enum TagName {
  DIV("div"), IMG("img");
  public String text;

  TagName(String text) {
    this.text = text;
  }

}