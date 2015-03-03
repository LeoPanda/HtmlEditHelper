package jp.leopanda.htmlEditHelper.validate;

import jp.leopanda.htmlEditHelper.enums.Error;

/**
 * 数値チェック
 * 
 * @author LeoPanda
 *
 */
public class NumericValidator extends ValidateBase implements Validate {

  public NumericValidator() {
    super(Error.NUMERIC);
  }

  @Override
  public boolean validate(String value) {
    try {
      Double.parseDouble(value);
    } catch (NumberFormatException exception) {
      return false;
    }
    return true;
  }

}
