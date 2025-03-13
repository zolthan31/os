package com.romulo.os.services;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.domain.enuns.Prioridade;
import com.romulo.os.domain.enuns.Status;
import com.romulo.os.dto.OSDTO;
import com.romulo.os.repositories.OSRepository;
import com.romulo.os.services.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OSService {

    @Autowired
    private OSRepository repository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TecnicoService tecnicoService;

    public OS findById(Integer id) {
        Optional<OS> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
    }

    public List<OS> findAll() {
        return repository.findAll();
    }

    public OS create(@Valid OSDTO obj) {
        return fromDTO(obj);
    }

    public OS upDate(@Valid OSDTO obj) {
        findById(obj.getId());
        return fromDTO(obj);
    }

    OS fromDTO(OSDTO obj) {
        OS newObj = new OS();
        if(obj.getId() != null) {
            newObj.setId(obj.getId());
        }
        newObj.setObservacoes(obj.getObservacoes());
        newObj.setStatus(Status.toEnum(obj.getStatus()));
        newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        newObj.setDataAbertura(obj.getDataAbertura());
        newObj.setDataFechamento(obj.getDataFechamento());

        Cliente cli = (obj.getCliente() != null) ? clienteService.findById(obj.getCliente()) : null;
        Tecnico tec = (obj.getTecnico() != null) ? tecnicoService.findById(obj.getTecnico()) : null;

        newObj.setTecnico(tec);
        newObj.setCliente(cli);
        if(newObj.getStatus().getCod().equals(2)) {
            newObj.setDataFechamento(LocalDateTime.now());
        }
        return repository.save(newObj);
    }
}
