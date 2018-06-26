package com.like.zero.spring.beans.factory.xml;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.factory.BeanDefinitionStoreException;
import com.like.zero.spring.beans.factory.support.BeanDefinitionRegistry;
import com.like.zero.spring.beans.factory.support.GenericBeanDefinition;
import com.like.zero.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by like
 * 2018/6/24
 */
public class XmlBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String SCOPE = "scope";

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    /**
     * 读取xml配置文件
     * <p>
     * 1.使用ClassUtils获取ClassLoader，这里的ClassUtils是直接复制的Spring源码；
     * 2.用ClassLoader将configFile变为InputStream
     * 3.使用SAXReader读取InputStream,将InputStream变为Document
     * 4.解析Document中的结点和属性
     * 5.异常处理，关闭InputStream
     *
     * @param resource
     */
    public void loadBeanDefinition(Resource resource) {
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
                beanDefinitionRegistry.registerBeanDefinition(id, beanDefinition);
//                beanDefinitionMap.put(id, new GenericBeanDefinition(id, beanClassName));
            }

        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
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

}
