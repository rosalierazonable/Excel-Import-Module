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
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "payment")
public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paymentId", columnDefinition = "int(11)", nullable = false)
	private Integer id;
	
	@NotNull(message = "userId is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer userId;
	
	@NotNull(message = "paymentType is required")
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	@PositiveOrZero
	private Integer paymentType;
	
	@NotBlank(message = "transactionCode field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String transactionCode;
	
	@Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
	@PositiveOrZero
	private Integer isRecurring;
	
	@NotNull(message = "paymentDate field is required")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	
	@NotBlank(message = "paymentFileName field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "text")
	private String pFName;
	
	@NotBlank(message = "paymentDescription field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "text")
	private String pDescription;
	
	@NotNull(message = "nextBillingDate field is required")
	@Column(name = "nextBillingDate", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date billingDate;
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createDate;

	@UpdateTimestamp
	@Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updateDate;

	@NotNull(message = "createUserId field is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer createUserId;

	@NotNull(message = "updateUserId field is required")
	@Column(columnDefinition = "int(11)", nullable = false)
	private Integer updateUserId;
	
	/*
	 * Constructors
	 */
	
	public Payments () {
		
	}

	public Payments(Integer id, @NotNull(message = "userId is required") Integer userId,
			@NotNull(message = "paymentType is required") @PositiveOrZero Integer paymentType,
			@NotBlank(message = "transactionCode field should neither be empty nor blank") String transactionCode,
			@PositiveOrZero Integer isRecurring, @NotNull(message = "paymentDate field is required") Date paymentDate,
			@NotBlank(message = "paymentFieldName field should neither be empty nor blank") String paymentFieldName,
			@NotBlank(message = "paymentDescription field should neither be empty nor blank") String paymentDescription,
			@NotNull(message = "nextBillingDate field is required") Date billingDate, Date createDate, Date updateDate,
			@NotNull(message = "createUserId field is required") Integer createUserId,
			@NotNull(message = "updateUserId field is required") Integer updateUserId) {
		super();
		this.id = id;
		this.userId = userId;
		this.paymentType = paymentType;
		this.transactionCode = transactionCode;
		this.isRecurring = isRecurring;
		this.paymentDate = paymentDate;
		this.pFName = paymentFieldName;
		this.pDescription = paymentDescription;
		this.billingDate = billingDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
	}
	
	
	/*
	 * Setters and Getters
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public Integer getIsRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(Integer isRecurring) {
		this.isRecurring = isRecurring;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getpFName() {
		return pFName;
	}

	public void setpFName(String paymentFieldName) {
		this.pFName = paymentFieldName;
	}

	public String getpDescription() {
		return pDescription;
	}

	public void setPDescription(String paymentDescription) {
		this.pDescription = paymentDescription;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
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

}
