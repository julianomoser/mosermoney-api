package br.com.moser.mosermoney.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Juliano Moser
 */
@Getter
@Component
@Validated
@ConfigurationProperties("mosermoney")
public class MoserMoneySecurityProperties {

    private final Security security = new Security();
    private final Application application = new Application();

    @Getter
    @Setter
    public static class Security {
        private boolean enableHttps;
    }

    @Getter
    @Setter
    public static class Application {
        private String url = "http://localhost:8000";
    }
}
