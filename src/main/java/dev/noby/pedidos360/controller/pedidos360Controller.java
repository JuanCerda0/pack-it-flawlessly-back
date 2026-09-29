package dev.noby.pedidos360.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class pedidos360Controller {
    @GetMapping
    public List<Map<String,Object>> obtenerPedidos() {
        return List.of(
                Map.of("id", 1, "producto", "Notebook", "estado", "EN_PREPARACION"),
                Map.of("id", 2, "producto", "Telefono", "estado", "DISPONIBLE")
        );
    }
}

