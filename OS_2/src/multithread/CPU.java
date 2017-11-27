package multithread;

import java.util.*;

import static java.lang.Thread.sleep;

public class CPU {
    private final int FCFS = 1;
    private final int SJF = 2;
    private final int HRN = 3;
    private final int RR = 4;
    private final int RUNNING = 1;
    private final int WAITING = 2;
    public final int totalTask = 4;

    private int method;
    private int status;
    public int taskNum = 0;
    private Set<Task> tasks = new HashSet<>();
    private Queue<Task> fcfs_q = new LinkedList<>();

    public CPU(int method) {
        this.method = method;
        this.status = WAITING;
    }

    //线程添加任务到任务池
    public void addTask(Task task) {
        tasks.add(task);
        this.Process(task);
    }

    //不同调度算法给cpu提交作业
    public void Process(Task task) {
        switch (method) {
            case FCFS:
                FCFS();
                break;
            case SJF:
                SJF();
                break;
            case HRN:
                HRN();
                break;
            case RR:
                RR();
                break;
        }
    }

    //从任务池中选择任务处理
    private void FCFS(Task task) {
        Task t;
        synchronized (fcfs_q) {
            fcfs_q.offer(task);
        }
        long start = System.currentTimeMillis();
        long end;
        synchronized (this) {
            while (this.status == RUNNING) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t = fcfs_q.poll();
            this.status = RUNNING;
            end = System.currentTimeMillis();
            Processing(t);
            this.status = WAITING;
            notifyAll();
        }
        t.setFinishTime(t.getArriveTime() + (end - start) / 1000 + t.getRunTime());
        t.setRoundTime(t.getFinishTime() - t.getArriveTime());
        System.out.println("t.getFinishTime() = " + t.getFinishTime());
    }

    private synchronized void FCFS() {
        Iterator<Task> iterator = tasks.iterator();
        Task chosenOne = iterator.next();
        while (iterator.hasNext()) {
            Task t = iterator.next();
            if (chosenOne.getArriveTime() > t.getArriveTime())
                chosenOne = t;
        }
        Processing(chosenOne);
    }

    private void SJF() {
        long start = System.currentTimeMillis();
        long end;
        //选择任务
        synchronized (this) {
            while (this.status==RUNNING) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.status=WAITING;
            end = System.currentTimeMillis();
            Iterator<Task> iterator = tasks.iterator();
            Task chosenOne = iterator.next();
            while (iterator.hasNext()) {
                Task t = iterator.next();
                if (chosenOne.getRunTime() > t.getRunTime()) {
                    chosenOne = t;
                }
            }
            Processing(chosenOne);
            this.status=WAITING;
            notifyAll();
            chosenOne.setFinishTime((end - start) / 1000 + chosenOne.getArriveTime() + chosenOne.getRunTime());
            System.out.println("chosenOne.getFinishTime() = " + chosenOne.getFinishTime());
        }
    }

    private void HRN() {

    }

    private void RR() {

    }

    //执行被选中的任务
    private void Processing(Task task) {
        synchronized (System.out) {
            System.out.print(task.getTaskName() + " is running...");
            try {
                sleep((long) task.getRunTime() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished in " + task.getRunTime() + "s!");
        }
        tasks.remove(task);
    }
}
