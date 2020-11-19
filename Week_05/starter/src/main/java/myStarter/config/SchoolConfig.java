package myStarter.config;


import myStarter.entity.School;
import myStarter.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(School.class)
@ConditionalOnClass(AllService.class)
public class SchoolConfig {

    @Autowired
    private School school;


    @Bean
    public AllService allService(){
        AllService allService = new AllService();
        allService.setSchool(school);
        return allService;
    }


}
