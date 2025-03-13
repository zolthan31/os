package com.romulo.os.config;

import com.romulo.os.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ConfigTeste {

    @Autowired
    public DBService dbService;

    @Bean
    public boolean instanciaDB() {
        this.dbService.instanciaDB();
        return true;
    }
}
