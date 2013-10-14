package com.subha.gridimagesearch;

import java.io.Serializable;

public class Settings implements Serializable {
	private static final long serialVersionUID = -3280854261661394753L;
	
	private String imageSize;
	private String colorFilter;
	private String imageType;
	private String siteFilter;

	public String getColorFilter() {
		return colorFilter;
	}

	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getSiteFilter() {
		return siteFilter;
	}

	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

}
