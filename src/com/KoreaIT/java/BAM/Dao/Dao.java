package com.KoreaIT.java.BAM.Dao;

public abstract class Dao {
	protected int lastId;
	
	public Dao() {
		lastId = 0;
	}
	
	public int getLastId() {
		return lastId;
	}
	
	public int getNewId() {
		return lastId + 1;
	}
}
