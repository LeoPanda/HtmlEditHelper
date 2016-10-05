package jp.leopanda.htmlEditHelper.common;

import java.util.HashMap;
import java.util.Map;

import jp.leopanda.panelFrame.filedParts.EventAction;
import jp.leopanda.panelFrame.filedParts.FieldGroup;
import jp.leopanda.panelFrame.filedParts.ListBoxField;
import jp.leopanda.panelFrame.filedParts.TextBoxField;
import jp.leopanda.panelFrame.validate.NumericValidator;
import jp.leopanda.panelFrame.validate.RegexValidator;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * SyntaxHilighterオプション値の入力パネルを生成するクラス （オプション選択リスト）と（オプション値入力）の ２つの入力フィールドのペア
 * 
 * @author LeoPanda
 *
 */
public class OptionGroup extends FieldGroup implements Cloneable {
  // 共通バリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private NumericValidator isNumeric = new NumericValidator();

  // オプション値フィールドの種類
  private enum FieldType {
    CHAR(1), NUMERIC(2), NUMLIST(3), BOOLEANS(4);
    private int val;
    private FieldType(int val) {
      this.val = val;
    }
    public String getVal() {
      return String.valueOf(val);
    }
  }

  // オプション選択リストフィールド
  private ListBoxField selector;

  /*
   * コンストラクタ
   */
  public OptionGroup() {
    this.selector = new ListBoxField("selector", "オプション:", null, setSelectorList());
    setUpSelector();
  }

  /*
   * クローン用コンストラクタ
   */
  private OptionGroup(ListBoxField option) {
    this.selector = option.clone();
    setUpSelector();
  }

  /*
   * オプション選択リストのセットアップ
   * リストから選択されたオプションに応じた入力値フィールドを生成する。
   */
  private void setUpSelector() {
    this.add(selector);
    selector.addEventListener(new EventAction() {
      @Override
      public void onValueChange() {
        setUpOptionValueField();
      }
    });
    setUpOptionValueField();
  }

  /*
   * オプション選択リストの選択値を用意する
   */
  private Map<String, String> setSelectorList() {
    Map<String, String> valueList = new HashMap<String, String>();
    valueList.put("auto-links", FieldType.BOOLEANS.getVal());
    valueList.put("class-name", FieldType.CHAR.getVal());
    valueList.put("collapse", FieldType.BOOLEANS.getVal());
    valueList.put("first-line", FieldType.NUMERIC.getVal());
    valueList.put("gutter", FieldType.BOOLEANS.getVal());
    valueList.put("highlight", FieldType.NUMLIST.getVal());
    valueList.put("html-script", FieldType.BOOLEANS.getVal());
    valueList.put("smart-tabs", FieldType.BOOLEANS.getVal());
    valueList.put("tab-size", FieldType.NUMERIC.getVal());
    valueList.put("toolbar", FieldType.BOOLEANS.getVal());
    return valueList;
  }

  /*
   * オプション値フィールドのセットアップ
   */
  private void setUpOptionValueField() {
    // 既にオプション値入力フィールドが生成されている場合はそれを除去する
    if (this.getFieldList().size() > 1) {
      this.remove(1);
    }
    // オプション選択に対応したオプション値入力フィールドの生成
    String selected = selector.getText();
    if (selected.equals(FieldType.CHAR.getVal())) {
      setOptionTextField(new ValidateBase[] { isRequired });
    } else if (selected.equals(FieldType.NUMERIC.getVal())) {
      setOptionTextField(new ValidateBase[] { isRequired, isNumeric });
    } else if (selected.equals(FieldType.NUMLIST.getVal())) {
      setOptionNumListField();
    } else if (selected.equals(FieldType.BOOLEANS.getVal())) {
      setOptionListField();
    }
  }

  /*
   * テキスト型オプション値フィールドの作成
   */
  private void setOptionTextField(ValidateBase[] validates) {
    TextBoxField optionValue = new TextBoxField("optionValue", "", validates);
    this.add(optionValue);
  }

  /*
   * 2値リスト型オプション値フィールドの作成
   */
  private void setOptionListField() {
    Map<String, String> valueList = new HashMap<String, String>();
    valueList.put("true", "true");
    valueList.put("false", "false");
    ListBoxField optionValue = new ListBoxField("optionValue", "", null, valueList);
    this.add(optionValue);
  }

  /*
   * 数値列挙型オプション値フィールドの作成
   */
  private void setOptionNumListField() {
    RegexValidator isNumList = new RegexValidator("\\[[0-9]+[,0-9]+\\]$", "数値をカンマ区切りで入力してください。");
    TextBoxField optionValue = new TextBoxField("optionValue", "", new ValidateBase[] { isRequired,
        isNumList });
    optionValue.setText("[]");
    this.add(optionValue);
  }

  /*
   * 値の取得
   */
  @Override
  public String getText() {
    return "SyntaxHighlighter.defaults['" + selector.getFieldKey() + "']="
        + this.getField(1).getText();
  }

  /*
   * クローンを作成する
   */
  @Override
  public OptionGroup clone() {
    return new OptionGroup(this.selector);
  }
}
