package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<Nerc>nercs;
	List<PowerOutage>allBlackout;
	List<PowerOutage>sequenzaCercata;
	int maxPersoneCoinvolte;
	
	
	public Model() {
		podao = new PowerOutageDAO();
		this.nercs = this.getNercList();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getAllPowerOutages (Nerc n) {
		return podao.getAllPowerOutages(n);
	}

	/**
	 * metodo che richiama ricorsione
	 * @param nerc indica il nerc inserito dall'utente
	 * @param nMaxAnni limite anni(inserito dall'utente)
	 * @param nMaxOre limite ore (inserito dall'utente)
	 */
	public List<PowerOutage> getWorstCase(Nerc nerc, int nMaxAnni, int nMaxOre) {
		this.sequenzaCercata = new ArrayList<PowerOutage>();
		maxPersoneCoinvolte = 0;
		allBlackout = this.getAllPowerOutages(nerc);
		List<PowerOutage>parziale = new ArrayList<PowerOutage>();		
		cerca(parziale, nMaxAnni, nMaxOre);
		return sequenzaCercata;
	}

	/**
	 * metodo ricorsivo che seleziona il sottoinsieme di eventi di PoewrOutage che si sono verificati in un
	   massimo di X anni, per un totale di Y ore di disservizio massimo, tale da massimizzare il numero
	   totale di persone coinvolte. 
	 * @param parziale è la soluzione parziale del livello corrente
	 * @param nMaxAnni è il numero massimo di anni scelto dall'utente
	 * @param nMaxOre è il  numero massimo di ore scelto dall'utente
	 */
	private void cerca(List<PowerOutage> parziale, int nMaxAnni, int nMaxOre) {
				
		
		if (calcolaPersoneCoinvolte(parziale) > maxPersoneCoinvolte) {
			maxPersoneCoinvolte = calcolaPersoneCoinvolte(parziale);
			sequenzaCercata = new ArrayList<PowerOutage>(parziale);
			
		}
		else {
			//caso intermedio
			for (PowerOutage prova: allBlackout) {
				if(!parziale.contains(prova)) {
					if (aggiuntaValida(parziale, nMaxAnni, nMaxOre)) {
						parziale.add(prova);
						cerca(parziale, nMaxAnni, nMaxOre);
						parziale.remove(parziale.size()-1);
					}
				}
			}			
		}
	}
	
	public int calcolaPersoneCoinvolte(List<PowerOutage> parziale) {
		int personeCoinvolte = 0;
		for(PowerOutage x : parziale) {
			personeCoinvolte += x.getCustomersAffected();
		}
		return personeCoinvolte;
	}
	
	public boolean aggiuntaValida(List<PowerOutage> parziale, int nMaxAnni, int nMaxOre) {
		boolean a = true;
		double sommaOre = 0;
		int annoVecchio = 10000;
		int annoRecente = 0;
		for(PowerOutage x : parziale) {//cerco per ogni poweroutage di parziale l'anno piu vecchio e l'anno piu nuovo, poi risalgo alla durata in ore dell'evento
			
			if(x.getDateEventBegan().toLocalDate().getYear() < annoVecchio) {
				annoVecchio = x.getDateEventBegan().toLocalDate().getYear();
			}
			
			if(x.getDateEventBegan().toLocalDate().getYear() > annoRecente) {
				annoRecente = x.getDateEventBegan().toLocalDate().getYear();
			}
			sommaOre += x.getDurataInOre();
			
		}
			
		if(annoRecente - annoVecchio > nMaxAnni) {//se la loro differenza è maggiore di nMaxAnni->false
			a = false;
		}
		
		if(sommaOre > nMaxOre) { //se il tot di ore supera il numero max imposto
			a = false;
		}
		
		return a;
	}
	
}
