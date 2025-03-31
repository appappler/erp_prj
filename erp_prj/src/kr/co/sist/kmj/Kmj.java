package kr.co.sist.kmj;

public class Kmj {
    public static void main(String[] args) {
        int size = 10; // 패턴 크기 조절 가능
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < (2 * i + 1); j++) {
                if (j == 0 || j == 2 * i || i == size - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        for (int i = size - 2; i >= 0; i--) {
            for (int j = 0; j < size - i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < (2 * i + 1); j++) {
                if (j == 0 || j == 2 * i) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}