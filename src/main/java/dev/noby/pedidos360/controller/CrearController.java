package dev.noby.pedidos360.controller;

import dev.noby.pedidos360.model.Producto;
import dev.noby.pedidos360.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CrearController {

    private final ProductoService productoService;

    public CrearController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Producto producto) {
        Producto creado = productoService.crear(producto);
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Producto creado exitosamente");
        respuesta.put("producto", creado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
