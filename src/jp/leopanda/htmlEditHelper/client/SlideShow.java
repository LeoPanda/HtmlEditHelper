package jp.leopanda.htmlEditHelper.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import jp.leopanda.htmlEditHelper.filedParts.TextAreaField;
import jp.leopanda.htmlEditHelper.panelParts.PanelBase;
import jp.leopanda.htmlEditHelper.validate.RequiredValidator;
import jp.leopanda.htmlEditHelper.validate.ValidateBase;
/**
 * ブログ画像をスライドショーにまとめる
 * @author LeoPanda
 *
 */
public class SlideShow extends PanelBase {
	//固定文字列
	private final String HOST_URL = GWT.getHostPageBaseURL();
	//バリデータ
	private RequiredValidator isRequired = new RequiredValidator();
	//フィールド
	private TextAreaField imageHtml 
			= new TextAreaField("imageHtml", "画像ソースHTML:", new ValidateBase[] {isRequired});
	 /*
	  * コンストラクタ
	  */
	public SlideShow() {
		super();
		setFieldMap();
		setPanel();
	}
	/*
	 * フィールドマップの作成
	 */
	private void setFieldMap() {
		fieldMap.add(imageHtml);		
	}
	/*
	 * 入力パネルの構成
	 */
	private void setPanel() {
		this.add(imageHtml);		
	}

	/*
	 * HTMLの生成
	 */
	@Override
	public String getGeneratedHtml() {
		return getImageDivision() + getButtonHtml() + getJSHtml() + getOnLoadHtml();
	}
	/*
	 * イメージ指定部を作成する
	 */
	private String getImageDivision(){
		String html = "<div id=\"slideShowImages\">" + "\n";
		html += getImgTag(imageHtml.getText()) + "</div>" + "\n";
		return html;
	}
	/*
	 * ソースのHTMLから画像表示タグを抽出する
	 */
	private String getImgTag(String source){
		String html = "";
		String regex = "(<a href[^>]*><img[^>]*></a>)";
		RegExp regExp = RegExp.compile(regex,"gm");
		MatchResult matcher = regExp.exec(source);
		while(matcher!=null){
			html += matcher.getGroup(0);
			matcher = regExp.exec(source);
		}
		return html;
	}
	/*
	 * 停止/再開ボタンHTMLを作成する
	 */
	private String getButtonHtml(){
		String html = "<br/><br/><div style=\"text-align: left;\">" + "\n";
		html += "<button id=\"slideShowButton\">" + "\n";
		html += "</button>";
		return html;

	}
	/*
	 * スクリプトタイプ指定部を生成する
	 */
	private String getJSHtml(){
		String html = "<script type=\"text/javascript\" src=\"" + HOST_URL + "js/";
		html += "slideShow.js" + "\" charset=\"UTF-8\"></script>"+ "\n";
		return html;
	}
	/*
	 * ロード後実行部を作成する
	 */
	private String getOnLoadHtml(){
		String html = "<script>" +"\n";
		html += "window.addEventListener('load', slideShow, false);" + "\n";
		html += "</script>" + "\n";
		return html;
	}
	/*
	 * HTML表示後に実行するスクリプト
	 */
	@Override
	public String getExstraHtml() {
		String html = "<script>" +"\n";
		html += "slideShow();" + "\n";
		html += "</script>" + "\n";
		return html;
	}

}
