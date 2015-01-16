package jp.leopanda.htmlEditHelper.filedParts;

import java.util.ArrayList;
import java.util.List;

import jp.leopanda.htmlEditHelper.enums.Error;
import jp.leopanda.htmlEditHelper.validate.GroupValidate;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * フィールドをグループ化して格納する
 * @author LeoPanda
 *
 * @param <E>格納するフィールドの種類
 */
public class FieldGroup implements FieldCommon{
	private List<FieldCommon> groupFieldList 
				= new ArrayList<FieldCommon>();  //グループフィールドのコンテナ
	private GroupValidate[] validates = null;	 //Group Validateインスタンスの配列
	private HorizontalPanel groupPanel 
					= new HorizontalPanel();	//グループフィールドを囲うパネル
	private String separator =","; 				//複数フィールド値のセパレータ
	/*
	 * コンストラクタ
	 */
	public FieldGroup() {
	}
	/*
	 * コンストラクタ（スタイル名付き）
	 */
	public FieldGroup(String style){
		this.groupPanel.addStyleName(style);
	}
	/*
	 * グループパネルを返す
	 */
	public CellPanel getGroupPanel(){
		return groupPanel;
	}
	/*
	 * フィールドを返す
	 */
	public FieldCommon getField(int index){
		return groupFieldList.get(index);
	}
	/*
	 * グループパネルにフィールドを追加する
	 */
	public void add(FieldCommon field){
		groupFieldList.add(field);
		groupPanel.add((Widget) field);
	}
	/*
	 * グループパネルからフィールドを削除する
	 */
	public boolean remove(int index){
		groupFieldList.remove(index);
		return groupPanel.remove(index); 
	}
	/*
	 * フィールドリストを取得する
	 */
	public List<FieldCommon> getFieldList(){
		return groupFieldList;
	}
	/*
	 * バリデータのセット
	 */
	public void setValidate(GroupValidate[] validates){
		this.validates = validates;
	}
	/*
	 *正当性チェック
	 *グループ内のフィールドを検査し、
	 *一つでもエラーがあればfalseを返す
	 *グループ化したフィールド間で相互チェックが必要な場合は
	 *FieldGroupをextendsする専用のクラスを作成してvalidate()を上書きしてください。
	 */
	public boolean validate(){
		boolean validate = true;
		for(FieldCommon field:groupFieldList){
			validate = validate & field.validate();
		}
		if(validates == null){
			return validate;
		}
		for(GroupValidate groupValidate:validates){
			validate = validate & groupValidate.validate(groupFieldList);
		}
		if(!validate){
			popError();
		}
		return validate;
	}
	/*
	 * グループのクローンを作成する
	 */
	public FieldGroup clone(){
		FieldGroup fieldGroup = new FieldGroup();
		for(FieldCommon field:groupFieldList){
			fieldGroup.add((FieldCommon) field.clone());
		}
		return fieldGroup;
	}
	/*
	 * グループ化されたフィールドのすべてのtext値を取得する。
	 * それぞれの値はセパレータによって区切られる
	 */
	@Override
	public String getText() {
		String text = null;
		for(FieldCommon field:groupFieldList){
			text += field.getText() + separator;
		}
		return text.length() > 0 ? text.substring(0, text.length()-1):text;
	}
	/*
	 * セパレータを変更する
	 */
	public void setSeparator(String separetor){
		this.separator = separetor;
	}
	/*
	 * 現在のセパレータ値を取得する
	 */
	public String getSeparator(){
		return this.separator;
	}
	/*
	 * グループ化されたフィールドのすべてに同一の値をセットする
	 */
	@Override
	public void setText(String text) {
		for(FieldCommon field:groupFieldList){
			field.setText(text);
		}
	}
	/*
	 * グループ化されたフィールドのすべてにエラースタイルをセットする
	 */
	@Override
	public void setErr(Error err,String errMsg) {
		for(FieldCommon field:groupFieldList){
			field.setErr(err,errMsg);
		}
	}
	/*
	 * error値の取得
	 */
	public Error getErr(){
		for(FieldCommon field:groupFieldList){
			if(field.getErr() != Error.NOTHING){
				return field.getErr();
			}
		}
		return Error.NOTHING;
	}
	/*
	 * エラーメッセージの取得
	 */
	public String getErrMsg(){
		for(FieldCommon field:groupFieldList){
			if(field.getErr() != Error.NOTHING){
				return field.getErrMsg();
			}
		}
		return null;
	}
	/*
	 * エラーの通知
	 */
	public void popError(){
		for(FieldCommon field:groupFieldList){
			if(field.getErr() != Error.NOTHING){
				field.popError();
			}
		}
	}
}

