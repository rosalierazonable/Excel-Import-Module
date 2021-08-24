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
@Table(name = "partners")
public class Partners {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partnerId", columnDefinition = "int(11)", nullable = false)
	private Integer id;

	@NotBlank(message = "username field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String username;

	@NotBlank(message = "password field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String password;

	@Column(nullable = true, columnDefinition = "varchar(50)")
	private String code;

	@NotBlank(message = "name field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "varchar(125)")
	private String name;

	@NotBlank(message = "email field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "varchar(125)")
	private String email;

	@Column(nullable = true, columnDefinition = "varchar(200)")
	private String image;

	@Column(nullable = true, columnDefinition = "text")
	private String description;

	@Column(nullable = true)
	private double sponsorship;

	@NotNull(message = "startDate field is required")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@NotNull(message = "endDate field is required")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@NotNull(message = "listOrder field is required")
	@Column(name = "listOrder", columnDefinition = "TINYINT(5)", nullable = false)
	@PositiveOrZero
	private Integer order;

	@Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
	@PositiveOrZero
	private Integer isDeleted;

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

	@Column(name = "isFeaturedPartner", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
	@PositiveOrZero
	private Integer isFeatured;

	/*
	 * Constructors
	 */

	public Partners() {

	}

	public Partners(Integer id, @NotBlank(message = "username field should neither be empty nor blank") String username,
			@NotBlank(message = "password field should neither be empty nor blank") String password, String code,
			@NotBlank(message = "name field should neither be empty nor blank") String name,
			@NotBlank(message = "email field should neither be empty nor blank") String email, String image,
			String description, double sponsorship, @NotNull(message = "startDate field is required") Date startDate,
			@NotNull(message = "endDate field is required") Date endDate,
			@NotNull(message = "listOrder field is required") @PositiveOrZero Integer order,
			@PositiveOrZero Integer isDeleted, Date createDate, Date updateDate,
			@NotNull(message = "createUserId field is required") Integer createUserId,
			@NotNull(message = "updateUserId field is required") Integer updateUserId,
			@PositiveOrZero Integer isFeatured) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.code = code;
		this.name = name;
		this.email = email;
		this.image = image;
		this.description = description;
		this.sponsorship = sponsorship;
		this.startDate = startDate;
		this.endDate = endDate;
		this.order = order;
		this.isDeleted = isDeleted;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.isFeatured = isFeatured;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(double sponsorship) {
		this.sponsorship = sponsorship;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public Integer getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Integer isFeatured) {
		this.isFeatured = isFeatured;
	}

}
