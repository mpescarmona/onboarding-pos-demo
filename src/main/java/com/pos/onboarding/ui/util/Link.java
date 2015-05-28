package com.pos.onboarding.ui.util;

import java.io.Serializable;

public class Link implements Serializable {
	
	private static final long serialVersionUID = -7076572573894853425L;
	
	private String text;
	private String link;
	
	public Link(String text, String link) {
		super();
		this.text = text;
		this.link = link;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "Link [text=" + text + ", link=" + link + "]";
	}
}
