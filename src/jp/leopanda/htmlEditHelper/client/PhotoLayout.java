package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import jp.leopanda.htmlEditHelper.parts.LayoutCalc;
import jp.leopanda.htmlEditHelper.parts.LayoutType;
import jp.leopanda.htmlEditHelper.parts.LayoutVariables;
import jp.leopanda.htmlEditHelper.parts.Error;
import jp.leopanda.htmlEditHelper.parts.ErrorListener;
import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;
import jp.leopanda.panelFrame.filedParts.ListBoxField;
import jp.leopanda.panelFrame.filedParts.EventAction;
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
  private final int DIV_ALIGN = 0;
  private final String DIV_CLASS = "pc-layout";
  private LayoutCalc layoutCalc = new LayoutCalc(DIV_CLASS);
  // 画像インデント単位
  private final String PIC_UNIT = "px";
  // 入力フィールドの初期値
  private final int intMaxCols = 2;
  private final int intXIndent = 0;
  private final int intYIndent = 0;
  private final int layoutWidth = 640; // レイアウト領域の幅
  private LayoutVariables variables = new LayoutVariables();
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private IntegerValidator isInteger = new IntegerValidator();
  // フィールド
  private TextAreaField sourceHtml = new TextAreaField("sourceHtml", "ソースHTML:",
      new ValidateBase[] { isRequired });
  private ListBoxField layoutSelector = new ListBoxField("layoutSelector", "レイアウトの種類", null,
      LayoutType.values());
  private TextBoxField maxCols = new TextBoxField("indent", "カラム数",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField xIndent = new TextBoxField("indent", "横インデント(" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });
  private TextBoxField yIndent = new TextBoxField("indent", "縦インデント(" + PIC_UNIT + ")",
      new ValidateBase[] { isRequired, isInteger });

  /**
   * コンストラクタ
   */
  public PhotoLayout() {
    super();
    setFieldMap();
    setPanel();
    // layoutSeleorの変更アクションリスナーの追加
    layoutSelector.addEventListener(new EventAction() {
      @Override
      public void onValueChange() {
        onLayoutTypeChange();
      }
    });
    // 初期値のセット
    maxCols.setText(String.valueOf(intMaxCols));
    xIndent.setText(String.valueOf(intXIndent));
    yIndent.setText(String.valueOf(intYIndent));
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(sourceHtml);
    fieldMap.add(layoutSelector);
    fieldMap.add(maxCols);
    fieldMap.add(xIndent);
    fieldMap.add(yIndent);
  }

  /**
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(sourceHtml);
    this.add(layoutSelector);
    this.add(maxCols);
    this.add(xIndent);
    this.add(yIndent);
  }

  // layoutType変更時の処理
  private void onLayoutTypeChange() {
    if (layoutSelector.getText() == LayoutType.Grid.getName()) {
      maxCols.setVisible(true);
    } else {
      maxCols.setText(String.valueOf(intMaxCols));
      maxCols.setVisible(false);
    }
  }

  /**
   * HTMLの生成
   */
  @Override
  public String getGeneratedHtml() {
    layoutCalc.setErrorListener(new ErrorListener() {
      @Override
      //レイアウト生成時のエラーハンドリング
      public void onError(Error error, String text) {
        sourceHtml.setErr(jp.leopanda.panelFrame.enums.Error.NOMESSAGE, error.text + text);
        sourceHtml.popError();
      }
    });
    layoutCalc.setVariables(
        variables.setVariables(LayoutType.values()[layoutSelector.getSelectedIndex()],
            Integer.valueOf(xIndent.getText()),
            Integer.valueOf(yIndent.getText()),
            DIV_ALIGN, Integer.valueOf(maxCols.getText()), layoutWidth));
    return layoutCalc.genLayoutedSource(
        replaceBr(setImgSourceSize(compressTextDiv(omitWhiteDiv(omitBr(sourceHtml.getText()))))));
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

  // 画像の表示ソースを変更する
  private String setImgSourceSize(String source) {
    String regex = "(<img.*src=.*)(/s[0-9]{3}/)(.*/>)";
    RegExp regExp = RegExp.compile(regex, "gm");
    regExp.exec(source);
    source = regExp.replace(source, "$1/s640/$3");
    return source;
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
