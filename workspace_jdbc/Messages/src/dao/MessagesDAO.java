package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import dto.MessagesDTO;


public class MessagesDAO {
	private BasicDataSource bds = new BasicDataSource();

	public MessagesDAO() {
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe"); 
		bds.setUsername("board");
		bds.setPassword("board");
		bds.setInitialSize(5);
	}

	private Connection getConnection() throws Exception {
		return bds.getConnection();
	}

	public int insert(MessagesDTO dto) throws Exception {
		String sql = "INSERT INTO Messages VALUES(Messages_seq.nextval,?,?,sysdate) ";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getMessage());

			int result = pstat.executeUpdate();
			return result;
		}
	}
	public List<MessagesDTO> selectAll() throws Exception{
		String sql ="SELECT * FROM MESSAGES";
		Connection con = bds.getConnection();
		PreparedStatement pstat = con.prepareStatement(sql);
		ResultSet rs = pstat.executeQuery();
		List<MessagesDTO> list = new ArrayList<>();

		while(rs.next()) {
			int seq = rs.getInt("SEQ");
			String writer = rs.getString("writer");
			String message = rs.getString("message");
			Timestamp write_date = rs.getTimestamp("write_date");

			MessagesDTO dto = new MessagesDTO(seq, writer, message, write_date);
			list.add(dto);
		}

		return list;
	}

	public int delete(MessagesDTO dto) throws Exception {

		String sql = "DELETE FROM MESSAGES WHERE SEQ = ?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setInt(1, dto.getSeq());
			int result = pstat.executeUpdate();

			return result;
		}
	}

	public int update(MessagesDTO dto) throws Exception {

		String sql = "update messages set writer = ?, message=? where seq =?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getMessage());
			pstat.setInt(3, dto.getSeq());
			int result = pstat.executeUpdate();

			return result;
		}
	}
	public List<MessagesDTO> search(String st) throws Exception{
		
		String sql = "SELECT * FROM MESSAGES WHERE WRITER LIKE ? OR MESSAGE LIKE ?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, "%"+st+"%");
			pstat.setString(2, "%"+st+"%");
			try(ResultSet rs = pstat.executeQuery();){
				ArrayList<MessagesDTO> list = new ArrayList<MessagesDTO>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String writer = rs.getString("writer");
					String message = rs.getString("message");
					Timestamp write_date = rs.getTimestamp("write_date");
					
					MessagesDTO dto = new MessagesDTO(seq, writer, message, write_date);
					list.add(dto);
				}
				return list;
			}
		}
	}
	


}
