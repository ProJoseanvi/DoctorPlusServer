package com.doctorplus.controller;

import java.util.List;

import com.doctorplus.dto.Date;


public class ListDatesResponse {
	private List<Date> dates;

	public List<Date> getDates() {
		return dates;
	}

	public void setMeds(List<Date> dates) {
		this.dates = dates;
	}

}
