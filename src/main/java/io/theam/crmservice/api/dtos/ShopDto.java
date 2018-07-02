package io.theam.crmservice.api.dtos;

public class ShopDto {
	private Long id;
	private String shopName;
	
	public ShopDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "ShopDto [id=" + id + ", shopName=" + shopName + "]";
	}
	
	
}
