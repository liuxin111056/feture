package demo;


import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class shuanfa {
    /**1
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
    /**2
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

    /**3
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

    /**4
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



/**5
 *  在包含1~100的数组中，查找任意丢失的一个数字
  思路：1. 算出1~100的和sum.
        2. 计算出给定数组所有数之和otherSum.
        3. 丢失的数字为sum-otherSum.
       */
    public static int findMissingNumber(int[] nums) {
        int n = nums.length;
        int sum = (n+1) * n/2;
        int otherSum = 0;
        for (int i : nums) {
            otherSum += i;
        }

        return sum-otherSum;
    }

    /**6
     * 如何找到一个给定的整型数组中的重复数字？
     *
     * @param
     * @return
     */
    public static void findSameNumber(int[] num) {
        int NumChange;
        System.out.println("重复数字是：");
        for(int index = 0; index < num.length; index++) {
            while(num[index] != index) {
                if(num[index] == num[num[index]]) {
                    System.out.print(num[index]+" ");
                    break;
                } else {
                    NumChange = num[num[index]];
                    num[num[index]] = num[index];
                    num[index] = NumChange;
                }
            }
        }
    }
    /**6
     * 如何找到一个给定的整型数组中的重复数字？
     *
     * @param
     * @return
     */
    public static void findSameNumber1(int[] num) {
        Arrays.sort(num);
        int[] arr=new int[num[num.length-1]+1];
        for(int i = 0; i < num.length-1; i++) {
                arr[num[i]]++;
        }
        for (int i=0;i<arr.length;i++){
            if (arr[i]>1){
                System.out.println("重复的数："+i);
            }
        }
    }

    /**7
     * 在一个未排序的整型数组中，如何找到最大和最小的数字？
     */
    public void findMaxAndMin(int[] arr){
        int min=arr[0];
        int max=arr[0];
        for (int i=0;i<arr.length;i++){
            if (arr[i]<min){
                min=arr[i];
            }
            if (arr[i]>max){
                max=arr[i];
            }
        }
        System.out.println("最大数："+max+"  最小数："+min);
    }

    /**9
     * 在一个整型数组中，如何找到一个所有成对的数字，满足它们的和等于一个给定的数字？
     */
    public static void twoSum(int[] arr,int target) {
        Map<Integer,Integer> map=new HashMap<>();
        for (int i=0;i<arr.length;i++){
            map.put(arr[i],i);
        }
        for (int i=0;i<arr.length;i++){
            if (map.containsKey(target-arr[i])){
                int j=map.get(target-arr[i]);
                System.out.println(arr[i]+"--"+arr[j]);
            }
        }
    }

    @Test
    public void tt(){
        int days= findDay(2015,12,12);
       // System.out.println(days);

        int []a={1,2,31,42,51,60};
        int num=findTwo(a,51);
      //  System.out.println(num);

       // String aa=reverseByStack("123456");
      //  System.out.println(aa);
    //6
        int[] arr={0,2,2,5,3,4,-1,4,9,99,99,99,99,99,10000};

       // findSameNumber(arr);
       //findSameNumber1(arr);
        //findMaxAndMin(arr);
        twoSum(arr,7);
    }
 }
