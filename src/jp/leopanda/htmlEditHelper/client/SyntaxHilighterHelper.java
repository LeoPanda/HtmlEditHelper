package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.core.client.GWT;

import jp.leopanda.htmlEditHelper.enums.SyntaxBrush;
import jp.leopanda.htmlEditHelper.enums.SyntaxCss;
import jp.leopanda.htmlEditHelper.enums.SyntaxOptionFields;
import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;
import jp.leopanda.panelFrame.filedParts.ListBoxField;
import jp.leopanda.panelFrame.filedParts.TextAreaField;
import jp.leopanda.panelFrame.panelParts.IncrementalWrapper;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * Syntaxhilighterの埋め込みHTML生成用 入力パネル
 * 
 * @author LeoPanda
 *
 */
public class SyntaxHilighterHelper extends FunctionPanelBase {
  // 固定文字列
  private static final String HOST_URL = GWT.getHostPageBaseURL();
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  // フィールド
  private ListBoxField brush = new ListBoxField("brush", "ソースの種類:", null, SyntaxBrush.values());
  private ListBoxField css = new ListBoxField("css", "修飾のスタイル:", null, SyntaxCss.values());
  private TextAreaField src = new TextAreaField("src", "ソース:", new ValidateBase[] { isRequired });
  private SyntaxOptionFields optionFields = new SyntaxOptionFields();
  private IncrementalWrapper optionPanel; // 可変パネル化ラッパー

  /**
   * コンストラクタ
   */
  public SyntaxHilighterHelper() {
    super();
    setFieldMap();
    setPanel();
    // 初期値
    brush.setText(SyntaxBrush.JAVA.getName());
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(brush);
    fieldMap.add(css);
    optionPanel = new IncrementalWrapper(optionFields, fieldMap);
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
    return getJavaScriptUrl() + getCssHtml() + getSrcHtml() + getExecHtml();
  }

  /*
   * スクリプトタイプ指定部を生成する
   */
  private String getJavaScriptUrl() {
    String jsTagFront = "<script type=\"text/javascript\" src=\"" + HOST_URL + "scripts/";
    String jsTagBack = "\"></script>" + "\n";
    String html = jsTagFront + "shCore.js" + jsTagBack;
    html += jsTagFront + SyntaxBrush.values()[brush.getSelectedIndex()].getKeyword() + jsTagBack;
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
    if (css.getText() != SyntaxCss.DEFAULT.getName()) {
      html += linkTagFront + "shTheme" + SyntaxCss.values()[css.getSelectedIndex()].getKeyword()
          + linkTagBack;
    }
    return html;
  }

  /*
   * ソース部を生成する
   */
  private String getSrcHtml() {
    String html = "<pre class=\"brush: "
        + brush.getText() + "\" id=\"code\">" + "\n";
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
