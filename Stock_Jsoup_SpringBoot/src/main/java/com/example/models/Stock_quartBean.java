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
@Table(name = "quart_change")
public class Stock_quartBean implements Serializable{
	
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
	Quart_CompositeKey quart_CompositeKey;
	
	@NotNull
	private long profit_AT_104;
	@NotNull
	private String profit_rate_AT_104;
	@NotNull
	private long profit_BT_104;
	@NotNull
	private String profit_rate_BT_104;
	@NotNull
	private long profit_AT_105;
	@NotNull
	private String profit_rate_AT_105;
	@NotNull
	private String achieve_rate_AT_105;
	@NotNull
	private long profit_BT_105;
	@NotNull
	private String profit_rate_BT_105;
	@NotNull
	private String achieve_rate_BT_105;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @MapsId("stockID")
    @JoinColumn(name = "stockID")
	private Stock_infoBean stock_infoBean;

	public Stock_quartBean() {
		super();
	}

	public Quart_CompositeKey getQuart_CompositeKey() {
		return quart_CompositeKey;
	}

	public void setQuart_CompositeKey(Quart_CompositeKey quart_CompositeKey) {
		this.quart_CompositeKey = quart_CompositeKey;
	}

	public long getProfit_AT_104() {
		return profit_AT_104;
	}

	public void setProfit_AT_104(long profit_AT_104) {
		this.profit_AT_104 = profit_AT_104;
	}

	public String getProfit_rate_AT_104() {
		return profit_rate_AT_104;
	}

	public void setProfit_rate_AT_104(String profit_rate_AT_104) {
		this.profit_rate_AT_104 = profit_rate_AT_104;
	}

	public long getProfit_BT_104() {
		return profit_BT_104;
	}

	public void setProfit_BT_104(long profit_BT_104) {
		this.profit_BT_104 = profit_BT_104;
	}

	public String getProfit_rate_BT_104() {
		return profit_rate_BT_104;
	}

	public void setProfit_rate_BT_104(String profit_rate_BT_104) {
		this.profit_rate_BT_104 = profit_rate_BT_104;
	}

	public long getProfit_AT_105() {
		return profit_AT_105;
	}

	public void setProfit_AT_105(long profit_AT_105) {
		this.profit_AT_105 = profit_AT_105;
	}

	public String getProfit_rate_AT_105() {
		return profit_rate_AT_105;
	}

	public void setProfit_rate_AT_105(String profit_rate_AT_105) {
		this.profit_rate_AT_105 = profit_rate_AT_105;
	}

	public String getAchieve_rate_AT_105() {
		return achieve_rate_AT_105;
	}

	public void setAchieve_rate_AT_105(String achieve_rate_AT_105) {
		this.achieve_rate_AT_105 = achieve_rate_AT_105;
	}

	public long getProfit_BT_105() {
		return profit_BT_105;
	}

	public void setProfit_BT_105(long profit_BT_105) {
		this.profit_BT_105 = profit_BT_105;
	}

	public String getProfit_rate_BT_105() {
		return profit_rate_BT_105;
	}

	public void setProfit_rate_BT_105(String profit_rate_BT_105) {
		this.profit_rate_BT_105 = profit_rate_BT_105;
	}

	public String getAchieve_rate_BT_105() {
		return achieve_rate_BT_105;
	}

	public void setAchieve_rate_BT_105(String achieve_rate_BT_105) {
		this.achieve_rate_BT_105 = achieve_rate_BT_105;
	}

	public Stock_infoBean getStock_infoBean() {
		return stock_infoBean;
	}

	public void setStock_infoBean(Stock_infoBean stock_infoBean) {
		this.stock_infoBean = stock_infoBean;
	}

}
