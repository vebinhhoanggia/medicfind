package com.allexceedvn.medicfind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailAdapter extends ArrayAdapter<Detail> {

	private Context context;
	private int resId;

	private List<Detail> arrDetails;

	public DetailAdapter(Context context, int resId, List<Detail> citys) {
		super(context, resId, citys);
		this.context = context;
		this.resId = resId;
		this.arrDetails = citys;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Detail city = arrDetails.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(resId, null);

		TextView tvId = (TextView) view.findViewById(R.id.tvDetailIdItem);
		TextView tvCityNameItem = (TextView) view.findViewById(R.id.tvDetailNameItem);

		if (city != null) {
			tvId.setText(city.get_id() + "");
            tvCityNameItem.setText(city.getName());
		}

		return view;
	}

	@Override
	public Detail getItem(int position) {
		return arrDetails.get(position);
	}

	public void addMoreSong(List<Detail> arrDetails) {
		this.arrDetails.addAll(arrDetails);
	}
}
