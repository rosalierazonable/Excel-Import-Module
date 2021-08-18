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
	@Column(name = "eventId", columnDefinition = "int(11)", nullable = false)
	private Integer id;

	@NotNull
	@Column(name = "eventType", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private Integer type;

	@NotBlank
	@Column(name = "eventTitle", length = 50, nullable = false)
	private String title;

//	@NotBlank
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
	@Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createDate;

	@UpdateTimestamp
	@Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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

	
	/*
	 * Constructors
	 */

	public Events() {

	}

	public Events(Integer id, @NotNull Integer type, @NotBlank String title, @NotBlank String banner,
			@NotBlank String description, @NotNull Date startDate, @NotNull Date endDate, @NotNull Date regStart,
			@NotNull Date regEnd, Date createDate, Date updateDate, @NotNull Integer createUserId,
			@NotNull Integer updateUserId, @NotNull Integer isDeleted, @NotNull Integer isInternal, float paymentFee,
			@NotBlank String rideId, @NotBlank String location) {

		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.banner = banner;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.regStart = regStart;
		this.regEnd = regEnd;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.isDeleted = isDeleted;
		this.isInternal = isInternal;
		this.paymentFee = paymentFee;
		this.rideId = rideId;
		this.location = location;
	}

	
	/*
	 * Getters and Setters
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegStart() {
		return regStart;
	}

	public void setRegStart(Date regStart) {
		this.regStart = regStart;
	}

	public Date getRegEnd() {
		return regEnd;
	}

	public void setRegEnd(Date regEnd) {
		this.regEnd = regEnd;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(Integer isInternal) {
		this.isInternal = isInternal;
	}

	public float getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(float paymentFee) {
		this.paymentFee = paymentFee;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
