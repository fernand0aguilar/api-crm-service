package io.theam.crmservice.api.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "shop")
public class Shop implements Serializable{
	
	private static final long serialVersionUID = -5341231458818417558L;
	
	private Long id;
	private String shopName;
	private Date createDate;
	private Date updateDate;
	private List<User> users;
	
	public Shop() {	
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="shop_name", nullable=false)
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "create_date", nullable = false)
	public Date getCreationDate() {
		return createDate;
	}
	public void setCreationDate(Date creationDate) {
		this.createDate = creationDate;
	}
	
	@Column(name = "update_date", nullable = false)
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@PreUpdate
    public void preUpdate() {
        updateDate = new Date();
	}
	
	@PrePersist
    public void prePersist() {
        final Date currently = new Date();
        createDate = currently;
        updateDate = currently;
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", shopName=" + shopName 
				+ ", createDate=" + createDate 
				+ ", updateDate=" + updateDate
				+ ", users=" + users + "]";
	}
	
	
}

