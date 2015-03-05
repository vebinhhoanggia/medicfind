package com.allexceedvn.medicfind;

public class Baihat {
	private int id;
	private String songName;
	private String songName2;
	private String lyric;

	public Baihat(int id, String songName, String lyric) {
		super();
		this.id = id;
		this.songName = songName;
		this.lyric = lyric;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public String getSongName2() {
		return songName2;
	}

	public void setSongName2(String songName2) {
		this.songName2 = songName2;
	}

}
