package de.hska.vislab.dbm.config;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Administrator on 22.03.2017.
 */
@Configuration
@EnableMongoRepositories(basePackages = "de.hska.vislab.dbm.repositories")
public class MongoConfiguration extends AbstractMongoConfiguration {

  @Override
  public String getDatabaseName() {
    return "vislab";
  }

  @Override
  @Bean
  public MongoClient mongoClient() {
    return new MongoClient("mongodb://mongodb", 27017);
  }

  @Bean
  public MongoDbFactory mongoDbFactory() {
    return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    MongoTemplate mongoTemplate = super.mongoTemplate();
    mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
    return mongoTemplate;
  }

  @Bean
  @Override
  public MappingMongoConverter mappingMongoConverter() throws Exception {
    MappingMongoConverter mongoConverter = super.mappingMongoConverter();
    //this is my customization
    mongoConverter.setMapKeyDotReplacement(",");
    return mongoConverter;
  }
}
