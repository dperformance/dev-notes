package com.example.devnotes.tobyspringboot.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {

    @Test
    void configuration() {
          /** 일치 하지 않다. */
//        Assertions.assertThat(new Common()).isSameAs(new Common());
//        Common common = new Common();
//        Assertions.assertThat(common).isSameAs(common);

         /** 일치 하지 않다. */
//        MyConfig myConfig = new MyConfig();
//        Bean1 bean1 = myConfig.bean1();//        Bean2 bean2 = myConfig.bean2();
//        Assertions.assertThat(bean1.common).isSameAs(bean2.common);

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Test
    void proxyCommonMethod() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {
        private Common common;

        @Override
        Common common() {
            if (this.common == null) this.common = super.common();

            return this.common;
        }
    }

    @Configuration(proxyBeanMethods = true)
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {
    }

    // bean1 <-- Common 을 의존 (스프링은 싱글톤으로 만들어짐)
    // bean2 <-- Common 을 의존 (스프링은 싱글톤으로 만들어짐)
}
