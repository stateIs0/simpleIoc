package cn.thinkinjava.myspring.factory;

import cn.thinkinjava.myspring.BeanDefinition;
import cn.thinkinjava.myspring.PropertyValue;
import cn.thinkinjava.myspring.BeanReference;
import java.lang.reflect.Field;


/**
 * 实现自动注入和递归注入(spring 的标准实现类 DefaultListableBeanFactory 有 1810 行)
 *
 * @author stateis0
 */
public class AutowireBeanFactory extends AbstractBeanFactory {


  /**
   * 根据bean 定义创建实例， 并将实例作为key， bean定义作为value存放，并调用 addPropertyValue 方法 为给定的bean的属性进行注入
   */
  @Override
  protected Object doCreate(BeanDefinition beandefinition) throws Exception {
    Object bean = beandefinition.getBeanclass().newInstance();
    addPropertyValue(bean, beandefinition);
    return bean;
  }

  /**
   * 给定一个bean定义和一个bean实例，为给定的bean中的属性注入实例。
   */
  protected void addPropertyValue(Object bean, BeanDefinition beandefinition) throws Exception {
    // 循环给定 bean 的属性集合
    for (PropertyValue pv : beandefinition.getPropertyValues().getPropertyValues()) {
      // 根据给定属性名称获取 给定的bean中的属性对象
      Field declaredField = bean.getClass().getDeclaredField(pv.getname());
      // 设置属性的访问权限
      declaredField.setAccessible(true);
      // 获取定义的属性中的对象
      Object value = pv.getvalue();
      // 判断这个对象是否是 BeanReference 对象
      if (value instanceof BeanReference) {
        // 将属性对象转为 BeanReference 对象
        BeanReference beanReference = (BeanReference) value;
        // 调用父类的 AbstractBeanFactory 的 getBean 方法，根据bean引用的名称获取实例，此处即是递归
        value = getBean(beanReference.getName());
      }
      // 反射注入bean的属性
      declaredField.set(bean, value);
    }

  }


}
