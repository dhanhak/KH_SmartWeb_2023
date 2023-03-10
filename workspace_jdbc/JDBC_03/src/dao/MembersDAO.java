package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import dto.MembersDTO;

public class MembersDAO {

	//	DBCP (DataBase Connection Pool
	// Database Connection 객체의 사용량이 폭발적으로 증가할 시,
	// Database 가 서비스 중단되지 않게끔 충격을 완화하는 완충장치
	// 정해진 Connection 객체를 대여 / 반환하는 시스템으로 운용하며 안정성을 도모하지만.
	// 접속자가 많을 시 상대적으로 기다리는 시간이 늘어날 수 있어 적절한 수요 배치가 필요함.

	//	public Connection getConnection() throws Exception {
	//		String url="jdbc:oracle:thin:@localhost:1521:xe";
	//		String id="kh";
	//		String pw="kh";
	//
	//		Connection con = DriverManager.getConnection(url,id,pw);
	//
	//		return con;
	//	}

	// Library 간 의존성 ( Dependencies )
	// new로 인스턴스를 만들면 

	private BasicDataSource bds = new BasicDataSource();

	public MembersDAO() {
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("kh");
		bds.setPassword("kh");
		bds.setInitialSize(8);	// 내서버의 접속하는 
	}

	private Connection getConnection() throws Exception {
		return bds.getConnection();
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

	public boolean searchidOk(String id) throws Exception {
		String sql="SELECT * FROM MEMBERS WHERE ID = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			try(ResultSet result = pstat.executeQuery();){

				return result.next();
			}
		}
	}

	public List<MembersDTO> selectAll() throws Exception {
		String sql="SELECT * FROM MEMBERS";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();){
			List<MembersDTO> list = new ArrayList<MembersDTO>();
			while(rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String contact = rs.getString("contact");
				Timestamp reg_date =rs.getTimestamp("reg_date");

				MembersDTO dto = new MembersDTO(id,pw,name,contact,reg_date);		
				list.add(dto);	
			}
			return list;
		}
	}
	

}
