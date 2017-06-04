package com.sketch.makemysketch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class OrderDetail implements Serializable {

	private String orderId;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String deliveryType;
	private String orderType;
	private String imageName;
	private String totalAmt;
	private String paidAmt;
	private String deliveryAmt;
	private String transactionStatus;
    @JsonIgnore
    private String imageUri;

	public enum TransationStatus{SUCCESS, FAILED, INPROGRESS};
    public enum DeliveryType{ONLINE, COD};
    public enum OrderType{SKETCH, PAINTING};
	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return the name
	 */
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * @return the totalAmt
	 */
	public String getTotalAmt() {
		return totalAmt;
	}
	/**
	 * @param totalAmt the totalAmt to set
	 */
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	/**
	 * @return the paidAmt
	 */
	public String getPaidAmt() {
		return paidAmt;
	}
	/**
	 * @param paidAmt the paidAmt to set
	 */
	public void setPaidAmt(String paidAmt) {
		this.paidAmt = paidAmt;
	}
	/**
	 * @return the deliveryAmt
	 */
	public String getDeliveryAmt() {
		return deliveryAmt;
	}
	/**
	 * @param deliveryAmt the deliveryAmt to set
	 */
	public void setDeliveryAmt(String deliveryAmt) {
		this.deliveryAmt = deliveryAmt;
	}

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
