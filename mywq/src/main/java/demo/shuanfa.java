package demo;


import org.junit.Test;

import java.util.*;

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
       // int[] arr=new int[127];
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

    /**
     * 用 Java 实现从一个给定数组中删除重复元素？
     */
    /**
     * for (int i : attr) {
     * 			if (!list.contains(i)) {//boolean contains(Object o):如果列表包含指定的元素，则返回 true
     * 				list.add(i);
     *                        }
     * @param arr
     */
    public void RemoveSameNum2(int []arr){
        HashMap<Integer,Integer> map=new HashMap<>();
        for (int i=0;i<arr.length;i++){
            map.put(arr[i],0);
        }
        for(Integer key:map.keySet()){
            System.out.println(key);
        }
    }

    /**
     * 用 Java 实现数组反转？
     * @param str
     * @return
     */
    public static void reverseByStack(int []arr) {

        Stack<Integer> stack=new Stack<>();
        for (int i=0;i<arr.length;i++) {
            stack.push(arr[i]);
        }
        for (int i=0;i<arr.length;i++){
            arr[i]=stack.pop();
        }
       System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序
     */

    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

        while (i<j) {
            //先看右边，依次往左递减
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);
    }

    /**
     * 在一次遍历中，怎样发现单个链表的中间元素?
     */
    class ListNode{
        int data;
        ListNode next;


    }
    public static void FindMid(ListNode first){
        ListNode fast = first;
        ListNode slow = first;
        while((fast != null)&&(fast.next != null)){
            fast = fast.next.next;
            slow = slow.next;
        }
        System.out.println(slow.data);

    }

    /**
     * 怎样验证给定的链表是环形的? 怎样发现这个环的起始节点?
     * //定义两个指针tmp1,tmp2
     */
    public static boolean hasLoop(ListNode n){
        ListNode tmp1=n;
        ListNode tmp2=n.next;
        while (tmp2!=null){
            int d1=tmp1.data;
            int d2=tmp2.data;
            if (d1==d2) return true;
            tmp1 = tmp1.next;
            tmp2 = tmp2.next.next;
            if (tmp2==null) return false;
        }
        return true;
    }

    /**
     * 方法2：将每次走过的节点保存到hash表中，如果节点在hash表中，则表示存在环
     * @param n
     * @return
     */
    public static boolean hasLoop2(ListNode n){
        ListNode tmp=n;
        HashMap map=new HashMap();
        while (n!=null){
            if (map.get(tmp)!=null) return true;
            else map.put(tmp,tmp);
            tmp=tmp.next;
            if (tmp==null) return false;
        }
        return true;
    }

    /**
     * 怎样翻转链表?
     * 迭代法
     */
    public static void reverse(ListNode node){
        ListNode pre=null;
        ListNode now=node;
        while (now!=null){
            ListNode next=now.next;
            node.next=pre;
            pre=now;
            now=next;
        }
    }

    /**
     * 递归方法
     */
/*    public Node reverse2(Node node, Node prev) {
        if (node.next == null) {
            node.next = prev;
            return node;
        } else {
            Node re = reverse2(node.next, node);
            node.next = prev;
            return re;
        }
    }*/

    /**
     * 在未排序链表中，怎样移除重复的节点?
     * 使用缓冲区N   时间复杂度N
     */
    public static void deleteDups(ListNode head) {
        Hashtable<Integer, Boolean> table = new Hashtable<Integer, Boolean>();
        //当前指针的前一个指针
        ListNode pre = null;
        while (head != null) {
            if (table.contains(head.data)) {
                pre.next = head.next;
            } else {
                table.put(head.data, true);
                pre = head;
            }
            head = head.next;
        }
    }

    //2不使用缓冲区   时间复杂度N*N（直接遍历）
    public static void deleteDups2(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode current = head;
        while (current != null) {
            ListNode p = current;
            while (p.next != null) {
                if (p.next.data == current.data) {
                    p.next = p.next.next;
                } else {
                    p = p.next;
                }
            }
            current = current.next;
        }
    }

    /**
     * 字符去重？
     */
    public void deleteSameString(String str){
        char[] ch=str.toCharArray();
        List list=new ArrayList();
        for(char c:ch){
            if (!list.contains(c)){
                list.add(c);
            }
        }
        for (int i=0;i<list.size();i++){
           System.out.println(list.get(i));
        }
    }

    /**
     *  字符去重
     *  HashSet
     */
    public void deleteSameString1(String str){
        char[] ch=str.toCharArray();
        //保存去重后的字符
        Set<Character> set1=new HashSet<>();
        //保存去重的字符
        Set<Character> set2=new HashSet<>();
        for (char c:ch){
            boolean b=set1.add(c);
            if (!b){
                set2.add(c);
            }
        }
        for (char c:set1){
            System.out.println(c);
        }
        for (char c:set2){
            System.out.println(c);
        }
    }

    /**
     *如何使用递归实现字符串反转?
     */
    public String reverseStr(String str){
        if(str.length() <= 1){
            return str;
        }
        return reverseStr(str.substring(1)) + str.charAt(0);
    }
    /**
     *如何使用递归实现字符串反转?
     */
    public void reverseStr1(String str){
        StringBuffer bf=new StringBuffer();
        for (int i=str.length()-1;i>=0;i--){
            bf.append(str.charAt(i));
        }
        System.out.println(bf.toString());
    }

    /**
     * 如何从字符串中输出第一个不重复字符？
     */
    public String findFisrstStr(String str){
        for (int i=0;i< str.length();i++){
            String sub=str.substring(i,i+1);
            if ((str.length()- str.replace(sub,"").length())==1){
                return sub ;
            }
        }
        return "No Same";
    }

    /**
     * 如何在字符串中找到重复字符？
     */
    public void findSaneStr(String str){
        for (int i=0;i<str.length();i++){
            String sub=str.substring(i,i+1);
            if ((str.length()-str.replace(sub,"").length())>1){
                System.out.println(sub);
            }
        }
    }

    /**
     * 如何计算给定字符传中特定字符出现的次数？
     */
    public void CountNumStr(String str,String c){
        //for (int i=0;i<str.length();i++){
            System.out.println(str.length()-str.replace(c,"").length());
      //  }
    }

    /**
     * 如何判断两个字符串是否互为旋转？
     */
    public void isXuanzhuan(String s1,String s2){
        char[] ch1=new char[127];
        char[] ch2=new char[127];
        char[] s11=s1.toCharArray();
        char[] s22=s2.toCharArray();

        for (int i=0;i<s11.length;i++){
            ch1[s11[i]]++;
        }
        for (int i=0;i<s22.length;i++){
            ch2[s22[i]]++;
        }
        Arrays.sort(s11);
        Arrays.sort(s22);
        if (Arrays.equals(s11,s22)){
            System.out.println("1");
        }else {
            System.out.println("0");
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
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19,1000000,1000000};

       //findSameNumber(arr);
     //  findSameNumber1(arr);
        //findMaxAndMin(arr);
       // twoSum(arr,8);
        //RemoveSameNum2(arr);
        //reverseByStack(arr);
        //quickSort(arr,0,arr.length-1);
        //System.out.println(Arrays.toString(arr));
        ListNode n1 = new ListNode();
        ListNode n2 = new ListNode();
        ListNode n3 = new ListNode();
        ListNode n4 = new ListNode();
        ListNode n5 = new ListNode();
        ListNode n6 = new ListNode();
        n1.data = 1;
        n2.data = 2;
        n3.data = 3;
        n4.data = 4;
        n5.data = 5;
        n6.data=6;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next=n6;
       // System.out.println(hasLoop2(n1));
       // yang();
        //String aa=reverseStr("12333");
        //System.out.println(aa);
        //System.out.println(findFisrstStr("hoaohf"));
        //CountNumStr("122222223345","2");
        //isXuanzhuan("123","2311");

    }
 }
