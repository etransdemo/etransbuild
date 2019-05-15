package com.epodSystem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.epodSystem.converter.LocalDateTimeConverter;




@Entity
@Table(name = "tbl_trip" , schema = "track")
public class TripEntity {
	
	//private int i_trip_id;
	
	private Integer i_inv_id;
	private String s_inv_no;

	private Integer i_epod_id;
	
	private String s_epod_attached_by;
	
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dt_epod_attached;
	
	private String s_epod_no;
	private String s_inv_attached_by;
	
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dt_inv_attached;
	
	@Id
	private String s_loading_advice_no;
	
//	@ManyToOne
//	@JoinColumn(name = "i_epod_id")
//	private ePOD_Dtls_entity epodDtls;
	
//	
//	public int getI_trip_id() {
//		return i_trip_id;
//	}
//	public void setI_trip_id(int i_trip_id) {
//		this.i_trip_id = i_trip_id;
//	}

	public Integer getI_inv_id() {
		return i_inv_id;
	}
	public void setI_inv_id(Integer i_inv_id) {
		this.i_inv_id = i_inv_id;
	}
	public Integer getI_epod_id() {
		return i_epod_id;
	}
	public void setI_epod_id(Integer i_epod_id) {
		this.i_epod_id = i_epod_id;
	}
	
	public String getS_inv_no() {
		return s_inv_no;
	}
	public void setS_inv_no(String s_inv_no) {
		this.s_inv_no = s_inv_no;
	}

	public String getS_epod_attached_by() {
		return s_epod_attached_by;
	}
	public void setS_epod_attached_by(String s_epod_attached_by) {
		this.s_epod_attached_by = s_epod_attached_by;
	}
	public LocalDateTime getDt_epod_attached() {
		return dt_epod_attached;
	}
	public void setDt_epod_attached(LocalDateTime dt_epod_attached) {
		this.dt_epod_attached = dt_epod_attached;
	}
	public String getS_epod_no() {
		return s_epod_no;
	}
	public void setS_epod_no(String s_epod_no) {
		this.s_epod_no = s_epod_no;
	}
	public String getS_inv_attached_by() {
		return s_inv_attached_by;
	}
	public void setS_inv_attached_by(String s_inv_attached_by) {
		this.s_inv_attached_by = s_inv_attached_by;
	}
	public LocalDateTime getDt_inv_attached() {
		return dt_inv_attached;
	}
	public void setDt_inv_attached(LocalDateTime dt_inv_attached) {
		this.dt_inv_attached = dt_inv_attached;
	}
	public String getS_loading_advice_no() {
		return s_loading_advice_no;
	}
	public void setS_loading_advice_no(String s_loading_advice_no) {
		this.s_loading_advice_no = s_loading_advice_no;
	}
	
	

}
