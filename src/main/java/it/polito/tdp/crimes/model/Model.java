package it.polito.tdp.crimes.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	
	public Model () {
		dao = new EventsDao();
	}
	
	public List<Integer> listAllYears(){
		return dao.listAllYears();
	}
}
