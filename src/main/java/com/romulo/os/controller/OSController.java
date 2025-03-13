package com.romulo.os.controller;

import com.romulo.os.domain.OS;
import com.romulo.os.dto.OSDTO;
import com.romulo.os.services.OSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
@Tag(name = "OS", description = "Contem todas as oprerações relativas aos recursos para cadastro, edição e leitura  de  Ordems de serviço")
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OSController {

    @Autowired
    private OSService service;


    @Operation(summary = "Buscar Os por ID",
            description = "Retorna uma Os com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OS encontrada"),
            @ApiResponse(responseCode = "400", description = "OS não encontrada")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<OSDTO> findById(@PathVariable Integer id) {
        OSDTO objDto = new OSDTO(service.findById(id));
        return ResponseEntity.ok().body(objDto);
    }

    @Operation(summary = "Buscar lista de OS",
            description = "Retorna a listagem de OS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OS encontradas"),
            @ApiResponse(responseCode = "400", description = "OS não encontradas")
    })
    @GetMapping
    public ResponseEntity<List<OSDTO>> findAll() {
        List<OSDTO> listDTO = service.findAll().stream().map(obj -> new OSDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @Operation(summary = "Registrar OS",
            description = "Registrar uma OS na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OS criada")
    })
    @PostMapping
    public ResponseEntity<OSDTO> create(@Valid @RequestBody OSDTO obj) {
        obj = new OSDTO(service.create(obj));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualizar OS",
            description = "Atualiza os dados de uma OS existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OS atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição - dados inválidos"),
            @ApiResponse(responseCode = "404", description = "OS não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping
    public ResponseEntity<OSDTO> upDate(@Valid @RequestBody OSDTO obj) {
        obj = new OSDTO(service.upDate(obj));
        return ResponseEntity.ok().body(obj);
    }
}
