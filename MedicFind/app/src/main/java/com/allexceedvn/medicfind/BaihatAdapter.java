package com.allexceedvn.medicfind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BaihatAdapter extends ArrayAdapter<Baihat> {

	private Context context;
	private int resId;

	private List<Baihat> arrSongs;

	public BaihatAdapter(Context context, int resId, List<Baihat> songs) {
		super(context, resId, songs);
		this.context = context;
		this.resId = resId;
		this.arrSongs = songs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Baihat baihat = arrSongs.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(resId, null);

		TextView tvId = (TextView) view.findViewById(R.id.tvIdItem);
//		TextView tvSongName = (TextView) view.findViewById(R.id.tvSongNameItem);
//		TextView tvLyric = (TextView) view.findViewById(R.id.tvLyricItem);

		if (baihat != null) {
			tvId.setText(baihat.getId() + "");
//			tvSongName.setText(baihat.getSongName());
//			tvLyric.setText(baihat.getLyric());
		}

		return view;
	}

	@Override
	public Baihat getItem(int position) {
		return arrSongs.get(position);
	}

	public void addMoreSong(List<Baihat> arrSongs) {
		this.arrSongs.addAll(arrSongs);
	}
}
