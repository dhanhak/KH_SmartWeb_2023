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
				System.out.println("1. �α���");
				System.out.println("2. ȸ������");
				System.out.println("3. ȸ�� ��� ���");
				System.out.println("0. ����");
				int menu = Integer.parseInt(sc.nextLine());
				try {
					if(menu == 1) {
						System.out.println("<<�α���>>");

						System.out.print("���̵� �Է� : ");
						String id = sc.nextLine();

						// �ܹ��� ��ȣȭ�� ����
						// SHA - 256 , 512
						System.out.print("��й�ȣ �Է� : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());

						boolean login = membersdao.login(id,pw);
						if(login) {
							System.out.println("�α��� ����");
							break;
						}else {
							System.out.println("�α��� ����");
						}			
					}else if(menu == 2) {
						System.out.println("<<ȸ�� ����>>");

						// ���̵� �ߺ� �˻�
						// ��� �Ұ��� "�̹� ������� ���̵� �Դϴ�." �� �޴��� ����
						// ��� �����ϴٸ� PW�Է�����..
						System.out.print("������ ���̵� �Է� : ");
						String id = sc.nextLine();

						boolean m =membersdao.searchidOk(id);
						if(m) {
							System.out.println("���̵� �ߺ��Ǿ����ϴ�.");
							continue;
						}
						
						System.out.print("������ ��й�ȣ �Է� : ");
						String pw = EncryptionUtils.sha512(sc.nextLine());

						System.out.print("����� �̸� �Է� : ");
						String name = sc.nextLine();
						System.out.print("����� ����ó �Է� : ");
						String contact = sc.nextLine();
						int result = membersdao.insert(new MembersDTO(id, pw, name, contact,null));
						if(result>0) {
							System.out.println("���̵� ����");
						}else {
							System.out.println("���� ����");
						}
					}else if(menu==3) {
						List<MembersDTO> list = membersdao.selectAll();
						System.out.println("ID\t�ð�");
						for(MembersDTO dto: list) {
							
							System.out.println(dto.getId()+"\t"+dto.getFormedDate());
						}
					}else if(menu == 0) {
						System.out.println("�ý����� �����մϴ�.");
						System.exit(0);
					}else {
						System.out.println("�޴��� �ٽ� Ȯ�����ּ���.");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}


			Student_Dao dao = new Student_Dao();

			while(true) {
				try {
					System.out.println("1.�л����� �Է�");
					System.out.println("2.�л���� ���(�й�,�̸� ����,����,����,�հ�,���)");
					System.out.println("3.�л����� ����");
					System.out.println("4.�л����� ����");
					System.out.println("5.�л����� �˻�");
					System.out.println("0.�л����� �Է�");
					int menu = Integer.parseInt(sc.nextLine());

					if(menu==1) {
						System.out.print("�̸� : ");
						String name = sc.nextLine();
						System.out.print("���� : ");
						int kor = Integer.parseInt(sc.nextLine());
						System.out.print("���� : ");
						int eng = Integer.parseInt(sc.nextLine());
						System.out.print("���� : ");
						int math = Integer.parseInt(sc.nextLine());


						int result = dao.insert(new Student_Dto(0,name,kor,eng,math));

						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}else if(menu==2) {
						List<Student_Dto> result = dao.select();

						System.out.println("ID\t�̸�\t����\t����\t����\t�հ�\t���");
						for(Student_Dto dto : result) {
							System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getKor()+"\t"+dto.getEng()+"\t"+dto.getMath()
							+"\t"+dto.sum()+"\t"+dto.avg());
						}
					}else if(menu==3) {
						System.out.print("������ ID : ");
						int id = Integer.parseInt(sc.nextLine());

						boolean bol = dao.isExist(id);

						if(!bol) {
							System.out.println("ID ["+id+"]�� ã�� �� �����ϴ�.");
							continue;
						}

						System.out.println("���� ���� :");
						int kor = Integer.parseInt(sc.nextLine());
						System.out.println("���� ���� :");
						int eng = Integer.parseInt(sc.nextLine());
						System.out.println("���� ���� :");
						int math = Integer.parseInt(sc.nextLine());

						int result = dao.update(new Student_Dto(id,null,kor,eng,math));
						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}else if(menu==4) {
						System.out.print("������ ID : ");
						int id = Integer.parseInt(sc.nextLine());

						int result = dao.delete(id);
						if(result>0) {
							System.out.println("Y");
						}else {
							System.out.println("N");
						}
					}else if(menu==5) {
						System.out.print("�˻��� �̸� : ");
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
















