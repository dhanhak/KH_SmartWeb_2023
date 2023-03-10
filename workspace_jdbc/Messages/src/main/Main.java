package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import commons.EncryptionUtils;
import dao.MembersDAO;
import dao.MessagesDAO;
import dto.MembersDTO;
import dto.MessagesDTO;

public class Main {
	public static void main(String[] args) {
		MembersDAO membersdao = new MembersDAO();
		String id;
		try(Scanner sc = new Scanner(System.in);){
			
			while(true) {
				System.out.println("1.로그인");
				System.out.println("2.회원가입 - (아이디 패스워드 이름 연락처)");
				System.out.println("3.종료");
				System.out.print(">>");
						
				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu==1) {
						System.out.println("< < 로그인 > >");

						System.out.print("id : ");
						id = sc.nextLine();
						System.out.println("pw : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());

						boolean bol = membersdao.login(new MembersDTO(id,pw,null,null,null));
						if(bol) {
							System.out.println("로그인 성공!");

							break;
						}else {
							System.out.println("로그인 실패");
						}

					}else if(menu==2) {
						System.out.print("id 입력 : ");
						id = sc.nextLine();
						System.out.print("pw 입력 : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());


						System.out.print("이름 입력 : ");
						String name = sc.nextLine();
						System.out.print("연락처 입력 : ");
						String contact = sc.nextLine();

						int result = membersdao.insert(new MembersDTO(id,pw,name,contact,null));
						if(result>0) {
							System.out.println("아이디 생성");
						}else {
							System.out.println("실패");
						}
					}else if(menu==3) {
						System.out.println("종료");
						System.exit(0);
					}
				}catch(Exception e ) {
					System.out.println("접속실패");
					e.printStackTrace();
				}
			}

			//시작
			MessagesDAO messagesdao = new MessagesDAO();

			while(true) {
				System.out.println("1. 신규 메세지 등록 (작성자,메세지 직접입력)");
				System.out.println("2. 메세지 목록 출력 (글 번호, 작성자, 글내용 ,작성일 [MM/dd])");
				System.out.println("3. 메세지 삭제 (seq 로 삭제)");
				System.out.println("4. 메세지 수정 (seq로 수정하되, writer및 message만 수정 가능");
				System.out.println("5. 메세지 검색 (writer 또는 message에 검색어를 포함하는 대상)");
				System.out.println("6. 내정보 보기 (아이디 이름 연락처 가입일[]");
				System.out.println("0. 시스템 종료");
				System.out.print(">>");

				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu == 1 ) {
						System.out.print("작성자 : ");
						String writer = sc.nextLine();
						System.out.print("message : ");
						String message = sc.nextLine();

						int result = messagesdao.insert(new MessagesDTO(0,writer,message,null));
						if(result>0) {
							System.out.println("메세지 작성 성공");
						}else {
							System.out.println("메세지 작성 실패");
						}

					}else if(menu==2) {
						List<MessagesDTO> list =  messagesdao.selectAll();
						System.out.println("글번호\t작성자\t글내용\t작성일");
						for(MessagesDTO dto : list) {
							System.out.println(dto.getSeq()+"\t"+dto.getWriter()+"\t"+dto.getMessage()+"\t"+dto.getWrite_date());
						}
					}else if(menu==3) {
						System.out.print("삭제할 번호 : ");
						int seq = Integer.parseInt(sc.nextLine());
						int result = messagesdao.delete(new MessagesDTO(seq,null,null,null));
						if(result>0) {
							System.out.println("삭제 성공");
						}else {
							System.out.println("삭제 실패");
						}
					}else if(menu==4) {
						System.out.print("수정할 글 번호 : ");
						int seq = Integer.parseInt(sc.nextLine());

						System.out.print("수정할 이름 : ");
						String writer = sc.nextLine();
						System.out.print("수정할 메세지 : ");
						String message = sc.nextLine();

						int result = messagesdao.update(new MessagesDTO(seq,writer,message,null));
						if(result>0) {
							System.out.println("수정 완료");
						}else {
							System.out.println("수정 실패");
						}
					}else if(menu==5) {
						System.out.println("writer 또는 message 검색어를 포함하는 대상 ");

						System.out.println("writer : ");
						String writer = sc.nextLine();

						List<MessagesDTO> list = messagesdao.search(writer);
						for(MessagesDTO dto : list) {
								System.out.println(dto.getSeq()+"\t"+dto.getWriter()+"\t"+dto.getMessage()+"\t"+dto.getWrite_date());
						}
					}else if(menu==6) {
						System.out.println("내정보 보기 (아이디 이름 연락처 가입일[]");
						
						List<MembersDTO> list =  membersdao.information(id);
						for(MembersDTO dto : list) {
							System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getContact()+"\t"+dto.getReg_date());
						}
					}else if(menu==0) {
						System.out.println("시스템 종료");
						System.exit(0);
					}
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("연결 실패");
				}
			}
		}
	}
}
