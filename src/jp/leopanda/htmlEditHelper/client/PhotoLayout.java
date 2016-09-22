package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import jp.leopanda.panelFrame.filedParts.TextAreaField;
import jp.leopanda.panelFrame.filedParts.TextBoxField;
import jp.leopanda.panelFrame.validate.IntegerValidator;
import jp.leopanda.panelFrame.validate.NumericValidator;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * ブログ画像をスライドショーにまとめる
 * 
 * @author LeoPanda
 *
 */
public class PhotoLayout extends FunctionPanelBase {
  // 変更対象のDIVタグ諸元
  private final String TARGET_CLASS = "separator";

  // 生成するDIVタグの諸元
  private final int DIV_WIDTH = 320;
  private final int DIV_HEIGHT = 213;
  private final int DIV_ALIGN = -15;
  private final int INT_X_INDENT = 5;
  private final int INT_Y_INDENT = 10;
  private final String DIV_CLASS = "pc-layout";
  // テキストの字下げ幅
  private final double INT_TEXT_INDENT = 1.5;
  // 画像インデント単位
  private final String PIC_UNIT = "px";
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private IntegerValidator isInteger = new IntegerValidator();
  private NumericValidator isNumeric = new NumericValidator();
  // フィールド
  private TextAreaField layoutHtml = new TextAreaField("layoutHtml", "ソースHTML:",
      new ValidateBase[] { isRequired });
  private TextBoxField xIndent = new TextBoxField("indent", "横インデント(" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField yIndent = new TextBoxField("indent", "縦インデント(" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField textIndent = new TextBoxField("indent", "テキストの字下げ文字数(em)",
      new ValidateBase[] { isRequired, isNumeric });

  /**
   * コンストラクタ
   */
  public PhotoLayout() {
    super();
    setFieldMap();
    setPanel();
    // 初期値のセット
    xIndent.setText(String.valueOf(INT_X_INDENT));
    yIndent.setText(String.valueOf(INT_Y_INDENT));
    textIndent.setText(String.valueOf(INT_TEXT_INDENT));
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(layoutHtml);
    fieldMap.add(xIndent);
    fieldMap.add(yIndent);
    fieldMap.add(textIndent);
  }

  /**
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(layoutHtml);
    this.add(xIndent);
    this.add(yIndent);
    this.add(textIndent);
  }

  /**
   * HTMLの生成
   */
  @Override
  public String getGeneratedHtml() {
    String source = omitBr(layoutHtml.getText());
    source = omitWhiteDiv(source);
    source = compressTextDiv(source);
    source = replaceAllDiv(source);
    source = setTextDivIndent(source);
    source = replaceBr(source);
    return source;
  }

  // 対象のDIVとDIVの間にある<br />を削除
  private String omitBr(String source) {
    String regex = "</div>[\t\n]*((<br />)[\t\n]*)*<div";
    return source.replaceAll(regex, "</div><div");
  }

  // 空白の対象DIVを削除
  private String omitWhiteDiv(String source) {
    String regex = getDivRegex(TARGET_CLASS) + "([\t\n]*((<br />)[\t\n]*)*</div>)";
    return source.replaceAll(regex, "");
  }

  // ソースのHTMLから対象DIVを検索し、スタイルを変換する
  private String replaceAllDiv(String source) {
    RegExp regExp = RegExp.compile(getDivRegex(TARGET_CLASS), "gm");
    MatchResult matcher = regExp.exec(source);
    int index = 0;
    while (matcher != null) {
      source = replaceEachDiv(source, index);
      matcher = regExp.exec(source);
      index++;
    }
    return source;
  }

  // 連続する複数行のテキストタグを１つのタグに集約する（間のDIVをタブ文字に置き換える）
  private String compressTextDiv(String source) {
    String regex = getDivRegexSnipet(TARGET_CLASS) + "\">" + "([^<]*)(</div>)";
    regex = regex + "[\n\t ]*" + regex;
    RegExp regExp = RegExp.compile(regex, "gm");
    MatchResult matcher = regExp.exec(source);
    while (matcher != null) {
      source = regExp.replace(source, "$1\">$2\t$5$6");
      matcher = regExp.exec(source);
    }
    return source;
  }

  // 文字だけで構成されるDIVを字下げする
  private String setTextDivIndent(String source) {
    String regex = getDivRegexSnipet(DIV_CLASS) + "(\">)" + "([^<]*)(</div>)";
    RegExp regExp = RegExp.compile(regex, "gm");
    return regExp.replace(source, "$1margin-left:" + textIndent.getText() + "em;$2$3$4");
  }

  // タブ文字を＜BR＞タグに置き換える
  private String replaceBr(String source) {
    return source.replaceAll("\t", "<br />");
  }

  // ソースのHTMLから対象DIVの一行を変換する
  private String replaceEachDiv(String source, int index) {
    return source.replaceFirst(getDivRegex(TARGET_CLASS), getDivTag(index));
  }

  // 指定classのDIVタグ正規表現パターンを得る
  private String getDivRegex(String claz) {
    return "(<div *class=\"" + claz + "\" [^>]*>)";
  }

  // 指定classDIVタグから最後の1文字を除いた正規表現パターンを得る
  private String getDivRegexSnipet(String claz) {
    String source = getDivRegex(claz);
    return source.substring(0, source.length() - 2) + ")";
  }

  // 変更後のDIVタグを生成する
  private String getDivTag(int index) {
    return "<div " + "class=\"" + DIV_CLASS + "\" " + "style=\"" + getDivStyle(index) + "\">";
  }

  // DIVタグのstyleを生成する
  private String getDivStyle(int index) {
    return "clear: both; text-align: left;" + getDivWidth() + getDivHeight() + getDivTrans(index)
        + getDivMarginBottom(index);
  }

  // DIVタグの横幅を設定する
  private String getDivWidth() {
    return "width:" + DIV_WIDTH + PIC_UNIT + ";";
  }

  // DIVタグの縦幅を設定する
  private String getDivHeight() {
    return "height:" + DIV_HEIGHT + PIC_UNIT + ";";
  }

  // DIVタグの移動位置を設定する
  private String getDivTrans(int index) {
    return "transform: translate3d(" + getDivCol(index) + "," + getDivRow(index) + ",0" + PIC_UNIT
        + ");";
  }

  private String getDivMarginBottom(int index) {
    String margin = "margin-bottom:" + getDivRow(index) + ";";
    return (index % 2) > 0 ? margin : "";
  }

  // DIVタグ横位置の生成
  private String getDivCol(int index) {
    int col = index % 2;
    return String.valueOf(col * (DIV_WIDTH + Integer.valueOf(xIndent.getText())) + DIV_ALIGN)
        + PIC_UNIT;
  }

  // DIVタグ縦位置の生成
  private String getDivRow(int index) {
    int row = -1 * (index % 2);
    int indent = index > 1 ? Integer.valueOf(yIndent.getText()) : 0;
    return String.valueOf(row * DIV_HEIGHT + indent) + PIC_UNIT;
  }

  /**
   * 追加HTMLの生成
   */
  @Override
  public String getExstraHtml() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

}
