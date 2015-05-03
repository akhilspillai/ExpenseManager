package com.trip.expensemanager;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ToSync {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long changerId;
	private Long syncItemId;
	private String syncType;
	private Date creationDate;

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	public Long getSyncItemId() {
		return syncItemId;
	}

	public void setSyncItem(Long syncItemId) {
		this.syncItemId = syncItemId;
	}

	public Long getChangerId() {
		return changerId;
	}

	public void setChangerId(Long changerId) {
		this.changerId = changerId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
