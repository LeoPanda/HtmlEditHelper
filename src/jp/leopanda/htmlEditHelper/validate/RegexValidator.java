package jp.leopanda.htmlEditHelper.validate;

import jp.leopanda.htmlEditHelper.enums.Error;

/**
 * パターン入力チェック
 */
public class RegexValidator extends ValidateBase implements Validate {
  private String regex;

  /*
   * コンストラクタ
   */
  public RegexValidator(String regex) {
    super(Error.REGEX);
    this.regex = regex;
  }

  /**
   * 個別エラーメッセージ付きコンストラクタ
   */
  public RegexValidator(String regex, String errMsg) {
    super(Error.REGEX);
    this.regex = regex;
    replaceErrMsg(errMsg);
  }

  @Override
  public boolean validate(String value) {
    if (value.matches(regex)) {
      return true;
    }
    return false;
  }
}
