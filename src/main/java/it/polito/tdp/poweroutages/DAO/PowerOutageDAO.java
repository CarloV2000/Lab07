package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	/**
	 * 
	 * @param n è il nerc scelto dall'utente 
	 * @return una lista di tutti i PowerOutage (blackout) del nerc scelto
	 */
	public List<PowerOutage> getAllPowerOutages (Nerc n) {
		int nId = n.getId();
		String sql = "SELECT p.* "
				+ "FROM poweroutages p "
				+ "WHERE p.nerc_id = ? ";
		List<PowerOutage>powerOutages = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nId);
			ResultSet res = st.executeQuery();
			

			while (res.next()) {
				//LocalDate dateEventBegan = res.getDate("date_event_began").toLocalDate();
				//LocalDate dateEventFinished = res.getDate("date_event_finished").toLocalDate();
				
				PowerOutage p = new PowerOutage( res.getInt("id"),
						res.getInt("event_type_id"),
						res.getInt("tag_id"),
						res.getInt("area_id"),
						res.getInt("nerc_id"),
						res.getInt("responsible_id"),
						res.getInt("customers_affected"),
						res.getDate("date_event_began"),
						res.getDate("date_event_finished"),
						res.getInt("demand_loss"));
				powerOutages.add(p);
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return powerOutages;
	}
	
}
