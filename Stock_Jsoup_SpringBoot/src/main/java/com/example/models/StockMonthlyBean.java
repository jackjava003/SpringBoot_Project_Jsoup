package com.example.models;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "stockMonthlyChange")
public class StockMonthlyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8196675731851356044L;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "stockID", column = @Column(name = "stockID")),
			@AttributeOverride(name = "month", column = @Column(name = "month")) })
	MonthCompositeKey monthCompositeKey;

	@NotNull
	private long revenueIn105;
	@NotNull
	private BigDecimal increaseRateIn105;
	@NotNull
	private long revenueIn106;
	@NotNull
	private BigDecimal increaseRateIn106;
	@NotNull
	private long accumulateRevenueIn106;
	@NotNull
	private BigDecimal accumulateIncreaseRateIn106;
	@NotNull
	private String achieveRateIn106;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("stockID")
	@JoinColumn(name = "stockID")
	private StockInfoBean stockInfoBean;

	public StockMonthlyBean() {

	}

	public MonthCompositeKey getMonthCompositeKey() {
		return monthCompositeKey;
	}

	public void setMonthCompositeKey(MonthCompositeKey monthCompositeKey) {
		this.monthCompositeKey = monthCompositeKey;
	}

	public long getRevenueIn105() {
		return revenueIn105;
	}

	public void setRevenueIn105(long revenueIn105) {
		this.revenueIn105 = revenueIn105;
	}

	public BigDecimal getIncreaseRateIn105() {
		return increaseRateIn105;
	}

	public void setIncreaseRateIn105(BigDecimal increaseRateIn105) {
		this.increaseRateIn105 = increaseRateIn105;
	}

	public long getRevenueIn106() {
		return revenueIn106;
	}

	public void setRevenueIn106(long revenueIn106) {
		this.revenueIn106 = revenueIn106;
	}

	public BigDecimal getIncreaseRateIn106() {
		return increaseRateIn106;
	}

	public void setIncreaseRateIn106(BigDecimal increaseRateIn106) {
		this.increaseRateIn106 = increaseRateIn106;
	}

	public long getAccumulateRevenueIn106() {
		return accumulateRevenueIn106;
	}

	public void setAccumulateRevenueIn106(long accumulateRevenueIn106) {
		this.accumulateRevenueIn106 = accumulateRevenueIn106;
	}

	public BigDecimal getAccumulateIncreaseRateIn106() {
		return accumulateIncreaseRateIn106;
	}

	public void setAccumulateIncreaseRateIn106(BigDecimal accumulateIncreaseRateIn106) {
		this.accumulateIncreaseRateIn106 = accumulateIncreaseRateIn106;
	}

	public String getAchieveRateIn106() {
		return achieveRateIn106;
	}

	public void setAchieveRateIn106(String achieveRateIn106) {
		this.achieveRateIn106 = achieveRateIn106;
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
