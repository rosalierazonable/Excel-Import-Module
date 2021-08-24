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
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "interests")
public class Interests {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interestId", columnDefinition = "int(11)", nullable = false)
	private Integer id;
	
	@NotBlank(message = "name field should neither be empty nor blank")
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String name;
	
	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String description;
	
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
	
	
	/*
	 * Constructors
	 */
	
	public Interests () {
		
	}


	public Interests(Integer id, @NotBlank(message = "name field should neither be empty nor blank") String name,
			String description, @PositiveOrZero Integer isDeleted, Date createDate, Date updateDate,
			@NotNull(message = "createUserId field is required") Integer createUserId,
			@NotNull(message = "updateUserId field is required") Integer updateUserId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isDeleted = isDeleted;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
	
}
