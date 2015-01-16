package jp.leopanda.htmlEditHelper.validate;

import java.util.List;

import jp.leopanda.htmlEditHelper.enums.Error;
import jp.leopanda.htmlEditHelper.filedParts.FieldCommon;

public interface GroupValidate {
	boolean validate(List<FieldCommon> fieldList );
	Error getError();
	String getErrMsg();

}
