package cn.thinkinjava.myspring.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 资源URL
 */
public class ResourceUrl implements Resource {

  /**
   * 类库URL
   */
  private final URL url;

  /**
   * 需要一个类库URL
   */
  public ResourceUrl(URL url) {
    this.url = url;
  }

  /**
   * 从URL中获取输入流
   */
  @Override
  public InputStream getInputstream() throws Exception {
    URLConnection urlConnection = url.openConnection();
    urlConnection.connect();
    return urlConnection.getInputStream();

  }

}
