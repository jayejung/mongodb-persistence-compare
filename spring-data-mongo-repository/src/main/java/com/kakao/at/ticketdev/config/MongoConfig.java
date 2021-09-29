package com.kakao.at.ticketdev.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.kakao.at.ticketdev.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "springRepoTestDb";
    }

    @Override
    public MongoClient mongoClient() {
        //String conString = "mongodb://ticketdev:ticketdev@10.202.4.201:27017/springDataTestDb";
        String conString = "mongodb://127.0.0.1";
        ConnectionString connectionString = new ConnectionString(conString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.kakao.at.ticketdev");
    }
}
