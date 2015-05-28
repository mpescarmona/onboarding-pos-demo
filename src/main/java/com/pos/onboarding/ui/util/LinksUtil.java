package com.pos.onboarding.ui.util;

import java.util.ArrayList;
import java.util.List;

public class LinksUtil {
    public static String getPageParams(Integer page, Integer size, String filter, String sort, String order) {
    	String showPage = "";
    	if (page != null) {
    		showPage = "?page=" + page;
    	}
    	if (size != null) {
    		showPage = showPage + (!"".equals(showPage) ? "&" : "?") + "size=" + size;
    	}
    	if (filter != null && !filter.trim().isEmpty()) {
    		showPage = showPage + (!"".equals(showPage) ? "&" : "?") + "filter=" + filter;
    	}
    	if (sort != null && !sort.trim().isEmpty()) {
    		showPage = showPage + (!"".equals(showPage) ? "&" : "?") + "sort=" + sort;
    	}
    	if (order != null && !order.trim().isEmpty()) {
    		showPage = showPage + (!"".equals(showPage) ? "&" : "?") + "order=" + order;
    	}
    	
    	return showPage;
	}


	public static List<Link> getPageLinks(Integer page, Integer size, String filter, String sort, String order, Long count) {
		List<Link> links = new ArrayList<Link>();

		if(count > size) {
			links.add(new Link("First", getPageParams(0, size, filter, sort, order)));
			links.add(new Link("Prev", getPageParams((page > 0 ? page - 1: 0), size, filter, sort, order)));
			links.add(new Link("Next", getPageParams(page + 1, size, filter, sort, order)));
			links.add(new Link("Last", getPageParams(Math.round(count/size + 1), size, filter, sort, order)));
		} else {
			links.add(new Link("Current", getPageParams(page, size, filter, sort, order)));
		}
		
		return links;
	}
}
