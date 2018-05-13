package de.hska.vislab.dbm;

import de.hska.vislab.dbm.config.MongoConfiguration;
import java.io.Serializable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MongoConfigurationManager implements Serializable {

  private static AnnotationConfigApplicationContext instance;

  private MongoConfigurationManager() {}

  public static AnnotationConfigApplicationContext getInstance() {
    if (MongoConfigurationManager.instance == null) {
      MongoConfigurationManager.instance =
          new AnnotationConfigApplicationContext(MongoConfiguration.class);
    }
    return MongoConfigurationManager.instance;
  }
}
