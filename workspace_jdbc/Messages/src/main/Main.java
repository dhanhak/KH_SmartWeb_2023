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
				System.out.println("1.�α���");
				System.out.println("2.ȸ������ - (���̵� �н����� �̸� ����ó)");
				System.out.println("3.����");
				System.out.print(">>");
						
				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu==1) {
						System.out.println("< < �α��� > >");

						System.out.print("id : ");
						id = sc.nextLine();
						System.out.println("pw : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());

						boolean bol = membersdao.login(new MembersDTO(id,pw,null,null,null));
						if(bol) {
							System.out.println("�α��� ����!");

							break;
						}else {
							System.out.println("�α��� ����");
						}

					}else if(menu==2) {
						System.out.print("id �Է� : ");
						id = sc.nextLine();
						System.out.print("pw �Է� : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());


						System.out.print("�̸� �Է� : ");
						String name = sc.nextLine();
						System.out.print("����ó �Է� : ");
						String contact = sc.nextLine();

						int result = membersdao.insert(new MembersDTO(id,pw,name,contact,null));
						if(result>0) {
							System.out.println("���̵� ����");
						}else {
							System.out.println("����");
						}
					}else if(menu==3) {
						System.out.println("����");
						System.exit(0);
					}
				}catch(Exception e ) {
					System.out.println("���ӽ���");
					e.printStackTrace();
				}
			}

			//����
			MessagesDAO messagesdao = new MessagesDAO();

			while(true) {
				System.out.println("1. �ű� �޼��� ��� (�ۼ���,�޼��� �����Է�)");
				System.out.println("2. �޼��� ��� ��� (�� ��ȣ, �ۼ���, �۳��� ,�ۼ��� [MM/dd])");
				System.out.println("3. �޼��� ���� (seq �� ����)");
				System.out.println("4. �޼��� ���� (seq�� �����ϵ�, writer�� message�� ���� ����");
				System.out.println("5. �޼��� �˻� (writer �Ǵ� message�� �˻�� �����ϴ� ���)");
				System.out.println("6. ������ ���� (���̵� �̸� ����ó ������[]");
				System.out.println("0. �ý��� ����");
				System.out.print(">>");

				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu == 1 ) {
						System.out.print("�ۼ��� : ");
						String writer = sc.nextLine();
						System.out.print("message : ");
						String message = sc.nextLine();

						int result = messagesdao.insert(new MessagesDTO(0,writer,message,null));
						if(result>0) {
							System.out.println("�޼��� �ۼ� ����");
						}else {
							System.out.println("�޼��� �ۼ� ����");
						}

					}else if(menu==2) {
						List<MessagesDTO> list =  messagesdao.selectAll();
						System.out.println("�۹�ȣ\t�ۼ���\t�۳���\t�ۼ���");
						for(MessagesDTO dto : list) {
							System.out.println(dto.getSeq()+"\t"+dto.getWriter()+"\t"+dto.getMessage()+"\t"+dto.getWrite_date());
						}
					}else if(menu==3) {
						System.out.print("������ ��ȣ : ");
						int seq = Integer.parseInt(sc.nextLine());
						int result = messagesdao.delete(new MessagesDTO(seq,null,null,null));
						if(result>0) {
							System.out.println("���� ����");
						}else {
							System.out.println("���� ����");
						}
					}else if(menu==4) {
						System.out.print("������ �� ��ȣ : ");
						int seq = Integer.parseInt(sc.nextLine());

						System.out.print("������ �̸� : ");
						String writer = sc.nextLine();
						System.out.print("������ �޼��� : ");
						String message = sc.nextLine();

						int result = messagesdao.update(new MessagesDTO(seq,writer,message,null));
						if(result>0) {
							System.out.println("���� �Ϸ�");
						}else {
							System.out.println("���� ����");
						}
					}else if(menu==5) {
						System.out.println("writer �Ǵ� message �˻�� �����ϴ� ��� ");

						System.out.println("writer : ");
						String writer = sc.nextLine();

						List<MessagesDTO> list = messagesdao.search(writer);
						for(MessagesDTO dto : list) {
								System.out.println(dto.getSeq()+"\t"+dto.getWriter()+"\t"+dto.getMessage()+"\t"+dto.getWrite_date());
						}

					}else if(menu==6) {
						System.out.println("������ ���� (���̵� �̸� ����ó ������[]");
						
						List<MembersDTO> list =  membersdao.information(id);
						for(MembersDTO dto : list) {
							System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getContact()+"\t"+dto.getReg_date());
						}
					}else if(menu==0) {
						System.out.println("�ý��� ����");
						System.exit(0);
					}
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("���� ����");
				}
			}
		}
	}
}