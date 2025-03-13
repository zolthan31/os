package com.romulo.os;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.domain.enuns.Prioridade;
import com.romulo.os.domain.enuns.Status;
import com.romulo.os.repositories.ClienteRepository;
import com.romulo.os.repositories.OSRepository;
import com.romulo.os.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


@SpringBootApplication
public class OsApplication {

	public static void main(String[] args) {

		SpringApplication.run(OsApplication.class, args);
	}
}
