package com.wuxinyu.test;

public class Print {

	
	public static void main(String[] args) {
		star(1);
	}
	
	static void star(int index){
		int number = index / 2, i = 0, count = 0;
		while(i < index){
			for(int j = 0; j < index; j++){
				System.out.print((j <= count + number && j >= number -count)?"*":" ");
			}
			System.out.println();
			if(i >= number)
				count--;
			else
				count++;
			i++;
		}
	}
	
	static void star1(int row){
		for(int i = 0; i < row; i++){
			int count = row/2 - i;
			if(count < 0){
				count = Math.abs(count);
			}
			for(int j = 0; j < count; j++){
				System.out.print(" ");
			}
			for (int k = 0; k < row-2*count; k++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	
	static void star2(int number) {
		int total = number / 2 + 1;// 首行的列数
        for (int row = 1; row <= number; row++) {
            int count = total + (row - 1);// 每行需要的列数
            if (row > total) {
                count = count - (2 * (row - total));
            }
            int columnSpace = Math.abs(row - total);// 每行需要的空格数
            for (int column = 0; column < count; column++) {
                if (column < columnSpace) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();// 换行
        }
	}
}
