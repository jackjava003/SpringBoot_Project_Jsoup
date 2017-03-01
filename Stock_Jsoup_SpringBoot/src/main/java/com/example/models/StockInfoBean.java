package com.example.models;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Entity assign the class as a table to do OR mapping
@Scope("prototype")
@Component
@Entity
@Table(name = "stocksInfo")
public class StockInfoBean {

	@Id
	@Column(name = "stockID")
	private int stockID;
	@NotNull
	@Column(name = "stockName")
	private String stockName;
	@NotNull
	@Column(name = "businessType")
	private String businessType;
	@NotNull
	@Column(name = "president")
	private String president;
	@NotNull
	@Column(name = "capital")
	private String capital;
	@NotNull
	@Column(name = "monthlyRevenueChart", columnDefinition = "BLOB")
	private byte[] monthlyRevenueChart;
	@NotNull
	@Column(name = "quarterlyRevenueChart", columnDefinition = "BLOB")
	private byte[] quarterlyRevenueChart;
	@NotNull
	@Column(name = "lastUpdate")
	private Date lastUpdate;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "stockID")
	@OrderBy("month")
	private Set<StockMonthlyBean> stockMonthlyBeans;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "stockID")
	@OrderBy("quarterly")
	private Set<StockQuarterlyBean> stockQuarterlyBeans;

	public StockInfoBean() {
		super();
	}

	public int getStockID() {
		return stockID;
	}

	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public Set<StockQuarterlyBean> getStockQuarterlyBeans() {
		return stockQuarterlyBeans;
	}

	public void setStockQuarterlyBeans(Set<StockQuarterlyBean> stockQuarterlyBeans) {
		this.stockQuarterlyBeans = stockQuarterlyBeans;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public byte[] getMonthlyRevenueChart() {
		return monthlyRevenueChart;
	}

	public void setMonthlyRevenueChart(byte[] monthlyRevenueChart) {
		this.monthlyRevenueChart = monthlyRevenueChart;
	}

	public byte[] getQuarterlyRevenueChart() {
		return quarterlyRevenueChart;
	}

	public void setQuarterlyRevenueChart(byte[] quarterlyRevenueChart) {
		this.quarterlyRevenueChart = quarterlyRevenueChart;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<StockMonthlyBean> getStockMonthlyBeans() {
		return stockMonthlyBeans;
	}

	public void setStockMonthlyBeans(Set<StockMonthlyBean> stockMonthlyBeans) {
		this.stockMonthlyBeans = stockMonthlyBeans;
	}

	public Set<StockQuarterlyBean> getStockQuarterlyBean() {
		return stockQuarterlyBeans;
	}

	public void setStockQuarterlyBean(Set<StockQuarterlyBean> stockQuarterlyBeans) {
		this.stockQuarterlyBeans = stockQuarterlyBeans;
	}

	// public String toString() {
	// String str = String.format("查詢資料股票代號:%4d %-15s %10s %10s %10s %-20s\n",
	// getStock_id(), " 公司名稱: " + getStock_Name(), " 產業類別: "
	// + getBusiness_type(), " 董事長: " + getPres_Name(), "股本: "
	// + getCapital(), " 最後更新日期: " + getLast_update());
	// return str;
	// }
}
