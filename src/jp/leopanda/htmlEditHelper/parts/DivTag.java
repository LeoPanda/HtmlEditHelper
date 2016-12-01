package jp.leopanda.htmlEditHelper.parts;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

/**
 * PhotoLayout用
 * 生成するDivの諸元を保持する
 * 
 * @author LeoPanda
 *
 */
public class DivTag {
  private float width = 0;
  private float height = 0;
  private float translateX = 0;
  private float translateY = 0;
  private float marginBottom = 0;
  private float marginLeft = 0;
  private String imgSrc = "";
  private float aspectRatio = 0;
  private ErrorListener errorListener;

  /**
   * コンストラクタ
   * @param col float 整形後のカラム位置
   * @param width float DIVのwidthエレメント値
   * @param height float DIVのheightエレメント値
   * @param marginLeft DIVのmarginLeftエレメント値
   */
  public DivTag(float width, float height, float marginLeft) {
    this.width = width;
    this.height = height;
    this.marginLeft = marginLeft;
  }
  /**
   * コンストラクタ
   * @param divElement HTMLソース中のDIVタグのエレメント
   * @param textAlign imgなしDIVの場合の左マージン
   */
  public DivTag(Element divElement,float textAlign,ErrorListener errorListener){
    this.errorListener = errorListener;
    NodeList<Element> childImgNode = divElement.getElementsByTagName(TagName.Img.text);
    if (childImgNode.getLength() > 0) {
      Element imgElement = childImgNode.getItem(0); 
      this.imgSrc = imgElement.getAttribute(AttributeName.Src.text);
      this.width = getNumericAttribute(imgElement,AttributeName.Width);
      this.height = getNumericAttribute(imgElement,AttributeName.Height);
      this.aspectRatio = (float)width/(float)height;
    }else{
      this.marginLeft = textAlign;      
    }
  }
  //エレメントから数値属性を取得する
  private float getNumericAttribute(Element element,AttributeName attributeName){
    String attribute =element.getAttribute(attributeName.text);
    String errorText = "<img>の"+attributeName.text;
    if(attribute.isEmpty()){
      errorListener.onError(Error.NO_ATTRIBUTE,errorText);
      return 0;
    }
    float retValue = 0;
    try {
      retValue = Float.valueOf(attribute);
    } catch (NumberFormatException exception) {
      errorListener.onError(Error.NOT_NUMERIC_ATTRIBUTE, errorText);
    }
    return retValue;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public float getTranslateX() {
    return translateX;
  }
  public float getTranslateY() {
    return translateY;
  }

  public float getMarginBottom() {
    return marginBottom;
  }

  public float getMarginLeft() {
    return this.marginLeft;
  }

  public String getImgSrc() {
    return this.imgSrc;
  }
  public float getAspectRatio(){
    return this.aspectRatio;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public void setTranslateX(float translateX) {
    this.translateX = translateX;
  }
  public void setTranslateY(float translateY) {
    this.translateY = translateY;
  }

  public void setMarginBottom(float marginBottom) {
    this.marginBottom = marginBottom;
  }
  public void setAspectRatio(float aspectRatio){
    this.aspectRatio = aspectRatio;
  }
}
