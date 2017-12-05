package cn.thinkinjava.myspring.factory;

import cn.thinkinjava.myspring.BeanDefinition;
import java.util.HashMap;

/**
 * 一个抽象类， 实现了 bean 的方法，包含一个map，用于存储bean 的名字和bean的定义
 *
 * @author stateis0
 */
public abstract class AbstractBeanFactory implements BeanFactory {

  /**
   * 容器
   */
  private HashMap<String, BeanDefinition> map = new HashMap<>();

  /**
   * 根据bean的名称获取bean， 如果没有，则抛出异常 如果有， 则从bean定义对象获取bean实例
   */
  @Override
  public Object getBean(String name) throws Exception {
    BeanDefinition beandefinition = map.get(name);
    if (beandefinition == null) {
      throw new IllegalArgumentException("No bean named " + name + " is defined");
    }
    Object bean = beandefinition.getBean();
    if (bean == null) {
      bean = doCreate(beandefinition);
    }
    return bean;
  }

  /**
   * 注册 bean定义 的抽象方法实现，这是一个模板方法， 调用子类方法doCreate，
   */
  @Override
  public void registerBeanDefinition(String name, BeanDefinition beandefinition) throws Exception {
    Object bean = doCreate(beandefinition);
    beandefinition.setBean(bean);
    map.put(name, beandefinition);
  }

  /**
   * 减少一个bean
   */
  abstract Object doCreate(BeanDefinition beandefinition) throws Exception;
}
