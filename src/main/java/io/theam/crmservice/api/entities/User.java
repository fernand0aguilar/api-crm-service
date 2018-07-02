package io.theam.crmservice.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import io.theam.crmservice.api.enums.ProfileEnum;

@Entity
@Table (name = "users")
public class User implements Serializable{
	
	private static final long serialVersionUID = -3589435415041349797L;
	
	private Long id;
	    
	private String name;
	private String surname;
	private String email;
	private String password;
	
	// private TODO --> FILE UPLOAD 
	// https://javatutorial.net/java-file-upload-rest-service
	
	private ProfileEnum profile;
	private Date createDate;
	private Date updateDate;
	private Shop shop;
	
	private User parent;	
    private List<User> children;
	

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
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy="parent")
	public List<User> getChildren() {
		return children;
	}
	public void setChildren(List<User> children) {
		this.children = children;
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
				+ ", parent=" + parent + "]";
	}
}
