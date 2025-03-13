package com.romulo.os.services;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.domain.enuns.Prioridade;
import com.romulo.os.domain.enuns.Status;
import com.romulo.os.repositories.ClienteRepository;
import com.romulo.os.repositories.OSRepository;
import com.romulo.os.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private OSRepository osRepository;

    public void instanciaDB() {

        Tecnico t1 = new Tecnico(null,"Romulo Lima", "404.703.510-63", "(91)98487-4775");
        Cliente c1 = new Cliente(null, "Fulano Silva", "934.794.020-89", "(11)98888-5555");
        OS os1 = new OS(null, Prioridade.ALTA, "Teste de banco de dados", Status.ABERTO, t1, c1);

        t1.getList().add(os1);
        c1.getList().add(os1);

        tecnicoRepository.saveAll(Arrays.asList(t1));
        clienteRepository.saveAll(Arrays.asList(c1));
        osRepository.saveAll(Arrays.asList(os1));
    }
}
