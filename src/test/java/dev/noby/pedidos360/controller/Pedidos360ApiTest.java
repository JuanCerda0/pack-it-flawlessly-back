package dev.noby.pedidos360.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class Pedidos360ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarPedidosDevuelveAlMenosDosPedidos() throws Exception {
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].cliente").isString())
                .andExpect(jsonPath("$[0].total").isNumber())
                .andExpect(jsonPath("$[0].estado").isString());
    }

    @Test
    void listarProductosDevuelveAlMenosTresProductos() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].nombre").isString())
                .andExpect(jsonPath("$[0].precio").isNumber())
                .andExpect(jsonPath("$[0].stock").isNumber());
    }

    @Test
    void crearProductoDevuelveCreatedConMensajeYProductoPersistido() throws Exception {
        String cuerpo = """
                {
                  "nombre": "Mouse Logitech G502",
                  "precio": 54990.00,
                  "stock": 25
                }
                """;

        mockMvc.perform(post("/api/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje", is("Producto creado exitosamente")))
                .andExpect(jsonPath("$.producto.nombre", is("Mouse Logitech G502")))
                .andExpect(jsonPath("$.producto.precio").isNumber())
                .andExpect(jsonPath("$.producto.stock", is(25)))
                .andExpect(jsonPath("$.producto.id").isNumber());

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nombre", hasItem("Mouse Logitech G502")));
    }
}
