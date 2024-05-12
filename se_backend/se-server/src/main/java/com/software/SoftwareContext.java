package com.software;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SoftwareContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SoftwareContext.context = context;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }

    // Generic method to return a beanClass
    public static <T> T getBean(Class<T> beanClass)
    {
        return context.getBean(beanClass);
    }
}