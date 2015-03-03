package jp.leopanda.htmlEditHelper.validate;

import jp.leopanda.htmlEditHelper.enums.Error;

public interface Validate {
  boolean validate(String value);

  Error getError();

  String getErrMsg();

}
