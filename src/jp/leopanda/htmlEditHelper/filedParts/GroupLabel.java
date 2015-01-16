package jp.leopanda.htmlEditHelper.filedParts;

import java.util.List;

import jp.leopanda.htmlEditHelper.enums.Style;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * ウィジェットをグループにまとめてラベルを貼る
 * 	List<Widget> widgetGroup = new ArrayList<Widget>();
 * のようにリストを定義してグループにまとめたいウィジェットを追加したうえで
 * setLabel メソッドを使ってラベルを貼ってください。
 * 
 * @author LeoPanda
 *
 */
public class GroupLabel<T extends CellPanel> extends VerticalPanel {
	private Label groupLabel;
	private T PanelFrame; //ウィジェットを展開する方向によって実装するパネルを選んでください。
	/**
	 * コンストラクタ
	 * @param labelName
	 */
	public GroupLabel(T panelFrame,String labelName) {
		 this.PanelFrame = panelFrame;
		 makeLabel(labelName);
	}
	/**
	 * コンストラクタ　カスタムスタイル名指定用
	 * @param labelName
	 * @param customStyleName
	 */
	 public GroupLabel(String labelName,String customStyleName){
		makeLabel(labelName);
		groupLabel.addStyleName(customStyleName);
		this.addStyleName(customStyleName);
	}
	/*
	 * ラベルを生成する
	 */
	private void makeLabel(String labelName) {
		groupLabel = new Label(labelName);
		groupLabel.addStyleName(Style.GROUP_LABEL.getName());
		this.addStyleName(Style.GROUP_FRAME.getName());
		this.add(groupLabel);
	}
	/*
	 * グループ化されたウィジェットにラベルを貼る
	 */
	public VerticalPanel setLabel(List<Widget> widgets){
		for (Widget widget : widgets) {
			PanelFrame.add(widget);
		}
		this.add(PanelFrame);
		return this;
	}
}
