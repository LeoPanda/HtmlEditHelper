package jp.leopanda.htmlEditHelper.enums;

import jp.leopanda.panelFrame.filedParts.ListElement;

/**
 * SyntaxHighlighter用言語セットリスト
 * @author LeoPanda
 *
 */
public enum SyntaxBrush implements ListElement {
  AS3("as3", "shBrushAS3.js"),
  BASH("bash", "shBrushBash.js"),
  COLDFUSION("coldfusion", "shBrushColdFusion.js"),
  C("c", "shBrushCpp.js"),
  CSHARP("c-sharp", "shBrushCSharp.js"),
  CSS("Css", "shBrushCss.js"),
  DELHI("delphi", "shBrushDelphi.js"),
  DIFF("diff", "shBrushDiff.js"),
  ERLANG("erlang", "shBrushErlang.js"),
  GROOVY("groovy", "shBrushGroovy.js"),
  JAVA("java", "shBrushJava.js"),
  JAVAFX("javafx", "shBrushJavaFX.js"),
  JAVASCRIPT("javascript", "shBrushJScript.js"),
  PERL("perl", "shBrushPerl.js"),
  PHP("php", "shBrushPhp.js"),
  TEXT("text", "shBrushPlain.js"),
  POWERSHELL("powershell", "shBrushPowerShell.js"),
  PYTHON("python", "shBrushPython.js"),
  RUBY("ruby", "shBrushRuby.js"),
  SASS("Sass", "shBrushSass.js"),
  SCALA("scala", "shBrushScala.js"),
  SQL("sql", "shBrushSql.js"),
  VB("vb", "shBrushVb.js"),
  XML("xml", "shBrushXml.js");

  private String name;
  private String keyword;

  SyntaxBrush(String name, String keyword) {
    this.name = name;
    this.keyword = keyword;
  }

  @Override
  public String getName() {
    return this.name;
  }

  public String getKeyword() {
    return this.keyword;
  }

}
