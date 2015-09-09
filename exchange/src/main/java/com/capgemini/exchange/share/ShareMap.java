package com.capgemini.exchange.share;

import java.util.HashMap;

public class ShareMap extends HashMap<String, Share> {

	private static final long serialVersionUID = -4468980879913173612L;
	
	public ShareMap() {
		super();
	}

	public ShareMap(ShareMap another) {
		super(another);
	}

	public Share put(Share share) {
		return super.put(share.getCompanyName(), share);
	}
	
	public Share get(Share share) {
		return super.get(share.getCompanyName());
	}

	public Share remove(Share share) {
		return super.remove(share.getCompanyName());
	}

	public ShareMap copy() {
		return new ShareMap(this);
	}
	
}
