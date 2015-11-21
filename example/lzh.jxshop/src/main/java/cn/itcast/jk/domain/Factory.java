package cn.itcast.jk.domain;

public class Factory {

	private String id;
	private String fullName; //完整名称
	private String factoryName; //简称
	private String contractor; //联系方式
	private String phone; //固定电话
	private String mobile; //移动电话
	private String fax; //传真
	private String cnote; //备注
	private int orderNo; //排列顺序
	private int state; //是否有效状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getCnote() {
		return cnote;
	}
	public void setCnote(String cnote) {
		this.cnote = cnote;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Factory [id=" + id + ", fullName=" + fullName
				+ ", factoryName=" + factoryName + ", contractor=" + contractor
				+ ", phone=" + phone + ", mobile=" + mobile + ", fax=" + fax
				+ ", cnote=" + cnote + ", orderNo=" + orderNo + ", state="
				+ state + "]";
	}	
	
}
