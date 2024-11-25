package Classes;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class Phil implements Runnable{

    private final int id;
    private boolean philsEatState;
    private CountDownLatch cdlSnacks;
    private final Lock leftFork;
    private final Lock rightFork;

    public Phil(int id, boolean philsEatState, CountDownLatch cdlSnacks, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.philsEatState = philsEatState;
        this.cdlSnacks = cdlSnacks;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public String toString() {
        return "Phil ";
    }

    public void philSnack(int id){
        cdlSnacks.countDown();
        setPhilsEatState(true);
        System.out.println("Phil " + Integer.toString(id + 1) + " eat");
    }

    public void philThink(int id){
        setPhilsEatState(false);
        System.out.println("Phil " + Integer.toString(id + 1) + " think");
    }

    public void setPhilsEatState(boolean philsEatState) {
        this.philsEatState = philsEatState;
    }

    public void setCdlSnacks(CountDownLatch cdlSnacks) {
        this.cdlSnacks = cdlSnacks;
    }

    public CountDownLatch getCdlSnacks() {
        return cdlSnacks;
    }

    public boolean isPhilsEatState() {
        return philsEatState;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (isPhilsEatState()) {
            System.out.println("Phil " + id + " is full");
            philThink(this.id);
        } else if (leftFork.tryLock() && rightFork.tryLock()) {
            if (leftFork.tryLock()) {
                System.out.println("Phil " + id + ": forks are busy");
            }
            philSnack(this.id);
            leftFork.unlock();
            rightFork.unlock();
        } else {
            philThink(this.id);
        }
    }
}
