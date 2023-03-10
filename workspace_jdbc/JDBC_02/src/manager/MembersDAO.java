package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DTO.MembersDTO;

public class MembersDAO {

	public Connection getConnection() throws Exception {
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String id="kh";
		String pw="kh";

		Connection con = DriverManager.getConnection(url,id,pw);

		return con;
	}
	public int insert(MembersDTO dto) throws Exception {

		String sql = "insert into members values(?, ?, ?, ?, sysdate)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){			
			
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			pstat.setString(3, dto.getName());
			pstat.setString(4, dto.getContact());
			
			int result = pstat.executeUpdate();
			con.commit();
			return result;
			
			
			
			
		}
				
				
//		try(Connection con = this.getConnection();
//				Statement stat = con.createStatement();){
//			String sql = "INSERT INTO MEMBERS VALUES('"+dto.getId()+"','"+dto.getPw()+"','"+dto.getName()+"','"+dto.getContact()+"', systimestamp)";
//			int result = stat.executeUpdate(sql);
//			con.commit();
//			return result;
//		}
	}
	
	
	public boolean login(String id, String pw) throws Exception, Exception {
		try(Connection con = this.getConnection();
				Statement stat = con.createStatement();){
			String sql="SELECT * FROM MEMBERS WHERE id='"+id+"' AND pw = '"+pw+"'";
			ResultSet rs = stat.executeQuery(sql);
			
			boolean bol = rs.next();
			return bol;
		}
	}
	
	
	
	

}
