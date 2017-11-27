package easy;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("调度算法：1.FCFS 2.SJF 3.HRN 4.RR");
        System.out.print("请选择调度算法：");
        int method = Integer.parseInt(in.next());
        CPU cpu = new CPU(method);
        System.out.print("请输入任务数量（输入0将以默认任务参数提交）：");
        Task.setTaskNum(in.nextInt());
        for (int i = 0; i < Task.getTaskNum(); i++) {
            System.out.print("请输入任务"+(i+1)+"的名称：");
            String name =in.next();
            System.out.print("请输入任务"+(i+1)+"的到达时间：");
            double arriveTime=in.nextDouble();
            System.out.print("请输入任务"+(i+1)+"的运行时间：");
            double runTime=in.nextDouble();
            new Task(name,arriveTime,runTime,cpu);
        }
        if (Task.getTaskNum()==0) {
            Task.setTaskNum(4);
            new Task("Task1", 8, 2, cpu);
            new Task("Task2", 8.5, 0.5, cpu);
            new Task("Task3", 9, 0.1, cpu);
            new Task("Task4", 9.5, 0.2, cpu);
        }
        cpu.Process();
    }
}
