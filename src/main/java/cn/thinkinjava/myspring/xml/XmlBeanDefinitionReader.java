package cn.thinkinjava.myspring.xml;

import cn.thinkinjava.myspring.AbstractBeanDefinitionReader;
import cn.thinkinjava.myspring.BeanDefinition;
import cn.thinkinjava.myspring.BeanReference;
import cn.thinkinjava.myspring.PropertyValue;
import cn.thinkinjava.myspring.io.ResourceLoader;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 解析XML文件
 *
 * @author stateis0
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

  /**
   * 构造器，必须包含一个资源加载器
   *
   * @param resourceLoader 资源加载器
   */
  public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
    super(resourceLoader);
  }

  public void readerXML(String location) throws Exception {
    // 创建一个资源加载器
    ResourceLoader resourceloader = new ResourceLoader();
    // 从资源加载器中获取输入流
    InputStream inputstream = resourceloader.getResource(location).getInputstream();
    // 获取文档建造者工厂实例
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    // 工厂创建文档建造者
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    // 文档建造者解析流 返回文档对象
    Document doc = docBuilder.parse(inputstream);
    // 根据给定的文档对象进行解析，并注册到bean容器中
    registerBeanDefinitions(doc);
    // 关闭流
    inputstream.close();
  }

  /**
   * 根据给定的文档对象进行解析，并注册到bean容器中
   *
   * @param doc 文档对象
   */
  private void registerBeanDefinitions(Document doc) {
    // 读取文档的根元素
    Element root = doc.getDocumentElement();
    // 解析元素的根节点及根节点下的所有子节点并添加进注册容器
    parseBeanDefinitions(root);
  }

  /**
   * 解析元素的根节点及根节点下的所有子节点并添加进注册容器
   *
   * @param root XML 文件根节点
   */
  private void parseBeanDefinitions(Element root) {
    // 读取根元素的所有子元素
    NodeList nl = root.getChildNodes();
    // 遍历子元素
    for (int i = 0; i < nl.getLength(); i++) {
      // 获取根元素的给定位置的节点
      Node node = nl.item(i);
      // 类型判断
      if (node instanceof Element) {
        // 强转为父类型元素
        Element ele = (Element) node;
        // 解析给给定的节点，包括name，class，property， name， value，ref
        processBeanDefinition(ele);
      }
    }
  }

  /**
   * 解析给给定的节点，包括name，class，property， name， value，ref
   *
   * @param ele XML 解析元素
   */
  private void processBeanDefinition(Element ele) {
    // 获取给定元素的 name 属性
    String name = ele.getAttribute("name");
    // 获取给定元素的 class 属性
    String className = ele.getAttribute("class");
    // 创建一个bean定义对象
    BeanDefinition beanDefinition = new BeanDefinition();
    // 设置bean 定义对象的 全限定类名
    beanDefinition.setClassname(className);
    // 向 bean 注入配置文件中的成员变量
    addPropertyValues(ele, beanDefinition);
    // 向注册容器 添加bean名称和bean定义
    getRegistry().put(name, beanDefinition);
  }

  /**
   * 添加配置文件中的属性元素到bean定义实例中
   *
   * @param ele 元素
   * @param beandefinition bean定义 对象
   */
  private void addPropertyValues(Element ele, BeanDefinition beandefinition) {
    // 获取给定元素的 property 属性集合
    NodeList propertyNode = ele.getElementsByTagName("property");
    // 循环集合
    for (int i = 0; i < propertyNode.getLength(); i++) {
      // 获取集合中某个给定位置的节点
      Node node = propertyNode.item(i);
      // 类型判断
      if (node instanceof Element) {
        // 将节点向下强转为子元素
        Element propertyEle = (Element) node;
        // 元素对象获取 name 属性
        String name = propertyEle.getAttribute("name");
        // 元素对象获取 value 属性值
        String value = propertyEle.getAttribute("value");
        // 判断value不为空
        if (value != null && value.length() > 0) {
          // 向给定的 “bean定义” 实例中添加该成员变量
          beandefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
        } else {
          // 如果为空，则获取属性ref
          String ref = propertyEle.getAttribute("ref");
          if (ref == null || ref.length() == 0) {
            // 如果属性ref为空，则抛出异常
            throw new IllegalArgumentException(
                "Configuration problem: <property> element for property '"
                    + name + "' must specify a ref or value");
          }
          // 如果不为空，测创建一个 “bean的引用” 实例，构造参数为名称，实例暂时为空
          BeanReference beanRef = new BeanReference(name);
          // 向给定的 “bean定义” 中添加成员变量
          beandefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanRef));
        }
      }
    }
  }

}
