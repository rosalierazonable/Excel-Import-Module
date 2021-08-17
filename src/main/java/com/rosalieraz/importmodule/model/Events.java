package com.rosalieraz.importmodule.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "events")
public class Events {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="eventId", columnDefinition = "int(11)", nullable = false)
	private Integer id;
	
	
	@NotNull
	@Column(name = "eventType", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private Integer type;
	
	
	@NotBlank
	@Column(name = "eventTitle", length = 50, nullable = false)
	private String title;
	
	
	@NotBlank
	@Column(length = 50, nullable = false)
	private String banner;
	
	
	@NotBlank
	@Column(length = 250, nullable = false)
	private String description;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registrationStart")
	private Date regStart;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registrationEnd")
	private Date regEnd;
	
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false,  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createDate;
	

	@UpdateTimestamp
	@Column(updatable = false, nullable = false,  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updateDate;
	
	
	@NotNull
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer createUserId;
	
	
	@NotNull
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer updateUserId;
	
	
	@NotNull
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private Integer isDeleted;

	
	@NotNull
	@Column(nullable = false, columnDefinition = "TINYINT(1)  DEFAULT 0")
	private Integer isInternal;
	
	
	@Column(precision = 4)
	private float paymentFee;
	
	
	@NotBlank
	@Column(length = 100)
	private String rideId;
	
	@NotBlank
	@Column(nullable = false, length = 50)
	private String location;
	
}
