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
@Table(name = "stocks_info")
public class Stock_infoBean {
	
	@Id
	@Column(name = "stockID")
	private int stockID;
	@NotNull
	@Column(name = "stock_Name")
	private String stock_Name;
	@NotNull
	@Column(name = "business_type")
	private String business_type;
	@NotNull
	@Column(name = "pres_Name")
	private String pres_Name;
	@NotNull
	@Column(name = "capital")
	private String capital;
	@NotNull
	@Column(name = "mon_annu_graph",columnDefinition="BLOB")
	private byte[] mon_annu_graph;
	@NotNull
	@Column(name = "quart_annu_graph", columnDefinition="BLOB")
	private byte[] quart_annu_graph;
	@NotNull
	@Column(name = "last_update")
	private Date last_update;
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stockID")
	@OrderBy("month")
	private Set<Stock_monthBean> stock_monthBeans;
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stockID")
	@OrderBy("quarterly")
	private Set<Stock_quartBean> stock_quartBean;

	public Stock_infoBean() {
		super();
	}

	public int getStockID() {
		return stockID;
	}


	public void setStockID(int stockID) {
		this.stockID = stockID;
	}


	public String getStock_Name() {
		return stock_Name;
	}


	public void setStock_Name(String stock_Name) {
		this.stock_Name = stock_Name;
	}


	public String getBusiness_type() {
		return business_type;
	}


	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}


	public String getPres_Name() {
		return pres_Name;
	}


	public void setPres_Name(String pres_Name) {
		this.pres_Name = pres_Name;
	}


	public String getCapital() {
		return capital;
	}


	public void setCapital(String capital) {
		this.capital = capital;
	}


	public byte[] getMon_annu_graph() {
		return mon_annu_graph;
	}


	public void setMon_annu_graph(byte[] mon_annu_graph) {
		this.mon_annu_graph = mon_annu_graph;
	}


	public byte[] getQuart_annu_graph() {
		return quart_annu_graph;
	}


	public void setQuart_annu_graph(byte[] quart_annu_graph) {
		this.quart_annu_graph = quart_annu_graph;
	}


	public Date getLast_update() {
		return last_update;
	}


	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	

	public Set<Stock_monthBean> getStock_monthBeans() {
		return stock_monthBeans;
	}


	public void setStock_monthBeans(Set<Stock_monthBean> stock_monthBeans) {
		this.stock_monthBeans = stock_monthBeans;
	}
	
	public Set<Stock_quartBean> getStock_quartBean() {
		return stock_quartBean;
	}


	public void setStock_quartBean(Set<Stock_quartBean> stock_quartBean) {
		this.stock_quartBean = stock_quartBean;
	}


//	public String toString() {
//		String str = String.format("查詢資料股票代號:%4d %-15s %10s %10s %10s %-20s\n",
//				getStock_id(), " 公司名稱: " + getStock_Name(), " 產業類別: "
//						+ getBusiness_type(), " 董事長: " + getPres_Name(), "股本: "
//						+ getCapital(), " 最後更新日期: " + getLast_update());
//		return str;
//	}
}
