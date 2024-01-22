package com.bancobase.pagoservice.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Builder
@ToString
@Table(name = "pago")
@Entity
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "concepto", nullable = false)
	private String concepto;
	
	@Column(name = "cantidad",nullable = false)
	private Integer	cantidad;
	
	@Column(name = "beneficiario",nullable = false)
	private String beneficiario;
	
	@Column(name = "emisor",nullable = false)
	private String emisor;
	
	@Column(name = "monto",nullable = false)
	private BigDecimal monto;
	
	@Column(name = "estatus",nullable = false)
	private String estatus;

}
