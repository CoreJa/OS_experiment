public class Consumer extends Thread {
    private int num;
    private static int idCnt = 0;
    private int id;

    private Storage storage;

    public Consumer(Storage storage) {
        idCnt++;
        id=idCnt;
        this.storage = storage;
    }

    public void run() {
        consume(num);
    }

    public void consume(int num) {
        storage.consume(num, id);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}