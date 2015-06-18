package com.pos.onboarding.bl;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.bean.SaleDetail;

public class SaleDetailBl {
	private static final Logger log = LogManager.getLogger(SaleDetailBl.class);
	private SaleDetail saleDetail;

	public SaleDetail createSaleDetail(SaleDetail newSaleDetail) {
		log.trace("Enter method createSaleDetail. method params: {}",
				newSaleDetail);

		if (validateSaleDetail(newSaleDetail)) {
			this.saleDetail = newSaleDetail;
		}

		log.trace(
				"Return method createSaleDetail. method params: {}. Result: {}",
				newSaleDetail, saleDetail);

		return saleDetail;
	}

	public boolean validateSaleDetail(SaleDetail saleDetail) {
		log.trace("Enter method validateSaleDetail. method params: {}",
				saleDetail);
		boolean isValid = true;

		log.debug("validating Sale ID of Sale's detail method.");
		if (saleDetail.getSaleId() == null) {
			log.error("The Sale ID of Sale's detail must be provided");
			isValid = false;
		} else {
			if (saleDetail.getSaleId() < 0l) {
				log.error("The Sale ID of Sale's detail must be positive");
				isValid = false;
			}
		}

		log.debug("validating sale's detail article.");
		if (saleDetail.getArticleId() == null) {
			log.error("The sale's detail article must be provided");
			isValid = false;
		} else {
			if (saleDetail.getArticleId() < 0l) {
				log.error("The article ID of sale's detail must be positive");
				isValid = false;
			}
		}

		log.debug("validating sale's detail quantity.");
		if (saleDetail.getQuantity() == null) {
			log.error("The quantity of the sale's detail must be provided");
			isValid = false;
		} else {
			if (saleDetail.getQuantity() < 0l) {
				log.error("The quantity of the sale's detail must be positive");
				isValid = false;
			}
		}

		log.debug("validating sale's detail amount.");
		if (saleDetail.getAmount() == null) {
			log.error("The sale's details amount must be provided");
			isValid = false;
		} else {
			if (saleDetail.getAmount().compareTo(new BigDecimal(0)) <= 0) {

				log.error("The sale's details amount must be positive");
				isValid = false;
			}
		}

		log.trace("Return method validateSale. method params: {}. Result: {}",
				saleDetail, isValid);

		return isValid;
	}

}
