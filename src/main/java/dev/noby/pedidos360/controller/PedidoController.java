package dev.noby.pedidos360.controller;

import dev.noby.pedidos360.model.Pedido;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return List.of(
                new Pedido(1L, "Ana Martinez", new BigDecimal("154980.00"), "EN_PREPARACION"),
                new Pedido(2L, "Carlos Diaz", new BigDecimal("89990.00"), "CONFIRMADO")
        );
    }
}
