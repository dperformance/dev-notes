package com.example.devnotes.tobyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class TobySpringApplication {
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }


    public static void main(String[] args) {
        SpringApplication.run(TobySpringApplication.class, args);
    }

        /** */
        // 가장 기본적인 스프링 컨테이너 생성, 빈 수동 등록이 가능하다.
        // 1. 수동 등록 컨테이너 생성
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){
//            @Override
//            protected void onRefresh() {
//                super.onRefresh();
//                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
//                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
//                dispatcherServlet.setApplicationContext(this);
//
//                WebServer webServer = serverFactory.getWebServer(servletContext ->
//                        servletContext.addServlet("dispatcherServlet", dispatcherServlet)
//                                .addMapping("/*"));
//                webServer.start();
//            }
//        };
//
//        // 2. 빈 등록
//        // 2.1. 컨트롤러와 서비스 빈 등록
//        applicationContext.register(TobySpringApplication.class);
//        // 3. 컨테이너 초기화
//        applicationContext.refresh();


        /** Bean 을 이용하여 분리 하기 전 설정 */
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){
//            @Override
//            protected void onRefresh() {
//                super.onRefresh();
//                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//                WebServer webServer = serverFactory.getWebServer(servletContext ->
//                        servletContext.addServlet("dispatcherServlet",
//                                        new DispatcherServlet(this))
//                                .addMapping("/*"));
//                webServer.start();
//            }
//        };


        /** */
        // 가장 기본적인 스프링 컨테이너 생성, 빈 수동 등록이 가능하다.
        // 1. 수동 등록 컨테이너 생성
//        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
//        // 2. 빈 등록
//        applicationContext.registerBean(HelloController.class);
//        applicationContext.registerBean(SimpleHelloService.class);
//        // 3. 컨테이너 초기화
//        applicationContext.refresh();
//        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//        WebServer webServer = serverFactory.getWebServer(servletContext -> {
//            servletContext.addServlet("dispatcherServlet",
//                    new DispatcherServlet(applicationContext)
//            ).addMapping("/*");
//        });


        /** DispatcherServlet 으로 전환을 위해 아래 코드는 더 이상 사용하지 않음. */
//        WebServer webServer = serverFactory.getWebServer(servletContext -> {
//            servletContext.addServlet("frontcontroller",new HttpServlet() {
//                @Override
//                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                    // 인증, 보안, 다국어, 공통 기능
//                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
//                        String name = req.getParameter("name");
//
//                        // 4. 빈 사용
//                        HelloController helloController = applicationContext.getBean(HelloController.class);
//                        String result = helloController.hello(name); // 매핑
//
//                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
//                        resp.getWriter().println(result); // 바인딩
//                    } else {
//                        resp.setStatus(HttpStatus.NOT_FOUND.value());
//                    }
//
//                }
//            }).addMapping("/*");
//        });
}

