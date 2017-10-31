package comunicationcenter.common.configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import reactor.Environment;

/**
 * Created by simon.calabrese on 31/10/2017.
 */
@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan({"comunicationcenter.service", "comunicationcenter.common", "comunicationcenter.controller" })
public abstract class BaseApplication {

    @Autowired
    private ObjectMapper jacksonMapper;

    @Bean
    public Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @PostConstruct
    public void initApp() {
        jacksonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }
}
