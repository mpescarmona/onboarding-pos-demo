package com.pos.onboarding.beans;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This class is used to hold the articles of the sales data.-
 * 
 * @author Mario Pescarmona
 *
 */
public class SaleDetail {
	private Long id;
	private Long saleId;
	private Long articleId;
	private Long quantity;
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
