package jp.leopanda.htmlEditHelper.enums;
/**
 * 項目エラー定義
 */
public enum Error {
	NOTHING("エラーはありません。"),
	REQUIRED("必須入力項目です。"),
	NUMERIC("数値を入力してください。"),
	DATE("日付を入力してください。"),
	REGEX("指定された入力パターンと一致しません。"),
	GROUP("フィールドのグループ内にエラーがあります。"),
	LEAST_ONE("いづれかを入力してください。");
	
	private String msg;
	Error(String msg){
		this.msg = msg;
	}
	public String getMsg(){
		return msg;
	}
}
