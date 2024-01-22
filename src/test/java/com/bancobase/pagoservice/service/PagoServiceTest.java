package com.bancobase.pagoservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import com.bancobase.pagoservice.model.dto.PagoDto;
import com.bancobase.pagoservice.model.entity.Pago;
import com.bancobase.pagoservice.repository.PagoRepository;

@SpringBootTest
class PagoServiceTest {
	
	@Autowired
	private PagoService pagoService;
    
	@MockBean
    private PagoRepository pagoRepository;

    @BeforeEach
    void setup() {
    	Pago pagoLocalSalida = Pago.builder()
    			.id(1L)
    			.beneficiario("Jorge")
    			.cantidad(1)
    			.concepto("Pago test")
    			.emisor("Juan")
    			.estatus("PENDIENTE")
    			.monto(BigDecimal.valueOf(100.50)).build();
    	
    	when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoLocalSalida));
    	when(pagoRepository.save(Mockito.any(Pago.class))).thenReturn(pagoLocalSalida);
    	when(pagoRepository.findAll()).thenReturn(Arrays.asList(pagoLocalSalida));
    	pagoLocalSalida.setEstatus("COMPLETADO");
    	when(pagoRepository.findAll()).thenReturn(Arrays.asList(pagoLocalSalida));
    	
    }

    @Test
    @DisplayName("Prueba para generar un pago exitodo.")
    public void generarPago(){
    	
    	PagoDto pagoLocal = PagoDto.builder()
    			.id(1L)
    			.beneficiario("Jorge")
    			.cantidad(1)
    			.concepto("Pago test")
    			.emisor("Juan")
    			.estatus("PENDIENTE")
    			.monto(BigDecimal.valueOf(100.50)).build();
    	
    	PagoDto pagoDto = pagoService.generarPago(pagoLocal);
    	
    	assertThat(pagoDto).isNotNull();
        assertEquals(1L, pagoDto.getId());
    }
    
    @Test
    @DisplayName("Prueba para obtener un pago.")
    public void obtenerPago(){
    	
    	Long id = 1L;
    	PagoDto pagoDto = pagoService.obtenerPago(id);
    	
        assertEquals(1L, pagoDto.getId());
    }
    
    @Test
    @DisplayName("Prueba para fallo al obtener un pago.")
    public void obtenerPagoNoExistenteFallo() {
    	Long id = 100L;

    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
        		() -> pagoService.obtenerPago(id));

        assertEquals("404 NOT_FOUND \"Pago 100 no encontrado\"", exception.getMessage());
    }
    
    @Test
    @DisplayName("Prueba para obtener los pagos.")
    public void obtenerPagos(){
    	
    	List<PagoDto> pagoDtoList = pagoService.obtenerPagos();
    	
    	assertThat(pagoDtoList).isNotNull();
        assertThat(pagoDtoList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Prueba para actuaizar el estatus.")
    public void actualizarEtatus(){

    	Long id = 1l;
    	String estatus = "COMPLETADO";
    	PagoDto pagoDto = pagoService.actualizarEtatus(id,estatus);

    	assertEquals(estatus, pagoDto.getEstatus());
    }
    
    @Test
    @DisplayName("Prueba para fallo al actualizar un pago.")
    public void actualizarEtatusNoExistenteFallo() {
    	Long id = 100l;
    	String estatus = "COMPLETADO";

    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
        		() -> pagoService.actualizarEtatus(id, estatus));

        assertEquals("404 NOT_FOUND \"Pago 100 no encontrado\"", exception.getMessage());
    }
}
