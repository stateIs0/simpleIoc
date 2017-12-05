package cn.thinkinjava.myspring;

/**
 * bean的引用
 *
 * @author stateis0
 */
public class BeanReference {

  /**
   * bean名称
   */
  private String name;
  /**
   * bean 对象
   */
  private Object bean;

  /**
   * 构造器， 必须包含一个bean名称
   */
  public BeanReference(String name) {
    this.name = name;
  }

  /**
   * 设置bean名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取bean名称
   */
  public String getName() {
    return this.name;
  }

  /**
   * 设置bean 对象
   */
  public void setBean(Object bean) {
    this.bean = bean;
  }

  /**
   * 获取bean对象
   */
  public Object getBean() {
    return this.bean;
  }
}
