package com.epodSystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.epodSystem.converter.LocalDateTimeConverter;


@Entity
@Table(name = "tbl_vehicle", schema = "epod")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "i_veh_id")
	private int vehId;

	
	@Column(name = "s_veh_reg",unique = true,nullable = false)
	private String vehReg;
	
	@Column(name = "s_created_by")
	private String createdBy;
	
	@Column(name = "dt_created_on", columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdOn;
	
	@Column(name = "s_updated_by")
	private String updatedBy;
	
	@Column(name = "dt_updated_on")
	@Convert(converter =LocalDateTimeConverter.class)
	private LocalDateTime updatedOn;

//	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<EpodDtls> epodDtls=new ArrayList<>();

	
	
//	public List<EpodDtls> getEpodDtls() {
//		return epodDtls;
//	}
//
//	public void setEpodDtls(List<EpodDtls> epodDtls) {
//		this.epodDtls = epodDtls;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getVehId() {
		return vehId;
	}

	public void setVehId(int vehId) {
		this.vehId = vehId;
	}

	public String getVehReg() {
		return vehReg;
	}

	public void setVehReg(String vehReg) {
		this.vehReg = vehReg;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	
//	 public void addEpod(EpodDtls epod) {
//		 epodDtls.add(epod);
//		 epod.setVehicle(this);
//	    }
//	 
//	    public void removeEpod(EpodDtls epod) {
//	    	epodDtls.remove(epod);
//	    	epod.setVehicle(null);
//	    }

}
