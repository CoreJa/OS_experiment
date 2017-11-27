package multithread;

import multithread.CPU;

public class Task extends Thread {
    private CPU cpu;
    private String taskName;
    private double arriveTime;//提交时间
    private double runTime;//运行时间
    private double finishTime;//完成时间
    private double roundTime;//周转时间
    private static double roundTimeAvg;//带权周转时间

    public Task(String name, double arriveTime, double runTime, CPU cpu) {
        this.taskName = name;
        this.arriveTime = arriveTime;
        this.runTime = runTime;
        this.cpu = cpu;
    }

    public String getTaskName() {
        return taskName;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    @Override
    public void run() {
        synchronized (cpu) {
            cpu.taskNum++;
            while (cpu.taskNum < cpu.totalTask) {
                try {
                    cpu.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cpu.notifyAll();
        }
        try {
            sleep((long) arriveTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cpu.addTask(this);
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public double getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(double roundTime) {
        this.roundTime = roundTime;
    }
}
