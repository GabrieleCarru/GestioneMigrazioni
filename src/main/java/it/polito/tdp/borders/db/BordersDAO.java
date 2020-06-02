package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries(Map<Integer,Country> countriesMap) {
		
		String sql = 
				"SELECT CCode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> result = new ArrayList<>();
			
			while( rs.next() ) {
				
					Country c = new Country(
							rs.getInt("CCode"),
							rs.getString("StateAbb"), 
							rs.getString("StateNme")) ;
					countriesMap.put(c.getcCode(), c);
					result.add(c);
					
			}
			
			conn.close() ;
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Country> getCountryByYear(int year, Map<Integer, Country> countriesMap) {
		
		String sql = "select distinct(c.state1no) " + 
				"from contiguity as c " + 
				"where c.conttype = 1 and year <= ? " + 
				"group by c.state1no, c.state1ab";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();
			
			List<Country> result = new ArrayList<>();
			
			while(rs.next()) {
				result.add(countriesMap.get(rs.getInt("state1no")));
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Adiacenza> getAdiacenzeFromCountryConttype1(int year, 
									Map<Integer, Country> countriesMap) {
		
		String sql = "select c.state1no as s1, c.state2no as s2 " + 
				"from contiguity as c " + 
				"where c.conttype = 1 and year <= ? " + 
				"group by c.state1no, c.state2no ";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();
			
			List<Adiacenza> result = new ArrayList<>();
			
			while(rs.next()) {
				Adiacenza a = new Adiacenza(rs.getInt("s1"), rs.getInt("s2"));
				result.add(a);
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
}
