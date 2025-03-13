package com.romulo.os.services;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.domain.enuns.Prioridade;
import com.romulo.os.domain.enuns.Status;
import com.romulo.os.dto.OSDTO;
import com.romulo.os.repositories.OSRepository;
import com.romulo.os.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OSServiceTest {

    @Mock
    private OSRepository repository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private TecnicoService tecnicoService;

    @InjectMocks
    private OSService osService;

    private OS os;
    private OSDTO osDTO;

    @BeforeEach
    void setUp() {
        os = new OS();
        os.setId(1);
        os.setObservacoes("Teste OS");
        os.setStatus(Status.ABERTO);
        os.setPrioridade(Prioridade.ALTA);
        os.setDataAbertura(LocalDateTime.now());

        osDTO = new OSDTO();
        osDTO.setId(1);
        osDTO.setObservacoes("Teste OS");
        osDTO.setStatus(1);
        osDTO.setPrioridade(1);
        osDTO.setDataAbertura(LocalDateTime.now());
        osDTO.setCliente(1);
        osDTO.setTecnico(1);
    }

    @Test
    void testFindById() {
        when(repository.findById(1)).thenReturn(Optional.of(os));

        OS result = osService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            osService.findById(1);
        });

        assertEquals("Objeto não encontrado! Id: 1, Tipo: com.romulo.os.domain.OS", exception.getMessage());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(os));

        List<OS> result = osService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(clienteService.findById(1)).thenReturn(new Cliente());
        when(tecnicoService.findById(1)).thenReturn(new Tecnico());
        when(repository.save(any(OS.class))).thenReturn(os);

        OS result = osService.create(osDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).save(any(OS.class));
    }

    @Test
    void testUpdate() {
        when(repository.findById(1)).thenReturn(Optional.of(os));
        when(clienteService.findById(1)).thenReturn(new Cliente());
        when(tecnicoService.findById(1)).thenReturn(new Tecnico());
        when(repository.save(any(OS.class))).thenReturn(os);

        OS result = osService.upDate(osDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(any(OS.class));
    }

    @Test
    void testFromDTO() {
        when(clienteService.findById(1)).thenReturn(new Cliente());
        when(tecnicoService.findById(1)).thenReturn(new Tecnico());
        when(repository.save(any(OS.class))).thenReturn(os);

        OS result = osService.fromDTO(osDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Teste OS", result.getObservacoes());
        assertEquals(Status.ABERTO, result.getStatus());
        assertEquals(Prioridade.ALTA, result.getPrioridade());
        verify(repository, times(1)).save(any(OS.class));
    }
}