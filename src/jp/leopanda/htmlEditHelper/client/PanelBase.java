package jp.leopanda.htmlEditHelper.client;

import java.util.ArrayList;
import java.util.List;

import jp.leopanda.htmlEditHelper.filedParts.FieldCommon;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 入力パネルの共通機能定義
 * @author LeoPanda
 *
 */
public abstract class PanelBase extends VerticalPanel{
	private final String DEFAULT_STYLE = "PanelBase";	//デフォルトのCSSスタイル名
	/*
	 * 入力フィールドコレクションマップ
	 * @param FieldAttr 	入力フィールド定義
	 * @param FieldBase 入力フィールドオブジェクト
	 */
	protected List<FieldCommon> fieldMap = new ArrayList<FieldCommon>();
	/*
	 * デフォルトのコンストラクタ
	 */
	protected PanelBase(){
		this.addStyleName(DEFAULT_STYLE);
	}
	/*
	 * 入力チェック
	 * 複合チェックが必要な場合は実装クラスで上書きしてください。
	 */	
	public boolean validateFields(){
		for(FieldCommon field:fieldMap){
			 if(!field.validate()){
				 field.popError();
				 return false;
			 }
		}
		return true;
	}
	/*
	 * HTMLを生成する
	 */
	public abstract String getGeneratedHtml();
	/*
	 * 開いたwindowに実行スクリプトなどの追加HTMLがある場合は
	 * ここへ記述する
	 */
	public abstract String getExstraHtml();

}