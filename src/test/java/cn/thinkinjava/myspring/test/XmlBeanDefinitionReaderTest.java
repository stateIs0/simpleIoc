package cn.thinkinjava.myspring.test;

import cn.thinkinjava.myspring.BeanDefinition;
import cn.thinkinjava.myspring.factory.AutowireBeanFactory;
import cn.thinkinjava.myspring.factory.BeanFactory;
import cn.thinkinjava.myspring.io.ResourceLoader;
import cn.thinkinjava.myspring.xml.XmlBeanDefinitionReader;
import java.util.Map;
import org.junit.Test;

public class XmlBeanDefinitionReaderTest {

  @Test
  public void test() throws Exception {
    // 创建一个XML解析器，携带一个资源加载器
    XmlBeanDefinitionReader xml = new XmlBeanDefinitionReader(new ResourceLoader());
    // 解析该文件
    xml.readerXML("myspring.xml");

    // 创建一个自动注入bean工厂
    BeanFactory beanfactory = new AutowireBeanFactory();
    // 循环xml中的所有bean
    for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xml.getRegistry().entrySet()) {
      // 将XML容器中的bean注册到bean工厂
      beanfactory
          .registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
    }
    // 获取持有另一个bean对象的bean（也是从容器中取得的）
    ReferenceBean hello = (ReferenceBean) beanfactory.getBean("referenceBean");

    // 调用对象方法
    hello.say();


  }
}
