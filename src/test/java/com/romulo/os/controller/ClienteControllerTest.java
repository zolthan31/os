package com.romulo.os.controller;

import com.romulo.os.domain.Cliente;
import com.romulo.os.dto.ClienteDTO;
import com.romulo.os.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

  @Mock
  private ClienteService service;

  @InjectMocks
  private ClienteController controller;

  private Cliente cliente;
  private ClienteDTO clienteDTO;

  @BeforeEach
  public void setUp() {
      cliente = new Cliente(1, "Neves João", "123.456.789.00", "91999999999");
      clienteDTO = new ClienteDTO();

      MockHttpServletRequest request = new MockHttpServletRequest();
      RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  public void findById() {
      when(service.findById(1)).thenReturn(cliente);

      ResponseEntity<ClienteDTO> response = controller.findById(1);

      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals("Neves João", response.getBody().getNome());

      verify(service, times(1)).findById(1);
  }

    @Test
    public void findAll() {
        when(service.findAll()).thenReturn(Collections.singletonList(cliente));

        ResponseEntity<List<ClienteDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Neves João", response.getBody().get(0).getNome());

        verify(service, times(1)).findAll();
    }

    @Test
    public void create() {
        when(service.create(any(ClienteDTO.class))).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = controller.create(clienteDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        verify(service, times(1)).create(any(ClienteDTO.class));
    }

    @Test
    public void upDate() {
        when(service.upDate(eq(1), any(ClienteDTO.class))).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = controller.upDate(1, clienteDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Neves João", response.getBody().getNome());

        verify(service, times(1)).upDate(eq(1), any(ClienteDTO.class));
    }

    @Test
    public void delete() {
        doNothing().when(service).delete(1);
        ResponseEntity<Void> response = controller.delete(1);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1);
    }
}