package com.romulo.os.controller;


import com.romulo.os.domain.Tecnico;
import com.romulo.os.dto.TecnicoDTO;
import com.romulo.os.services.TecnicoService;
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
@Tag(name = "Tecnicos", description = "Contem todas as oprerações relativas aos recursos para cadastro, edição, leitura e exclusão de um tecnico")
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService service;



    @Operation(summary = "Buscar técnico por ID",
            description = "Retorna um técnico com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Técnico encontrado"),
            @ApiResponse(responseCode = "400", description = "Técnico não encontrado")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
        return ResponseEntity.ok().body(objDTO);
    }

    @Operation(summary = "Buscar lista de técnico",
            description = "Retorna a listagem de técnico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Técnicos encontrados"),
            @ApiResponse(responseCode = "400", description = "Técnicos não encontrados")
    })
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<TecnicoDTO> listDTO = service.findAll()
                .stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @Operation(summary = "Registrar técnico",
            description = "Registrar um técnico na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Técnico criado")
    })
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @Operation(summary = "Atualizar técnico",
            description = "Atualiza os dados de um técnico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "técnico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição - dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Técnico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> upDate(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        TecnicoDTO newObj = new TecnicoDTO(service.upDate(id, objDTO));
        return ResponseEntity.ok().body(newObj);
    }


    @Operation(summary = "Excluir técnico",
            description = "Remove um técnico do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Técnico excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição - ID inválido"),
            @ApiResponse(responseCode = "404", description = "Técnico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}