package multithread;

import multithread.CPU;
import multithread.Task;

import java.util.*;

/*
* 已知bug unfixed
* 如果第一个任务占用cpu时间过短，
* 会导致第二个任务直接能占用cpu资源（第一个任务完成的太快（比如runTime为1.5时）第二个任务提交的太慢）
* 个人分析原因是线程开启的时间不统一，第一个任务是由主线程最先提交的
* t1.start()从主线程被开启后，主线程还没有运行t2.start()时，t1就已经运行结束了，导致t2的finishTime提前*/
public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("请选择调度算法：");
        int method = Integer.parseInt(in.next());
        CPU cpu = new CPU(method);
        Task t1 = new Task("Task1", 0, 2, cpu);
        Task t2 = new Task("Task2", 0.8, 1.2, cpu);
        Task t3 = new Task("Task3", 4, 2, cpu);
        Task t4 = new Task("Task4", 5, 0.7, cpu);
        Task t5 = new Task("Task5", 5.5, 0.3, cpu);

        //tasks start
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
/*        final ExecutorService taskPool = Executors.newFixedThreadPool(cpu.totalTask);
        taskPool.execute(t1);
        taskPool.execute(t2);
        taskPool.execute(t3);
        taskPool.execute(t4);*/
    }
}
