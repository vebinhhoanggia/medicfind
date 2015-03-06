package com.allexceedvn.medicfind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CityAdapter extends ArrayAdapter<City> {

	private Context context;
	private int resId;

	private List<City> arrCitys;

	public CityAdapter(Context context, int resId, List<City> citys) {
		super(context, resId, citys);
		this.context = context;
		this.resId = resId;
		this.arrCitys = citys;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final City city = arrCitys.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(resId, null);

		TextView tvId = (TextView) view.findViewById(R.id.tvCityIdItem);
		TextView tvCityNameItem = (TextView) view.findViewById(R.id.tvCityNameItem);

		if (city != null) {
			tvId.setText(city.getCityCd() + "");
            tvCityNameItem.setText(city.getCityName());
		}

		return view;
	}

	@Override
	public City getItem(int position) {
		return arrCitys.get(position);
	}

	public void addMoreSong(List<City> arrCitys) {
		this.arrCitys.addAll(arrCitys);
	}
}
