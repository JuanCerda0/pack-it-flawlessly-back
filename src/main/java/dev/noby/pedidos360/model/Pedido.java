package dev.noby.pedidos360.model;

import java.math.BigDecimal;

public class Pedido {

    private Long id;
    private String cliente;
    private BigDecimal total;
    private String estado;

    public Pedido() {
    }

    public Pedido(Long id, String cliente, BigDecimal total, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente='" + cliente + "', total=" + total + ", estado='" + estado + "'}";
    }
}
