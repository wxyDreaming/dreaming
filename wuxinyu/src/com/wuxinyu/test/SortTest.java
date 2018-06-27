package com.wuxinyu.test;

public class SortTest {

	public static void main(String[] args) {
		System.out.println("Hello World");
		int[] a = { 12, 20, 5, 16, 15, 1, 30, 45, 23, 9 };
		int start = 0;
		int end = a.length - 1;
		sort(a, start, end);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}

	}

	public static void sort(int[] a, int low, int high) {
		int start = low;
		int end = high;
		int key = a[low];
		while (end > start) {
			// 从后往前比较
			while (end > start && a[end] >= key)
				// 如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
				end--;
			if (a[end] <= key) {
				int temp = a[end];
				a[end] = a[start];
				a[start] = temp;
			}
			// 从前往后比较
			while (end > start && a[start] <= key)
				// 如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
				start++;
			if (a[start] >= key) {
				int temp = a[start];
				a[start] = a[end];
				a[end] = temp;
			}
			// 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
		}
		// 递归
		if (start > low)
			sort(a, low, start - 1);// 左边序列。第一个索引位置到关键值索引-1
		if (end < high)
			sort(a, end + 1, high);// 右边序列。从关键值索引+1到最后一个
	}
	
	public static void quickSort(int a[]) {
		sort1(a, 0, a.length - 1);
	}
	
	public static void sort1(int a[], int low, int hight) {
        int i, j, index;
        if (low > hight) {
            return;
        }
        i = low;
        j = hight;
        index = a[i]; // 用子表的第一个记录做基准
        while (i < j) { // 从表的两端交替向中间扫描
            while (i < j && a[j] >= index)
                j--;
            if (i < j)
                a[i++] = a[j];// 用比基准小的记录替换低位记录
            while (i < j && a[i] < index)
                i++;
            if (i < j) // 用比基准大的记录替换高位记录
                a[j--] = a[i];
        }
        a[i] = index;// 将基准数值替换回 a[i]
        sort1(a, low, i - 1); // 对低子表进行递归排序
        sort1(a, i + 1, hight); // 对高子表进行递归排序
    }

}
