package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.panelFrame.filedParts.ListElement;

/**
 * SyntaxHilighter用オプションリスト
 * @author LeoPanda
 *
 */
public enum SyntaxOption implements ListElement {
  AUTOLINK("auto-links", FieldType.BOOLEANS),
  CLASSTEXT("class-text", FieldType.CHAR),
  COLLAPSE("collapse", FieldType.BOOLEANS),
  FIRSTLINE("first-line", FieldType.NUMERIC),
  GUTTER("gutter", FieldType.BOOLEANS),
  HIGHLIGHT("highlight", FieldType.NUMLIST),
  HTMLSCRIPT("html-script", FieldType.BOOLEANS),
  SMARTTABS("smart-tabs", FieldType.BOOLEANS),
  TABSIZE("tab-size", FieldType.NUMERIC),
  TOOLBAR("toolbar", FieldType.BOOLEANS);
  
  private String name;
  private FieldType fieldType;
  SyntaxOption(String name,FieldType fieldType){
    this.name= name;
    this.fieldType = fieldType;
  }
  @Override
  public String getName() {
    return this.name;
  }
  public FieldType getFieldType(){
    return this.fieldType;
  }

}
