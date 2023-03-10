package main;

import java.util.List;
import java.util.Scanner;

import DTO.Movies_DTO;
import manager.MembersDAO;
import manager.Movies_DAO;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Movies_DAO dao = new Movies_DAO();
		// Netflix 영화 관리 프로그램
		// 테이블 이름 : movies
		// id - 1001~무한까지 1씩증가하는 주키
		// title - 문자열 최대 50바이트
		// genre - 문자열 20바이트
		// 모두 not null

		// main은 throws 전가 하면 프로그램 꺼짐으로 main에선 예외처리해야함

		

			String url ="jdbc:oracle:thin:@localhost:1521:xe";
			String id ="kh";
			String pw ="kh";

			while(true) {
				System.out.println("<<Netfilx 영화 관리 프로그램>>");
				System.out.println("1. 신규 영화 등록");	// C
				System.out.println("2. 영화 목록 출력");	// R
				System.out.println("3. 영화 정보 수정");	// U
				System.out.println("4. 영화 정보 삭제");	// D
				System.out.println("5. 영화 정보 검색");
				System.out.println(">>");
				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu==1) {

						System.out.print("title 입력 : ");
						String ntitle = sc.nextLine();
						System.out.print("genre 입력 : ");
						String ngenre = sc.nextLine();


						int result = dao.insert(new Movies_DTO(0,ntitle,ngenre));
						if(result>0) {
							System.out.println("입력 완료");
						}

					}else if(menu==2) {
						List<Movies_DTO> result = dao.SelectAll();

						System.out.println("id\title\tgenre");
						for(Movies_DTO dto : result) {
							System.out.println(dto.getId()+"\t"+dto.getTitle()+"\t"+dto.getGenre());
						}
					}else if(menu==3) {

						System.out.print("수정할 id : ");
						int nid = Integer.parseInt(sc.nextLine());

						boolean isIdExist = dao.isIdExist(nid);
						if(!isIdExist) {
							System.out.println("ID : ["+nid+"] 찾을 수 없습니다.");
							continue;
						}

						System.out.println("신규 영화 제목 : ");
						String ntitle = sc.nextLine();

						System.out.println("신규 영화 장르 : ");
						String ngenre = sc.nextLine();

						int result = dao.update(new Movies_DTO(nid,ntitle,ngenre));

						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}
					else if(menu==4) {

						System.out.print("삭제할 id : ");
						int nid = Integer.parseInt(sc.nextLine());

						int result = dao.delete(new Movies_DTO(nid,null,null));

						if(result>0) {
							System.out.println("삭제완료");
						}else {
							System.out.println("ID : ["+ nid +"] 영화를 찾을 수 없습니다.");
						}
					}
					else if(menu==5) {
						System.out.print("검색할 제목 : ");
						String title = sc.nextLine();

						List<Movies_DTO> result = dao.Search(title);

						System.out.println("id\title\tgenre");
						for(Movies_DTO dto : result) {
							//	if(dto.getTitle().equals(title)) {
							System.out.println(dto.getId()+"\t"+dto.getTitle()+"\t"+dto.getGenre());
							//	}
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
