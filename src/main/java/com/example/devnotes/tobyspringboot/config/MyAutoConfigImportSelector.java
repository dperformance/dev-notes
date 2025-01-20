package com.example.devnotes.tobyspringboot.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {
                "com.example.devnotes.tobyspringboot.config.autoconfig.DispatcherServletConfig",
                "com.example.devnotes.tobyspringboot.config.autoconfig.TomcatWebServerConfig"
        };
    }
}
