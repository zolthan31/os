package com.romulo.os.controller;

import com.romulo.os.domain.Cliente;
import com.romulo.os.domain.OS;
import com.romulo.os.domain.Tecnico;
import com.romulo.os.domain.enuns.Prioridade;
import com.romulo.os.domain.enuns.Status;
import com.romulo.os.dto.OSDTO;
import com.romulo.os.services.OSService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OSControllerTest {

    @InjectMocks
    private OSController controller;

    @Mock
    private OSService service;

    private OSDTO osDTO;
    private OS os;
    private Tecnico tecnico;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        tecnico = new Tecnico();
        tecnico.setId(1);
        tecnico.setNome("Técnico Teste");

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Cliente teste");

        os = new OS(1, Prioridade.MEDIA, "Teste de OS", Status.ANDAMENTO, tecnico, cliente);
        os.setDataAbertura(LocalDateTime.now());
        os.setDataFechamento(LocalDateTime.now().plusDays(1));

        osDTO = new OSDTO();
        osDTO.setId(1);

        HttpServletRequest mockRequest = new MockHttpServletRequest();
        HttpServletResponse mockResponse = new MockHttpServletResponse();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }

    @Test
    public void testFindById() {
        when(service.findById(1)).thenReturn(os);

        ResponseEntity<OSDTO> response = controller.findById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(os.getId(), response.getBody().getId());
    }

    @Test
    public void testFindAll() {
        when(service.findAll()).thenReturn(Arrays.asList(os));

        ResponseEntity<List<OSDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testCreate() {
        when(service.create(any(OSDTO.class))).thenReturn(os);

        ResponseEntity<OSDTO> response = controller.create(osDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void testUpDate() {
        when(service.upDate(any(OSDTO.class))).thenReturn(os);

        ResponseEntity<OSDTO> response = controller.upDate(osDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(os.getId(), response.getBody().getId());
    }
}
