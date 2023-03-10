package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Student_Dto;

public class Student_Dao {

	// try ~ catch						: 실행코드 내에 하나 이상의 예외가 발생하고, 이를 모두 한가지 로직으로 처리할 경우
	// try ~ catch, catch, catch...		: 실행코드 내에서 다양한 종류의 예외가 발생하고, 이를 다 따로따로 처리해야 할 경우
	// try ~ finally					: 실행코드를 완료 후, 반드시(무조건) 실행되어야 할 코드가 있는 경우
	// try ~ catch- finally				: 실행코드가 예외를 발생시켰을 경우, catch 블럭 실행후 finally 실행,
	//									: 실행코드가 정상 종료일 경우, try 블럭 실행후 finally 실행
	// try with resources				: close가 필요한 자원을 사용하는 경우, 절대적으로 자원을 close하는 더 심플한 문법
	// 									  AutoCloseable을 상속한 클래스에 한하여 적용 가능


	public Connection getConnection() throws Exception {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id="kh";
		String pw="kh";
		Connection con = DriverManager.getConnection(url,id,pw);

		return con;
	}

	public int insert(Student_Dto dto) throws Exception {

		String sql = "INSERT INTO STUDENTS VALUES(students_seq.nextval,?, ?, ?, ?)";
		try(Connection con = this.getConnection();	
				PreparedStatement pstat = con.prepareStatement(sql);) {

			int result = pstat.executeUpdate();	

			con.commit();							
			return result;	
		}
	}

	public List<Student_Dto> select() throws Exception{
		String sql = "SELECT * FROM STUDENTS";

		try(Connection con = this.getConnection();
				Statement stat = con.createStatement();){

			try(ResultSet rs = stat.executeQuery(sql);){
				List<Student_Dto> result = new ArrayList<Student_Dto>();

				while(rs.next()) {
					int id = rs.getInt("ID");
					String name = rs.getString("NAME");
					int kor = rs.getInt("KOR");
					int eng = rs.getInt("ENG");
					int math = rs.getInt("MATH");

					Student_Dto dto = new Student_Dto(id,name,kor,eng,math);
					result.add(dto);
				}
				return result;
			}
		}
	}

	public int update(Student_Dto dto) throws Exception {
		String sql="UPDATE STUDENTS SET kor=?,ENG = ?,MATH =? WHERE ID =?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setInt(1, dto.getKor());
			pstat.setInt(2, dto.getEng());
			pstat.setInt(3, dto.getMath());
			pstat.setInt(4, dto.getId());

			int result = pstat.executeUpdate();

			con.commit();
			return result;
		}
	}

	public boolean isExist(int id) throws Exception {
		String sql = "SELECT * FROM STUDENTS WHERE ID ="+id+"";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			ResultSet rs = pstat.executeQuery();
			boolean result = rs.next();

			return result;
		}
	}

	public int delete(int id) throws Exception {
		String sql = "DELETE FROM STUDENTS WHERE ID =?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat =con.prepareStatement(sql);){
			pstat.setInt(1, id);
			int result = pstat.executeUpdate();

			con.commit();
			return result;
		}
	}

	public List<Student_Dto> issele(String name) throws Exception {
		String sql = "select * from students where name like ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, "%"+name+"%");
			try(ResultSet rs = pstat.executeQuery();){
				List<Student_Dto> result = new ArrayList<Student_Dto>();
				while(rs.next()) {
					int id = rs.getInt("id");
					String nname = rs.getString("name");
					int kor = rs.getInt("kor");
					int eng = rs.getInt("eng");
					int math = rs.getInt("math");

					Student_Dto dto = new Student_Dto(id, nname, kor, eng, math); 
					result.add(dto);		
				}
				return result;
			}
		}
	}

	
}















