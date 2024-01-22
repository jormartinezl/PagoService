package com.bancobase.pagoservice.model.dto;

import java.math.BigDecimal;

import com.bancobase.pagoservice.validators.EstatusValido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PagoDto {

		private Long id;
		
		@NotNull
		@NotBlank
		private String concepto;
		
		@NotNull
		private Integer	cantidad;
		
		@NotNull
		@NotBlank
		private String beneficiario;
		
		@NotNull
		@NotBlank
		private String emisor;
		
		@NotNull
		@Positive
		private BigDecimal monto;
		
		@NotNull
		@EstatusValido(message = "Estatus no válido")
		private String estatus;
}
