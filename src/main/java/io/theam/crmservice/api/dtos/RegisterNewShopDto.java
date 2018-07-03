package io.theam.crmservice.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class RegisterNewShopDto {

	private Long id;
	
	// Shop Owner Informations
	private String userName;
	private String surname;
	private String password;
	private String email;
		
	private String shopName;

	public RegisterNewShopDto() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Name cannot be empty.")
	@Length(min = 3, max = 30, message = "Name needs min 3 and max 30 characteres")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String name) {
		userName = name;
	}
	
	@NotEmpty(message = "Surname cannot be empty.")
	@Length(min = 3, max = 30, message = "Surname needs minimun 3 and maximun 30 characteres")
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotEmpty(message = "Password cannot be empty.")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	@NotEmpty(message = "Shop Name cannot be empty.")
	@Length(min = 5, max = 200, message = "Shop Name needs minimun 5 and maximun 200 characteres")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	

	@Override
	public String toString() {
		return "RegisterShopDto [id=" + id + ", UserName=" + userName + ", surname=" + surname + ", password="
				+ password + ", email=" + email + ", shopName=" + shopName + "]";
	}
	
}
