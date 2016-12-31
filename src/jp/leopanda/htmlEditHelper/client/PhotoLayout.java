package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.aria.client.OrientationValue;

import jp.leopanda.htmlEditHelper.parts.LayoutCalc;
import jp.leopanda.htmlEditHelper.parts.LayoutVariables;
import jp.leopanda.htmlEditHelper.parts.SourceFormatter;
import jp.leopanda.htmlEditHelper.enums.Error;
import jp.leopanda.htmlEditHelper.enums.LayoutTypeElements;
import jp.leopanda.htmlEditHelper.parts.ErrorListener;
import jp.leopanda.htmlEditHelper.parts.FunctionPanelBase;
import jp.leopanda.panelFrame.filedParts.EventAction;
import jp.leopanda.panelFrame.filedParts.ImageRadioButtonField;
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
  private static final String TARGET_CLASS = "separator";
  // 生成するDIVタグの諸元
  private static final int DIV_ALIGN = 0;
  private static final String DIV_CLASS = "pc-layout";
  // 画像インデント単位
  private static final String PIC_UNIT = "px";
  // 入力フィールドの初期値
  private final int intMaxCols = 2;
  private final int intXIndent = 0;
  private final int intYIndent = 0;
  private final int layoutWidth = 640; // レイアウト領域の幅
  // バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private IntegerValidator isInteger = new IntegerValidator();
  // フィールド
  private TextAreaField sourceHtml = new TextAreaField("ソースHTML", new ValidateBase[] {isRequired});
  private ImageRadioButtonField layoutSelector = new ImageRadioButtonField("レイアウトの種類",
      "layoutSelector", LayoutTypeElements.values(), OrientationValue.HORIZONTAL);

  private TextBoxField maxCols =
      new TextBoxField("カラム数", new ValidateBase[] {isRequired, isInteger});
  private TextBoxField indentX =
      new TextBoxField("横インデント(" + PIC_UNIT + ")", new ValidateBase[] {isRequired, isInteger});
  private TextBoxField indentY =
      new TextBoxField("縦インデント(" + PIC_UNIT + ")", new ValidateBase[] {isRequired, isInteger});

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
    indentX.setText(String.valueOf(intXIndent));
    indentY.setText(String.valueOf(intYIndent));
  }

  /*
   * フィールドマップの作成
   */
  private void setFieldMap() {
    fieldMap.add(sourceHtml);
    fieldMap.add(layoutSelector);
    fieldMap.add(maxCols);
    fieldMap.add(indentX);
    fieldMap.add(indentY);
  }

  /**
   * 入力パネルの構成
   */
  private void setPanel() {
    this.add(sourceHtml);
    this.add(layoutSelector);
    this.add(maxCols);
    this.add(indentX);
    this.add(indentY);
  }


  /**
   * 写真レイアウト後のHTMLの生成
   */
  @Override
  public String getGeneratedHtml() {
    LayoutCalc layoutCalc = new LayoutCalc(DIV_CLASS,
        new LayoutVariables().setVariables(
            LayoutTypeElements.values()[layoutSelector.getSelectedIndex()],
            Integer.valueOf(indentX.getText()), Integer.valueOf(indentY.getText()), DIV_ALIGN,
            Integer.valueOf(maxCols.getText()), layoutWidth));
    layoutCalc.setErrorListener(new ErrorListener() {
      @Override
      // レイアウト生成時のエラーハンドリング
      public void onError(Error error, String text) {
        sourceHtml.setErr(jp.leopanda.panelFrame.enums.Error.NOMESSAGE, error.text + text);
        sourceHtml.popError();
      }
    });
    return layoutCalc.genLayoutedSource(getFormattedSource(sourceHtml.getText()));
  }

  // 入力HTMLソースを整形する
  private String getFormattedSource(String source) {
    SourceFormatter formatter = new SourceFormatter(source);
    formatter.omitBr().omitWhiteDiv(TARGET_CLASS).compressTextDiv(TARGET_CLASS)
        .setImgSourceSize(640).replaceTab2Br();
    return formatter.getFormattedSource();
  }

  // layoutType変更時の処理
  private void onLayoutTypeChange() {
    if (layoutSelector.getText() == LayoutTypeElements.GRID.getName()) {
      maxCols.setVisible(true);
    } else {
      maxCols.setText(String.valueOf(intMaxCols));
      maxCols.setVisible(false);
    }
  }

}

