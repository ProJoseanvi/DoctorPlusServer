package com.doctorplus.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class ListIdsResponse {
	private List<Id> ids;

	public List<Id> getIds() {
		return ids;
	}

	public void setIds(List<Id> ids) {
		this.ids = ids;

}
	
}