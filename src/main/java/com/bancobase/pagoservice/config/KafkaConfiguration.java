package com.bancobase.pagoservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaConfiguration {

	/**
	 * Método que configura y devuelve las propiedades del productor de Kafka.
	 *
	 * @return Mapa de propiedades configuradas para el productor de Kafka.
	 */
	private Map<String, Object> producerProps() { 
		Map<String, Object> props=new HashMap<>();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"kafka2:29092");//configuración de los servidores de arranque
		props.put(ProducerConfig.RETRIES_CONFIG, 0);//Define los reintentos que se realizarán en caso de error.
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);//tamaño del lote
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);//tiempo de espera
		//Define el espacio de memoria para colocar los mensajes que están pendientes por enviar.
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		return props;
	}
	
	/**
	 * Método que crea y devuelve una instancia de {@link KafkaTemplate} 
	 * configurada para enviar mensajes a un tópico de Kafka.
	 *
	 * @return Instancia de {@link KafkaTemplate} configurada.
	 */
	@Bean
	public KafkaTemplate<String, String> createTemplate() { 
		Map<String, Object> senderProps = producerProps();
		ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
		KafkaTemplate<String, String> template = new KafkaTemplate<>(pf);
		return template;
	}
}
