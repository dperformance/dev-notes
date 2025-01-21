package com.example.devnotes.tobyspringboot.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);

        List<String> autoConfigs = new ArrayList<>();

//        for (String candidate : ImportCandidates.load(MyAutoConfiguration.class, classLoader)) {
//            autoConfigs.add(candidate);
//        }

        /**
         * load에 들어가보면
         * META-INF/ spring/ full-qualified-annotation-name. imports
         * 경로에 .imports 파일 내에 선언된 파일을 읽어온다고 되어 있다.
         */
        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

        return autoConfigs.toArray(String[]::new);
//        return autoConfigs.toArray(new String[0]);

//        return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);
//        return new String[] {
//                "com.example.devnotes.tobyspringboot.config.autoconfig.DispatcherServletConfig",
//                "com.example.devnotes.tobyspringboot.config.autoconfig.TomcatWebServerConfig"

//        };
    }
}
