package easy;

import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class CPU {
    private final int FCFS = 1;
    private final int SJF = 2;
    private final int HRN = 3;
    private final int RR = 4;

    private int method;
    private double timeAt;
    private double timeRobin;//时间片长度
    private Set<Task> tasks = new HashSet<>();//任务池
    private Set<Task> arrivedTasks = new HashSet<>();//已到达任务池
    private Queue<Task> arrivedTasksQueue = new LinkedList<>();//已到达任务队列

    public CPU(int method) {
        this.method = method;
        this.timeAt = 0;
    }

    //添加任务到任务池
    public void addTask(Task task) {
        tasks.add(task);
    }

    //不同调度算法给cpu提交作业
    public void Process() {
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
    private void FCFS() {
        while (!tasks.isEmpty()) {
            processFirstOne();
        }
        setAvgTime();
        printAvg();
    }
    private void SJF() {
        while (!tasks.isEmpty()) {
            //将早于当前时间的所有任务加入已到达任务池，后续任务从已到达任务池中选取并执行，直到已到达任务池中没有任务
            updateArrivedTasks();
            //找出已到达任务池中符合条件的作业并执行
            while (!arrivedTasks.isEmpty()) {
                Iterator<Task> iterator = arrivedTasks.iterator();
                Task chosenOne = iterator.next();
                while (iterator.hasNext()) {
                    Task t = iterator.next();
                    if (chosenOne.getRunTime() > t.getRunTime())
                        chosenOne = t;
                }
                //执行作业
                processingJob(chosenOne);
                //从已到达任务池和任务池中清除
                arrivedTasks.remove(chosenOne);
                tasks.remove(chosenOne);
                setAllTime(chosenOne);
                printAllTime(chosenOne);
            }
            //已到达任务池中没有任务可执行时，找到一个当前时间下最先到达了的作业
            processFirstOne();
        }
        setAvgTime();
        printAvg();
    }
    private void HRN() {
        while (!tasks.isEmpty()) {
            updateArrivedTasks();
            while (!arrivedTasks.isEmpty()) {
                Iterator<Task> iterator = arrivedTasks.iterator();
                Task chosenOne = iterator.next();
                while (iterator.hasNext()) {
                    Task t = iterator.next();
                    double chosenOneHRN = 1 + (this.timeAt - chosenOne.getArriveTime()) / chosenOne.getRunTime();
                    double tHRN = 1 + (this.timeAt - t.getArriveTime()) / t.getRunTime();
                    if (tHRN > chosenOneHRN)
                        chosenOne = t;
                }
                processingJob(chosenOne);
                arrivedTasks.remove(chosenOne);
                tasks.remove(chosenOne);
                setAllTime(chosenOne);
                printAllTime(chosenOne);
            }
            processFirstOne();
        }
        setAvgTime();
        printAvg();
    }
    private void RR() {
        Scanner in = new Scanner(System.in);
        System.out.print("请输入时间片长度(s)：");
        this.timeRobin = in.nextDouble();
        while (!tasks.isEmpty()) {
            updateArrivedTasks();
            updateArrivedTasksQueue();
            if (!arrivedTasksQueue.isEmpty()) {
                Task chosenOne = arrivedTasksQueue.poll();
                if (processingProcess(chosenOne)) {
                    tasks.remove(chosenOne);
                    setAllTime(chosenOne);
                    printAllTime(chosenOne);
                } else
                    arrivedTasksQueue.offer(chosenOne);
            }
            if(arrivedTasksQueue.isEmpty()&&!tasks.isEmpty()) {
                Task firstOne=findFirstOne();
                this.timeAt=Math.max(this.timeAt,firstOne.getArriveTime());
            }
        }
        setAvgTime();
        printAvg();
    }


    private void updateArrivedTasks() {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task t = iterator.next();
            if ((Math.abs(this.timeAt-t.getArriveTime())<0.000001||this.timeAt>t.getArriveTime()) &&!arrivedTasksQueue.contains(t))
                arrivedTasks.add(t);
        }
    }
    private void updateArrivedTasksQueue() {
        while (!arrivedTasks.isEmpty()) {
            Iterator<Task> iterator = arrivedTasks.iterator();
            Task chosenOne = iterator.next();
            while (iterator.hasNext()) {
                Task t = iterator.next();
                if (t.getArriveTime() < chosenOne.getArriveTime())
                    chosenOne = t;
            }
            arrivedTasksQueue.offer(chosenOne);
            arrivedTasks.remove(chosenOne);
        }
    }


    private void processingJob(Task task) {
        System.out.print(task.getTaskName() + " is running...");
        try {
            sleep((long) task.getRunTime() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished in " + task.getRunTime() + "s!");
    }
    private boolean processingProcess(Task task) {
        System.out.print(task.getTaskName() + " is running...");
        try {
            sleep((long) timeRobin * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double leftTime = task.getRunTime() - task.getRanTime();
        if (leftTime > timeRobin) {
            task.setRanTime(task.getRanTime()+timeRobin);
            System.out.printf("已运行%.3fs，剩余%.3fs\t",task.getRanTime(),leftTime-timeRobin);
            this.timeAt += timeRobin;
            System.out.printf("当前时间%.3fs\n",this.timeAt);
            return false;
        } else {
            System.out.printf("Finished in%.3fs!\n",task.getRunTime());
            this.timeAt += leftTime;
            return true;
        }
    }


    //找到tasks任务池处理第一个到达的
    private void processFirstOne() {
        if (!tasks.isEmpty()) {
            Task chosenOne = findFirstOne();
            this.timeAt = Math.max(this.timeAt, chosenOne.getArriveTime());
            //执行作业
            processingJob(chosenOne);
            tasks.remove(chosenOne);
            setAllTime(chosenOne);
            printAllTime(chosenOne);
        }
    }
    private Task findFirstOne() {
        Iterator<Task> iterator = tasks.iterator();
        Task chosenOne = iterator.next();
        while (iterator.hasNext()) {
            Task t = iterator.next();
            if (chosenOne.getArriveTime() > t.getArriveTime()) {
                chosenOne = t;
            }
        }
        return chosenOne;
    }


    private void setAllTime(Task chosenOne) {
        if(method!=RR)
            this.timeAt += chosenOne.getRunTime();
        chosenOne.setFinishTime(this.timeAt);
        chosenOne.setRoundTime(chosenOne.getFinishTime() - chosenOne.getArriveTime());
        chosenOne.setRoundTimeWeight(chosenOne.getRoundTime() / chosenOne.getRunTime());
        Task.setRoundTimeAvg(Task.getRoundTimeAvg() + chosenOne.getRoundTime());
        Task.setRoundTimeWeightAvg(Task.getRoundTimeWeightAvg() + chosenOne.getRoundTimeWeight());
    }
    private void setAvgTime() {
        Task.setRoundTimeAvg(Task.getRoundTimeAvg() / Task.getTaskNum());
        Task.setRoundTimeWeightAvg(Task.getRoundTimeWeightAvg() / Task.getTaskNum());
    }

    private void printAllTime(Task chosenOne) {
        System.out.printf("%s完成时间为%.3fs\n", chosenOne.getTaskName(), chosenOne.getFinishTime());
        System.out.printf("%s周转时间%.3fs\n", chosenOne.getTaskName(), chosenOne.getRoundTime());
        System.out.printf("%s带权周转时间为%.3fs\n", chosenOne.getTaskName(), chosenOne.getRoundTimeWeight());
    }
    private void printAvg() {
        System.out.println();
        System.out.printf("此算法平均周转时间为%.3fs\n", Task.getRoundTimeAvg());
        System.out.printf("此算法平均带权周转时间为%.3fs\n", Task.getRoundTimeWeightAvg());
    }
}
