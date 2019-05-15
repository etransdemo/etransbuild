package com.epodSystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.epodSystem.model.TripEntity;



public interface TripRepository extends CrudRepository<TripEntity, String> {

}
