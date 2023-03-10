import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import commons.EncryptionUtils;
import dao.MembersDAO;
import dao.Student_Dao;
import dto.MembersDTO;
import dto.Student_Dto;
import org.apache.commons.dbcp2.BasicDataSource;

public class Main {
	public static void main(String[] args) {
		MembersDAO membersdao = new MembersDAO();

		try(Scanner sc = new Scanner(System.in);){
			while(true) {
				System.out.println("<< index >>");
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");
				System.out.println("3. 회원 목록 출력");
				System.out.println("0. 종료");
				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu == 1) {
						System.out.println("<<로그인>>");

						System.out.print("아이디 입력 : ");
						String id = sc.nextLine();

						// 단방향 암호화를 적용
						// SHA - 256 , 512
						System.out.print("비밀번호 입력 : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());

						boolean login = membersdao.login(id,pw);
						if(login) {
							System.out.println("로그인 성공");
							break;
						}else {
							System.out.println("로그인 실패");
						}			
					}else if(menu == 2) {
						System.out.println("<<회원 가입>>");

						// 아이디 중복 검사
						// 사용 불가시 "이미 사용중인 아이디 입니다." 후 메뉴로 복귀
						// 사용 가능하다면 PW입력으로..
						System.out.print("생성할 아이디 입력 : ");
						String id = sc.nextLine();

						boolean m =membersdao.searchidOk(id);
						if(m) {
							System.out.println("아이디가 중복되었습니다.");
							continue;
						}
						
						System.out.print("생성할 비밀번호 입력 : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());

						System.out.print("사용자 이름 입력 : ");
						String name = sc.nextLine();
						System.out.print("사용자 연락처 입력 : ");
						String contact = sc.nextLine();
						int result = membersdao.insert(new MembersDTO(id, pw, name, contact,null));
						if(result>0) {
							System.out.println("아이디 생성");
						}else {
							System.out.println("생성 실패");
						}
					}else if(menu==3) {
						List<MembersDTO> list = membersdao.selectAll();
						System.out.println("ID\t시간");
						for(MembersDTO dto: list) {
							
							System.out.println(dto.getId()+"\t"+dto.getFormedDate());
						}
					}else if(menu == 0) {
						System.out.println("시스템을 종료합니다.");
						System.exit(0);
					}else {
						System.out.println("메뉴를 다시 확인해주세요.");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}


			Student_Dao dao = new Student_Dao();

			while(true) {
				try {
					System.out.println("1.학생정보 입력");
					System.out.println("2.학생목록 출력(학번,이름 국어,영어,수학,합계,평균)");
					System.out.println("3.학생정보 수정");
					System.out.println("4.학생정보 삭제");
					System.out.println("5.학생정보 검색");
					System.out.println("0.학생정보 입력");
					int menu = Integer.parseInt(sc.nextLine());

					if(menu==1) {
						System.out.print("이름 : ");
						String name = sc.nextLine();
						System.out.print("국어 : ");
						int kor = Integer.parseInt(sc.nextLine());
						System.out.print("영어 : ");
						int eng = Integer.parseInt(sc.nextLine());
						System.out.print("수학 : ");
						int math = Integer.parseInt(sc.nextLine());


						int result = dao.insert(new Student_Dto(0,name,kor,eng,math));

						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}else if(menu==2) {
						List<Student_Dto> result = dao.select();

						System.out.println("ID\t이름\t국어\t영어\t수학\t합계\t평균");
						for(Student_Dto dto : result) {
							System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getKor()+"\t"+dto.getEng()+"\t"+dto.getMath()
							+"\t"+dto.sum()+"\t"+dto.avg());
						}
					}else if(menu==3) {
						System.out.print("수정할 ID : ");
						int id = Integer.parseInt(sc.nextLine());

						boolean bol = dao.isExist(id);

						if(!bol) {
							System.out.println("ID ["+id+"]를 찾을 수 없습니다.");
							continue;
						}

						System.out.println("국어 점수 :");
						int kor = Integer.parseInt(sc.nextLine());
						System.out.println("영어 점수 :");
						int eng = Integer.parseInt(sc.nextLine());
						System.out.println("수학 점수 :");
						int math = Integer.parseInt(sc.nextLine());

						int result = dao.update(new Student_Dto(id,null,kor,eng,math));
						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}else if(menu==4) {
						System.out.print("삭제할 ID : ");
						int id = Integer.parseInt(sc.nextLine());

						int result = dao.delete(id);
						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}else if(menu==5) {
						System.out.print("검색할 이름 : ");
						String name = sc.nextLine();

						List<Student_Dto> result = dao.issele(name);

						for(Student_Dto dto : result) {
							System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getKor()+"\t"+dto.getEng()+"\t"+dto.getMath());
						}
					}
				}catch(Exception e) {
					System.out.println("x");
					e.printStackTrace();
				}
			}

		}

	}
}

















