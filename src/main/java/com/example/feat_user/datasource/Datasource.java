package com.example.feat_user.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;

public class Datasource {
    private final MongoDatabaseFactory mongo;

    @Autowired
    public  Datasource(MongoDatabaseFactory mongo){
        this.mongo = mongo;
    }

}
