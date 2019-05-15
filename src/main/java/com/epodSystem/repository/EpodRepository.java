package com.epodSystem.repository;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epodSystem.model.EpodDtls;

@Transactional
public interface EpodRepository extends CrudRepository<EpodDtls, Integer> {

	EpodDtls findByvehRegNo(String vehRegNo);
	
	EpodDtls findByApiKey(String apiKey);
	
	@Modifying
	@Query("update EpodDtls epod set epod.s_feedback=?1,epod.status=?2 where epod.i_epod_id=?3")
	int sets_feedbackForEpodDtls(HashMap<String, Object> feedBackJson,String status,int epodId);
	
	@Modifying
	@Query("update EpodDtls epod set epod.loadedDoc=?1 where epod.i_epod_id=?2")
	int setLoadedDocForEpodDtls(HashMap<String, Object> loadedDoc,int epodId);
	
	@Modifying
	@Query("update EpodDtls epod set epod.status=?1 where epod.i_epod_id=?2")
	int setStatusForEpodDtls(String status,int epodId);
	
	@Modifying
	@Query("update EpodDtls epod set epod.vehRegNo=?1,epod.vehId=?2,epod.driverContact=?3,epod.driId=?4 where epod.i_epod_id=?5")
	int updateEpod(String vehReg,int vehId,String driverContact,int driId,int epodId);
	
	@Query("select epod from EpodDtls epod where epod.driverContact=?1 and epod.status in ('CRT','RCV','OPN')")
	List<EpodDtls> checkDriverAvailability(String driverContact);
	
	@Query("select epod from EpodDtls epod where epod.vehRegNo=?1 and epod.status in ('CRT','RCV','OPN')")
	List<EpodDtls> checkVehicleAvailability(String vehRegNo);

}
