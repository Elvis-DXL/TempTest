package interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/24 16:51
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthWhiteListConfig implements Serializable {
    private List<String> whiteList = new ArrayList<>();
}