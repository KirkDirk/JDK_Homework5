import Classes.Phil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    /**
     * Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом стоит тарелка спагетти.
     * Вилки лежат на столе между каждой парой ближайших философов.
     * Каждый философ может либо есть, либо размышлять.
     * Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
     * Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)
     * Описать в виде кода такую ситуацию.
     * Каждый философ должен поесть три раза
     */

    public static void main(String[] args) throws InterruptedException {

        int countPhil = 5;
        int countSnacks = 3;
        CountDownLatch cdlCountSnacks = new CountDownLatch(countSnacks);
        //List<Phil> philList = new ArrayList<>();
        Lock[] forks = new Lock[countPhil];
        for (int i = 0; i < countPhil; i++) {
            //Phil phil = new Phil();
            //phil.setCdlSnacks(cdlCountSnacks);
            //philList.add(phil);
            forks[i] = new ReentrantLock();
        }


//        Runnable snacking = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < countPhil; i++) {
//                    Phil currentPhil = philList.get(i);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    if (currentPhil.isPhilsEatState()){
//                        currentPhil.philThink(i);
//                    } else {
//                        if (forks[i].tryLock() && forks[(i+countPhil-1)%countPhil].tryLock()){
//                            currentPhil.philSnack(i);
//                        }
//
//                    }
//                }
//            }
//        };

//        Runnable stateSnacking = new Runnable() {
//            @Override
//            public void run() {
//                while (!(cdlCountSnacks.getCount() == 0)) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println("Phils have a snack:");
//                    for (int i = 0; i < countPhil; i++) {
//                        System.out.println("Phil " + Integer.toString(i + 1) +
//                                " had a snack " +
//                                (countSnacks - philList.get(i).getCdlSnacks().getCount()) +
//                                " times");
//                    }
//                }
//
//            }
//        };

        Thread[] snakingThread = new Thread[countPhil];
        for (int i = 0; i < countPhil; i++) {
            System.out.println("Phil " + (i +1) + " begins to eat");
            snakingThread[i] = new Thread(new Phil(
                    i,
                    false,
                    cdlCountSnacks,
                    forks[i],
                    forks[(i+countPhil-1)%countPhil]));
            snakingThread[i].start();
        }

//        Thread getStateSnacking = new Thread(stateSnacking);
//        getStateSnacking.start();

        cdlCountSnacks.await();

    }
}