package jp.leopanda.htmlEditHelper.validate;

import jp.leopanda.htmlEditHelper.enums.Error;

/**
 * 正当性検査を行うクラスの共通機能
 * @author LeoPanda
 *
 */
public abstract class ValidateBase implements Validate{
	private Error error;			//正当性検査で返されれるエラーの種類
	private String errMsg;			//返されるエラーメッセージ
	/*
	 * デフォルトのコンストラクタ
	 */
	public ValidateBase(Error error) {
		this.error = error;
		this.errMsg = error.getMsg(); //デフォルトのエラーメッセージをセットしておく
	}
	/*
	 * デフォルトのerror値を取得
	 */
	public Error getError(){
		return this.error;
	}
	/*
	 * エラーメッセージを取得
	 */
	public String getErrMsg(){
		return errMsg;
	}
	/*
	 * 正当性チェック
	 */
	public abstract boolean validate(String value);
	/*
	 * エラーメッセージの内部上書き
	 */
	protected void replaceErrMsg(String errMsg){
		this.errMsg = errMsg;
	}

}
