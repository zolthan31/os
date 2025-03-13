package com.romulo.os.controller.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardErrorTest {

    @Test
    public void testDefaultConstructor() {
        // Testa o construtor padrão
        StandardError error = new StandardError();

        // Verifica se os valores são nulos (ou padrão)
        assertNull(error.getTimestamp());
        assertNull(error.getStatus());
        assertNull(error.getError());
    }

    @Test
    public void testParameterizedConstructor() {
        // Testa o construtor com parâmetros
        Long timestamp = System.currentTimeMillis();
        Integer status = 404;
        String errorMessage = "Not Found";

        StandardError error = new StandardError(timestamp, status, errorMessage);

        // Verifica se os valores foram atribuídos corretamente
        assertEquals(timestamp, error.getTimestamp());
        assertEquals(status, error.getStatus());
        assertEquals(errorMessage, error.getError());
    }

    @Test
    public void testSettersAndGetters() {
        // Testa os setters e getters
        StandardError error = new StandardError();

        Long timestamp = System.currentTimeMillis();
        Integer status = 400;
        String errorMessage = "Bad Request";

        // Usa os setters para atribuir valores
        error.setTimestamp(timestamp);
        error.setStatus(status);
        error.setError(errorMessage);

        // Verifica se os getters retornam os valores corretos
        assertEquals(timestamp, error.getTimestamp());
        assertEquals(status, error.getStatus());
        assertEquals(errorMessage, error.getError());
    }

    @Test
    public void testNotEquals() {
        // Testa a desigualdade de objetos
        Long timestamp1 = System.currentTimeMillis();
        Long timestamp2 = timestamp1 + 1000; // Diferente do primeiro timestamp
        Integer status = 404;
        String errorMessage = "Not Found";

        StandardError error1 = new StandardError(timestamp1, status, errorMessage);
        StandardError error2 = new StandardError(timestamp2, status, errorMessage);

        // Verifica se os objetos não são iguais
        assertNotEquals(error1, error2);
        assertNotEquals(error1.hashCode(), error2.hashCode());
    }

}