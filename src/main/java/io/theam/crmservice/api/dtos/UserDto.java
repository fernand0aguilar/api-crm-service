package io.theam.crmservice.api.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import io.theam.crmservice.api.enums.ProfileEnum;


public class UserDto {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private ProfileEnum profile;
	private MultipartFile picture;
		
	public UserDto() {
	}
	
	//	Getter & Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Name cannot be empty.")
	@Length(min = 3, max = 30, message = "Name needs min 3 and max 30 characteres")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty(message = "Surname cannot be empty.")
	@Length(min = 3, max = 30, message = "Surname needs minimun 3 and maximun 30 characteres")	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotEmpty(message = "Email Cannot Be Empty")
	@Length(min = 5, max = 200, message = "Email needs minimum 5 and maximum 200 characteres.")
	@Email(message="Invalid Email.")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", profile=" + profile + ", picture=" + picture + "]";
	}
}
