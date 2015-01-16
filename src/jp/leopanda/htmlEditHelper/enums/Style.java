package jp.leopanda.htmlEditHelper.enums;
/**
 * 共通スタイル名
 * @author LeoPanda
 *
 */
public enum Style {
	WARNING("warning"),
	REMARKS("remarks"),
	REQUIRED("required"),
	GROUP_LABEL("gourplabel"),
	GROUP_FRAME("gourpframe"),
	DATE("date"),
	REGEX("regex"),
	NUMERIC("numeric");
	
	private String name;
	Style(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}
