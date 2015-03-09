package com.allexceedvn.medicfind;

public class City {
	private int cityCd;
	private String cityName;

	public City(int cityCd, String cityName) {
		super();
        this.cityCd = cityCd;
		this.cityName = cityName;
	}

    public int getCityCd() {
        return cityCd;
    }

    public void setCityCd(int cityCd) {
        this.cityCd = cityCd;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
