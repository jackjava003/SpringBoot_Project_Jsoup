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
@Table(name = "monthly_change")
public class Stock_monthBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8196675731851356044L;

	@EmbeddedId
    @AttributeOverrides
    ({
            @AttributeOverride(name = "stockID", column = @Column(name = "stockID")),
            @AttributeOverride(name = "month", column = @Column(name = "month"))
    })
	Month_CompositeKey month_CompositeKey;
	
	@NotNull
	private long rev_105;
	@NotNull
	private BigDecimal incr_rate_105;
	@NotNull
	private long rev_106;
	@NotNull
	private BigDecimal incr_rate_106;
	@NotNull
	private long cumu_rev_106;
	@NotNull
	private BigDecimal annu_rate_106;
	@NotNull
	private String achieve_rate_106;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @MapsId("stockID")
    @JoinColumn(name = "stockID")
	private Stock_infoBean stock_infoBean;

	

	public Stock_monthBean() {

	}
	
	public Month_CompositeKey getMonth_CompositeKey() {
		return month_CompositeKey;
	}

	public void setMonth_CompositeKey(Month_CompositeKey month_CompositeKey) {
		this.month_CompositeKey = month_CompositeKey;
	}

	public long getRev_105() {
		return rev_105;
	}

	public void setRev_105(long rev_105) {
		this.rev_105 = rev_105;
	}

	public BigDecimal getIncr_rate_105() {
		return incr_rate_105;
	}

	public void setIncr_rate_105(BigDecimal incr_rate_105) {
		this.incr_rate_105 = incr_rate_105;
	}

	public long getRev_106() {
		return rev_106;
	}

	public void setRev_106(long rev_106) {
		this.rev_106 = rev_106;
	}

	public BigDecimal getIncr_rate_106() {
		return incr_rate_106;
	}

	public void setIncr_rate_106(BigDecimal incr_rate_106) {
		this.incr_rate_106 = incr_rate_106;
	}

	public long getCumu_rev_106() {
		return cumu_rev_106;
	}
	
	public void setCumu_rev_106(long cumu_rev_106) {
		this.cumu_rev_106 = cumu_rev_106;
	}

	public BigDecimal getAnnu_rate_106() {
		return annu_rate_106;
	}
	
	public void setAnnu_rate_106(BigDecimal annu_rate_106) {
		this.annu_rate_106 = annu_rate_106;
	}

	public String getAchieve_rate_106() {
		return achieve_rate_106;
	}

	public void setAchieve_rate_106(String achieve_rate_106) {
		this.achieve_rate_106 = achieve_rate_106;
	}

	public Stock_infoBean getStock_infoBean() {
		return stock_infoBean;
	}

	public void setStock_infoBean(Stock_infoBean stock_infoBean) {
		this.stock_infoBean = stock_infoBean;
	}

}
