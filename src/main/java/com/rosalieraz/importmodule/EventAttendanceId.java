package com.rosalieraz.importmodule;

import java.io.Serializable;

public class EventAttendanceId implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Integer eventId;
	private Integer userId;
	private Integer paymentId;

	
	/*
	 * Constructors
	 */	
    
	public EventAttendanceId() {
    }

	public EventAttendanceId(Integer eventId, Integer userId) {
		super();
		this.eventId = eventId;
		this.userId = userId;
	}

	
	/*
	 * Setters and Getters
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


	@Override
	public int hashCode() {
		return super.hashCode(); // not sure how to override this
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
        
		if (o == null || getClass() != o.getClass()) 
        	return false;

		EventAttendanceId eaId = (EventAttendanceId) o;
        return eventId.equals(eaId.eventId) &&
        		userId.equals(eaId.userId) && paymentId.equals(eaId.paymentId);
	}
	
}


// reference
// https://attacomsian.com/blog/spring-data-jpa-composite-primary-key