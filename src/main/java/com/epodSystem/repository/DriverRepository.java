package com.epodSystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.epodSystem.model.Driver;


public interface DriverRepository extends CrudRepository<Driver, Integer>{

	Driver findBydriverContact(String driverContact);

	boolean existsBydriverContact(String driverContact);
	
}
