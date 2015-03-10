package com.allexceedvn.medicfind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class DistrictAdapter extends ArrayAdapter<District> {

	private Context context;
	private int resId;

	private List<District> arrDistricts;

	public DistrictAdapter(Context context, int resId, List<District> citys) {
		super(context, resId, citys);
		this.context = context;
		this.resId = resId;
		this.arrDistricts = citys;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final District district = arrDistricts.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(resId, null);

		TextView tvId = (TextView) view.findViewById(R.id.tvDistrictIdItem);
		TextView tvDistrictNameItem = (TextView) view.findViewById(R.id.tvDistrictNameItem);

		if (district != null) {
			tvId.setText(district.getDistrictCd()+ "");
			tvDistrictNameItem.setText(district.getDistrictName());
		}

		return view;
	}

	@Override
	public District getItem(int position) {
		return arrDistricts.get(position);
	}

	public void addMoreSong(List<District> arrCitys) {
		this.arrDistricts.addAll(arrCitys);
	}
}
