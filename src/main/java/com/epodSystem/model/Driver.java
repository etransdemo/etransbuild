package com.epodSystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.epodSystem.converter.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tbl_driver",schema = "epod")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class Driver implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "i_dri_id")
	private Integer driverId;
	
	@Column(name = "s_dri_name")
	private String driverName;
	
	
	@Column(name = "s_dri_cont",unique = true,nullable = false)
	private String driverContact;
	
	@Column(name = "s_fcm_code")
	private String fcmCode;
	
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
	
	@Column(name = "s_license_no")
	private String licenseNo;
	
	@JsonFormat(pattern="dd/MM/yyyy",shape=Shape.STRING)
	@Column(name = "dt_licence_val", columnDefinition = "timestamp without time zone")
	//@Convert(converter = LocalDateTimeConverter.class)
	private Date licenseValidity;

//	@OneToMany(mappedBy = "driver", cascade = CascadeType.MERGE, orphanRemoval = true)
//	private List<EpodDtls> epodDtls=new ArrayList<>();
	
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

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public Date getLicenseValidity() {
		return licenseValidity;
	}

	public void setLicenseValidity(Date licenseValidity) {
		this.licenseValidity = licenseValidity;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverContact() {
		return driverContact;
	}

	public void setDriverContact(String driverContact) {
		this.driverContact = driverContact;
	}

	public String getFcmCode() {
		return fcmCode;
	}

	public void setFcmCode(String fcmCode) {
		this.fcmCode = fcmCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//	 public void addEpod(EpodDtls epod) {
//	 epodDtls.add(epod);
//	 epod.setDriver(this);
//   }
	

}
