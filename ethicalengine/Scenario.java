package ethicalengine;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class Scenario {


    ArrayList<Character> myPassengers = new ArrayList<>();
    ArrayList<Character> myPedestrians = new ArrayList<>();
    private boolean isLegalCrossing;
    private boolean hasYouInCar;
    private boolean isPet;

    /**
     * Default Constructor
     */
    public Scenario(){
        int passengersCount = 1;
        int pedestriansCount = 1;
    }

    /**
     * Constructor main
     * @param passengers Passenger Array
     * @param pedestrians Pedestrian Array
     * @param isLegalCrossing legalcrossing status
     */

    public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing){

        myPassengers.addAll(Arrays.asList(passengers));
        myPedestrians.addAll(Arrays.asList(pedestrians));

        this.isLegalCrossing = isLegalCrossing;
    }

    /**
     *
     * @return boolean
     */
    public boolean hasYouInCar(){
        return this.hasYouInCar;
    }

    /**
     *
     * @param hasYouInCar boolean
     */

    public void setHasYouInCar(boolean hasYouInCar){
        this.hasYouInCar = hasYouInCar;
    }

    /**
     *
     * @return boolean
     */
    public boolean hasYouInLane() {
        return !this.hasYouInCar;
    }

    /**
     *
     * @return Character array of passengers
     */
    public Character[] getPassengers() {
        return myPassengers.toArray(new Character[myPassengers.size()]);
    }

    /**
     *
     * @return Character array of pedestrians
     */
    public Character[] getPedestrians(){
        return myPedestrians.toArray(new Character[myPedestrians.size()]);
    }
    public boolean isLegalCrossing() {
        return this.isLegalCrossing;
    }

    /**
     * Setter to set legal Crossing status
     * @param isLegalCrossing boolean
     */
    public void setLegalCrossing(boolean isLegalCrossing){
        this.isLegalCrossing = isLegalCrossing;
    }

    /**
     * total passenger counts
     * @return int
     */
    public int getPassengerCount() {
        return myPassengers.size();
    }
    /**
     * total pedestrian counts
     * @return int
     */
    public int getPedestrianCount() {
        return myPedestrians.size();
    }

    /**
     * set pet
     * @param isPet boolean
     */

    public void setIsPet(boolean isPet){
        this.isPet = isPet;
    }

    /**
     * Get pet
     * @return boolean
     */
    public boolean getIsPet(){
        return this.isPet;
    }

    /**
     * get legal crossing as string
     * @return String
     */
    public String getLegalCrossing(){
        if(this.isLegalCrossing){
            return "yes";
        }
        return "no";
    }

    /**
     * Helper method to print in to String
     * @return String
     */
    private String printHelp(){
        String print="";
        for (int i=0; i<38; i++)
            print = print + "=";

        return print;
    }

    /**
     * to String method
     * @return String
     */
    @Override
    public String toString() {
        String printOut="";

        printOut = printHelp()+ "\n# Scenario\n"+printHelp();


        printOut = printOut+ "\nLegal Crossing: "+getLegalCrossing()+"\nPassengers ("+getPassengerCount()+")\n";

        for (Character passenger: myPassengers){
            printOut = printOut+"- "+passenger.toString()+"\n";
       }
        printOut = printOut + "Pedestrians ("+getPedestrianCount()+")";
        for (Character pedestrian: myPedestrians){
            printOut = printOut + "\n- "+pedestrian.toString();
        }
        return printOut;
    }

}
