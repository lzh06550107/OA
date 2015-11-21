package cn.itcast.jk.domain;

import java.io.Serializable;

/**
 * 
 * @author:LZH
 * @time:2015年7月14日
 */
public class Contract implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id; 
	private String cpnum; //当前合同下有几个货物
	private String epnum; //所有货物共有几个附件
	private String offeror; //收购方（杰信）
	private String contractNo; //合同号（出货表中订单号）
	private java.util.Date signingDate; //签单日期
	private String inputBy; //制单人
	private String checkBy; //审单人
	private String inspector; //验货员
	private Double totalAmount; //总金额
	private Double cptotal; //合同货物总费用
	private Double eptotal; //合同附件总费用
	private String crequest; //具体需求描述
	private String customName; //客户名称（出货表中客人）
	private java.util.Date shipTime; //发货日期（出货表中的船期）
	private Integer importNum; //重要程度
	private java.util.Date deliveryPeriod; //交货日期
	private String remark; //说明信息
	private String printStyle; //记录打印类型
	private Integer oldState; //归档前状态(方便回退)
	private Integer state; //0草稿1上报待报运
	private Integer outState; //走货状态(0未走货1部分2全部)
	private String tradeTerms; //贸易条款（出货表中贸易合同）
	
	private String createBy; //创建人
	private String createDept; //创建部门
	private java.util.Date createTime; //创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfferor() {
		return offeror;
	}
	public void setOfferor(String offeror) {
		this.offeror = offeror;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public java.util.Date getSigningDate() {
		return signingDate;
	}
	public void setSigningDate(java.util.Date signingDate) {
		this.signingDate = signingDate;
	}
	public String getInputBy() {
		return inputBy;
	}
	public void setInputBy(String inputBy) {
		this.inputBy = inputBy;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCrequest() {
		return crequest;
	}
	public void setCrequest(String crequest) {
		this.crequest = crequest;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public java.util.Date getShipTime() {
		return shipTime;
	}
	public void setShipTime(java.util.Date shipTime) {
		this.shipTime = shipTime;
	}
	public Integer getImportNum() {
		return importNum;
	}
	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}
	public java.util.Date getDeliveryPeriod() {
		return deliveryPeriod;
	}
	public void setDeliveryPeriod(java.util.Date deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPrintStyle() {
		return printStyle;
	}
	public void setPrintStyle(String printStyle) {
		this.printStyle = printStyle;
	}
	public Integer getOldState() {
		return oldState;
	}
	public void setOldState(Integer oldState) {
		this.oldState = oldState;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getOutState() {
		return outState;
	}
	public void setOutState(Integer outState) {
		this.outState = outState;
	}
	public String getTradeTerms() {
		return tradeTerms;
	}
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getCpnum() {
		return cpnum;
	}
	public void setCpnum(String cpnum) {
		this.cpnum = cpnum;
	}
	public String getEpnum() {
		return epnum;
	}
	public void setEpnum(String epnum) {
		this.epnum = epnum;
	}
	public Double getCptotal() {
		return cptotal;
	}
	public void setCptotal(Double cptotal) {
		this.cptotal = cptotal;
	}
	public Double getEptotal() {
		return eptotal;
	}
	public void setEptotal(Double eptotal) {
		this.eptotal = eptotal;
	}
	
}
