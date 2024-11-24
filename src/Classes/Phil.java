package Classes;


import java.util.concurrent.CountDownLatch;

public class Phil {

    private boolean philsEatState = false;
    private CountDownLatch cdlSnacks = new CountDownLatch(3);

    public void setPhilsEatState(boolean philsEatState, CountDownLatch cdlSnacks){
        this.philsEatState = philsEatState;
        this.cdlSnacks = cdlSnacks;
    }

    @Override
    public String toString() {
        return "Phil ";
    }

    public void philSnack(){
        cdlSnacks.countDown();
        setPhilsEatState(true);
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
}
