package demo;
//在一个数组中找出出现次数最多的字符
public class BubbleSort {
    public static void main(String[] args){
        String s="aaahssjhssssssshwws123222";
        // 转化为字符数组。
        char[] c=s.toCharArray();
        int[] b=new int[127];
        for(int i=0;i<c.length-1;i++){
            b[c[i]]++;
        }
        int max=b[0];   //定义次数
        int value=0;    //定义最大值
        for(int i=1;i<b.length;i++){
            if(b[i]>max){
                max=b[i];
                value=i;
            }
        }
        System.out.print("出现次数最多的值是:"+(char)value+", 出现了"+max+"次");
    }

}

