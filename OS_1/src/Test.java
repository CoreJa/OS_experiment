public class Test {
    public static void main(String[] args) {
        Storage storage = new Storage();

        Producer p1 = new Producer(storage);
        Producer p2 = new Producer(storage);
        Producer p3 = new Producer(storage);
        Producer p4 = new Producer(storage);

        Consumer c1 = new Consumer(storage);
        Consumer c2 = new Consumer(storage);
        Consumer c3 = new Consumer(storage);

        p1.setNum(30);
        p2.setNum(30);
        p3.setNum(30);
        p4.setNum(30);
        c1.setNum(50);
        c2.setNum(20);
        c3.setNum(30);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        c1.start();
        c2.start();
        c3.start();
    }
}
