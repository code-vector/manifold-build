package com.lanyine.manifold.base.beans;

import java.io.Serializable;

import com.lanyine.manifold.base.enums.CallerFrom;

/**
 * 服务代用来源
 *
 * @author shadow
 */
public final class RequestHeader implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作人ID （网站后台管理人员）
     */
    private Integer operatorId; // required

    /**
     * 操作人名称（网站后台管理人员）
     */
    private String operatorName; // required

    /**
     * 客户ID
     */
    private Integer customerId; // required

    /**
     * 客户名称
     */

    private String customerName; // optional

    /**
     * 客户类型
     */
    private Integer customerType;

    /**
     * 调用源
     *
     * @see CallerFrom
     */

    private CallerFrom callerFrom; // optional

    /**
     * 调用源IP
     */
    private String callerIP; // optional

    /**
     * 获取instance
     *
     * @return instance instance
     */
    public static RequestHeader getInstance() {
        return new RequestHeader();
    }

    /**
     * @return the 操作人ID（网站后台管理人员）
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * @param 操作人ID（网站后台管理人员） the operatorId to set
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the 操作人名称（网站后台管理人员）
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * @param 操作人名称（网站后台管理人员） the operatorName to set
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * @return the 客户ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param 客户ID the customerId to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the 客户名称
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param 客户名称 the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the 客户类型
     */
    public Integer getCustomerType() {
        return customerType;
    }

    /**
     * @param 客户类型 the customerType to set
     */
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    /**
     * @return the 调用源@seeCallerFrom
     */
    public CallerFrom getCallerFrom() {
        return callerFrom;
    }

    /**
     * @param 调用源@seeCallerFrom the callerFrom to set
     */
    public void setCallerFrom(CallerFrom callerFrom) {
        this.callerFrom = callerFrom;
    }

    /**
     * @return the 调用源IP
     */
    public String getCallerIP() {
        return callerIP;
    }

    /**
     * @param 调用源IP the callerIP to set
     */
    public void setCallerIP(String callerIP) {
        this.callerIP = callerIP;
    }

}
