package io.theam.crmservice.api.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {
	
	private String email;
	private String password;

	public JwtAuthenticationDto() {
	}

	@NotEmpty(message = "Email Cannot Be Empty")
	@Email(message = "Invalid Email .")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Password Cannot Be Empty")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [email=" + email + ", password=" + password + "]";
	}

}
