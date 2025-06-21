package backend.receipt.global.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "naver.ocr")
@Getter
public class NaverOcrProperties {
    private String invokeUrl;
    private String secretKey;

    public void setInvokeUrl(String invokeUrl) {
        this.invokeUrl = invokeUrl;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
