package com.romulo.os.controller.exceptions;

import com.romulo.os.services.exception.DataIntegrityViolationException;
import com.romulo.os.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void objectNotFoundException() {

        ObjectNotFoundException ex = new ObjectNotFoundException("Objeto não encontrado!");

        ResponseEntity<StandardError> response = exceptionHandler.objectNotFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("Objeto não encontrado!", response.getBody().getError());
    }

    @Test
    public void dataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Violação de integridade de dados!");

        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolationException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Violação de integridade de dados!", response.getBody().getError());
    }
}