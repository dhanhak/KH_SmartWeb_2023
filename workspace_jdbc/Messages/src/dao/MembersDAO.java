package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import dto.MembersDTO;

public class MembersDAO {
	private BasicDataSource bds = new BasicDataSource();

	public MembersDAO() {
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe"); 
		bds.setUsername("board");
		bds.setPassword("board");
		bds.setInitialSize(5);
	}

	private Connection getConnection() throws Exception {
		return bds.getConnection();

	}

	public int insert(MembersDTO dto) throws Exception {
		String sql = "INSERT INTO MEMBERS VALUES(?,?,?,?,sysdate) ";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			pstat.setString(3, dto.getName());
			pstat.setString(4, dto.getContact());

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public boolean login(MembersDTO dto) throws Exception {
		String sql = "SELECT * FROM MEMBERS WHERE id = ?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, dto.getId());

			ResultSet rs = pstat.executeQuery();
			
			return rs.next();
		}
	}
	
	public List<MembersDTO> information(String st) throws Exception{

		String sql = "SELECT * FROM MEMBERS WHERE id = ?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			
			pstat.setString(1, st);
			ResultSet rs = pstat.executeQuery();
			List<MembersDTO> list = new ArrayList<MembersDTO>();

			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String contact = rs.getString("contact");
				Timestamp reg_date = rs.getTimestamp("reg_date");

				MembersDTO dto = new MembersDTO(id,null,name,contact,reg_date);
				list.add(dto);
			}
			return list;
		}
	}


}


















