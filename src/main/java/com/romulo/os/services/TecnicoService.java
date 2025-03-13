package com.romulo.os.services;

import com.romulo.os.domain.Tecnico;
import com.romulo.os.dto.TecnicoDTO;
import com.romulo.os.repositories.TecnicoRepository;
import com.romulo.os.services.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.romulo.os.services.exception.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        if (repository.findByCPF(objDTO.getCpf()).isPresent()) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }

        Tecnico tecnico = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());

        try {
            return repository.save(tecnico);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Erro ao salvar: CPF já existe na base de dados!");
        }
    }

    public Tecnico upDate(Integer id, @Valid TecnicoDTO objDTO) {
        Tecnico oldObj = findById(id);

        if (!oldObj.getCpf().equals(objDTO.getCpf())) {
            Optional<Tecnico> tecnicoExistente = findByCPF(objDTO.getCpf());

            if (tecnicoExistente.isPresent() && !tecnicoExistente.get().getId().equals(id)) {
                throw new DataIntegrityViolationException("Erro ao salvar: CPF já existe na base de dados!");
            }
        }
        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getList().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private Optional<Tecnico> findByCPF(String cpf) {
        return repository.findByCPF(cpf);
    }

}
