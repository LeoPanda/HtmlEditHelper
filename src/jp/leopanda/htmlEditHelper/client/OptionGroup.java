package jp.leopanda.htmlEditHelper.client;

import java.util.HashMap;
import java.util.Map;

import jp.leopanda.htmlEditHelper.filedParts.EventAction;
import jp.leopanda.htmlEditHelper.filedParts.FieldGroup;
import jp.leopanda.htmlEditHelper.filedParts.ListBoxField;
import jp.leopanda.htmlEditHelper.filedParts.TextBoxField;
import jp.leopanda.htmlEditHelper.validate.NumericValidator;
import jp.leopanda.htmlEditHelper.validate.RegexValidator;
import jp.leopanda.htmlEditHelper.validate.RequiredValidator;
import jp.leopanda.htmlEditHelper.validate.ValidateBase;
/**
 * SyntaxHilighterオプション値の入力パネルを生成するクラス
 * （オプション選択リスト）と（オプション値入力）の
 * ２つの入力フィールドのペア
 * 
 * @author LeoPanda
 *
 */
public class OptionGroup extends FieldGroup{
	//共通バリデータ
	private RequiredValidator isRequired = new RequiredValidator();
	private NumericValidator isNumeric = new NumericValidator();
	//オプション値フィールドの種類
	private enum FIELD_TYPE{
		chars(1),
		numeric(2),
		numList(3),
		booleans(4);
		
		private int val;
		private FIELD_TYPE(int val) {
			this.val = val;
		}
		public String getVal(){
			return String.valueOf(val);
		}
	}
	//オプション選択リストフィールド
	private ListBoxField selector;
	
	/*
	 * コンストラクタ
	 */
	public OptionGroup() {
		this.selector  = new ListBoxField("selector", "オプション:", null, setSelectorList());
		setUpSelector();
	}
	/*
	 * クローン用コンストラクタ
	 */
	private OptionGroup(ListBoxField option){
		this.selector = option.clone();
		setUpSelector();
	}
	/*
	 * オプション選択リストのセットアップ
	 */
	private void setUpSelector(){
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
	private Map<String,String> setSelectorList(){
		Map<String,String> valueList = new HashMap<String,String>();
		valueList.put("auto-links", FIELD_TYPE.booleans.getVal());
		valueList.put("class-name", FIELD_TYPE.chars.getVal());
		valueList.put("collapse", FIELD_TYPE.booleans.getVal());
		valueList.put("first-line", FIELD_TYPE.numeric.getVal());
		valueList.put("gutter",FIELD_TYPE.booleans.getVal());
		valueList.put("highlight", FIELD_TYPE.numList.getVal());
		valueList.put("html-script", FIELD_TYPE.booleans.getVal());
		valueList.put("smart-tabs", FIELD_TYPE.booleans.getVal());
		valueList.put("tab-size",FIELD_TYPE.numeric.getVal());
		valueList.put("toolbar", FIELD_TYPE.booleans.getVal());
		return valueList;
	}
	/*
	 * オプション値フィールドのセットアップ
	 */
	private void setUpOptionValueField(){
		//既にオプション値入力フィールドが生成されている場合はそれを除去する
		if(this.getFieldList().size()>1){
			this.remove(1);
		}
		//オプション選択に対応したオプション値入力フィールドの生成
		String selected = selector.getText();
		if(selected == FIELD_TYPE.chars.getVal()){
			setOptionTextField(new ValidateBase[] {isRequired});
		}else if(selected == FIELD_TYPE.numeric.getVal()){
			setOptionTextField(new ValidateBase[] {isRequired,isNumeric});			
		}else if(selected == FIELD_TYPE.numList.getVal()){
			setOptionNumListField();
		}else if(selected == FIELD_TYPE.booleans.getVal()){
			setOptionListField();
		}
	}
	/*
	 * テキスト型オプション値フィールドの作成
	 */
	private void setOptionTextField(ValidateBase[] validates){
		TextBoxField optionValue = new TextBoxField("optionValue", "", validates);
		this.add(optionValue);
	}
	/*
	 * 2値リスト型オプション値フィールドの作成
	 */
	private void setOptionListField(){
		Map<String,String> valueList = new HashMap<String,String>();
		valueList.put("true","true");
		valueList.put("false", "false");
		ListBoxField optionValue
				= new ListBoxField("optionValue", "",null, valueList);
		this.add(optionValue);
	}
	/*
	 * 数値列挙型オプション値フィールドの作成
	 */
	private void setOptionNumListField(){
		RegexValidator isNumList = new RegexValidator("\\[[0-9]+[,0-9]+\\]$"
											,"数値をカンマ区切りで入力してください。");
		TextBoxField optionValue 
				= new TextBoxField("optionValue", "",new ValidateBase[] {isRequired,isNumList});
		optionValue.setText("[]");
		this.add(optionValue);
	}
	/*
	 * 値の取得
	 */
	@Override
	public String getText() {
		return "SyntaxHighlighter.defaults['" 
				+ selector.getFieldKey() + "']=" + this.getField(1).getText();
	}
	/*
	 * クローンを作成する
	 */
	@Override
	public OptionGroup clone(){
		return  new OptionGroup(this.selector);
	}
}
