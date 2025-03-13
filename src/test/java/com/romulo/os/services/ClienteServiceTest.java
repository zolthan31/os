package com.romulo.os.services;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.dto.ClienteDTO;
import com.romulo.os.repositories.ClienteRepository;
import com.romulo.os.services.exception.DataIntegrityViolationException;
import com.romulo.os.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente(1, "Neves João", "123.456.789-00", "91999999999");
        clienteDTO = new ClienteDTO();
    }

    @Test
    public void findById() {
        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente result = service.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Neves João", result.getNome());

        verify(repository, times(1)).findById(1);
    }

    @Test
    public void findByIdNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            service.findById(1);
        });

        verify(repository, times(1)).findById(1);
    }

    @Test
    public void findAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(cliente));

        List<Cliente> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Neves João", result.get(0).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void create() {

        when(repository.findByCPF(clienteDTO.getCpf())).thenReturn(Optional.empty());
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = service.create(clienteDTO);

        assertNotNull(result);
        assertEquals("Neves João", result.getNome());

        verify(repository, times(1)).findByCPF(clienteDTO.getCpf());
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void upDate() {
        Cliente updatedCliente = new Cliente(1, "Carlos Silva", "123.456.789-00", "91988888888");

        when(repository.findById(1)).thenReturn(Optional.of(cliente));
        when(repository.findByCPF(clienteDTO.getCpf())).thenReturn(Optional.empty());
        when(repository.save(any(Cliente.class))).thenReturn(updatedCliente);

        Cliente result = service.upDate(1, clienteDTO);

        assertNotNull(result);
        assertEquals("Carlos Silva", result.getNome());
        assertEquals("123.456.789-00", result.getCpf());
        assertEquals("91988888888", result.getTelefone());

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).findByCPF(clienteDTO.getCpf());
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void delete() {
        when(repository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.delete(1));

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void upDateWithDuplicateCPF() {
        Cliente existingCliente = new Cliente(2, "Outro Cliente", "987.654.321-00", "91977777777");
        when(repository.findById(1)).thenReturn(Optional.of(cliente));
        when(repository.findByCPF(clienteDTO.getCpf())).thenReturn(Optional.of(existingCliente));

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.upDate(1, clienteDTO);
        });

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).findByCPF(clienteDTO.getCpf());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    public void createWithDuplicateCPF() {
        when(repository.findByCPF(clienteDTO.getCpf())).thenReturn(Optional.of(cliente));

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.create(clienteDTO);
        });
        verify(repository, times(1)).findByCPF(clienteDTO.getCpf());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    public void deleteWithOrders() {
        Cliente clienteWithOrders = new Cliente(1, "Neves João", "123.456.789-00", "91999999999");

        OS ordemDeServico = new OS();
        clienteWithOrders.getList().add(ordemDeServico);

        when(repository.findById(1)).thenReturn(Optional.of(clienteWithOrders));

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.delete(1);
        });

        verify(repository, times(1)).findById(1);
        verify(repository, never()).deleteById(anyInt());
    }

}