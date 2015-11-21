package cn.itcast.jk.domain;

/**
 * @Description:
 * @Author:	nutony
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014-3-20
 */
public class Export {
	private String id;
	private java.util.Date inputDate; //制单时间
	private String contractIds; //合同id集合,用来关联货物表来获取货物信息
	private String customerContract; //合同及确认书号(合同序号集合)
	private String lcNo; //信用证号
	private String consignee; //收货人及地址
	private String marks; //唛头
	private String shipmentPort; //装运港
	private String destinationPort; //目的港
	private String transportMode; //运送方式
	private String priceCondition; //价格条件
	private String remark;
	
	private Integer pnum;
	private Integer extnum;
	
	private Integer boxNum; //箱数
	private Integer cnumber; //货物数量
	private String packingUnit; //单位
	private String grossWeight; //毛重
	private String netWeight; //净重
	
	private Double sizeLength; //长
	private Double sizeWidth; //宽
	private Double sizeHeight; //高
	private Double csize; //尺寸
	private Double amount; //
	private Double noTax; //
	private Double costPrice; //
	private Double costTax; //
	private Integer state; //
	
	private String createBy;
	private String createDept;
	private java.util.Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPnum() {
		return pnum;
	}
	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}
	public Integer getExtnum() {
		return extnum;
	}
	public void setExtnum(Integer extnum) {
		this.extnum = extnum;
	}
	public java.util.Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(java.util.Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getContractIds() {
		return contractIds;
	}
	public void setContractIds(String contractIds) {
		this.contractIds = contractIds;
	}
	public String getCustomerContract() {
		return customerContract;
	}
	public void setCustomerContract(String customerContract) {
		this.customerContract = customerContract;
	}
	public String getLcNo() {
		return lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getShipmentPort() {
		return shipmentPort;
	}
	public void setShipmentPort(String shipmentPort) {
		this.shipmentPort = shipmentPort;
	}
	public String getDestinationPort() {
		return destinationPort;
	}
	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	public String getPriceCondition() {
		return priceCondition;
	}
	public void setPriceCondition(String priceCondition) {
		this.priceCondition = priceCondition;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
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
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public Double getSizeLength() {
		return sizeLength;
	}
	public void setSizeLength(Double sizeLength) {
		this.sizeLength = sizeLength;
	}
	public Double getSizeWidth() {
		return sizeWidth;
	}
	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}
	public Double getSizeHeight() {
		return sizeHeight;
	}
	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}
	public Double getCsize() {
		return csize;
	}
	public void setCsize(Double csize) {
		this.csize = csize;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getNoTax() {
		return noTax;
	}
	public void setNoTax(Double noTax) {
		this.noTax = noTax;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public Double getCostTax() {
		return costTax;
	}
	public void setCostTax(Double costTax) {
		this.costTax = costTax;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
}
