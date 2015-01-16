/**
 * 入力フィールド部品パッケージ
 */
package jp.leopanda.htmlEditHelper.filedParts;

import java.util.Map;

import jp.leopanda.htmlEditHelper.validate.ValidateBase;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

/**
 * リストボックス入力フィールドを作成するクラス
 * @author LeoPanda
 *
 */
public class ListBoxField extends FieldBase<ListBox> implements FieldCommon{
	private Map<String,String> valueList_; //選択値のリストマップ
	private EventAction actionListener_;
	public void addEventListener(EventAction actionListener){
		this.actionListener_ = actionListener;
	}
	/**
	 * コンストラクタ
	 * @param styleName スタイル名
	 * @param label ラベルに表示する文字列
	 * @param valueList リストボックスに表示する選択リスト
	 * @param validates バリデータの配列
	 */
	public ListBoxField(String styleName,String label,
				ValidateBase[] validates,Map<String,String> valueList) {
		super(styleName,label,validates,new ListBox());
		inputter_.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
						actionListener_.onValueChange();
					}
		});
		this.valueList_ = valueList;
		setListBox(this.valueList_);
	}
	/**
	 * クローン用コンストラクタ
	 * @param valueList
	 */
	private ListBoxField(ListBoxField original){
		super(original,new ListBox());
		inputter_.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
						actionListener_.onValueChange();
					}
		});
		this.valueList_ = original.valueList_;
		this.valueList_.remove(original.getFieldKey());//オリジナルで選択されている項目は除く
		setListBox(valueList_);
	}
	/*
	 * リストボックスに選択リストをセットする
	 */
	public void setListBox(Map<String,String> valueList) {
		for(Map.Entry<String, String> value:valueList.entrySet()){
			inputter_.addItem(value.getKey(), value.getValue());
		}
	}

	/* 選択された値を返す
	 * @see jp.leopanda.htmlEditHelper.filedParts.FieldBase#getFieldValue()
	 */
	@Override
	protected String getFieldValue() {
		return inputter_.getValue(inputter_.getSelectedIndex());
	}
	 /*
	  * 選択されたキー値を返す
	  */
	public String getFieldKey(){
		return inputter_.getItemText(inputter_.getSelectedIndex());
	}
	
	/* 初期選択をキー値でセット
	 * @see jp.leopanda.htmlEditHelper.filedParts.FieldBase#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		for (int i = 0; i < inputter_.getItemCount(); i++) {
			if(inputter_.getItemText(i) == text){
				inputter_.setSelectedIndex(i);
			}
		}
	}
	/* 正当性のチェック
	 */
	@Override
	public boolean validate() {
		return super.validate();
	}
	@Override
	public ListBoxField clone() {
		return new ListBoxField(this);
	}
}
