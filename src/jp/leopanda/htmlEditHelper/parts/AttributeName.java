package jp.leopanda.htmlEditHelper.parts;

/**
 * タグの属性名
 * @author LeoPanda
 *
 */
enum AttributeName {
  Width("width"), Height("height"), MarginLeft("margin-left"), MarginBottom("margin-bottom"),
  Src("src"),Style("style"),Class("class");
  public String text;

  AttributeName(String text) {
    this.text = text;
  }
}