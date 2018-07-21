package com.like.zero.spring.beans.factory.xml;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.ConstructorArgument;
import com.like.zero.spring.beans.PropertyValue;
import com.like.zero.spring.beans.factory.BeanDefinitionStoreException;
import com.like.zero.spring.beans.factory.config.RuntimeBeanReference;
import com.like.zero.spring.beans.factory.config.TypedStringValue;
import com.like.zero.spring.beans.factory.support.BeanDefinitionRegistry;
import com.like.zero.spring.beans.factory.support.GenericBeanDefinition;
import com.like.zero.spring.core.io.Resource;
import com.like.zero.spring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by like 2018/6/24
 */
public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE = "scope";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    protected static final Log logger = LogFactory.getLog(XmlBeanDefinitionReader.class);

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    /**
     * 读取xml配置文件
     * <p>
     * 1.使用ClassUtils获取ClassLoader，这里的ClassUtils是直接复制的Spring源码； 2.用ClassLoader将configFile变为InputStream
     * 3.使用SAXReader读取InputStream,将InputStream变为Document 4.解析Document中的结点和属性 5.异常处理，关闭InputStream
     */
    public void loadBeanDefinitions(Resource resource) {
        // 1.使用ClassUtils获取ClassLoader，这里的ClassUtils是直接复制的Spring源码；
//        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        // 2.用ClassLoader将configFile变为InputStream
//        InputStream inputStream = classLoader.getResourceAsStream(configFile);
        InputStream inputStream = null;

        SAXReader saxReader = new SAXReader();
        try {
            inputStream = resource.getInputStream();
            // 3.使用SAXReader读取InputStream,将InputStream变为Document
            Document document = saxReader.read(inputStream);
            // 4.解析Document中的结点和属性
            Element rootElement = document.getRootElement();// 这里得到的根节点指的是<beans>
            Iterator<Element> elementIterator = rootElement.elementIterator();
            while (elementIterator.hasNext()) {
                Element element = elementIterator.next();// 这里取到的是<bean>
                // 解析<bean>中的属性，如id,class等
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                String scope = element.attributeValue(SCOPE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
                if (scope != null) {
                    beanDefinition.setScope(scope);
                }
                parseConstructorArgElements(element, beanDefinition);
                // 解析bean内的标签
                parsePropertyElement(element, beanDefinition);
                beanDefinitionRegistry.registerBeanDefinition(id, beanDefinition);
            }

        } catch (Exception e) {
            throw new BeanDefinitionStoreException(
                    "IOException parsing XML document from " + resource.getDescription(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析bean标签里的constructor-arg
     *
     * @param beanElement
     * @param beanDefinition
     */
    private void parseConstructorArgElements(Element beanElement, BeanDefinition beanDefinition) {
        Iterator<Element> elementIterator = beanElement.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (elementIterator.hasNext()) {
            Element constructorElement = elementIterator.next();// 这里取到的是<constructor-arg>
            parseConstructorArgElement(constructorElement, beanDefinition);
        }
    }

    private void parseConstructorArgElement(Element constructorElement, BeanDefinition beanDefinition) {
        String typeAttr = constructorElement.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = constructorElement.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(constructorElement, beanDefinition, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }
        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }


    public void parsePropertyElement(Element beanElement, BeanDefinition beanDefinition) {
        Iterator<Element> elementIterator = beanElement.elementIterator(PROPERTY_ELEMENT);
        while (elementIterator.hasNext()) {
            Element propertyElement = elementIterator.next();// 这里取到的是<property>
            // 解析<property>中的属性，如name,ref,value等
            String propertyName = propertyElement.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag property must have a 'name' attribute");
                return;
            }

            Object value = parsePropertyValue(propertyElement, beanDefinition, propertyName);
            PropertyValue propertyValue = new PropertyValue(propertyName, value);
            beanDefinition.getPropertyValues().add(propertyValue);
        }

    }

    public Object parsePropertyValue(Element element, BeanDefinition beanDefinition,
                                     String propertyName) {
        String elementName =
                (propertyName != null) ? "<property> element for property '" + propertyName + "'"
                        : "<constructor-arg> element";

        boolean hasRefAttribute = element.attribute(REF_ATTRIBUTE) != null;
        boolean hasValueAttribute = element.attribute(VALUE_ATTRIBUTE) != null;

        if (hasRefAttribute) {
            String refName = element.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasLength(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(refName);
            return runtimeBeanReference;
        } else if (hasValueAttribute) {
            TypedStringValue typedStringValue = new TypedStringValue(
                    element.attributeValue(VALUE_ATTRIBUTE));
            return typedStringValue;
        } else {
            throw new RuntimeException(elementName + "must specify a ref or value");
        }

    }
}
