package jp.leopanda.htmlEditHelper.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * イメージリソース
 * 
 * @author LeoPanda
 *
 */
public interface LayoutImage extends ClientBundle {
  LayoutImage INSTANCE = GWT.create(LayoutImage.class);

  ImageResource grid();

  ImageResource lcombine();

  ImageResource rcombine();

}
