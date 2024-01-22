package com.bancobase.pagoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancobase.pagoservice.model.dto.PagoDto;
import com.bancobase.pagoservice.service.PagoService;
import com.bancobase.pagoservice.validators.EstatusValido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

	@Autowired
	private PagoService pagoService;

	@PostMapping
	@Operation(
        summary = "Genera un nuevo pago",
        description = "Genera un pago con la informacion proporcionada."
    )	
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "Pago generado correctamente",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = PagoDto.class)
					)
			}),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor",
			content = @Content
			)
	})
	public ResponseEntity<PagoDto> generarPago(@Valid @RequestBody PagoDto pagoDto){
		return new ResponseEntity<> (pagoService.generarPago(pagoDto), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(
        summary = "Busca un pago",
        description = "Busca el pago por medio del identificador proporcionado."
    )	
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Pago encontrado",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = PagoDto.class)
					)
			}),
		@ApiResponse(
			responseCode = "404",
			description = "Pago no encontrado",
			content = @Content
			),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor",
			content = @Content
			)
	})
	public ResponseEntity<PagoDto> obtenerPago(@NotNull @PathVariable Long id){
		return ResponseEntity.ok(pagoService.obtenerPago(id));
	}

	@GetMapping
	@Operation(
        summary = "Busca todos los pagos",
        description = "Busca todos los pagos."
    )
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Lista de pagos obtenida exitosamente",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = PagoDto.class))
					)
			}),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor",
			content = @Content
			)
	})
	public ResponseEntity<List<PagoDto>> obtenerPagos(){
		return ResponseEntity.ok(pagoService.obtenerPagos());
	}

	@PatchMapping("/{id}/estatus")
	@Operation(
        summary = "Actualiza el estatus del pago",
        description = "Actualiza el estatus del pago mediante el identificador proporcionado."
    )
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Estado del pago actualizado exitosamente",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = PagoDto.class)
					)
			}),
		@ApiResponse(
			responseCode = "404",
			description = "Pago no encontrado",
			content = @Content
			),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor",
			content = @Content
				)
	})
	public ResponseEntity<PagoDto> actualizarEtatus(
			@NotNull @PathVariable Long id, 
			@EstatusValido @RequestParam String estatus){

		return ResponseEntity.ok(pagoService.actualizarEtatus(id, estatus));
	}
}
