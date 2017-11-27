import java.util.*;

import static java.lang.Thread.sleep;

public class Storage {
    private final int MAX_SIZE = 100;
    private LinkedList<Object> list = new LinkedList<Object>();

    public void produce(int num, int id) {
        System.out.println("【生产者" + id + "】正在生产产品……");
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("【生产者" + id + "】生产结束");
        synchronized (list) {
            while (list.size() + num > MAX_SIZE) {
                System.out.println("【生产者" + id + "】已生产产品：" + num + "\t当前库存："
                        + list.size() + "\t仓库已满，不能存入");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("【生产者" + id + "】预计存入仓库数量：" + num + "\n"
                    + "【生产者" + id + "】正在存入中……");
            //锁住print输出，防止被其他线程的输出打断
            synchronized (System.out) {
                for (int i = 1; i <= num; ++i) {
                    try {
                        sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.add(new Object());
                    System.out.print(i + "...");
                }
                System.out.println();
            }

            System.out.println("【生产者" + id + "】已存入：" + num + "\t当前库存：" + list.size());

            list.notifyAll();
        }
    }

    public void consume(int num, int id) {
        synchronized (list) {
            while (list.size() < num) {
                System.out.println("【消费者" + id + "】预计取出产品数量:" + num + "\t当前库存："
                        + list.size() + "\t产品太少，不够消费。等待中……");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("【消费者" + id + "】预计取出产品数量：" + num + "\n"
                    + "【消费者" + id + "】正在取出中……");
            //锁住print输出，防止被其他线程的输出打断
            synchronized (System.out) {
                for (int i = 1; i <= num; ++i) {
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.remove();
                    System.out.print(i + "...");
                }
                System.out.println();
            }
            list.notifyAll();
        }
        System.out.println("【消费者" + id + "】已取出：" + num + "\t当前库存：" + list.size());
        System.out.println("【消费者" + id + "】正在消费产品……");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("【消费者" + id + "】消费结束");
    }

    public LinkedList<Object> getList() {
        return list;
    }

    public void setList(LinkedList<Object> list) {
        this.list = list;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}