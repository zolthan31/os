package com.romulo.os.services;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.dto.ClienteDTO;
import com.romulo.os.repositories.ClienteRepository;
import com.romulo.os.services.exception.DataIntegrityViolationException;
import com.romulo.os.services.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        if (repository.findByCPF(objDTO.getCpf()).isPresent()) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }

        Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());

        try {
            return repository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Erro ao salvar: CPF já existe na base de dados!");
        }
    }

    public Cliente upDate(Integer id, @Valid ClienteDTO objDTO) {
        Cliente oldObj = findById(id);

        if (!oldObj.getCpf().equals(objDTO.getCpf())) {
            Optional<Cliente> clienteExistente = findByCPF(objDTO.getCpf());

            if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(id)) {
                throw new DataIntegrityViolationException("Erro ao salvar: CPF já existe na base de dados!");
            }
        }
        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getList().size() > 0){
            throw new DataIntegrityViolationException("Cliente possui Ordens de Serviço, não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private Optional<Cliente> findByCPF(String cpf) {
        return repository.findByCPF(cpf);
    }
}
