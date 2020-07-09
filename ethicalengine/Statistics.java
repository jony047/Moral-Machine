package ethicalengine;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class Statistics {


    private String characteristics;
    private int occurrence;
    private int saved;
    private int age;
    private double sumAge;
    private int personCount;

    /**
     * Default Constructor
     */

    public Statistics(){
        this.sumAge = 0.0;
        this.personCount =0;
    }

    /**
     *
     * @param characteristics String
     * @param occurrence int
     * @param saved int
     */

    public Statistics(String characteristics,int occurrence, int saved) {

        this.characteristics = characteristics;
        this.occurrence = occurrence;
        this.saved = saved;
    }

    /**
     *
     * @param age int
     */

    public void addToSumAge(double age){
        this.sumAge = this.sumAge + age;
    }

    /**
     *
     * @return double
     */
    public double getSumAge(){
        return this.sumAge;
    }

    /**
     *
     * @param personCount int
     */
    public void addToPersonCount(int personCount){
        this.personCount = this.personCount + personCount;
    }

    /**
     *
     * @return int
     */
    public int getPersonCount(){
        return this.personCount;
    }

    /**
     *
     * @return int
     */
    public int getOccurrence() {
        return this.occurrence;
    }

    /**
     *
     * @param occurrence int
     */

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    /**
     *
     * @param saved int
     */

    public void setSaved(int saved) {
        this.saved = saved;
    }

    /**
     *
     * @param age int
     */

    public void setAge(int age) {
        this.age = age;
    }

    /**
     *
     * @return int
     */

    public int getSaved() {
        return this.saved;
    }

    /**
     *
     * @return String
     */

    public String getCharacteristics() {
        return characteristics;
    }

    /**
     *
     * @return double
     */

    public double getSurvivalRatio(){
        String str="";
        String formattedString="";
        if (this.occurrence == 0)
            return 0.0;
        else{
            str = String.valueOf((double)this.saved/this.occurrence);
            formattedString = str.substring(0,3);
        }
            return Double.parseDouble(formattedString);
    }
    public double getAge(){
        return this.age;
    }

    /**
     * To string method
     * @return String
     */

    public String toString() {
        String formattedRatio = String.valueOf(getSurvivalRatio());
        String print = getCharacteristics().toLowerCase()+ ": "+formattedRatio;
        return print;
    }
}
