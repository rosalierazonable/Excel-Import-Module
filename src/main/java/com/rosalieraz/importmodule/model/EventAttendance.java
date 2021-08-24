package com.rosalieraz.importmodule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.rosalieraz.importmodule.EventAttendanceId;

@SuppressWarnings("serial")
@Entity
@Table(name = "event_attendance")
@IdClass(EventAttendanceId.class)
public class EventAttendance implements Serializable {

	@Id
	@NotNull(message = "eventId is required")	
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer eventId;
	
	@Id
	@NotNull(message = "userId is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer userId;
	
	@Id
	@NotNull(message = "paymentId is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer paymentId;
	
	@NotNull(message = "signUpDate field is required")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signUpDate")
	private Date signUpDate;
	
	
	/*
	 * Constructors
	 */
	
	public EventAttendance () {
		
	}


	public EventAttendance(@NotNull(message = "eventId is required") Integer eventId,
			@NotNull(message = "userId is required") Integer userId, Integer paymentId,
			@NotNull(message = "signUpDate field is required") Date signUpDate) {
		super();
		this.eventId = eventId;
		this.userId = userId;
		this.paymentId = paymentId;
		this.signUpDate = signUpDate;
	}

	
	/*
	 * Setters and Getter
	 */
	

	public Integer getEventId() {
		return eventId;
	}


	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}


	public Date getSignUpDate() {
		return signUpDate;
	}


	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}
	
}
