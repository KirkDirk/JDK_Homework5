import Classes.Phil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
        List<Phil> philList = new ArrayList<>();
        for (int i = 0; i < countPhil; i++) {
            Phil phil = new Phil();
            phil.setCdlSnacks(cdlCountSnacks);
            philList.add(phil);
        }

        Runnable snacking = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < countPhil; i++) {
                    Phil currentPhil = philList.get(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (currentPhil.isPhilsEatState()){
                        currentPhil.setPhilsEatState(false);
                        System.out.println(currentPhil + Integer.toString(i + 1) + " think");
                    } else {
                        currentPhil.philSnack();
                        System.out.println(currentPhil + Integer.toString(i + 1) + " eat");
                    }
                }
            }
        };

        Runnable stateSnacking = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Phils have a snack:");
                for (int i = 0; i < countPhil; i++) {
                    System.out.println("Phil " + Integer.toString(i + 1) +
                            " had a snack " +
                            (countSnacks - philList.get(i).getCdlSnacks().getCount()) +
                            " times");
                }

            }
        };

        Thread snakingThread = new Thread(snacking);
        snakingThread.start();

        Thread getStateSnacking = new Thread(stateSnacking);
        getStateSnacking.start();

        cdlCountSnacks.await();

    }
}