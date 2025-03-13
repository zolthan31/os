package com.romulo.os.config;

import com.romulo.os.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class ConfigDev {

    @Autowired
    public DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Bean
    public boolean instanciaDB() {
        if (ddlAuto.equals("update")) {
            this.dbService.instanciaDB();
        }
        return false;
    }
}
