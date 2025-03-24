package com.app.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {return new Class[0];}

    @Override
    protected Class<?>[] getServletConfigClasses() {return new Class[]{DemoAppConfig.class};}

    @Override
    protected String[] getServletMappings() {return new String[]{"/"};}
}
