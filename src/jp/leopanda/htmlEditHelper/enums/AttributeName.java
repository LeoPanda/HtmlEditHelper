package jp.leopanda.htmlEditHelper.enums;

/**
 * タグの属性名
 * @author LeoPanda
 *
 */
public enum AttributeName {
  WIDTH("width"), HEIGHT("height"), MARGINLEFT("margin-left"), MARGINBOTTOM("margin-bottom"),
  SRC("src"),STYLE("style"),CLASS("class");
  public String text;

  AttributeName(String text) {
    this.text = text;
  }
}