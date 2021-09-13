package com.airfrance.ums.repository;

import com.airfrance.ums.entities.AppConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAppConfigRepository extends MongoRepository<AppConfig,String> {
    public AppConfig findAppConfigByConfigName(String configName);
}
