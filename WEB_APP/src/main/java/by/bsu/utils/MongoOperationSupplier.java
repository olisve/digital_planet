package by.bsu.utils;

import by.bsu.configuration.MongoConfiguration;
import com.google.common.base.Supplier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

public class MongoOperationSupplier implements Supplier<MongoOperations> {

    public MongoOperations get() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        return mongoOperation;
    }
}
