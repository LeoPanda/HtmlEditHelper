package jp.leopanda.htmlEditHelper.parts;

/**
 * DIVエリアの矩形情報を保持する
 * @author LeoPanda
 *
 */
public class RectAngle {
  private int width=0;
  private int length=0;
  private int orgWidth=0;
  private int orgLength=0;
  private int aspectRatio=0;
  
  public int getWidth() {
    return width;
  }
  public int getLength() {
    return length;
  }
  public int getOrgWidth() {
    return orgWidth;
  }
  public int getOrgLength() {
    return orgLength;
  }
  public int getAspectRatio() {
    return aspectRatio;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  public void setLength(int length) {
    this.length = length;
  }
  public void setOrgWidth(int orgWidth) {
    this.orgWidth = orgWidth;
  }
  public void setOrgLength(int orgLength) {
    this.orgLength = orgLength;
  }
  public void setAspectRatio(int aspectRatio) {
    this.aspectRatio = aspectRatio;
  }
  

}
