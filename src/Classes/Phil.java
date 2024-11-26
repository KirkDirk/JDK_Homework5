package Classes;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class Phil implements Runnable{

    /**
     * Порядковый номер философа
     */
    private final int id;
    //private boolean philsEatState;
    //private CountDownLatch cdlSnacks;
    /**
     * Количество приемов пищи
     */
    private int countSnack;
    /**
     * Вилка слева от философа
     */
    private final Lock leftFork;
    /**
     * Вилка справа от философа
     */
    private final Lock rightFork;

    public Phil(int id, int countSnack, Lock leftFork, Lock rightFork) {
        this.id = id;
        //this.philsEatState = philsEatState;
        //this.cdlSnacks = cdlSnacks;
        this.countSnack = countSnack;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public String toString() {
        return "Phil ";
    }

    public void philSnack(int id) throws InterruptedException{
        //cdlSnacks.countDown();
        //setPhilsEatState(true);
        System.out.println("Phil " + Integer.toString(id + 1) + " eat");
        Thread.sleep(1000);
    }

    public void philThink(int id) throws InterruptedException {
        //setPhilsEatState(false);
        System.out.println("Phil " + Integer.toString(this.id + 1) + " think");
        Thread.sleep(1000);
    }

//    public void setPhilsEatState(boolean philsEatState) {
//        this.philsEatState = philsEatState;
//    }

//    public void setCdlSnacks(CountDownLatch cdlSnacks) {
//        this.cdlSnacks = cdlSnacks;
//    }
//
//    public CountDownLatch getCdlSnacks() {
//        return cdlSnacks;
//    }

//    public boolean isPhilsEatState() {
//        return philsEatState;
//    }


    /**
     * Попытки приема пищи. Если какая-то из вилок заблокирована,
     * философ впадает в задумчивость
     */
    @Override
    public void run() {
        try {
            for (int i = 0; i < this.countSnack; i++) {
                philThink(this.id);
                leftFork.lock();
                try {
                    rightFork.lock();
                    try {
                        philSnack(this.id);
                    } finally {
                        rightFork.unlock();
                    }
                } finally {
                    leftFork.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        if (isPhilsEatState()) {
//            System.out.println("Phil " + id + " is full");
//            philThink(this.id);
//        } else {
//            try {
//                leftFork.lock();
//                try {
//                    rightFork.lock();
//                    try {
//                        philSnack(this.id);
//                    } finally {
//                        rightFork.unlock();
//                    }
//                } finally {
//                    leftFork.unlock();
//                }
//            }
//            if (leftFork.tryLock() && rightFork.tryLock()) {
//                if (leftFork.tryLock()) {
//                    System.out.println("Phil " + id + ": forks are busy");
//                }
//                philSnack(this.id);
//                leftFork.unlock();
//                rightFork.unlock();
//            } else {
//                philThink(this.id);
//            }
//        }
    }
}
