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
		// Netflix ��ȭ ���� ���α׷�
		// ���̺� �̸� : movies
		// id - 1001~���ѱ��� 1�������ϴ� ��Ű
		// title - ���ڿ� �ִ� 50����Ʈ
		// genre - ���ڿ� 20����Ʈ
		// ��� not null

		// main�� throws ���� �ϸ� ���α׷� �������� main���� ����ó���ؾ���

		

			String url ="jdbc:oracle:thin:@localhost:1521:xe";
			String id ="kh";
			String pw ="kh";

			while(true) {
				System.out.println("<<Netfilx ��ȭ ���� ���α׷�>>");
				System.out.println("1. �ű� ��ȭ ���");	// C
				System.out.println("2. ��ȭ ��� ���");	// R
				System.out.println("3. ��ȭ ���� ����");	// U
				System.out.println("4. ��ȭ ���� ����");	// D
				System.out.println("5. ��ȭ ���� �˻�");
				System.out.println(">>");
				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu==1) {

						System.out.print("title �Է� : ");
						String ntitle = sc.nextLine();
						System.out.print("genre �Է� : ");
						String ngenre = sc.nextLine();


						int result = dao.insert(new Movies_DTO(0,ntitle,ngenre));
						if(result>0) {
							System.out.println("�Է� �Ϸ�");
						}

					}else if(menu==2) {
						List<Movies_DTO> result = dao.SelectAll();

						System.out.println("id\title\tgenre");
						for(Movies_DTO dto : result) {
							System.out.println(dto.getId()+"\t"+dto.getTitle()+"\t"+dto.getGenre());
						}
					}else if(menu==3) {

						System.out.print("������ id : ");
						int nid = Integer.parseInt(sc.nextLine());

						boolean isIdExist = dao.isIdExist(nid);
						if(!isIdExist) {
							System.out.println("ID : ["+nid+"] ã�� �� �����ϴ�.");
							continue;
						}

						System.out.println("�ű� ��ȭ ���� : ");
						String ntitle = sc.nextLine();

						System.out.println("�ű� ��ȭ �帣 : ");
						String ngenre = sc.nextLine();

						int result = dao.update(new Movies_DTO(nid,ntitle,ngenre));

						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}
					else if(menu==4) {

						System.out.print("������ id : ");
						int nid = Integer.parseInt(sc.nextLine());

						int result = dao.delete(new Movies_DTO(nid,null,null));

						if(result>0) {
							System.out.println("�����Ϸ�");
						}else {
							System.out.println("ID : ["+ nid +"] ��ȭ�� ã�� �� �����ϴ�.");
						}
					}
					else if(menu==5) {
						System.out.print("�˻��� ���� : ");
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