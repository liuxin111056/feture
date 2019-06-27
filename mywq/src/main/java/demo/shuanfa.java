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

    /**
     * 直接插入排序
     */
    public void insertSort(int[] a){
        int length=a.length;//数组长度，将这个提取出来是为了提高速度。
        int insertNum;//要插入的数
        for(int i=1;i<length;i++){//插入的次数
            insertNum=a[i];//要插入的数
            int j=i-1;//已经排序好的序列元素个数
            while(j>=0&&a[j]>insertNum){//序列从后到前循环，将大于insertNum的数向后移动一格
                a[j+1]=a[j];//元素移动一格
                j--;
            }
            a[j+1]=insertNum;//将需要插入的数放在要插入的位置。
        }
    }
    /**
     * 简单选择排序
     */
    public void selectSort(int[] a) {
        int length = a.length;
        for (int i = 0; i < length; i++) {//循环次数
            int key = a[i];
            int position=i;
            for (int j = i + 1; j < length; j++) {//选出最小的值和位置
                if (a[j] < key) {
                    key = a[j];
                    position = j;
                }
            }
            a[position]=a[i];//交换位置
            a[i]=key;
        }
    }
    /**
     * 堆排序
     */
    public  void heapSort(int[] a){
        System.out.println("开始排序");
        int arrayLength=a.length;
        //循环建堆
        for(int i=0;i<arrayLength-1;i++){
            //建堆

            buildMaxHeap(a,arrayLength-1-i);
            //交换堆顶和最后一个元素
            swap(a,0,arrayLength-1-i);
            System.out.println(Arrays.toString(a));
        }
    }
    private  void swap(int[] data, int i, int j) {
        int tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private void buildMaxHeap(int[] data, int lastIndex) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex]<data[biggerIndex+1]){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                if(data[k]<data[biggerIndex]){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * 希尔排序
     */
    public  void sheelSort(int[] a){
        int d  = a.length;
        while (d!=0) {
            d=d/2;
            for (int x = 0; x < d; x++) {//分的组数
                for (int i = x + d; i < a.length; i += d) {//组中的元素，从第二个数开始
                    int j = i - d;//j为有序序列最后一位的位数
                    int temp = a[i];//要插入的元素
                    for (; j >= 0 && temp < a[j]; j -= d) {//从后往前遍历。
                        a[j + d] = a[j];//向后移动d位
                    }
                    a[j + d] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     */
    public void bubbleSort(int[] a){
        int length=a.length;
        int temp;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a.length-i-1;j++){
                if(a[j]>a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

    /**
     * 快速排序
     */
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end))
                    i++;
                while ((numbers[j] > base) && (j > start))
                    j--;
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j)
                quickSort(numbers,start, j);
            if (end > i)
                quickSort(numbers, i, end);
        }
    }

    /**
     * 归并排序
     */
    public static void mergeSort(int[] numbers, int left, int right) {
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(numbers, i, i + (s - 1), i + (t - 1));
                i += t;
            }
            if (i + (s - 1) < right)
                merge(numbers, i, i + (s - 1), right);
        }
    }
    private static void merge(int[] data, int p, int q, int r) {
        int[] B = new int[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (data[s] <= data[t]) {
                B[k] = data[s];
                s++;
            } else {
                B[k] = data[t];
                t++;
            }
            k++;
        }
        if (s == q + 1)
            B[k++] = data[t++];
        else
            B[k++] = data[s++];
        for (int i = p; i <= r; i++)
            data[i] = B[i];
    }

    /**
     * 基数排序
     */
    public void sort(int[] array) {
        //首先确定排序的趟数;
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        int time = 0;
        //判断位数;
        while (max > 0) {
            max /= 10;
            time++;
        }
        //建立10个队列;
        List<ArrayList> queue = new ArrayList<ArrayList>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> queue1 = new ArrayList<Integer>();
            queue.add(queue1);
        }
        //进行time次分配和收集;
        for (int i = 0; i < time; i++) {
            //分配数组元素;
            for (int j = 0; j < array.length; j++) {
                //得到数字的第time+1位数;
                int x = array[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList<Integer> queue2 = queue.get(x);
                queue2.add(array[j]);
                queue.set(x, queue2);
            }
            int count = 0;//元素计数器;
            //收集队列元素;
            for (int k = 0; k < 10; k++) {
                while (queue.get(k).size() > 0) {
                    ArrayList<Integer> queue3 = queue.get(k);
                    array[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
    }
    @Test
    public void tt(){
        int days= findDay(2015,12,12);


        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19,1000000,1000000};
        insertSort(arr);

    }
 }
