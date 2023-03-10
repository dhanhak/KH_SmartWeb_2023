import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dao {

	public ShopMember searchUser(String memberId) {

		Connection con = null;

		Statement stmt = null;

		ResultSet rs = null;

		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		String user = "test_01";

		String password = "1234";

		ShopMember shopMember = null;

		try {

		con = DriverManager.getConnection(url, user, password);

		String query = "SELECT * FROM SHOP_MEMBER WHERE MEMBER_ID = '"+memberId+"'";
		// 1. preparedStatement가 아니고 Statement로 작성하여서 ?가 아닌 매개변수를 query문에 넣어주어야함
		               // ? -> '"+memberId+"' 
		
		stmt = con.createStatement(); 
		// 2. stmt = con.prepapareStatement(query); -> stmt = con.createStatement();

		rs = stmt.executeQuery(query);
		// 3. rs = stmt.executeQuery(query);	// arguments 넣어주어야함 (query)​

		if(rs.next()) {			

		shopMember = new ShopMember();

		shopMember.setMemberId(rs.getString("MEMBER_ID"));

		shopMember.setMemberPw(rs.getString("MEMBER_PW"));

		shopMember.setMemberName(rs.getString("MEMBER_NAME"));

		shopMember.setMemberAge(rs.getInt("MEMBER_AGE"));	// 4. rs.getInt("MEMBER_AGE")로 바꾸어주어야함

		shopMember.setPhone(rs.getString("MEMBER_PHONE"));	// 5. DB의 칼럼명과 동일해야함 MEMBER_PHONE

		shopMember.setAddr(rs.getString("MEMBER_ADDR"));	// 6. DB의 칼럼명과 동일해야함 MEMBER_ADDR

		}

		rs.close(); stmt.close(); con.close();

		} catch (SQLException e) {

		e.printStackTrace();

		}

		return shopMember;

		}
}

