import java.util.Scanner;

public class Exam_01 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("당신이 좋아하는 과일은 무엇입니까?");
		System.out.println("1.사과 2.망고 3.딸기");
		System.out.print(">>");
		int menu = Integer.parseInt(sc.nextLine());
		
		if(menu == 1 || menu == 2 || menu == 3) {
			
			if(menu == 1) {
				System.out.println("사과는 영어로 Apple 입니다.");
			}else if(menu == 2){	//종속성 문법
				System.out.println("망고는 영어로 mango 입니다.");
			}else if(menu ==3 ){
				System.out.println("딸기는 영어로 Strawberry 입니다.");
			}
		}else {
			System.out.println("메뉴를 다시 고르시오.");
		}
		
		
	}
}
