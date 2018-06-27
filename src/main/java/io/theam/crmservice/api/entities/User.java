package io.theam.crmservice.api.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import io.theam.crmservice.api.enums.ProfileEnum;

@Entity
@Table (name = "users")
public class User implements Serializable{
	
	private static final long serialVersionUID = -3589435415041349797L;
	
	private Long id;
	private String name;
	private String surname;
	//	private TODO --> FILE UPLOAD 
	// https://javatutorial.net/java-file-upload-rest-service
	private String email;
	private String password;
	
	private ProfileEnum profile;
	private Date createDate;
	private Date updateDate;
	private Shop shop;
	private User father;
	
	//TODO -> setup get_father_id()
	
	public User() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "surname", nullable = false)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "profile", nullable = false)
	public ProfileEnum getProfile() {
		return profile;
	}
	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}
	
	@Column(name = "create_date", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "update_date", nullable = false)
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public User getFather() {
		return father;
	}
	public void setFather(User father) {
		this.father = father;
	}
	
	@PreUpdate
	public void preUpdate() {
		updateDate = new Date();
		// father = user who updated
		//TODO -> Insert ID of who updated
	}
	
	@PrePersist
	public void prePersist() {
		final Date currently = new Date();
		createDate = currently;
		updateDate = currently;
		// father = user who created
		//TODO -> Insert ID of who created
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname 
				+ ", email=" + email + ", password=" + password 
				+ ", profile=" + profile + ", createDate=" + createDate 
				+ ", updateDate=" + updateDate + ", shop=" + shop 
				+ ", father=" + father + "]";
	}
}
