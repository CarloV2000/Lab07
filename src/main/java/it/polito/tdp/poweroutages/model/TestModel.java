package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		List<Nerc>allNercs = model.getNercList();
		System.out.println(allNercs);
		Nerc n = null;
		for(Nerc x : allNercs) {
			if(x.getValue().compareTo("MAAC")==0) {
				n = x;
			}
		}
		System.out.println("soluzioni : " + model.getWorstCase(n, 4, 200));

	}

}
