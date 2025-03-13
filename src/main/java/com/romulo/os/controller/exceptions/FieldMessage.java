package com.romulo.os.controller.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Representação de um erro específico em um campo")
public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Nome do campo que apresentou erro", example = "nome")
    private String fieldName;

    @Schema(description = "Mensagem de erro associada ao campo", example = "O nome é obrigatório")
    private String message;

    public FieldMessage() {
    }

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
