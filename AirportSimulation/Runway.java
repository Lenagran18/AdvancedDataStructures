package AirportSimulation;

public class Runway {
    private int minutesToLand; 
    private int minutesToTakeoff; 
    private int runwayInUseTimeLeft; 
    private boolean isLanding;

    /**
     * Initialize a runway.
     * 
     * @param s
     * the number of minutes required landing and takeoff
     * <b>Postcondition:</b>
     * This runway has been initialized so that it takes <CODE>minutesToLand, minutesToTakeoff</CODE>
     * minutes to complete one cycle.
     **/
    public Runway(int minutesToLand, int minutesToTakeoff) {
        this.minutesToLand = minutesToLand;
        this.minutesToTakeoff = minutesToTakeoff;
        this.runwayInUseTimeLeft = 0;
        this.isLanding = false;
    }

    /**
     * Determine whether this runway is currently busy.
     * 
     * @return
     * <CODE>true</CODE> if this runway is busy;
     * otherwise <CODE>false</CODE>
     **/
    public boolean isBusy() {
        return (runwayInUseTimeLeft > 0);
    }

    /**
     * Reduce the remaining time in the current runway by one minute.
     * <b>Postcondition:</b>
     * If a plane is taking off or landing, then the remaining time in the current
     * operation has been reduced by one minute.
     **/
    public void reduceRemainingTime() {
        if (runwayInUseTimeLeft > 0)
            runwayInUseTimeLeft--;
    }

    /**
     * Start a landing or departure for this runway.
     * <b>Precondition:</b>
     * <CODE>isBusy()</CODE> is <CODE>false</CODE>.
     * <b>Postcondition:</b>
     * This runway has started simulating one landing or depatrute depending on if isLanding is true. Therefore,
     * <CODE>isBusy()</CODE> will return <CODE>true</CODE> until the required
     * number of simulated minutes have passed.
     * 
     * @exception IllegalStateException
     * Indicates that this runway is busy.
     **/
    public void startOperation(boolean landing) {
        if (runwayInUseTimeLeft > 0) {
            throw new IllegalStateException("Runway is already busy.");
        }
        this.isLanding = landing;
        if (landing) {
            runwayInUseTimeLeft = minutesToLand;
        } else {
            runwayInUseTimeLeft = minutesToTakeoff;
        }
    }

    public boolean isLanding() {
        return isLanding;
    }

}