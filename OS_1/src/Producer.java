public class Producer extends Thread {
    private int num;
    private int id;
    private static int idCnt = 0;

    private Storage storage;

    public Producer(Storage storage) {
        idCnt++;
        this.id = idCnt;
        this.storage = storage;
    }

    public void run() {
        produce(num);
    }

    public void produce(int num) {
        storage.produce(num, id);
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