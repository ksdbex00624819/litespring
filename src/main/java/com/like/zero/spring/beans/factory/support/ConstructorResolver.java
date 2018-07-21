package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.ConstructorArgument;
import com.like.zero.spring.beans.ConstructorArgument.ValueHolder;
import com.like.zero.spring.beans.SimpleTypeConverter;
import com.like.zero.spring.beans.factory.BeanCreationException;
import com.like.zero.spring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by like
 * 2018/7/21
 */
public class ConstructorResolver {

    protected final Log logger = LogFactory.getLog(getClass());

    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(BeanDefinition beanDefinition) {
        // 需要用来注入的构造方法
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class beanClass = null;
        try {
            beanClass = beanFactory.getBeanClassLoader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(beanDefinition.getID(), "Instantiation of bean failed, can't resolve class", e);
        }

        // 拿到需要注入类的可访问的构造方法
        Constructor<?>[] candidates = beanClass.getConstructors();

        // 需要注入构造方法的参数
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();

        // 用来将变量变为实例
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            // 当前循环的构造方法的所有参数
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            // 判断当前构造方法的参数个数，和需要注入的属性的个数是否匹配，不匹配则循环下一个构造方法
            if (parameterTypes.length != constructorArgument.getArgumentCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];

            boolean result = this.valuesMatchTypes(parameterTypes, constructorArgument.getArgumentValues(), argsToUse,
                    valueResolver, typeConverter);

            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        // 循环完所有的构造方法，还是没有找到可以用来注入的，则报错
        if (constructorToUse == null) {
            throw new BeanCreationException(beanDefinition.getID(), "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanCreationException(beanDefinition.getID(),
                    "can't find a create instance using " + constructorToUse);
        }
    }

    /**
     * 判断传入的构造方法参数是否能和需要注入的属性匹配
     *
     * @param parameterTypes 需要判断的构造方法参数
     * @param valueHolders   需要注入的属性值
     * @param argsToUse      记录经过转换后可以使用的参数
     * @param valueResolver  获取参数对应的真正的值，如果ref了一个对象，则得到那个对象
     * @param typeConverter  用来进行类型的转型，如value是"3"，可能需要转成int
     * @return
     */
    private boolean valuesMatchTypes(Class<?>[] parameterTypes, List<ValueHolder> valueHolders,
                                     Object[] argsToUse, BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter) {
        // 遍历构造方法的所有参数
        for (int i = 0; i < parameterTypes.length; i++) {
            ValueHolder valueHolder = valueHolders.get(i);
            // 获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
            Object orginalValue = valueHolder.getValue();
            try {
                // 获得真正的值
                Object resolvedValue = valueResolver.resolveValueIfNecessary(orginalValue);
                // 如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
                // 如果转型失败，则抛出异常。说明这个构造函数不可用
                resolvedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                // 转型成功，记录下来
                argsToUse[i] = resolvedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }

        return true;
    }
}
