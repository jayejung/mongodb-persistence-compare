package com.kakao.at.ticketdev.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {
	@Bean
	public MongoClient mongoClient() {
		//String conString = "mongodb://ticketdev:ticketdev@10.202.4.201:27017/springDataTestDb";
		String conString = "mongodb://127.0.0.1";
		ConnectionString connectionString = new ConnectionString(conString);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();
		return MongoClients.create(mongoClientSettings);
	}

	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory() {
		MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient(), "springDataTestDb");
		return mongoDatabaseFactory;
	}

	@Bean(name = "mongoTestDbTemplate")
	public MongoTemplate mongoTestDbTemplate() throws Exception {
		return new MongoTemplate(mongoDatabaseFactory(), null);
	}
}
