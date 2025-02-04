package com.example.devnotes.tobyspringboot.config.autoconfig;

import com.example.devnotes.tobyspringboot.config.ConditionalMyOnClass;
import com.example.devnotes.tobyspringboot.config.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

//@Configuration
//@Conditional(TomcatWebServerConfig.TomcatCondition.class)
@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@EnableMyConfigurationProperties(ServerProperties.class)
//@Import(ServerProperties.class)
public class TomcatWebServerConfig {
    /** 프로퍼티 클래스로 만든다. */
//    @Value("${contextPath:}")
//    String contextPath;
//
//    @Value("${port:8080}")
//    int port;

    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean
//    public ServletWebServerFactory servletWebServerFactory(Environment env) {
    public ServletWebServerFactory servletWebServerFactory(ServerProperties properties) {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//        serverFactory.setContextPath(env.getProperty("contextPath"));
        serverFactory.setContextPath(properties.getContextPath());
        serverFactory.setPort(properties.getPort());
        return serverFactory;
    }



//    static class TomcatCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return ClassUtils.isPresent("org.apache.catalina.startup.Tomcat",
//                    context.getClassLoader());
//        }
//    }
}
