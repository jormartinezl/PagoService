package com.bancobase.pagoservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.bancobase.pagoservice.model.dto.PagoDto;
import com.bancobase.pagoservice.service.PagoService;
import com.google.gson.Gson;


@WebMvcTest(PagoController.class)
class PagoControllerTest {

	@MockBean
    private PagoService pagoService;

    @InjectMocks
    private PagoController pagoController;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private Gson gson;

    PagoDto pagoLocal;
    
    @BeforeEach
    void setUp() {
    	PagoDto pagoLocalRespuesta = PagoDto.builder()
    			.id(1L)
    			.beneficiario("Jorge")
    			.cantidad(1)
    			.concepto("Pago tests")
    			.emisor("Juan")
    			.estatus("PENDIENTE")
    			.monto(BigDecimal.valueOf(100.50)).build();
    	
    	pagoLocal = PagoDto.builder()
    			.beneficiario("Jorge")
    			.cantidad(1)
    			.concepto("Pago tests")
    			.emisor("Juan")
    			.estatus("PENDIENTE")
    			.monto(BigDecimal.valueOf(100.50)).build();
    	
    	when(pagoService.generarPago(Mockito.any(PagoDto.class))).thenReturn(pagoLocalRespuesta);
    	when(pagoService.obtenerPago(1L)).thenReturn(pagoLocalRespuesta);
    	when(pagoService.obtenerPago(2L)).thenThrow(
    		    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Pago %d no encontrado", 2L))
    		);
    	when(pagoService.obtenerPagos()).thenReturn(Arrays.asList(pagoLocalRespuesta));
    	when(pagoService.actualizarEtatus(1L, "PENDIENTE")).thenReturn(pagoLocalRespuesta);
    	when(pagoService.actualizarEtatus(2L,"COMPLETADO")).thenThrow(
    		    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Pago %d no encontrado", 2L))
    		);
    }

    @Test
    @DisplayName("Prueba para realizar un pago.")
    public void saveLocal() throws Exception{
    	
        mockMvc.perform(post("/api/v1/pagos").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isCreated());
    }
    
    @Test
    @DisplayName("Prueba para validar beneficiario nulo.")
    public void saveLocalBeneficiarioNulo() throws Exception {
        pagoLocal.setBeneficiario(null);
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest()); 
    }

    @Test
    @DisplayName("Prueba para validar beneficiario vacío.")
    public void saveLocalBeneficiarioVacio() throws Exception {
        pagoLocal.setBeneficiario("");
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest()); 
    }
    
    @Test
    @DisplayName("Prueba para validar cantidad nulo.")
    public void saveLocalcantidadNulo() throws Exception {
        pagoLocal.setCantidad(null);
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest()); 
    }
    
    @Test
    @DisplayName("Prueba para validar concepto nulo.")
    public void saveLocalConceptoNulo() throws Exception {
        pagoLocal.setConcepto(null);
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Prueba para validar concepto vacío.")
    public void saveLocalConceptoVacio() throws Exception {
        pagoLocal.setConcepto("");
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Prueba para validar emisor nulo.")
    public void saveLocalEmisorNulo() throws Exception {
        pagoLocal.setEmisor(null);
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Prueba para validar emisor vacío.")
    public void saveLocalEmisorVacio() throws Exception {
        pagoLocal.setEmisor("");
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Prueba para validar estatus inválido.")
    public void saveLocalEstatusInvalido() throws Exception {
        pagoLocal.setEstatus("ESTATUS_INVALIDO");
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pagoLocal)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Prueba para obtener un pago existente.")
    public void obtenerPagoExistente() throws Exception {
        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.beneficiario").value("Jorge"))
                .andExpect(jsonPath("$.cantidad").value(1))
                .andExpect(jsonPath("$.concepto").value("Pago tests"))
                .andExpect(jsonPath("$.emisor").value("Juan"))
                .andExpect(jsonPath("$.estatus").value("PENDIENTE"))
                .andExpect(jsonPath("$.monto").value(100.50));
    }
    
    @Test
    @DisplayName("Prueba para obtener un pago inexistente.")
    public void obtenerPagoNoExistente() throws Exception {

        mockMvc.perform(get("/api/v1/pagos/2"))
        .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Prueba para obtener todos los pagos exitosamente.")
    public void obtenerPagosExitoso() throws Exception {

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
    
    @Test
    @DisplayName("Prueba para actualizar el estatus exitosamente.")
    public void actualizarEstatusExitoso() throws Exception {
        Long id = 1L;
        String nuevoEstatus = "PENDIENTE";
        
        pagoLocal.setId(id);
        pagoLocal.setEstatus(nuevoEstatus);

        mockMvc.perform(patch("/api/v1/pagos/{id}/estatus", id)
                .param("estatus", nuevoEstatus))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.estatus").value(nuevoEstatus));
    }
    
    @Test
    @DisplayName("Prueba para actualizar el estatus de un pago no encontrado.")
    public void actualizarEstatusPagoNoEncontrado() throws Exception {
        Long idNoExistente = 2L;
        String nuevoEstatus = "COMPLETADO";

        mockMvc.perform(patch("/api/v1/pagos/{id}/estatus", idNoExistente)
                .param("estatus", nuevoEstatus))
               .andExpect(status().isNotFound());
    }
}
