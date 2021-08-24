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
	@Column(name = "userId", columnDefinition = "int(11)", nullable = false)
	private Integer id;
	
	@NotNull(message = "eventId is required") // foreign key
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
	@Column(name = "OCRD.cellular", nullable = false, columnDefinition = "varchar(50)")
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
	@Column(name = "CRD1.zipcode", nullable = false, columnDefinition = "varchar(20)")
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
	
}
