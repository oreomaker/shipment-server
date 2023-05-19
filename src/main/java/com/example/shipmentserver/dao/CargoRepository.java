package com.example.shipmentserver.dao;

import com.example.shipmentserver.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}
