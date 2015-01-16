package jp.leopanda.htmlEditHelper.filedParts;


import jp.leopanda.htmlEditHelper.validate.ValidateBase;

import com.google.gwt.user.client.ui.TextBox;
/**
 * テキストボックス入力フィールドを生成するクラス
 * @author LeoPanda
 *
 */
public class TextBoxField extends FieldBase<TextBox> implements FieldCommon{
	/**
	 * コンストラクタ
	 * @param styleName スタイル名
	 * @param label ラベルに表示する文字列
	 * @param validates バリデータの配列
	 */
	public TextBoxField(String styleName,String label,ValidateBase[] validates){
		super(styleName,label,validates,new TextBox());
	}
	/**
	 * クローン用コンストラクタ
	 */
	private TextBoxField(TextBoxField textBoxField){
		super(textBoxField,new TextBox());
	}
	/*
	 * フィールド値のgetter
	 */
	@Override
	protected String getFieldValue() {
		return inputter_.getValue();
	}
	/*
	 * 初期値のsetter
	 */
	@Override
	public void setText(String text){
		inputter_.setText(text);
	}
	/*
	 * フィールドの正当性をチェックする
	 */
	@Override
	public boolean validate(){
		return super.validate();
	}
	/*
	 * フィールドのクローンを作成する
	 */
	@Override
	public TextBoxField clone() {
		return new TextBoxField(this);
	}
}

