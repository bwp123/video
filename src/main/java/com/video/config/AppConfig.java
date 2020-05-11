package com.video.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.io.File;

/**
 * @Author biwenpan
 * @create 2020/4/28 8:36
 * @description 配置图片视频虚拟化访问路径
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Value("${mogu.picture.upload}")
    private String uploadUrl;
    @Value("${mogu.picture.mapping}")
    private String mappingUrl;

    /**
     * 得到的访问路径为当前项目端口路径 + /upload/image/     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 说明：增加虚拟路径
        */
        registry.addResourceHandler(mappingUrl + "/**").addResourceLocations("file:" + uploadUrl + File.separator);
        super.addResourceHandlers(registry);
    }

}
