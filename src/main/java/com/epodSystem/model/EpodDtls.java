package com.epodSystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.epodSystem.converter.LocalDateTimeConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(schema = "epod", name = "tbl_epod_dtls")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EpodDtls implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(generator = "seq_epod_dtls_id")
//	@SequenceGenerator(sequenceName = "track.seq_epod_dtls_id", name = "seq_epod_dtls_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "i_epod_id")
	private Integer i_epod_id;

	@Column(name = "s_epod_no")
	private String s_epod_no;

	@Type(type = "jsonb")
	@Column(name = "s_epod_dtls", columnDefinition = "json")
	private HashMap<String, Object> s_epod_dtls;

	@Column(name = "b_active", columnDefinition = "boolean DEFAULT true")
	private boolean b_active;

	@Column(name = "s_create_by")
	private String s_create_by;
	
	@Column(name = "dt_created_on", columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdOn;

	@Column(name = "dt_update_on")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dt_update_on;

	@Column(name = "s_update_by")
	private String s_update_by;

	@Column(name = "i_corp_id")
	private int i_corp_id;

	@Column(name = "dt_epod_receive", columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dt_epod_receive;

	@Column(name = "s_epod_receive_by")
	private String s_epod_receive_by;


	@Column(name = "dt_epod_acknow")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dt_epod_acknow;

	@Column(name = "s_epod_acknow_by")
	private String s_epod_acknow_by;

//	@Column(name = "s_trans_feedback", columnDefinition = "TEXT")
//	private String s_trans_feedback;
//
//	@Column(name = "dt_trans_feedback")
//	@Convert(converter = LocalDateTimeConverter.class)
//	private LocalDateTime dt_trans_feedback;
//
//	@Column(name = "s_trans_feedback_by")
//	private String s_trans_feedback_by;

	
	@Type(type = "jsonb")
	@Column(name = "s_feedback", columnDefinition = "json")
	private HashMap<String, Object> s_feedback;
	
	@Column(name = "s_veh_reg")
	private String vehRegNo;
	
	@Column(name = "i_veh_id")
	private int vehId;
	
	@Column(name = "i_dri_id",nullable=true)
	private Integer driId;
	
	@ManyToOne(optional=true,fetch=FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	private Driver driver;
//	
//	@ManyToOne
//	@JoinColumn(name = "i_veh_id")
//	private Vehicle vehicle;
	
	@Column(name = "s_dri_cont")
	private String driverContact;
	
	

	@Column(name = "s_status")
	private String status;
	
	@Column(name = "s_api_key")
	private String apiKey;
	
	@Type(type = "jsonb")
	@Column(name = "s_loaded_doc", columnDefinition = "json")
	private HashMap<String, Object> loadedDoc;

	public EpodDtls() {}
	
//	public Vehicle getVehicle() {
//		return vehicle;
//	}
//
//	public void setVehicle(Vehicle vehicle) {
//		this.vehicle = vehicle;
//	}

	
	public HashMap<String, Object> getLoadedDoc() {
		return loadedDoc;
	}

	public void setLoadedDoc(HashMap<String, Object> loadedDoc) {
		this.loadedDoc = loadedDoc;
	}

	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public int getVehId() {
		return vehId;
	}

	public void setVehId(int vehId) {
		this.vehId = vehId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getVehRegNo() {
		return vehRegNo;
	}

	public Integer getDriId() {
		return driId;
	}

	public void setDriId(Integer driId) {
		this.driId = driId;
	}

	public void setVehRegNo(String vehRegNo) {
		this.vehRegNo = vehRegNo;
	}

	public String getDriverContact() {
		return driverContact;
	}

	public void setDriverContact(String driverContact) {
		this.driverContact = driverContact;
	}

	public Integer getI_epod_id() {
		return i_epod_id;
	}

	public void setI_epod_id(Integer i_epod_id) {
		this.i_epod_id = i_epod_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getS_epod_no() {
		return s_epod_no;
	}

	public void setS_epod_no(String s_epod_no) {
		this.s_epod_no = s_epod_no;
	}

	public HashMap<String, Object> getS_epod_dtls() {
		return s_epod_dtls;
	}

	public void setS_epod_dtls(HashMap<String, Object> s_epod_dtls) {
		this.s_epod_dtls = s_epod_dtls;
	}

	public boolean isB_active() {
		return b_active;
	}

	public void setB_active(boolean b_active) {
		this.b_active = b_active;
	}

	public String getS_create_by() {
		return s_create_by;
	}

	public void setS_create_by(String s_create_by) {
		this.s_create_by = s_create_by;
	}

	public LocalDateTime getDt_update_on() {
		return dt_update_on;
	}

	public void setDt_update_on(LocalDateTime dt_update_on) {
		this.dt_update_on = dt_update_on;
	}

	public String getS_update_by() {
		return s_update_by;
	}

	public void setS_update_by(String s_update_by) {
		this.s_update_by = s_update_by;
	}

	public int getI_corp_id() {
		return i_corp_id;
	}

	public void setI_corp_id(int i_corp_id) {
		this.i_corp_id = i_corp_id;
	}

	public LocalDateTime getDt_epod_receive() {
		return dt_epod_receive;
	}

	public void setDt_epod_receive(LocalDateTime dt_epod_receive) {
		this.dt_epod_receive = dt_epod_receive;
	}

	public String getS_epod_receive_by() {
		return s_epod_receive_by;
	}

	public void setS_epod_receive_by(String s_epod_receive_by) {
		this.s_epod_receive_by = s_epod_receive_by;
	}


//	public String getS_cust_feedback_dtls() {
//		return s_cust_feedback_dtls;
//	}
//
//	public void setS_cust_feedback_dtls(String s_cust_feedback_dtls) {
//		this.s_cust_feedback_dtls = s_cust_feedback_dtls;
//	}

	

	public LocalDateTime getDt_epod_acknow() {
		return dt_epod_acknow;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public void setDt_epod_acknow(LocalDateTime dt_epod_acknow) {
		this.dt_epod_acknow = dt_epod_acknow;
	}

	public String getS_epod_acknow_by() {
		return s_epod_acknow_by;
	}

	public void setS_epod_acknow_by(String s_epod_acknow_by) {
		this.s_epod_acknow_by = s_epod_acknow_by;
	}


	public HashMap<String, Object> getS_feedback() {
		return s_feedback;
	}

	public void setS_feedback(HashMap<String, Object> s_feedback) {
		this.s_feedback = s_feedback;
	}



	
//	 @Override
//	    public boolean equals(Object o) {
//	        if (this == o) return true;
//	        if (!(o instanceof EpodDtls )) return false;
//	        return i_epod_id != null && i_epod_id.equals(((EpodDtls) o).getI_epod_id());
//	    }
//	    @Override
//	    public int hashCode() {
//	        return 31;
//	    }

}
