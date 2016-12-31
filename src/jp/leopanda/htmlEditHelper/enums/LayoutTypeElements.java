package jp.leopanda.htmlEditHelper.enums;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

import jp.leopanda.htmlEditHelper.resources.LayoutImage;
import jp.leopanda.panelFrame.filedParts.ListImageElement;

/**
 * 写真レイアウトの配置選択肢
 * @author LeoPanda
 *
 */
public enum LayoutTypeElements implements ListImageElement {
  GRID("グリッド", LayoutImage.INSTANCE.grid()),
  VHCOMBINE("縦＋横列", LayoutImage.INSTANCE.lcombine()),
  HVCOMBINE("横列＋縦", LayoutImage.INSTANCE.rcombine());
  public String text;
  public Image image;

  LayoutTypeElements(String text, ImageResource imgResource) {
    this.text = text;
    this.image = new Image(imgResource);
  }

  @Override
  public String getName() {
    return this.text;
  }

  public Image getImage() {
    return this.image;
  }
}
