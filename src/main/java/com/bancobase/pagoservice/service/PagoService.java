package com.bancobase.pagoservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bancobase.pagoservice.model.dto.PagoDto;
import com.bancobase.pagoservice.model.entity.Pago;
import com.bancobase.pagoservice.repository.PagoRepository;
import com.google.gson.Gson;

@Service
public class PagoService {


	@Autowired
	private PagoRepository pagoRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private KafkaTemplate<String, String> producer;
	
	@Autowired
	private Gson gson;

	/**
	 * Crea un pago.
	 *
	 * @param pagoDto Objeto PagoDto que contiene la información del pago a crear.
	 * @return Objeto PagoDto que representa el pago creado.
	 */
	@Transactional
	public PagoDto generarPago(PagoDto pagoDto) {
		Pago pago = mapper.map(pagoDto, Pago.class); 
		Pago pagoSave = pagoRepository.save(pago);

		return mapper.map(pagoSave, PagoDto.class); 
	}

	/**
	 * Obtiene la información de un pago por su identificador.
	 *
	 * @param id Identificador del pago.
	 * @return Objeto PagoDto que representa la información del pago.
	 * @throws ResponseStatusException Si el pago no se encuentra, se lanza una excepción con el código de estado 404.
	 */
	@Transactional(readOnly = true)
	public PagoDto obtenerPago(Long id) {
		Pago pago = pagoRepository.findById(id).orElseThrow(()->
			new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Pago %d no encontrado", id)));

		return mapper.map(pago, PagoDto.class); 
	}

	/**
	 * Obtiene la lista de todos los pagos disponibles.
	 *
	 * @return Lista de objetos PagoDto que representan la información de los pagos.
	 */
	@Transactional(readOnly = true)
	public List<PagoDto> obtenerPagos() {
		List<Pago> pagosList = pagoRepository.findAll();
		return pagosList.stream()
                .map(pago -> mapper.map(pago, PagoDto.class))
                .collect(Collectors.toList());
	}
	
	/**
	 * Actualiza el estado de un pago por su identificador.
	 *
	 * @param id Identificador del pago a actualizar.
	 * @param estatus Nuevo estado del pago.
	 * @return Objeto PagoDto que representa el pago después de la actualización.
	 * @throws ResponseStatusException Si el pago no se encuentra, se lanza una excepción con el código de estado 404.
	 */
	@Transactional
	public PagoDto actualizarEtatus(Long id, String estatus){
		Pago pago = pagoRepository.findById(id).orElseThrow(()->
			new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Pago %d no encontrado", id)));
		pago.setEstatus(estatus);
		Pago pagoSave = pagoRepository.save(pago);
		
		PagoDto pagoDto = mapper.map(pagoSave, PagoDto.class);
		
		producer.send("estatus-topic", pagoDto.getId().toString(), gson.toJson(pagoDto));
		
		return pagoDto;
		
	}
}
