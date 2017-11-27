import java.util.*;

public class HardDrive {
    private Set<Integer> trackSet = new HashSet<>();
    private int[] tracks;
    private int headAt;
    private boolean direction;//true = 左, false = 右
    private int distance;
    private int method;

    public HardDrive(int[] tracks, int headAt, boolean direction, int method) {
        this.tracks = tracks;
        this.headAt = headAt;
        this.direction = direction;
        this.method = method;
        this.distance = 0;
        for (int i : tracks) {
            trackSet.add(i);
        }
    }

    public void process() {
        switch (this.method) {
            case 1:
                FCFS();
                break;
            case 2:
                SSTF();
                break;
            case 3:
                SCAN();
                break;
        }
    }

    private void FCFS() {
        System.out.print("寻道顺序为：" + headAt + " ");
        for (int i : tracks) {
            System.out.print(i + " ");
            this.distance += Math.abs(headAt - i);
            headAt = i;
        }
        System.out.println();
        System.out.println("总路程=" + this.distance);
    }

    private void SSTF() {
        System.out.print("寻道顺序为：" + headAt + " ");
        while (!trackSet.isEmpty()) {
            Iterator<Integer> iterator = trackSet.iterator();
            int chosenOne = iterator.next();
            while (iterator.hasNext()) {
                int t = iterator.next();
                if (Math.abs(t - this.headAt) < Math.abs(chosenOne - this.headAt))
                    chosenOne = t;
            }
            System.out.print(chosenOne + " ");
            this.distance += Math.abs(this.headAt - chosenOne);
            this.headAt = chosenOne;
            trackSet.remove(chosenOne);
        }
        System.out.println();
        System.out.println("总路程=" + this.distance);
    }

    private void SCAN() {
        System.out.print("寻道顺序为：" + headAt + " ");
        int[] orderTracks = this.tracks.clone();
        for (int i = 0; i < orderTracks.length; i++) {
            for (int j = i + 1; j < orderTracks.length; j++) {
                if (orderTracks[i] > orderTracks[j]) {
                    int t = orderTracks[i];
                    orderTracks[i] = orderTracks[j];
                    orderTracks[j] = t;
                }
            }
        }
        int head = 0;
        while (this.headAt > orderTracks[head]) {
            head++;
        }
        if (direction) {
            for (int i = head; i < orderTracks.length; i++) {
                System.out.print(orderTracks[i] + " ");
                this.distance += Math.abs(this.headAt - orderTracks[i]);
                this.headAt = orderTracks[i];
            }
            for (int i = head - 1; i >= 0; i--) {
                System.out.print(orderTracks[i] + " ");
                this.distance += Math.abs(this.headAt - orderTracks[i]);
                this.headAt = orderTracks[i];
            }
        } else {
            for (int i = head; i < orderTracks.length; i++) {
                System.out.print(orderTracks[i] + " ");
                this.distance += Math.abs(this.headAt - orderTracks[i]);
                this.headAt = orderTracks[i];
            }
            for (int i = head - 1; i >= 0; i--) {
                System.out.print(orderTracks[i] + " ");
                this.distance += Math.abs(this.headAt - orderTracks[i]);
                this.headAt = orderTracks[i];
            }
        }
        System.out.println();
        System.out.println("总路程=" + this.distance);
//        System.out.print(chosenOne + " ");
//        this.distance += Math.abs(this.headAt - chosenOne);
//        this.headAt = chosenOne;
//        trackSet.remove(chosenOne);
//        System.out.println();
//        System.out.println("总路程=" + this.distance);
    }
}
