package com.romulo.os.controller;

import com.romulo.os.domain.Tecnico;
import com.romulo.os.dto.TecnicoDTO;
import com.romulo.os.services.TecnicoService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TecnicoControllerTest {

    @Mock
    private TecnicoService service;

    @InjectMocks
    private TecnicoController controller;

    private Tecnico tecnico;
    private TecnicoDTO tecnicoDTO;

    @BeforeEach
    public void setUp() {
        tecnico = new Tecnico(1, "João Neves", "123.456.789-00", "91999999999");
        tecnicoDTO = new TecnicoDTO();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void findById() {
        when(service.findById(1)).thenReturn(tecnico);

        ResponseEntity<TecnicoDTO> response = controller.findById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Neves", response.getBody().getNome());

        verify(service, times(1)).findById(1);
    }

    @Test
    public void findAll() {
        when(service.findAll()).thenReturn(Collections.singletonList(tecnico));

        ResponseEntity<List<TecnicoDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("João Neves", response.getBody().get(0).getNome());

        verify(service, times(1)).findAll();
    }

    @Test
    public void create() {
        when(service.create(any(TecnicoDTO.class))).thenReturn(tecnico);

        ResponseEntity<TecnicoDTO> response = controller.create(tecnicoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        verify(service, times(1)).create(any(TecnicoDTO.class));
    }

    @Test
    public void upDate() {
        when(service.upDate(eq(1), any(TecnicoDTO.class))).thenReturn(tecnico);

        ResponseEntity<TecnicoDTO> response = controller.upDate(1, tecnicoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Neves", response.getBody().getNome());

        verify(service, times(1)).upDate(eq(1), any(TecnicoDTO.class));
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