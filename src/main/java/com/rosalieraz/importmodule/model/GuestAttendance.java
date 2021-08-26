package com.rosalieraz.importmodule.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "guest_attendance")
public class GuestAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer userId;
	
	@NotNull(message = "eventId is required") // foreign key, did not setup it already since Sir July mentioned models are already configured in the system
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer eventId;
	
	@NotNull(message = "paymentId is required") // foreign key
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer paymentId;
	
	@NotBlank(message = "OCRD.U_Fname is required")
	@Column(name = "OCRD.U_Fname", nullable = false, columnDefinition = "varchar(100)")
	private String fName;
	
	@NotBlank(message = "OCRD.U_Lname is required")
	@Column(name = "OCRD.U_Lname", nullable = false, columnDefinition = "varchar(100)")
	private String lName;
	
	@NotBlank(message = "OCRD.U_Mname is required")
	@Column(name = "OCRD.U_Mname", nullable = false, columnDefinition = "varchar(100)")
	private String mName;
	
	@NotBlank(message = "OCRD.U_Suffix is required")
	@Column(name = "OCRD.U_Suffix", nullable = false, columnDefinition = "char(5)")
	private String suffix;
	
	@NotBlank(message = "OCRD.E_Mail is required")
	@Column(name = "OCRD.E_Mail", nullable = false, columnDefinition = "varchar(100)")
	private String mail;
	
	@NotBlank(message = "OCRD.cellular is required")
	@Column(name = "OCRD.cellular", nullable = false , columnDefinition = "varchar(50)" )
	private String cellular;
	
	@NotBlank(message = "CRD1.street is required")
	@Column(name = "CRD1.street", nullable = false, columnDefinition = "varchar(100)")
	private String street;
	
	@NotBlank(message = "CRD1.city is required")
	@Column(name = "CRD1.city", nullable = false, columnDefinition = "varchar(100)")
	private String city;
	
	@NotBlank(message = "state is required")
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String state;
	
	@NotBlank(message = "CRD1.country is required")
	@Column(name = "CRD1.country", nullable = false, columnDefinition = "varchar(100)")
	private String country;
	
	@NotBlank(message = "CRD1.zipcode is required")
	@Column(name = "CRD1.zipcode", nullable = false , columnDefinition = "varchar(20)" )
	private String zipcode; 
	
	@NotBlank(message = "bikeModel is required")
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String bikeModel;
	
	@CreationTimestamp
	@Column(name = "OCRD.CreateDate", updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createDate;

	@UpdateTimestamp
	@Column(name = "OCRD.UpdateDate",updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updateDate;

	@NotNull(message = "createUserId field is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer createUserId;

	@NotNull(message = "updateUserId field is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer updateUserId;
	
	@Column(name = "OCRD.UpdateTS", columnDefinition = "int(11)", nullable = true)
	private Integer updateTS;
	
	@Column(name = "OCRD.CreateTS", columnDefinition = "int(11)", nullable = true)
	private Integer createTS;

	
	/*
	 * Constructors
	 */

	public GuestAttendance () {
		
	}
	
	public GuestAttendance(Integer userId, @NotNull(message = "eventId is required") Integer eventId,
			@NotNull(message = "paymentId is required") Integer paymentId,
			@NotBlank(message = "OCRD.U_Fname is required") String fName,
			@NotBlank(message = "OCRD.U_Lname is required") String lName,
			@NotBlank(message = "OCRD.U_Mname is required") String mName,
			@NotBlank(message = "OCRD.U_Suffix is required") String suffix,
			@NotBlank(message = "OCRD.E_Mail is required") String mail,
			@NotBlank(message = "OCRD.cellular is required") String cellular,
			@NotBlank(message = "CRD1.street is required") String street,
			@NotBlank(message = "CRD1.city is required") String city,
			@NotBlank(message = "state is required") String state,
			@NotBlank(message = "CRD1.country is required") String country,
			@NotBlank(message = "CRD1.zipcode is required") String zipcode,
			@NotBlank(message = "bikeModel is required") String bikeModel, Date createDate, Date updateDate,
			@NotNull(message = "createUserId field is required") Integer createUserId,
			@NotNull(message = "updateUserId field is required") Integer updateUserId, Integer updateTS,
			Integer createTS) {
		super();
		this.userId = userId;
		this.eventId = eventId;
		this.paymentId = paymentId;
		this.fName = fName;
		this.lName = lName;
		this.mName = mName;
		this.suffix = suffix;
		this.mail = mail;
		this.cellular = cellular;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.bikeModel = bikeModel;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.updateTS = updateTS;
		this.createTS = createTS;
	}
	
	
	/*
	 * Setters and Getter
	 */
	
	public Integer getId() {
		return userId;
	}

	public void setId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getBikeModel() {
		return bikeModel;
	}

	public void setBikeModel(String bikeModel) {
		this.bikeModel = bikeModel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(Integer updateTS) {
		this.updateTS = updateTS;
	}

	public Integer getCreateTS() {
		return createTS;
	}

	public void setCreateTS(Integer createTS) {
		this.createTS = createTS;
	}
	
}
