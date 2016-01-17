package jp.leopanda.htmlEditHelper.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;

import jp.leopanda.panelFrame.filedParts.ListBoxField;
import jp.leopanda.panelFrame.filedParts.TextAreaField;
import jp.leopanda.panelFrame.panelParts.IncrementalWrapper;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * Syntaxhilighterの埋め込みHTML生成用　入力パネル
 * 
 * @author LeoPanda
 *
 */
public class SyntaxHilighterHelper extends FunctionPanelBase {
  // 固定文字列
  private final String HOST_URL = GWT.getHostPageBaseURL();
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  // フィールド
  private ListBoxField brush = new ListBoxField("brush", "ソースの種類:", null, setBrushList());
  private ListBoxField css = new ListBoxField("css", "修飾のスタイル:", null, setCSSList());
  private TextAreaField src = new TextAreaField("src", "ソース:", new ValidateBase[] { isRequired });
  private OptionGroup optionGroup = new OptionGroup();
  private IncrementalWrapper optionPanel; // 可変パネル化ラッパー

  /**
   * コンストラクタ
   */
  public SyntaxHilighterHelper() {
    super();
    setFieldMap();
    setPanel();
    // 初期値
    brush.setText("java");
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(brush);
    fieldMap.add(css);
    optionPanel = new IncrementalWrapper(optionGroup, fieldMap);
    fieldMap.add(src);
  }

  /*
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(brush);
    this.add(css);
    this.add(optionPanel);
    this.add(src);
  }

  /*
   * Brush選択リストの作成
   */
  private Map<String, String> setBrushList() {
    Map<String, String> valueList = new HashMap<String, String>();
    valueList.put("as3", "shBrushAS3.js");
    valueList.put("bash", "shBrushBash.js");
    valueList.put("coldfusion", "shBrushColdFusion.js");
    valueList.put("c", "shBrushCpp.js");
    valueList.put("c-sharp", "shBrushCSharp.js");
    valueList.put("Css", "shBrushCss.js");
    valueList.put("delphi", "shBrushDelphi.js");
    valueList.put("diff", "shBrushDiff.js");
    valueList.put("erlang", "shBrushErlang.js");
    valueList.put("groovy", "shBrushGroovy.js");
    valueList.put("java", "shBrushJava.js");
    valueList.put("javafx", "shBrushJavaFX.js");
    valueList.put("javascript", "shBrushJScript.js");
    valueList.put("perl", "shBrushPerl.js");
    valueList.put("php", "shBrushPhp.js");
    valueList.put("text", "shBrushPlain.js");
    valueList.put("powershell", "shBrushPowerShell.js");
    valueList.put("python", "shBrushPython.js");
    valueList.put("ruby", "shBrushRuby.js");
    valueList.put("Sass", "shBrushSass.js");
    valueList.put("scala", "shBrushScala.js");
    valueList.put("sql", "shBrushSql.js");
    valueList.put("vb", "shBrushVb.js");
    valueList.put("xml", "shBrushXml.js");
    return valueList;
  }

  /*
   * CSSテーマ選択リストの作成
   */
  private Map<String, String> setCSSList() {
    Map<String, String> valueList = new HashMap<String, String>();
    valueList.put("標準", "Default.css");
    valueList.put("Django", "Django.css");
    valueList.put("Eclipse", "Eclipse.css");
    valueList.put("Emacs", "Emacs.css");
    valueList.put("FadeToGray", "FadeToGray.css");
    valueList.put("MDUltra", "MDUltra.css");
    valueList.put("Midnight", "Midnight.css");
    valueList.put("RDark", "RDark.css");
    return valueList;
  }

  /*
   * 正当性チェック
   */
  @Override
  public boolean validateFields() {
    if (super.validateFields()) {
      return true;
    }
    return false;
  }

  /*
   * HTMLの生成
   */
  @Override
  public String getGeneratedHtml() {
    return getJSHtml() + getCssHtml() + getSrcHtml() + getExecHtml();
  }

  /*
   * スクリプトタイプ指定部を生成する
   */
  private String getJSHtml() {
    String jsTagFront = "<script type=\"text/javascript\" src=\"" + HOST_URL + "scripts/";
    String jsTagBack = "\"></script>" + "\n";
    String html = jsTagFront + "shCore.js" + jsTagBack;
    html += jsTagFront + brush.getText() + jsTagBack;
    return html;
  }

  /*
   * CSS指定部を生成する
   */
  private String getCssHtml() {
    String linkTagFront = "<link href=\"" + HOST_URL + "styles/";
    String linkTagBack = "\" rel=\"stylesheet\" type=\"text/css\" />" + "\n";
    String html = linkTagFront + "shCore.css" + linkTagBack;
    html += linkTagFront + "shThemeDefault.css" + linkTagBack;
    html += linkTagFront + "shTheme" + css.getText() + linkTagBack;
    return html;
  }

  /*
   * ソース部を生成する
   */
  private String getSrcHtml() {
    String html = "<pre class=\"brush: " + brush.getFieldKey() + "\" id=\"code\">" + "\n";
    html += src.getText().replace("<", "&lt").replace(">", "&gt") + "\n";
    html += "</pre>" + "\n";
    return html;
  }

  /*
   * 実行部を生成する
   */
  private String getExecHtml() {
    String html = "<script type=\"text/javascript\">" + "\n";
    html += getExecScript();
    html += "</script>";
    return html;
  }

  /**
   * HTML表示後に実行するスクリプトを生成する
   */
  public String getExecScript() {
    String script = "";
    for (String js : optionPanel.getTextList()) {
      script += js + ";\n";
    }
    script += "var element = document.getElementById('code');" + "\n";
    script += "SyntaxHighlighter.highlight(undefined, element);" + "\n";
    return script;
  }

  @Override
  public String getExstraHtml() {
    return null;
  }
}
