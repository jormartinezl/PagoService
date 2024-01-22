package com.bancobase.pagoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancobase.pagoservice.model.entity.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {


}
