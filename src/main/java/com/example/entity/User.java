package com.example.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "H_User")
public class User {
	@Id
	@SequenceGenerator(name = "userIdSeq", sequenceName = "USER_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdSeq")
	@Column(name = "userId")
	private int userId;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Transient
	private String confirmPassword;

	@Column(name = "email")
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDateTime")
	private Calendar createdDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifiedDateTime")
	private Calendar modifiedDateTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Calendar getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Calendar modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getCreatedDateTimeInStringFormate() {
		String output = null;
		if (createdDateTime != null) {
			SimpleDateFormat dateTimeFormate = new SimpleDateFormat("yyyy-MM-dd '|' HH:mm:ss");
			output = dateTimeFormate.format(createdDateTime.getTime());
		}
		return output;
	}

	public String getModifiedDateTimeInStringFormate() {
		String output = null;
		if (modifiedDateTime != null) {
			SimpleDateFormat dateTimeFormate = new SimpleDateFormat("yyyy-MM-dd '|' HH:mm:ss");
			output = dateTimeFormate.format(modifiedDateTime.getTime());
		}
		return output;
	}
}
