package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import jp.leopanda.htmlEditHelper.common.DivUtil;
import jp.leopanda.htmlEditHelper.common.FunctionPanelBase;
import jp.leopanda.panelFrame.filedParts.TextAreaField;
import jp.leopanda.panelFrame.filedParts.TextBoxField;
import jp.leopanda.panelFrame.validate.IntegerValidator;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * ブログ画像をレイアウト表示に変換する
 * 
 * @author LeoPanda
 *
 */
public class PhotoLayout extends FunctionPanelBase {
  // 変更対象のDIVタグ諸元
  private final String TARGET_CLASS = "separator";
  // 生成するDIVタグの諸元
  private final int DIV_ALIGN = -15;
  private final String DIV_CLASS = "pc-layout";
  private DivUtil divUtil = new DivUtil(DIV_CLASS);
  // 画像インデント単位
  private final String PIC_UNIT = "px";
  // 入力フィールドの初期値
  private final int intMaxCols = 2;
  private final int intXIndent = 5;
  private final int intYIndent = 10;
  private final int intTextAlign = 15;
  private final int intTextWidth = 320;

  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private IntegerValidator isInteger = new IntegerValidator();
  // フィールド
  private TextAreaField sourceHtml = new TextAreaField("sourceHtml", "ソースHTML:",
      new ValidateBase[] { isRequired });
  private TextBoxField maxCols = new TextBoxField("indent", "カラム数",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField xIndent = new TextBoxField("indent", "横インデント(" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField yIndent = new TextBoxField("indent", "縦インデント(" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField textAlign = new TextBoxField("indent", "テキスト字下げ (" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField textWidth = new TextBoxField("indent", "テキスト幅 (" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });

  /**
   * コンストラクタ
   */
  public PhotoLayout() {
    super();
    setFieldMap();
    setPanel();
    // 初期値のセット
    maxCols.setText(String.valueOf(intMaxCols));
    xIndent.setText(String.valueOf(intXIndent));
    yIndent.setText(String.valueOf(intYIndent));
    textAlign.setText(String.valueOf(intTextAlign));
    textWidth.setText(String.valueOf(intTextWidth));
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(sourceHtml);
    fieldMap.add(maxCols);
    fieldMap.add(xIndent);
    fieldMap.add(yIndent);
    fieldMap.add(textAlign);
    fieldMap.add(textWidth);
  }

  /**
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(sourceHtml);
    this.add(maxCols);
    this.add(xIndent);
    this.add(yIndent);
    this.add(textAlign);
    this.add(textWidth);
  }

  /**
   * HTMLの生成
   */
  @Override
  public String getGeneratedHtml() {
    divUtil.setValues(Integer.valueOf(xIndent.getText()), Integer.valueOf(yIndent.getText()),
        DIV_ALIGN, Integer.valueOf(maxCols.getText()), Integer.valueOf(textAlign.getText()),
        Integer.valueOf(textWidth.getText()));
    return divUtil
        .genLayoutDiv(replaceBr(compressTextDiv(omitWhiteDiv(omitBr(sourceHtml.getText())))));
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

  // 連続する複数行のテキストタグを１つのタグに集約する（不要なタグをタブ文字に置き換える）
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

  // タブ文字を＜BR＞タグに置き換える
  private String replaceBr(String source) {
    return source.replaceAll("\t", "<br />");
  }

  // 指定classのDIVタグ正規表現パターンを得る
  private String getDivRegex(String claz) {
    return "(<div *class=\"" + claz + "\" [^>]*>)";
  }

  // 指定classDIVタグから行末の">)"を除いた正規表現パターンを得る
  private String getDivRegexSnipet(String claz) {
    String source = getDivRegex(claz);
    return source.substring(0, source.length() - 2) + ")";
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
