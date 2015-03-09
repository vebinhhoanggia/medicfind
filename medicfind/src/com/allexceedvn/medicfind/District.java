package com.allexceedvn.medicfind;

public class District {
    private int cityCd;
	private int districtCd;
	private String districtName;

	public District(int cityCd, int districtCd, String districtName) {
		super();
        this.cityCd = cityCd;
        this.districtCd = districtCd;
		this.districtName = districtName;
	}

    public int getCityCd() {
        return cityCd;
    }

    public void setCityCd(int cityCd) {
        this.cityCd = cityCd;
    }

    public int getDistrictCd() {
        return districtCd;
    }

    public void setDistrictCd(int districtCd) {
        this.districtCd = districtCd;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
