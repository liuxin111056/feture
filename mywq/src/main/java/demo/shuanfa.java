package demo;


import org.junit.Test;

import java.util.Stack;

public class shuanfa {
    /**
     * 打印杨辉三角
     */
    @Test
    public void yang(){
        int num = 10;
        int[][] arr = new int[num][];
        for (int i = 0; i < arr.length; i++)
            arr[i] = new int[i + 1];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = 1;
                if (i > 1 && j > 0 && j < i) {
                    arr[i][j] = arr[i - 1][j] + arr[i - 1][j - 1];
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < (num - i) / 2; j++)
                System.out.print("\t");
            for (int j = 0; j < arr[i].length; j++)
                System.out.print(arr[i][j] + "\t");
            System.out.println();
        }

    }
    /**
     *输出给定日期是一年的第几天
     */
    public int findDay(int year,int mouth,int day){
        boolean isLeapYear=false;
        int days=day;
        int []leapYear = {31,29,31,30,31,30,31,31,30,31,30,31};  //闰年12个月的天数
        int []commonYear = {31,28,31,30,31,30,31,31,30,31,30,31}; //平年12个月的天数
        if (year<0||mouth<0||day<0||day>31)
            return 0;
        if (year %400 ==0 || (year%4 ==0 && year%100!=0))
            isLeapYear=true;
        for (int i=0;i<mouth-1;i++) {
            if (isLeapYear) {
                days+=leapYear[i];
            }else{
                days+=commonYear[i];
            }

        }
        return days;
    }

    /**
     * 1 2 3 4 5 6 二分查找
     */
    public int findTwo(int[]a,int key) {

        int low = 0;
        int high = a.length - 1;
        while (low <=high){
            int mid=(low+high)>>>1;
            int midVal=a[mid];
            if (midVal<key)
                low=mid+1;
            else if (midVal>key)
                high=mid-1;
            else
                return mid;
        }
        return 0;
    }

    /**
     * 栈的后进先出，逆序输出字符串
     */

    public static String reverseByStack(String str) {

        Stack<Character> stack=new Stack<>();
        char []arr=str.toCharArray();
        for (Character ch :arr)
            stack.push(ch);
        for (int i=0;i<arr.length;i++){
            arr[i]=stack.pop();
        }
        return new String(arr);
    }
    @Test
    public void tt(){
       int days= findDay(2015,12,12);
       System.out.println(days);

        int []a={1,2,31,42,51,60};
        int num=findTwo(a,51);
        System.out.println(num);

        String aa=reverseByStack("123456");
        System.out.println(aa);
    }
}
