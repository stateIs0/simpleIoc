package cn.thinkinjava.myspring;

/**
 * bean 的定义
 *
 * @author stateis0
 */
public class BeanDefinition {

  /**
   * bean
   */
  private Object bean;

  /**
   * bean 的 CLass 对象
   */
  private Class beanClass;

  /**
   * bean 的类全限定名称
   */
  private String ClassName;

  /**
   * 类的属性集合
   */
  private PropertyValues propertyValues = new PropertyValues();

  /**
   * 获取bean对象
   */
  public Object getBean() {
    return this.bean;
  }

  /**
   * 设置bean的对象
   */
  public void setBean(Object bean) {
    this.bean = bean;
  }

  /**
   * 获取bean的Class对象
   */
  public Class getBeanclass() {
    return this.beanClass;
  }

  /**
   * 通过设置类名称反射生成Class对象
   */
  public void setClassname(String name) {
    this.ClassName = name;
    try {
      this.beanClass = Class.forName(name);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取bean的属性集合
   */
  public PropertyValues getPropertyValues() {
    return this.propertyValues;
  }

  /**
   * 设置bean的属性
   */
  public void setPropertyValues(PropertyValues pv) {
    this.propertyValues = pv;
  }

}
