package com.pos.onboarding.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.pos.onboarding.beans.SaleDetail;

public class SaleDetailBlTest {

	private static final Long SALE_DETAIL_ID = 1l;
	private static final Long SALE_DETAIL_SALE_ID = 1l;
	private static final Long SALE_DETAIL_ARTICLE_ID = 1l;
	private static final Long SALE_DETAIL_QUANTITY = 1l;
	private static final BigDecimal SALE_DETAIL_AMOUNT = new BigDecimal(150);

	@Test
	public void testSaleDetail() {
		SaleDetail validSaleDetail = getValidSaleDetail();
		SaleDetailBl saleDetailBl = new SaleDetailBl();

		assertTrue(saleDetailBl.validateSaleDetail(validSaleDetail));
		assertEquals(validSaleDetail,
				saleDetailBl.createSaleDetail(validSaleDetail));
	}

	private static SaleDetail getValidSaleDetail() {
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setId(SALE_DETAIL_ID);
		saleDetail.setSaleId(SALE_DETAIL_SALE_ID);
		saleDetail.setArticleId(SALE_DETAIL_ARTICLE_ID);
		saleDetail.setQuantity(SALE_DETAIL_QUANTITY);
		saleDetail.setAmount(SALE_DETAIL_AMOUNT);

		return saleDetail;
	}
}
