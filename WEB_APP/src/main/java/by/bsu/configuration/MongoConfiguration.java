package by.bsu.configuration;

import com.mongodb.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfiguration {

    public @Bean MongoDbFactory mongoDbFactory() throws Exception {
        MongoClientURI mongoClient = new MongoClientURI("mongodb://admin:admin@ds119436.mlab.com:19436/db_digital");
        return new SimpleMongoDbFactory(mongoClient);
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
