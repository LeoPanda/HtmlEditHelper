package jp.leopanda.htmlEditHelper.parts;

import jp.leopanda.panelFrame.filedParts.EventAction;
import jp.leopanda.panelFrame.filedParts.FieldCommon;
import jp.leopanda.panelFrame.filedParts.FieldGroup;
import jp.leopanda.panelFrame.filedParts.ListBoxField;
import jp.leopanda.panelFrame.filedParts.ListElement;
import jp.leopanda.panelFrame.filedParts.TextBoxField;
import jp.leopanda.panelFrame.validate.NumericValidator;
import jp.leopanda.panelFrame.validate.RegexValidator;
import jp.leopanda.panelFrame.validate.RequiredValidator;
import jp.leopanda.panelFrame.validate.ValidateBase;

/**
 * SyntaxHilighterオプション値の入力パネルを生成するクラス （オプション選択リスト）と（オプション値入力）の ２つの入力フィールドのペア
 * 
 * 
 * @author LeoPanda
 *
 */
public class SyntaxOptionFields extends FieldGroup implements Cloneable {
  // 入力チェックバリデータ
  private RequiredValidator isRequired = new RequiredValidator();
  private NumericValidator isNumeric = new NumericValidator();

  // グループ内フィールド
  private ListBoxField selector;
  private FieldCommon optionValue;

  /*
   * コンストラクタ
   */
  public SyntaxOptionFields() {
    this.selector = new ListBoxField("selector", "オプション:", null, SyntaxOption.values());
    setUpFields();
  }

  /*
   * クローン用コンストラクタ
   */
  private SyntaxOptionFields(ListBoxField option) {
    this.selector = option.clone();
    setUpFields();
  }

  /*
   * オプション選択リストのセットアップ リストから選択されたオプションに応じた入力値フィールドを生成する。
   */
  private void setUpFields() {
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
   * オプション値フィールドのセットアップ
   */
  private void setUpOptionValueField() {
    // 既にオプション値入力フィールドが生成されている場合はそれを除去する
    if (this.getFieldList().size() > 1) {
      this.remove(1);
    }
    switch (SyntaxOption.values()[selector.getSelectedIndex()].getFieldType()) {
    case CHAR:
      setTextOptionField(new ValidateBase[] { isRequired });
      break;
    case NUMERIC:
      setTextOptionField(new ValidateBase[] { isRequired, isNumeric });
      break;
    case NUMLIST:
      setNumListOptionField();
      break;
    case BOOLEANS:
      setBooleanOptionField();
      break;
    default:
      break;
    }
  }

  /*
   * テキスト型オプション値フィールドの作成
   */
  private void setTextOptionField(ValidateBase[] validates) {
    optionValue = new TextBoxField("optionValue", "", validates);
    this.add(optionValue);
  }

  /*
   * Boolean型オプション値フィールドの作成
   */
  private void setBooleanOptionField() {
    optionValue = new ListBoxField("optionValue", "", null, BooleanElement.values());
    this.add(optionValue);
  }

  /*
   * 数値列挙型オプション値フィールドの作成
   */
  private void setNumListOptionField() {
    RegexValidator isNumList = new RegexValidator("\\[[0-9]+[,0-9]+\\]$", "数値をカンマ区切りで入力してください。");
    optionValue = new TextBoxField("optionValue", "", new ValidateBase[] { isRequired,
        isNumList });
    optionValue.setText("[]");
    this.add(optionValue);
  }

  /*
   * 値の取得
   */
  @Override
  public String getText() {
    return "SyntaxHighlighter.defaults['" + selector.getText() + "']="
        + optionValue.getText();
  }

  /*
   * クローンを作成する
   */
  @Override
  public SyntaxOptionFields clone() {
    return new SyntaxOptionFields(this.selector);
  }

  // Boolean型オプションの選択値
  private enum BooleanElement implements ListElement {
    True("true"), Faluse("false");
    private String name;

    BooleanElement(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return this.name;
    }

  }

}
