package com.rosalieraz.importmodule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "emergency_numbers")
public class EmergencyNumbers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "numId", columnDefinition = "int(11)", nullable = false)
	private Integer id;
	
	
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	@NotNull(message = "type field is required")
	@PositiveOrZero
	private Integer type;
	
	
//	@NotBlank(message = "contactNumber field should neither be empty nor blank")
	@NotNull(message = "contactNumber field is required")
	@Column(name = "contactNumber", nullable = false/* , columnDefinition = "varchar(50)" */)
	private Integer number;
//	private String number;

	
	/*
	 * Constructors
	 */
	
	public EmergencyNumbers () {
		
	}
	
	public EmergencyNumbers (Integer id, @NotNull(message = "type field is required") @PositiveOrZero Integer type,
			@NotBlank(message = "contactNumber field should neither be empty nor blank") Integer number) {
		super();
		this.id = id;
		this.type = type;
		this.number = number;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
