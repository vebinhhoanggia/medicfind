package com.allexceedvn.medicfind;

public class Detail {
	private int typeSearch;
	private int city_cd;
	private int district_cd;
	private int _id;
	private String name;
	private float longtitude;
	private float latitude;

	public Detail(int typeSearch, int city_cd, int district_cd, int _id,
			String name) {
		super();
		this.typeSearch = typeSearch;
		this.city_cd = city_cd;
		this.district_cd = typeSearch;
		this._id = _id;
		this.name = name;
	}
	
	/**
	 * @return the typeSearch
	 */
	public int getTypeSearch() {
		return typeSearch;
	}

	/**
	 * @param typeSearch
	 *            the typeSearch to set
	 */
	public void setTypeSearch(int typeSearch) {
		this.typeSearch = typeSearch;
	}

	/**
	 * @return the city_cd
	 */
	public int getCity_cd() {
		return city_cd;
	}

	/**
	 * @param city_cd
	 *            the city_cd to set
	 */
	public void setCity_cd(int city_cd) {
		this.city_cd = city_cd;
	}

	/**
	 * @return the district_cd
	 */
	public int getDistrict_cd() {
		return district_cd;
	}

	/**
	 * @param district_cd
	 *            the district_cd to set
	 */
	public void setDistrict_cd(int district_cd) {
		this.district_cd = district_cd;
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the longtitude
	 */
	public float getLongtitude() {
		return longtitude;
	}

	/**
	 * @param longtitude the longtitude to set
	 */
	public void setLongtitude(float longtitude) {
		this.longtitude = longtitude;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	

}
