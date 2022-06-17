package com.rongmei.config;

import com.google.gson.Gson;
import com.rongmei.util.PathUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter msgConverter = new GsonHttpMessageConverter();
        Gson gson = GsonFactory.get();
        msgConverter.setGson(gson);
        converters.add(msgConverter);
    }

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + PathUtil.getStaticPath());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);//可以让URL路径中带小数点 '.' 后面的值不被忽略
    }


 /*   @Override
    public void addCorsMappings(CorsRegistry registry) {
        //本应用的所有方法都会去处理跨域请求
        registry.addMapping("/**")
                //允许远端访问的域名
                .allowedOrigins("http://localhost:8080")
                //允许请求的方法("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedMethods("*")
                //允许请求头
                .allowedHeaders("*");
    }*/


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .maxAge(3600)
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT");

    }

}
