package cn.itcast.jk.domain.vo;

import java.util.List;

import cn.itcast.jk.domain.Factory;

/**
 * @Description:
 * @Author:	nutony
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014-3-16
 */
public class ContractProduct {
	private String contractProductId;
	
	private String productNo;
	private String productDesc;
	private Integer cnumber;
	private String packingUnit;
	private Double price;
	private Double amount;
	private String productImage;
	private Factory factory;
	private List<ExtCproduct> extCproducts;
	
	public String getContractProductId() {
		return contractProductId;
	}
	public void setContractProductId(String contractProductId) {
		this.contractProductId = contractProductId;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Integer getCnumber() {
		return cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}
	public String getPackingUnit() {
		return packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public List<ExtCproduct> getExtCproducts() {
		return extCproducts;
	}
	public void setExtCproducts(List<ExtCproduct> extCproducts) {
		this.extCproducts = extCproducts;
	}
	@Override
	public String toString() {
		return "ContractProduct [contractProductId=" + contractProductId
				+ ", productNo=" + productNo + ", productDesc=" + productDesc
				+ ", cnumber=" + cnumber + ", packingUnit=" + packingUnit
				+ ", price=" + price + ", amount=" + amount + ", productImage="
				+ productImage + ", factory=" + factory + ", extCproducts="
				+ extCproducts + "]";
	}
	
}
