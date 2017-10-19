package com.longcai.medical.bean;

import java.util.ArrayList;

public class QuestionInfo {

	private int id;
	private String text;
	private String date;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int pubStationId;
	private int pubTypeId;
	private int agriTypeId;
	private int questionStateId;
	private int isAnswer;
	private int mId;
	private int gz_num;
	public int getGz_num() {
		return gz_num;
	}
	public void setGz_num(int gz_num) {
		this.gz_num = gz_num;
	}
	public String getPhotopath() {
		return photopath;
	}
	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
	public String getResouding() {
		return resouding;
	}
	public void setResouding(String resouding) {
		this.resouding = resouding;
	}
	private String photopath;
	private String resouding;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getPubStationId() {
		return pubStationId;
	}
	public void setPubStationId(int pubStationId) {
		this.pubStationId = pubStationId;
	}
	public int getPubTypeId() {
		return pubTypeId;
	}
	public void setPubTypeId(int pubTypeId) {
		this.pubTypeId = pubTypeId;
	}
	public int getAgriTypeId() {
		return agriTypeId;
	}
	public void setAgriTypeId(int agriTypeId) {
		this.agriTypeId = agriTypeId;
	}
	public int getQuestionStateId() {
		return questionStateId;
	}
	public void setQuestionStateId(int questionStateId) {
		this.questionStateId = questionStateId;
	}
	public int getIsAnswer() {
		return isAnswer;
	}
	public void setIsAnswer(int isAnswer) {
		this.isAnswer = isAnswer;
	}
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	
	public String getPubAddress() {
		return pubAddress;
	}
	public void setPubAddress(String pubAddress) {
		this.pubAddress = pubAddress;
	}
	private ArrayList<String> picpath;
	private String pubAddress;
	private int isWarn;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ArrayList<String> getPicpath() {
		return picpath;
	}
	public void setPicpath(ArrayList<String> picpath) {
		this.picpath = picpath;
	}
	
	public int getIsWarn() {
		return isWarn;
	}
	public void setIsWarn(int isWarn) {
		this.isWarn = isWarn;
	}
	
	
}
