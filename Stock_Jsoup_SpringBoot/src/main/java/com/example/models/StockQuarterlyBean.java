package com.example.models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Entity assign the class as a table to do OR mapping
@Component
@Scope("prototype")
@Entity
@Table(name = "stockQuarterlyChange")
public class StockQuarterlyBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3262128593029797404L;

	@EmbeddedId
    @AttributeOverrides
    ({
            @AttributeOverride(name = "stockID", column = @Column(name = "stockID")),
            @AttributeOverride(name = "quarterly", column = @Column(name = "quarterly"))
    })
	QuartCompositeKey quartCompositeKey;
	
	@NotNull //AT = after tax 
	private long profitAT104;
	@NotNull
	private String profitRateAT104;
	@NotNull //BT = before tax
	private long profitBT104;
	@NotNull
	private String profitRateBT104;
	@NotNull
	private long profitAT105;
	@NotNull
	private String profitRateAT105;
	@NotNull
	private String achieveRateAT105;
	@NotNull
	private long profitBT105;
	@NotNull
	private String profitRateBT105;
	@NotNull
	private String achieveRateBT105;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @MapsId("stockID")
    @JoinColumn(name = "stockID")
	private StockInfoBean stockInfoBean;

	public StockQuarterlyBean() {
		super();
	}

	public QuartCompositeKey getQuartCompositeKey() {
		return quartCompositeKey;
	}

	public void setQuartCompositeKey(QuartCompositeKey quartCompositeKey) {
		this.quartCompositeKey = quartCompositeKey;
	}

	public long getProfitAT104() {
		return profitAT104;
	}

	public void setProfitAT104(long profitAT104) {
		this.profitAT104 = profitAT104;
	}

	public String getProfitRateAT104() {
		return profitRateAT104;
	}

	public void setProfitRateAT104(String profitRateAT104) {
		this.profitRateAT104 = profitRateAT104;
	}

	public long getProfitBT104() {
		return profitBT104;
	}

	public void setProfitBT104(long profitBT104) {
		this.profitBT104 = profitBT104;
	}

	public String getProfitRateBT104() {
		return profitRateBT104;
	}

	public void setProfitRateBT104(String profitRateBT104) {
		this.profitRateBT104 = profitRateBT104;
	}

	public long getProfitAT105() {
		return profitAT105;
	}

	public void setProfitAT105(long profitAT105) {
		this.profitAT105 = profitAT105;
	}

	public String getProfitRateAT105() {
		return profitRateAT105;
	}

	public void setProfitRateAT105(String profitRateAT105) {
		this.profitRateAT105 = profitRateAT105;
	}

	public String getAchieveRateAT105() {
		return achieveRateAT105;
	}

	public void setAchieveRateAT105(String achieveRateAT105) {
		this.achieveRateAT105 = achieveRateAT105;
	}

	public long getProfitBT105() {
		return profitBT105;
	}

	public void setProfitBT105(long profitBT105) {
		this.profitBT105 = profitBT105;
	}

	public String getProfitRateBT105() {
		return profitRateBT105;
	}

	public void setProfitRateBT105(String profitRateBT105) {
		this.profitRateBT105 = profitRateBT105;
	}

	public String getAchieveRateBT105() {
		return achieveRateBT105;
	}

	public void setAchieveRateBT105(String achieveRateBT105) {
		this.achieveRateBT105 = achieveRateBT105;
	}

	public StockInfoBean getStockInfoBean() {
		return stockInfoBean;
	}

	public void setStockInfoBean(StockInfoBean stockInfoBean) {
		this.stockInfoBean = stockInfoBean;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
