package com.hatoberry.api.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 全てのエンドポイントに適用
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")  // 許可するオリジン
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // 許可するHTTPメソッド
                .allowedHeaders("*")  // 全てのヘッダーを許可
                .allowCredentials(true)  // Cookie等の送信を許可
                .maxAge(3600);  // プリフライトリクエストのキャッシュ時間（秒）
    }
}
