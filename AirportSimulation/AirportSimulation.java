//Lena Gran 
//N03734335

package AirportSimulation;

import java.util.LinkedList;
import java.util.Queue;
// import java.util.Scanner;

public class AirportSimulation {

    public static void main(String[] args) {
        
        // // Create a scanner object for user input
        // Scanner input = new Scanner(System.in);

        // // Ask user for input parameters
        // System.out.print("Enter amount of minutes to land: ");
        // int landingTime = input.nextInt();
        
        // System.out.print("Enter amount of minutes to take off: ");
        // int takeoffTime = input.nextInt();
          
        // System.out.print("Enter probability of arrival during a minute: ");
        // double arrivalProbability = input.nextDouble();
          
        // System.out.print("Enter probability of departure during a minute: ");
        // double departureProbability = input.nextDouble();

        // System.out.print("Enter a maximum amount of time in the air before crashing: ");
        // int maxAirTime = input.nextInt();

        // System.out.print("Enter the total simulation time in minutes: ");
        // int totalTime = input.nextInt();

        // // Validate arrival probability input
        // while (arrivalProbability < 0 || arrivalProbability > 1) {
        //     System.out.print("Please enter a valid arrival probability between 0 and 1: ");
        //     arrivalProbability = input.nextDouble();
        // }
        
        // // Validate departure probability input
        // while (departureProbability < 0 || departureProbability > 1) {
        //     System.out.print("Please enter a valid departure probability between 0 and 1: ");
        //     departureProbability = input.nextDouble();
        // }
        
        // // Run the simulation with user-provided inputs
        // airportSimulate(landingTime, takeoffTime, arrivalProbability, departureProbability, totalTime, maxAirTime);

        // input.close();
        
        airportSimulate(4, 2, 0.05, 0.05, 6000, 2);
        airportSimulate(4, 2, 0.1, 0.1, 6000, 5);
        airportSimulate(4, 2, 0.05, 0.05, 6000, 2);
        airportSimulate(4, 2, 0.1, 0.1, 6000, 5);
    }

    public static void airportSimulate(
        int landingTime, 
        int takeoffTime, 
        double arrivalProbability,
        double departureProbability, 
        int totalTime, int maxAirTime
    ) {

        Queue<Integer> landingQueue = new LinkedList<>();
        Queue<Integer> takeoffQueue = new LinkedList<>();

        BooleanSource landingSource = new BooleanSource(arrivalProbability);
        BooleanSource takeoffSource = new BooleanSource(departureProbability);

        Runway runway = new Runway(landingTime, takeoffTime);

        Averager landingWaitTimes = new Averager();
        Averager takeoffWaitTimes = new Averager();

        int next;
        int crashCount = 0;

        //parameters
        System.out.println(" ");
        System.out.println("Amount of minutes to land: " + landingTime);
        System.out.println("Amount of minutes to take off: " + takeoffTime);
        System.out.println("Probability of arrival during one minute: " + arrivalProbability);
        System.out.println("Average amount of time between planes to land: " + 1 / arrivalProbability);
        System.out.println("Probability of departure during a minute: " + departureProbability);
        System.out.println("Average amount of time between planes to take off: " + 1 / arrivalProbability);
        System.out.println("Maximum amount of time in the air before crashing: " + maxAirTime); // This is given?
        System.out.println("Total simulation minutes: " + totalTime);

        // Precondition
        if (landingTime < 0 || takeoffTime < 0 || arrivalProbability < 0 || arrivalProbability > 1
                || departureProbability < 0 || departureProbability > 1 || totalTime < 0) {
            throw new IllegalArgumentException("Illegal argument value");
        }
        // Simulate passage of one minute
        for (int currentMinute = 0; currentMinute < totalTime; currentMinute++) {
            if (landingSource.query()) {
                landingQueue.add(currentMinute);
            }
            if (takeoffSource.query()) {
                takeoffQueue.add(currentMinute);
            }

            if (!runway.isBusy() && !landingQueue.isEmpty()) {
                while (!landingQueue.isEmpty()) {
                    next = landingQueue.remove();
                    int currentlandingTime = currentMinute - next;

                    if (currentlandingTime > maxAirTime) {
                        crashCount++;
                    } else {
                        landingWaitTimes.addNumber(currentlandingTime);
                        runway.startOperation(true);
                        break;
                    }
                }

            } else if (!runway.isBusy() && !takeoffQueue.isEmpty()) {
                next = takeoffQueue.remove();
                takeoffWaitTimes.addNumber(currentMinute - next);
                runway.startOperation(false);
            }
            runway.reduceRemainingTime();
        }
        if (!landingQueue.isEmpty()) {
            for (int plane : landingQueue) {
                if ((totalTime - plane) > maxAirTime) {
                    crashCount++;
                }
            }
        }
        //Calculate and print results
        System.out.println(" ");
        System.out.println("Number of planes taken off: " + takeoffWaitTimes.howManyNumbers());
        System.out.println("Number of planes landed: " + landingWaitTimes.howManyNumbers());
        System.out.println("Number of planes crashed: " + crashCount);
        System.out.printf("Average waiting time for taking off: %.2f minutes%n", takeoffWaitTimes.average());
        System.out.printf("Average waiting time for landing: %.2f minutes%n", landingWaitTimes.average());
    }
}
