package br.com.moser.mosermoney;

import br.com.moser.mosermoney.security.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class MosermoneyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MosermoneyApiApplication.class, args);
    }

    @PostConstruct
    public void initIt() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
    }

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
