package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DTO.Movies_DTO;


// 데이터와 관련된 모든 작업을 총괄하는 클래스
// DAO : Data Access Object
public class Movies_DAO {

	private Connection getConnection() throws Exception {
		String url ="jdbc:oracle:thin:@localhost:1521:xe";
		String id ="kh";
		String pw ="kh";
		Connection con = DriverManager.getConnection(url,id,pw);	// 기능당 하나씩 만들어야 좋음(만들었으면close())
		return con;
	}

	public int insert(Movies_DTO dto) throws Exception{

		String sql ="INSERT INTO movies VALUES(movies_seq.nextval,?,?)";
		try(
				Connection con = this.getConnection();	
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getGenre());

			int result = pstat.executeUpdate();

			con.commit();
			return result;

		}
	}

	public int update(Movies_DTO dto) throws Exception {

		String sql="UPDATE movies SET title=?, genre=? WHERE id=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getGenre());
			pstat.setInt(3, dto.getId());

			int result = pstat.executeUpdate();

			con.commit();
			return result;
		}
	}

	public int delete(Movies_DTO dto) throws Exception {

		String sql = "DELETE FROM MOVIES WHERE id= ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setInt(1, dto.getId());
			int result = pstat.executeUpdate();

			con.commit();
			return result;
		}
	}


	public List<Movies_DTO> SelectAll() throws Exception {		// ResultSet :Connection 세션이 열려있는동안만 사용가능.

		try(Connection con = this.getConnection();
				Statement stat = con.createStatement();){
			String sql="SELECT * FROM movies";
			ResultSet rs = stat.executeQuery(sql);

			List<Movies_DTO> result = new ArrayList();

			while(rs.next()) {
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				String genre = rs.getString("genre");

				Movies_DTO dto = new Movies_DTO(nid,title,genre);

				result.add(dto);
			}
			con.close();
			return result;
		}
	}

	public List<Movies_DTO> Search(String title) throws Exception {

		String sql="SELECT * FROM movies WHERE title like ?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, "%"+title+"%");
			
			try(ResultSet rs = pstat.executeQuery();){	
				List<Movies_DTO> result = new ArrayList<Movies_DTO>();

				while(rs.next()) {
					int nid = rs.getInt("id");
					String ntitle = rs.getString("title");
					String genre = rs.getString("genre");

					Movies_DTO dto = new Movies_DTO(nid,ntitle,genre);

					result.add(dto);
				}
				con.close();
				return result;
			}
		}
	}

	public boolean isIdExist(int id) throws Exception {
		String sql = "select * from movies where id = ?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat =con.prepareStatement(sql);){

			pstat.setInt(1, id);

			ResultSet rs = pstat.executeQuery();	
			boolean result = rs.next();
			return result;
		}
	}


}


















