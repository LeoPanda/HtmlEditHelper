package jp.leopanda.htmlEditHelper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtmlEditHelper implements EntryPoint {

  @Override
  /*
   * メインモジュール
   */
  public void onModuleLoad() {
    RootPanel.get().add(new MainPanel());
  }
}
