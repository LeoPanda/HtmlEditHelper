package jp.leopanda.htmlEditHelper.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * テキストリソース
 * 
 * @author LeoPanda
 *
 */
public interface TextResources extends ClientBundle {
  TextResources INSTANCE = GWT.create(TextResources.class);

  @Source("previewHead.txt")
  TextResource previewHead();

}
