package com.romulo.os.services;

import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.dto.TecnicoDTO;
import com.romulo.os.repositories.TecnicoRepository;
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
 public class TecnicoServiceTest {

    @Mock
    private TecnicoRepository repository;

    @InjectMocks
    private TecnicoService service;

    private Tecnico tecnico;
    private TecnicoDTO tecnicoDTO;

    @BeforeEach
    public void setUp() {
       tecnico = new Tecnico(1,"João Neves", "123.456.789-00", "91999999999");
       tecnicoDTO = new TecnicoDTO();
    }

    @Test
    public void findById() {
        when(repository.findById(1)).thenReturn(Optional.of(tecnico));

        Tecnico result = service.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("João Neves", result.getNome());

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
        when(repository.findAll()).thenReturn(Collections.singletonList(tecnico));

        List<Tecnico> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João Neves", result.get(0).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void create() {

        when(repository.findByCPF(tecnicoDTO.getCpf())).thenReturn(Optional.empty());
        when(repository.save(any(Tecnico.class))).thenReturn(tecnico);

        Tecnico result = service.create(tecnicoDTO);

        assertNotNull(result);
        assertEquals("João Neves", result.getNome());

        verify(repository, times(1)).findByCPF(tecnicoDTO.getCpf());
        verify(repository, times(1)).save(any(Tecnico.class));
    }

    @Test
    public void upDate() {
        Tecnico updatedTecnico = new Tecnico(1, "Carlos Silva", "123.456.789-00", "91988888888");

        when(repository.findById(1)).thenReturn(Optional.of(tecnico));
        when(repository.findByCPF(tecnicoDTO.getCpf())).thenReturn(Optional.empty());
        when(repository.save(any(Tecnico.class))).thenReturn(updatedTecnico);

        Tecnico result = service.upDate(1, tecnicoDTO);

        assertNotNull(result);
        assertEquals("Carlos Silva", result.getNome());
        assertEquals("123.456.789-00", result.getCpf());
        assertEquals("91988888888", result.getTelefone());

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).findByCPF(tecnicoDTO.getCpf());
        verify(repository, times(1)).save(any(Tecnico.class));
    }

    @Test
    public void delete() {
        when(repository.findById(1)).thenReturn(Optional.of(tecnico));
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.delete(1));

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void upDateWithDuplicateCPF() {
        Tecnico existingTecnico = new Tecnico(2, "Outro Técnico", "987.654.321-00", "91977777777");
        when(repository.findById(1)).thenReturn(Optional.of(tecnico));
        when(repository.findByCPF(tecnicoDTO.getCpf())).thenReturn(Optional.of(existingTecnico));

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.upDate(1, tecnicoDTO);
        });

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).findByCPF(tecnicoDTO.getCpf());
        verify(repository, never()).save(any(Tecnico.class));
    }

    @Test
    public void createWithDuplicateCPF() {
        when(repository.findByCPF(tecnicoDTO.getCpf())).thenReturn(Optional.of(tecnico));

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.create(tecnicoDTO);
        });
        verify(repository, times(1)).findByCPF(tecnicoDTO.getCpf());
        verify(repository, never()).save(any(Tecnico.class));
    }

    @Test
    public void deleteWithOrders() {
        Tecnico tecnicoWithOrders = new Tecnico(1, "João Neves", "123.456.789-00", "91999999999");

        OS ordemDeServico = new OS();
        tecnicoWithOrders.getList().add(ordemDeServico);

        when(repository.findById(1)).thenReturn(Optional.of(tecnicoWithOrders));

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.delete(1);
        });

        verify(repository, times(1)).findById(1);
        verify(repository, never()).deleteById(anyInt());
    }
}