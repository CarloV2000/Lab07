package it.polito.tdp.poweroutages.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class PowerOutage {
	private Integer id;
	private Integer eventTypeId;
	private Integer tagId;
	private Integer areaId;
	private Integer nercId;
	private Integer responsibleId;
	private Integer customersAffected;
	private Date dateEventBegan;
	private Date dateEventFinished;
	private Integer demandLoss;
	
	public PowerOutage(Integer id, Integer eventTypeId, Integer tagId, Integer areaId, Integer nercId,
			Integer responsibleId, Integer customersAffected, Date dateEventBegan, Date dateEventFinished,
			Integer demandLoss) {
		super();
		this.id = id;
		this.eventTypeId = eventTypeId;
		this.tagId = tagId;
		this.areaId = areaId;
		this.nercId = nercId;
		this.responsibleId = responsibleId;
		this.customersAffected = customersAffected;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinished = dateEventFinished;
		this.demandLoss = demandLoss;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getNercId() {
		return nercId;
	}

	public void setNercId(Integer nercId) {
		this.nercId = nercId;
	}

	public Integer getResponsibleId() {
		return responsibleId;
	}

	public void setResponsibleId(Integer responsibleId) {
		this.responsibleId = responsibleId;
	}

	public Integer getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(Integer customersAffected) {
		this.customersAffected = customersAffected;
	}

	public Date getDateEventBegan() {
		return dateEventBegan;
	}

	public void setDateEventBegan(Date dateEventBegan) {
		this.dateEventBegan = dateEventBegan;
	}

	public Date getDateEventFinished() {
		return dateEventFinished;
	}

	public void setDateEventFinished(Date dateEventFinished) {
		this.dateEventFinished = dateEventFinished;
	}

	public Integer getDemandLoss() {
		return demandLoss;
	}

	public void setDemandLoss(Integer demandLoss) {
		this.demandLoss = demandLoss;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Periodo evento : "+this.dateEventBegan + "  " + this.dateEventFinished + "  /  ore di blackOut : " + this.getDurataInOre() + " Cittadini colpiti : " + this.customersAffected +'\n';
	}
	/**
	 * metodo che calcola in ore la durata di un PowerOutage
	 * @return double durataInOreEvento
	 */
	public double getDurataInOre() {
		double msIniziali = this.getDateEventBegan().getTime();//sono i ms passati dal  1 gennaio 1970
		double msFinali = this.getDateEventFinished().getTime();
		double durataEventoInMs = msFinali - msIniziali;
		double durataOre = durataEventoInMs/(1000*60*60);
		return durataOre;
	}
	
	
	
	

	

}
