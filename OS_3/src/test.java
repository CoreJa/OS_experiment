import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        int[] tracks = {98, 183, 37, 122, 14, 124, 65, 67};
        Scanner in = new Scanner(System.in);
/*        System.out.print("请输入磁盘访问序列，输入-1结束输入：");
        ArrayList<Integer> arrayList = new ArrayList<>();
        int a = in.nextInt();
        while (a != -1)
            arrayList.add(a);
        int[] tracks = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            tracks[i] = arrayList.indexOf(i);
        }*/
        System.out.println("1.FCFS 2.SSTF 3.SCAN");
        System.out.print("请输入调度算法：");
        int method = in.nextInt();
        System.out.println("请输入磁头起始位置：");
        int headAt = in.nextInt();
        System.out.println("请输入方向（0.左 1.右）：");
        boolean direction = in.nextInt() != 0;
        HardDrive hd = new HardDrive(tracks, headAt, direction, method);
        hd.process();
    }
}
