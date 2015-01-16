package jp.leopanda.htmlEditHelper.client;

import java.util.ArrayList;
import java.util.List;

import jp.leopanda.htmlEditHelper.filedParts.FieldCommon;
import jp.leopanda.htmlEditHelper.filedParts.FieldGroup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * 入力フィールドを可変数化するラッパー
 * @author LeoPanda
 */
public class IncrementalWrapper extends VerticalPanel{
	private List<ClonePanel> panelList = new ArrayList<ClonePanel>();   //可変数パネルのリスト
	private List<FieldCommon> fieldMap;									//呼び出し元のフィールドマップコレクション
	/**
	 * コンストラクタ
	 * @param baseField　可変数化したい入力フィールド
	 */
	public IncrementalWrapper(FieldGroup fieldGroup,List<FieldCommon> fieldMap) {
		this.fieldMap = fieldMap;
		ClonePanel clonePanel = new ClonePanel(fieldGroup);
		this.add(clonePanel);
	}
	/*
	 * フィールドグループを追加する
	 */
	private void addField() {
		int index = panelList.size()-1;
		ClonePanel clonePanel = new ClonePanel(panelList.get(index).getFieldGroup().clone());
		this.add(clonePanel);
	}
	/*
	 * フィールドグループを削除する
	 */
	private void removeField(){
		int index = panelList.size()-1;
		if(index==1){
			panelList.get(index-1).getPlusButton().setVisible(true);			
			panelList.get(index-1).getMinusButton().setVisible(false);
		}
		if(index>1){
			panelList.get(index-1).getPlusButton().setVisible(true);			
			panelList.get(index-1).getMinusButton().setVisible(true);
		}
		int retreaveIndex = panelList.get(index).getRelativeIndex();
		fieldMap.remove(retreaveIndex);
		panelList.remove(index);
		this.remove(index);
	}

	/*
	 * フィールド追加ボタン
	 */
	private class PlusButton extends Button{
		public PlusButton(){
			this.setText("+");
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addField();
			}
		});
		}
	}
	/*
	 * フィールド削除ボタン
	 */
	private class MinusButton extends Button{
		public MinusButton(){
			this.setText("-");
			this.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					removeField();
				}
			});
		}	
	}
	/*
	 * 値のgetter
	 */
	public List<String> getTextList(){
		List<String> valueList = new ArrayList<String>();
		for(ClonePanel clonePanel:panelList){
			valueList.add(fieldMap.get(clonePanel.getRelativeIndex()).getText());
		}
		return valueList;
	}
	/*
	 * 可変数化パネルの定義クラス
	 */
	private class ClonePanel extends HorizontalPanel{
		private int relativeIndex;						//fieldMapへの挿入位置
		private FieldGroup fieldGroup;					//フィールドグループ
		private PlusButton plusButton 	= new PlusButton();	//追加ボタン
		private MinusButton minusButton = new MinusButton();//削除ボタン
		//コンストラクタ
		public ClonePanel(FieldGroup fieldGroup){
			this.fieldGroup = fieldGroup;
			this.add(fieldGroup.getGroupPanel());
			this.add(plusButton);
			this.add(minusButton);
			panelList.add(this);
			fieldMap.add(this.fieldGroup);
			relativeIndex = fieldMap.size()-1;
			int index = panelList.size()-1;
			if(index == 0){
				minusButton.setVisible(false);				
			}else{
				panelList.get(index-1).getPlusButton().setVisible(false);
				panelList.get(index-1).getMinusButton().setVisible(false);
			}
		}
		//relativeIndex getter
		public int getRelativeIndex(){
			return this.relativeIndex;
		}
		//field group getter
		public FieldGroup getFieldGroup() {
			return fieldGroup;
		}
		//plus Button getter
		public PlusButton getPlusButton() {
			return plusButton;
		}
		//minus button getter
		public MinusButton getMinusButton() {
			return minusButton;
		}
	}
}
