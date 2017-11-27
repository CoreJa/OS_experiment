package easy;

public class Task {
    private CPU cpu;
    private String taskName;
    private double arriveTime;//提交时间
    private double runTime;//运行时间
    private double ranTime = 0;//已运行时间（用于进程调度算法）
    private double finishTime;//完成时间
    private double roundTime;//周转时间
    private double roundTimeWeight;//带权周转时间
    private static double roundTimeAvg = 0;//平均周转时间
    private static double roundTimeWeightAvg = 0;//平均带权周转时间
    private static int TaskNum;

    public Task(String name, double arriveTime, double runTime, CPU cpu) {
        this.taskName = name;
        this.arriveTime = arriveTime;
        this.runTime = runTime;
        this.cpu = cpu;
        cpu.addTask(this);
    }

    public static double getRoundTimeAvg() {
        return roundTimeAvg;
    }
    public static void setRoundTimeAvg(double roundTimeAvg) {
        Task.roundTimeAvg = roundTimeAvg;
    }
    public static int getTaskNum() {
        return TaskNum;
    }
    public static void setTaskNum(int taskNum) {
        TaskNum = taskNum;
    }
    public static double getRoundTimeWeightAvg() {
        return roundTimeWeightAvg;
    }
    public static void setRoundTimeWeightAvg(double roundTimeWeightAvg) {
        Task.roundTimeWeightAvg = roundTimeWeightAvg;
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
    public double getRoundTimeWeight() {
        return roundTimeWeight;
    }
    public void setRoundTimeWeight(double roundTimeWeight) {
        this.roundTimeWeight = roundTimeWeight;
    }
    public double getRanTime() {
        return ranTime;
    }
    public void setRanTime(double ranTime) {
        this.ranTime = ranTime;
    }
}
