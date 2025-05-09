package com.github.acnaweb.study_apir.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.acnaweb.study_apir.dto.pedido.PedidoRequestCreate;
import com.github.acnaweb.study_apir.dto.pedido.PedidoResponse;
import com.github.acnaweb.study_apir.service.PedidoService;

@RestController
@RequestMapping("pedidos")
public class ControllerPedido {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> create(
                                @RequestBody PedidoRequestCreate dto) {  
        
        return ResponseEntity.status(201).body(new PedidoResponse().toDto(pedidoService.create(dto)));

    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable Long id){
        return pedidoService.getById(id)
        .map(p-> new PedidoResponse().toDto(p))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());  
    
    }

    @GetMapping()
    public ResponseEntity<List<PedidoResponse>> listAll(){
        List<PedidoResponse> response = 
            pedidoService.getAll().stream()
            .map(p-> new PedidoResponse().toDto(p))
            .collect(Collectors.toList()); 
        return ResponseEntity.ok(response);
    }
}
