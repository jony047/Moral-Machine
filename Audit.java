
import ethicalengine.*;
import ethicalengine.Character;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class Audit {

    private int runs;
    private String auditType;
    private String resultPath;
    public EthicalEngine eEngine = new EthicalEngine();
    private  ArrayList<Statistics> myStatistics = new ArrayList<>();
    Statistics statistics = new Statistics();
    private static final int DEFAULT_RUN = 3;
    private static final int SEED_HELPER = 100000;

    /**
     * Audit constructor set auditType to default value
     */
    public Audit(){
        this.auditType = "Unspecified";
    }


    /**
     *
     * @param scenario scenario
     * @param consent user Consent
     * @param auditType audit type
     * @param newPath fileSave path user defined
     * Constructor Audit to take 4 arguments and run with the either user audit or algorithm audit, also if new path provided (-r) used that path to save
     *
     */

    public Audit(ArrayList<Scenario> scenario, boolean consent, String auditType, String newPath){


        this.auditType = auditType;

        if (getAuditType().equalsIgnoreCase("User")){

            int scenarioReminder = scenario.size() % 3;
            int scenarioLeft = scenario.size();

            for (int i=0; i<scenario.size();i++){
                run(scenario.get(i));
                scenarioLeft = scenarioLeft - 1;

                if ((i+1) % DEFAULT_RUN == 0){
                    this.runs = i+1;
                    printStatistics();
                    printTofile(newPath,consent);

                    if (i+1 < scenario.size()){
                        System.out.println("Would you like to continue? (yes/no)");
                        String userInput = EthicalEngine.keyboard.nextLine();
                        if (userInput.equalsIgnoreCase("no"))
                            System.exit(0);
                    }
                }

                if (scenarioLeft == 0 ){
                    if (scenarioReminder == 1 || scenarioReminder == 2){
                        this.runs = scenario.size();
                        printStatistics();
                        printTofile(newPath,consent);
                    }
                    System.out.println("That's all. Press Enter to quit.");
                    String quitInput = EthicalEngine.keyboard.nextLine();
                    if (quitInput.isEmpty())
                        break;
                }
            }
       }


        ArrayList<Character> tempPassengers = new ArrayList<>();
        ArrayList<Character> tempPedestrians = new ArrayList<>();

        Character[] arrPassenger, arrPedestrian;

        if (getAuditType().equalsIgnoreCase("Algorithm")){

            for (int i=0; i<scenario.size(); i++){

                EthicalEngine.Decision saved = EthicalEngine.decide(scenario.get(i));

                boolean isLegalCrossing = scenario.get(i).isLegalCrossing();
                arrPassenger = scenario.get(i).getPassengers();
                arrPedestrian = scenario.get(i).getPedestrians();

                tempPassengers.addAll(Arrays.asList(arrPassenger));
                tempPedestrians.addAll(Arrays.asList(arrPedestrian));

                addToStatistics(saved,isLegalCrossing,tempPassengers,tempPedestrians);
            }
            this.runs = scenario.size();
            printStatistics();
            printTofile(newPath,true);
        }
    }

    /**
     *
     * take Scenario as input present it to user to ask whom to save (pedestrians or passengers)
     * @param scenario Scenario
     * @return void
     *
     */

    public void run(Scenario scenario){

        EthicalEngine.Decision saved = null;

        System.out.println(scenario.toString());

        while (true){
            System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
            String userInput = EthicalEngine.keyboard.nextLine();
            if (userInput.equalsIgnoreCase("passenger") || userInput.equalsIgnoreCase("passengers") || userInput.equals("1")){
                saved = EthicalEngine.Decision.PASSENGERS;
                break;
            }
            else if (userInput.equalsIgnoreCase("pedestrian") || userInput.equalsIgnoreCase("pedestrians") || userInput.equals("2")){
                saved = EthicalEngine.Decision.PEDESTRIANS;
                break;
            }
        }

        ArrayList<Character> tempPassengers = new ArrayList<>();
        ArrayList<Character> tempPedestrians = new ArrayList<>();

        Character[] arrPassenger, arrPedestrian;

        arrPassenger = scenario.getPassengers();
        arrPedestrian = scenario.getPedestrians();

        //copy data from two Character array to Character Arraylist

        tempPassengers.addAll(Arrays.asList(arrPassenger));
        tempPedestrians.addAll(Arrays.asList(arrPedestrian));


        boolean isLegalCrossing = scenario.isLegalCrossing();

        addToStatistics(saved,isLegalCrossing,tempPassengers,tempPedestrians);

    }

    /**
     *
     * @param runs int
     * @return void
     * public method run to take two arguments and run with user or algorithm audit
     */

    public void run(int runs){

        ArrayList<Character> tempPassengers = new ArrayList<>();
        ArrayList<Character> tempPedestrians = new ArrayList<>();
        Character[] arrPassenger, arrPedestrian;

        if (getAuditType().equalsIgnoreCase("User")) {

            EthicalEngine eEngine = new EthicalEngine();
            eEngine.welcomeMessagePrint();
            boolean userConsent = eEngine.userConsent();

            for (int i = 0; i < runs; i++) {
                EthicalEngine.Decision saved = null;

                //long seedValue = (long)Math.floor(Math.random()*SEED_HELPER);
                //ScenarioGenerator sng = new ScenarioGenerator(seedValue,1,5,1,5);

                ScenarioGenerator sng = new ScenarioGenerator();
                Scenario scenario = sng.generate();
                System.out.println(scenario.toString());
                System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
                String userInput = EthicalEngine.keyboard.nextLine();
                if (userInput.equalsIgnoreCase("passenger") || userInput.equalsIgnoreCase("passengers") || userInput.equals("1")) {
                    saved = EthicalEngine.Decision.PASSENGERS;
                }
                if (userInput.equalsIgnoreCase("pedestrian") || userInput.equalsIgnoreCase("pedestrians") || userInput.equals("2")) {
                    saved = EthicalEngine.Decision.PEDESTRIANS;
                }
                boolean isLegalCrossing = scenario.isLegalCrossing();

                arrPassenger = scenario.getPassengers();
                arrPedestrian = scenario.getPedestrians();

                //copy data from two Character array to Character Arraylist

                tempPassengers.addAll(Arrays.asList(arrPassenger));
                tempPedestrians.addAll(Arrays.asList(arrPedestrian));

                addToStatistics(saved, isLegalCrossing, tempPassengers, tempPedestrians);

                if ((i+1) % 3 == 0) {
                    this.runs = (i+1);
                    printStatistics();
                    if (this.resultPath.isEmpty())
                        printTofile("",userConsent);
                    else
                        printTofile(this.resultPath,userConsent);

                    System.out.println("Would you like to continue? (yes/no)");
                    String newInput = EthicalEngine.keyboard.nextLine();
                    if (newInput.equalsIgnoreCase("no"))
                        break;
                }
            }
        }

            if (getAuditType().equalsIgnoreCase("Algorithm")){

                for (int i=0; i<runs; i++){

                    //long seedValue = (long)Math.floor(Math.random()*SEED_HELPER);
                    //ScenarioGenerator sng = new ScenarioGenerator(seedValue,1,5,1,5);
                    ScenarioGenerator sng = new ScenarioGenerator();
                    Scenario scenario = sng.generate();
                    EthicalEngine.Decision decision = EthicalEngine.decide(scenario);
                    boolean isLegalCrossing = scenario.isLegalCrossing();

                    arrPassenger = scenario.getPassengers();
                    arrPedestrian = scenario.getPedestrians();

                    tempPassengers.addAll(Arrays.asList(arrPassenger));
                    tempPedestrians.addAll(Arrays.asList(arrPedestrian));

                    addToStatistics(decision,isLegalCrossing,tempPassengers,tempPedestrians);
                }

                this.runs = runs;
                printStatistics();
                if (this.resultPath.isEmpty())
                    printTofile("",true);
                else
                    printTofile(this.resultPath,true);
            }
    }


    /**
     *
     * private method to add / update Statistics for all survivors
     * @param saved Decision who to be saved
     * @param islegalCrossing legalCrossing
     * @param passengers  Passengers
     * @param pedestrians Pedestrians
     *
     */
    private void addToStatistics(EthicalEngine.Decision saved,boolean islegalCrossing,ArrayList<Character> passengers, ArrayList<Character> pedestrians){

        Person myObj1;
        Animal myObj2;

        ArrayList<Person> myPassengers = new ArrayList<>();
        ArrayList<Animal> myAnimalsPassengers = new ArrayList<>();
        ArrayList<Person> myPedestrians = new ArrayList<>();
        ArrayList<Animal> myAnimalsPedestrians= new ArrayList<>();


        // below block of codes to casting passengers to either person or animal array

        for (Character tempPassenger : passengers) {
            if (tempPassenger instanceof Person) {
                myObj1 = (Person) tempPassenger;
                myPassengers.add(myObj1);
            }
            if (tempPassenger instanceof Animal) {
                myObj2 = (Animal) tempPassenger;
                myAnimalsPassengers.add(myObj2);
            }
        }
        for (Character tempPedestrian : pedestrians) {
            if (tempPedestrian instanceof Person) {
                myObj1 = (Person) tempPedestrian;
                myPedestrians.add(myObj1);
            }
            if (tempPedestrian instanceof Animal) {
                myObj2 = (Animal) tempPedestrian;
                myAnimalsPedestrians.add(myObj2);
            }
        }



        //Below block of codes to add occurrence of  characteristics into statistics


        for (Person passenger:myPassengers){
            int age = passenger.getAge();

            String profession = passenger.getProfession().name();
            String bodyType = passenger.getBodyType().name();
            String gender = passenger.getGender().name();
            String ageCategory = passenger.getAgeCategory().name();


            if(passenger.isYou()) {
                String isYou = "you";
                addOccuranceToStatistics(isYou);
            }
            if(passenger.isPregnant()){
                String isPregnant = "pregnant";
                addOccuranceToStatistics(isPregnant);
            }

            if(!profession.equals(Person.Profession.NONE.name()))
                addOccuranceToStatistics(profession);

            if (!bodyType.equals(Character.BodyType.UNSPECIFIED.name()))
                addOccuranceToStatistics(bodyType);

            if (!gender.equals(Character.Gender.UNKNOWN.name()))
                addOccuranceToStatistics(gender);

            addOccuranceToStatistics(ageCategory);


            if (saved == EthicalEngine.Decision.PASSENGERS){
                if(!profession.equals(Person.Profession.NONE.name()))
                    updateStatisticsSaved(profession);

                updateStatisticsSaved(bodyType);

                if (!gender.equals(Character.Gender.UNKNOWN.name()))
                    updateStatisticsSaved(gender);

                updateStatisticsSaved(ageCategory);

                if (passenger.isYou())
                    updateStatisticsSaved("you");
                if (passenger.isPregnant())
                    updateStatisticsSaved("pregnant");
            }
        }
        for (Animal animal:myAnimalsPassengers){
            String animalName = animal.getSpecies();
            addOccuranceToStatistics(animalName);
            if(animal.isPet()){
                String isPet = "pet";
                addOccuranceToStatistics(isPet);
            }
            if (saved == EthicalEngine.Decision.PASSENGERS){
                updateStatisticsSaved(animalName);
                if (animal.isPet())
                    updateStatisticsSaved("pet");
            }
        }

        for (Person pedestrian: myPedestrians){
            int age = pedestrian.getAge();

            String profession = pedestrian.getProfession().name();
            String bodyType = pedestrian.getBodyType().name();
            String gender = pedestrian.getGender().name();
            String ageCategory = pedestrian.getAgeCategory().name();


            if(pedestrian.isYou()) {
                String isYou = "you";
                addOccuranceToStatistics(isYou);
            }
            if(pedestrian.isPregnant()){
                String isPregnant = "pregnant";
                addOccuranceToStatistics(isPregnant);
            }
            if(!profession.equals(Person.Profession.NONE.name()))
                addOccuranceToStatistics(profession);

            if (!bodyType.equals(Character.BodyType.UNSPECIFIED.name()))
                addOccuranceToStatistics(bodyType);


            if (!gender.equals(Character.Gender.UNKNOWN.name()))
                addOccuranceToStatistics(gender);

            addOccuranceToStatistics(ageCategory);

            if (saved == EthicalEngine.Decision.PEDESTRIANS){
                if(!profession.equals(Person.Profession.NONE.name()))
                    updateStatisticsSaved(profession);

                updateStatisticsSaved(bodyType);

                if (!gender.equals(Character.Gender.UNKNOWN.name()))
                    updateStatisticsSaved(gender);

                updateStatisticsSaved(ageCategory);

                if (pedestrian.isYou())
                    updateStatisticsSaved("you");
                if (pedestrian.isPregnant())
                    updateStatisticsSaved("pregnant");
            }
        }

        for (Animal animal:myAnimalsPedestrians){
            String animalName = animal.getSpecies();
            addOccuranceToStatistics(animalName);
            if(animal.isPet()){
                String isPet = "pet";
                addOccuranceToStatistics(isPet);
            }
            if (saved == EthicalEngine.Decision.PEDESTRIANS){
                updateStatisticsSaved(animalName);
                if (animal.isPet())
                    updateStatisticsSaved("pet");
            }
        }

        if (saved == EthicalEngine.Decision.PASSENGERS){
            statistics.addToPersonCount(myPassengers.size());
            for (Person passenger: myPassengers)
                statistics.addToSumAge(passenger.getAge());

            int x = findCharacteristics("person");
            int y = findCharacteristics("animal");

            if (x >= 0){
                myStatistics.get(x).setOccurrence(myStatistics.get(x).getOccurrence()+ myPassengers.size()+ myPedestrians.size());
                myStatistics.get(x).setSaved(myStatistics.get(x).getSaved()+ myPassengers.size());
            }
            if (x < 0){
                Statistics newObj = new Statistics("person",myPassengers.size()+myPedestrians.size(),myPassengers.size());
                myStatistics.add(newObj);
            }
            if (y >= 0){
                myStatistics.get(y).setOccurrence(myStatistics.get(y).getOccurrence()+myAnimalsPassengers.size()+myAnimalsPedestrians.size());
                myStatistics.get(y).setSaved(myStatistics.get(y).getSaved()+ myAnimalsPassengers.size());
            }
            if (y < 0){
                Statistics newObj = new Statistics("animal",myAnimalsPassengers.size()+myAnimalsPedestrians.size(),myAnimalsPassengers.size());
                myStatistics.add(newObj);
            }


            if (islegalCrossing){
                int index = findCharacteristics("green");
                if (index >= 0){
                    myStatistics.get(index).setOccurrence(myStatistics.get(index).getOccurrence() + passengers.size()+ pedestrians.size());
                    myStatistics.get(index).setSaved(myStatistics.get(index).getSaved()+passengers.size());
                }
                if (index <0){
                    Statistics newObj = new Statistics("green",passengers.size()+pedestrians.size(),passengers.size());
                    myStatistics.add(newObj);
                }

            }
            if (!islegalCrossing){
                int index = findCharacteristics("red");
                if (index >= 0){
                    myStatistics.get(index).setOccurrence(myStatistics.get(index).getOccurrence() + passengers.size()+pedestrians.size());
                    myStatistics.get(index).setSaved(myStatistics.get(index).getSaved()+passengers.size());
                }
                if (index <0){
                    Statistics newObj = new Statistics("red",passengers.size()+pedestrians.size(),passengers.size());
                    myStatistics.add(newObj);
                }

            }
        }

        if (saved == EthicalEngine.Decision.PEDESTRIANS){

            statistics.addToPersonCount(myPedestrians.size());
            for (Person pedestrian: myPedestrians)
                statistics.addToSumAge(pedestrian.getAge());

            int x = findCharacteristics("person");
            int y = findCharacteristics("animal");

            if (x >=0){
                myStatistics.get(x).setOccurrence(myStatistics.get(x).getOccurrence()+myPassengers.size()+myPedestrians.size());
                myStatistics.get(x).setSaved(myStatistics.get(x).getSaved()+myPedestrians.size());
            }
            if (x < 0){
                Statistics newObj = new Statistics("person",myPassengers.size()+myPedestrians.size(),myPedestrians.size());
                myStatistics.add(newObj);
            }
            if (y >=0){
                myStatistics.get(y).setOccurrence(myStatistics.get(y).getOccurrence()+myAnimalsPassengers.size()+myAnimalsPedestrians.size());
                myStatistics.get(y).setSaved(myStatistics.get(y).getSaved()+myAnimalsPedestrians.size());
            }
            if (y < 0){
                Statistics newObj = new Statistics("animal",myAnimalsPassengers.size()+myAnimalsPedestrians.size(),myAnimalsPedestrians.size());
                myStatistics.add(newObj);
            }

            if (islegalCrossing){
                int index = findCharacteristics("green");
                if (index >= 0){
                    myStatistics.get(index).setOccurrence(myStatistics.get(index).getOccurrence() + passengers.size()+pedestrians.size());
                    myStatistics.get(index).setSaved(myStatistics.get(index).getSaved()+pedestrians.size());
                }
                if (index <0){
                    Statistics newObj = new Statistics("green",passengers.size()+pedestrians.size(),pedestrians.size());
                    myStatistics.add(newObj);
                }

            }
            if (!islegalCrossing){
                int index = findCharacteristics("red");
                if (index >= 0){
                    myStatistics.get(index).setOccurrence(myStatistics.get(index).getOccurrence() + passengers.size()+pedestrians.size());
                    myStatistics.get(index).setSaved(myStatistics.get(index).getSaved()+pedestrians.size());
                }
                if (index <0){
                    Statistics newObj = new Statistics("red",passengers.size()+pedestrians.size(),pedestrians.size());
                    myStatistics.add(newObj);
                }

            }
        }
    }

    /**
     * private method to find any characteristics in myStatistics array
     * @param characteristics String
     * @return int
     *
     */

    private int findCharacteristics(String characteristics){

        for (int i=0; i<myStatistics.size(); i++){
            if (characteristics.equals(myStatistics.get(i).getCharacteristics()))
                return i;
        }
        return -1;

    }

    /**
     * private method to add any characteristics to myStatistics array
     * @param characteristics String
     *
     */


    private void addOccuranceToStatistics(String characteristics){
        int index = findCharacteristics(characteristics);
        if (index >= 0){
            myStatistics.get(index).setOccurrence((myStatistics.get(index).getOccurrence() + 1));
            return;
        }
        Statistics object = new Statistics(characteristics, 1,0);
        myStatistics.add(object);
    }

    /**
     * Private method to updated Statistics array for any survived characteristics
     * @param characteristics String
     */

    private void updateStatisticsSaved(String characteristics){
        int index = findCharacteristics(characteristics);
        if (index >=0){
            myStatistics.get(index).setSaved((myStatistics.get(index).getSaved() + 1));
        }
    }

    /**
     * Setter method to set auditType
     * @param auditType String
     */

    public void setAuditType(String auditType){
        this.auditType = auditType;
    }

    /**
     * Getter method to get auditType
     * @return String
     */
    public String getAuditType(){
        if (this.auditType.isEmpty())
            return "Unspecified";
        return this.auditType;
    }

    public void setResultPath(String path){
        this.resultPath = path;
    }

    /**
     * to string method of Audit Class
     * @return String
     */

    public String toString(){
        String str = null;
        double avgAge = statistics.getSumAge()/statistics.getPersonCount();
        String avgAgeStr = String.valueOf(avgAge);
        int indexOfDot = avgAgeStr.indexOf(".");
        str = "======================================\n";
        str = str + "# "+this.auditType+" Audit\n";
        str = str + "======================================\n";
        str = str + "- % SAVED AFTER "+this.runs+" RUNS\n";

        for (Statistics x : myStatistics){
            str = str + x.toString()+"\n";
        }
        str = str + "--\n";
        if (this.runs == 0)
            str = "no audit available";
        else
            str = str + "average age: "+avgAgeStr.substring(0,(indexOfDot+2));
        return str;
    }

    /**
     * print Statistics method of Audit class
     */

    public void printStatistics(){
        characteristicsSortAsc();
        survivalRatioSortDesc();

        System.out.println(this.toString());

    }

    /**
     * Method to save statistics into a file, if new filepath provided (-r) to be saved at new location else default location either user.log or result.log
     * @param filePath new file path if provided (-r or -- results)
     * @param consent user Consent to save data
     */

    public void printTofile(String filePath,boolean consent){

        String fileName ="";

        if (filePath.isEmpty()){
            if (this.auditType.equalsIgnoreCase("user")){
                fileName = "user.log";
            }
            else
                fileName = "result.log";
        }
        else{
            fileName = filePath;
            int i = fileName.lastIndexOf("\\");
            String directoryPath = fileName.substring(0,i);
            File f = new File(directoryPath);
            if (!f.isDirectory()){
                System.out.println("Error: could not print results. Target directory does not exist.");
                System.exit(0);
            }
        }

        if (consent) {
            PrintWriter outPutStream = null;
            try{
                outPutStream = new PrintWriter(new FileOutputStream(fileName,true));
            }catch (FileNotFoundException e){
                System.exit(0);
            }
            characteristicsSortAsc();
            survivalRatioSortDesc();

            outPutStream.println(this.toString());
            outPutStream.close();
        }
    }

    /**
     * Sorting method of characteristics to alphabetical order
     */
    private void characteristicsSortAsc(){
        Statistics temp;
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for (int i=0; i<myStatistics.size()-1; i++){
                if (myStatistics.get(i).getCharacteristics().compareToIgnoreCase(myStatistics.get(i+1).getCharacteristics()) > 0){
                    temp = myStatistics.get(i);
                    myStatistics.set(i,myStatistics.get(i+1));
                    myStatistics.set(i+1,temp);
                    sorted = false;
                }
            }
        }
    }

    /**
     * private method to sort survival ratio to descending order
     */

    private void survivalRatioSortDesc(){
        Statistics temp;
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for (int i = 0; i< myStatistics.size()-1; i++){
                if (myStatistics.get(i).getSurvivalRatio() < myStatistics.get(i+1).getSurvivalRatio()) {
                    temp = myStatistics.get(i);
                    myStatistics.set(i,myStatistics.get(i+1));
                    myStatistics.set(i+1,temp);
                    sorted = false;
                }
            }
        }
    }
}
