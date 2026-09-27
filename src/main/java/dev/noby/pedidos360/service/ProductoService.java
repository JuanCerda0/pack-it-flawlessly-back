package dev.noby.pedidos360.service;

import dev.noby.pedidos360.model.Producto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final List<Producto> productos = new CopyOnWriteArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public ProductoService() {
        productos.add(new Producto(siguienteId(), "Notebook ASUS Vivobook 15", new BigDecimal("749990.00"), 12));
        productos.add(new Producto(siguienteId(), "Smartphone Xiaomi Redmi Note 13", new BigDecimal("189990.00"), 40));
        productos.add(new Producto(siguienteId(), "Audifonos Sony WH-1000XM5", new BigDecimal("299990.00"), 7));
    }

    public List<Producto> listar() {
        return List.copyOf(productos);
    }

    public Producto crear(Producto producto) {
        producto.setId(siguienteId());
        productos.add(producto);
        return producto;
    }

    private long siguienteId() {
        return secuenciaId.incrementAndGet();
    }
}
