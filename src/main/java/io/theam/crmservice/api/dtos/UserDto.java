package io.theam.crmservice.api.dtos;


import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;


public class UserDto {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private Optional<String> password;
	private Long father_id;
	//TODO -> Insert picture field
	//TODO -> Create father logic
	
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
	
	public Optional<String> getPassword() {
		return password;
	}
	public void setPassword(Optional<String> password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + "]";
	}

	public Long getFather_id() {
		return father_id;
	}

	public void setFather_id(Long father_id) {
		this.father_id = father_id;
	}
}
