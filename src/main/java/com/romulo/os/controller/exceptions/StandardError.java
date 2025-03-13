package com.romulo.os.controller.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Modelo padrão para erros da API")
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Momento em que o erro ocorreu", example = "1712345678901")
    private Long timestamp;

    @Schema(description = "Código de status HTTP", example = "400")
    private Integer status;

    @Schema(description = "Mensagem de erro", example = "Objeto não encontrado")
    private String message;

    public StandardError() {
    }

    public StandardError(Long timestamp, Integer status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return message;
    }

    public void setError(String message) {
        this.message = message;
    }
}
