

import ethicalengine.*;
import ethicalengine.Character;
import java.io.*;
import java.util.*;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class EthicalEngine {
    public static Scanner keyboard = new Scanner(System.in);
    public static int ALGORITHM_AUDIT_DEFAULT_MAX = 100;
    public static int USER_AUDIT_DEFAULT_MAX = 1000;
    public boolean DEFAULT_BOOLEAN = false;


    /**
     * Enumeration Decision
     */
    public enum Decision {
        PASSENGERS,
        PEDESTRIANS;
    }

    /**
     * Main method, entry to program taking args as input to run several scenarios as per specification
     * @param args (input command as arguments to drive the program)
     */

    public static void main(String[] args) {
        EthicalEngine ethicalEngine = new EthicalEngine();
        ArrayList<Scenario> configFileScenario;


        String command = "",configFilePath="",configFlag="", resultFileFlag="", resultFilePath ="";

        //if args comes with -c or --config and -i or --interactive.
        // -c flag must follow with a path
        // -i and -c flag is interchangeable
        // (-c path -i) is valid but (-c -i path) is invalid

        if (!Arrays.toString(args).contains("-r") || !Arrays.toString(args).contains("--results") ){
            if (args.length == 1){
                command = args[0];
            }
            if (args.length == 2){
                command = args[0];
                configFilePath = args[1];
            }
            if (args.length == 3){
                if (args[0].equals("-i") || args[0].equals("--interactive")){
                    command = args[0];
                    configFlag = args[1];
                    configFilePath = args[2];
                }
                if (args[0].equals("-c") || args[0].equals("--config")){
                    configFlag = args[0];
                    configFilePath = args[1];
                    command = args[2];

                }
            }
        }
        //if args comes with -r or --result  and other -c and -i flags.
        // -c flag must follow with a path
        // -i and -c flag is interchangeable
        // (-c path -i) is valid but (-c -i path) is invalid
        // -r must comes with a path (-r only is invalid)
        // (-r resultPath) is valid (Algorithm audit to start)
        // (-i -r resultPath) is valid. User Audit from Random scenario to start
        // (-c -configPath -r -resultPath_ is valid. Algorithm Audit from config file to start, save in result path
        // (-i -c -configPath -r -resultPath) is valid. User Audit from config file and result to be saved in resultPath


        if (Arrays.toString(args).contains("-r") || Arrays.toString(args).contains("--results")){
            if (args.length == 1){
                command = args[0];
            }
            else if (args.length == 2){
                command = args [0];
                resultFilePath = args [1];
            }
            else if (args.length == 3){
                if (args[0].equals("-i") || args[0].equals("--interactive")){
                    command = args[0];
                    resultFileFlag = args[1];
                    resultFilePath = args[2];
                }
                if (args[0].equals("-r") || args[0].equals("--results")){
                    resultFileFlag = args[0];
                    resultFilePath = args[1];
                    command = args[2];
                }

            }
            else if (args.length == 4){
                command = args[0];
                configFilePath = args[1];
                resultFileFlag = args[2];
                resultFilePath = args[3];
            }
            else if (args.length == 5){
                command = args[0];
                configFlag = args[1];
                configFilePath = args[2];
                resultFileFlag = args[3];
                resultFilePath = args[4];
            }
        }


            switch (command){
                case "":
                    Audit audit = new Audit();
                    audit.setAuditType("Algorithm");
                    audit.setResultPath("");
                    audit.run(ALGORITHM_AUDIT_DEFAULT_MAX);
                    break;
                case "-r":
                case "--results":
                    if (args.length == 1)
                        ethicalEngine.helpOutput();
                    if (args.length == 2){
                        Audit audit2 = new Audit();
                        audit2.setAuditType("Algorithm");
                        audit2.setResultPath(resultFilePath);
                        audit2.run(ALGORITHM_AUDIT_DEFAULT_MAX);
                    }
                    break;

                case "--config":
                case "-c":
                    if(configFilePath.isEmpty())
                        ethicalEngine.helpOutput();
                    else{
                        configFileScenario = ethicalEngine.configFileLoad(configFilePath);
                        Audit newAudit = new Audit(configFileScenario,true, "Algorithm","");

                    }
                    if (args.length == 4 && resultFileFlag.equals("-r") || resultFileFlag.equals("--results")){
                        configFileScenario = ethicalEngine.configFileLoad(configFilePath);
                        new Audit(configFileScenario,true,"Algorithm",resultFilePath);
                    }
                    break;

                case "-i":
                case "--interactive":

                    if (args.length == 3 && (resultFileFlag.equals("-r") || resultFileFlag.equals("--results"))){
                        Audit audit1 = new Audit();
                        audit1.setAuditType("User");
                        audit1.setResultPath(resultFilePath);
                        audit1.run(USER_AUDIT_DEFAULT_MAX);
                    }
                    if (args.length == 1){
                        Audit audit1 = new Audit();
                        audit1.setAuditType("User");
                        audit1.setResultPath("");
                        audit1.run(USER_AUDIT_DEFAULT_MAX);
                    }
                    if (args.length == 3 && (configFlag.equals("-c") || configFlag.equals("--config"))){
                        configFileScenario = ethicalEngine.configFileLoad(configFilePath);
                        ethicalEngine.welcomeMessagePrint();
                        boolean userConsent = ethicalEngine.userConsent();
                        new Audit(configFileScenario,userConsent,"User","");
                    }

                    if (args.length == 5 && (resultFileFlag.equals("-r") || resultFileFlag.equals("--results"))){
                        configFileScenario = ethicalEngine.configFileLoad(configFilePath);
                        ethicalEngine.welcomeMessagePrint();
                        boolean userConsent = ethicalEngine.userConsent();
                        new Audit(configFileScenario,userConsent,"User",resultFilePath);
                    }
                    break;

                case "--help":
                case "-h":
                default:
                    ethicalEngine.helpOutput();
                    break;
                }
    }

    /**
     *
     * Method to take user Consent to save data
     * @return boolean
     */


    public boolean userConsent(){
        boolean userConsent;
        while(true){
            try{
                System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
                String consentInput = keyboard.nextLine();
                if (consentInput.equalsIgnoreCase("yes")){
                    userConsent = true;
                    break;
                }
                if (consentInput.equalsIgnoreCase("no")){
                    userConsent = false;
                    break;
                }
                throw new Exception();

            }catch (Exception e){
                System.out.print("Invalid response. ");
            }
        }
        return userConsent;
    }

    /**
     * Method to load config file from a specific path, loading into scenario Arraylist.
     * @param filePath (path of config file)
     * @return ArrayList<Scenario>
     * @throw InvalidDataFormatException
     * @throw InvalidCharacteristicException
     * @throw NumberFormatException
     *
     */

    private ArrayList<Scenario> configFileLoad(String filePath){
        ArrayList<Scenario> myScenario = new ArrayList<>();
        ArrayList<Character> myPassenger = new ArrayList<>();
        ArrayList<Character> myPedestrian = new ArrayList<>();

        Scanner inputStream = null;
        try{
            inputStream = new Scanner (new FileInputStream(filePath));
            String line = null;
            boolean isLegalCrossing = false;
            int lineNum = 0;

            while(inputStream.hasNextLine()){
                line = inputStream.nextLine();
                String[] newLine = line.split(",");
                int length = newLine.length;

                if (newLine[0].equalsIgnoreCase("class")){
                    lineNum++;
                    continue;
                }
                try{
                    if ((length >1 && length <10) || length >10){
                        lineNum++;
                        throw new InvalidDataFormatException();
                    }
                }catch (InvalidDataFormatException e){
                    System.out.println(e.getMessage()+lineNum);
                }


                if (newLine[0].contains("scenario") && myPassenger.size()==0 && myPedestrian.size()==0){
                    lineNum++;
                    isLegalCrossing = newLine[0].trim().equalsIgnoreCase("scenario:green");
                }



                if(length == 10){
                    lineNum++;
                    int age;
                    Character.Gender gender = null;
                    Character.BodyType bodyType = null;
                    Person.Profession profession = null;
                    Character.AgeCategory ageCategory = null;

                    //Exception handling for Gender

                    try{
                        if (newLine[1].isEmpty())
                            gender = Character.Gender.UNKNOWN;
                        else {
                            gender = Character.Gender.getGender(newLine[1]);
                            if ( gender == null && newLine[0].equalsIgnoreCase("person")){
                                throw new InvalidCharacteristicException();
                            }
                        }
                    }catch (InvalidCharacteristicException e){
                        System.out.println(e.getMessage()+lineNum);
                        gender = Character.Gender.UNKNOWN;
                    }

                    //Exception handling for Age

                    try{
                        String ageStr = newLine[2];
                        age = Integer.parseInt(ageStr);
                    }catch (NumberFormatException e){
                        System.out.println("WARNING! invalid number format in config file in line "+lineNum);
                        age = Character.DEFAULT_AGE;
                    }

                    //Exception handling for BodyType

                    try{
                        if (newLine[3].isEmpty())
                            bodyType = Character.BodyType.UNSPECIFIED;
                        else {
                            bodyType = Character.BodyType.getBdt(newLine[3]);
                            if (bodyType == null && newLine[0].equalsIgnoreCase("person")){
                                throw new InvalidCharacteristicException();
                            }
                        }
                    }catch (InvalidCharacteristicException e){
                        System.out.println(e.getMessage()+lineNum);
                        bodyType = Character.BodyType.UNSPECIFIED;
                    }

                    //Exception handling for Profession

                    try{
                        profession = Person.Profession.getProf(newLine[4],age);
                        if (profession == null && newLine[0].equalsIgnoreCase("person")){
                            profession = Person.Profession.UNKNOWN;
                            throw new InvalidCharacteristicException();
                        }
                    }catch (InvalidCharacteristicException e){
                        System.out.println(e.getMessage()+lineNum);
                    }

                    //Exception handling for isPregnant

                    boolean isPregnant, isYou, isPet;
                    try{
                        if (!exceptionHelp(newLine[5]) && newLine[0].equalsIgnoreCase("person"))
                            throw new InvalidCharacteristicException();
                        else
                            isPregnant = Boolean.parseBoolean(newLine[5].toLowerCase());
                    }catch (InvalidCharacteristicException e){
                        System.out.println(e.getMessage() + lineNum);
                        isPregnant = DEFAULT_BOOLEAN;
                    }

                    //Exception handling for isYou

                    try{
                        if (!exceptionHelp(newLine[6]) && newLine[0].equalsIgnoreCase("person"))
                            throw new InvalidCharacteristicException();
                        else
                            isYou = Boolean.parseBoolean(newLine[6].toLowerCase());
                    }catch (InvalidCharacteristicException e){
                        System.out.println(e.getMessage() + lineNum);
                        isYou = DEFAULT_BOOLEAN;
                    }


                    String species = newLine[7];

                    //Exception handling for isPet

                    try{
                        if (!exceptionHelp(newLine[8]) && newLine[0].equalsIgnoreCase("animal"))
                            throw new InvalidCharacteristicException();
                        else
                            isPet = Boolean.parseBoolean(newLine[8].toLowerCase());
                    }catch (InvalidCharacteristicException e){
                        System.out.println(e.getMessage() + lineNum);
                        isPet = DEFAULT_BOOLEAN;
                    }


                    if (newLine[0].equalsIgnoreCase("person") && newLine[9].equalsIgnoreCase("passenger")){

                        Person newPerson = new Person(age, profession, gender, bodyType, isPregnant);
                        newPerson.setAsYou(isYou);
                        myPassenger.add(newPerson);

                    }
                    if (newLine[0].equalsIgnoreCase("animal") && newLine[9].equalsIgnoreCase("passenger")) {
                        Animal newAnimal = new Animal(species);
                        newAnimal.setPet(isPet);
                        myPassenger.add(newAnimal);

                    }
                    if (newLine[0].equalsIgnoreCase("person") && newLine[ 9 ].equalsIgnoreCase("pedestrian")) {
                        Person newPerson = new Person(age, profession, gender, bodyType, isPregnant);
                        newPerson.setAsYou(isYou);
                        myPedestrian.add(newPerson);
                    }
                    if (newLine[0].equalsIgnoreCase("animal") && newLine[9].equalsIgnoreCase("pedestrian")) {
                        Animal newAnimal = new Animal(species);
                        newAnimal.setPet(isPet);
                        myPedestrian.add(newAnimal);

                    }
                }

                if (newLine[0].contains("scenario") && (myPassenger.size() >0 || myPedestrian.size()>0)){
                    lineNum++;

                    Character[] arrPassenger = myPassenger.toArray(new Character[myPassenger.size()]);
                    Character[] arrPedestrian = myPedestrian.toArray(new Character[myPedestrian.size()]);

                    Scenario newScenario = new Scenario(arrPassenger,arrPedestrian,isLegalCrossing);

                    myScenario.add(newScenario);

                    myPassenger.clear();
                    myPedestrian.clear();
                    isLegalCrossing = newLine[0].trim().equalsIgnoreCase("scenario:green");
                }
            }

            Character[] arrPassenger = myPassenger.toArray(new Character[myPassenger.size()]);
            Character[] arrPedestrian = myPedestrian.toArray(new Character[myPedestrian.size()]);

            Scenario newScenario = new Scenario(arrPassenger,arrPedestrian,isLegalCrossing);
            myScenario.add(newScenario);
        } catch (FileNotFoundException e){
            System.out.println("ERROR: could not find config file.");
        }

        inputStream.close();

        return myScenario;
}

    /**
     * helper method to check all boolean inputs from config files
     * @param str String
     * @return boolean
     *
     */
     private boolean exceptionHelp(String str){

        if (str.equalsIgnoreCase("true"))
            return true;
        else if (str.equalsIgnoreCase("false"))
            return true;
        else
            return false;
     }

    /**
     * method to print help output
     */

    private void helpOutput(){
        System.out.println("EthicalEngine - COMP90041 - Final Project\n\n" +
                            "Usage: java EthicalEngine [arguments] \n\n"+
                              "Arguments:\n"+
                            "\t -c or -- config       Optional: path to config file\n" +
                            "\t -h or -- help         Print Help (this message) and exit\n"+
                            "\t -r or --results       Optional: path to results log file\n"+
                            "\t -i or --interactive   Optional: launches interactive mode");

    }


    /**
     * Static method Decision to make decision whom to save from a scenario (passengers or pedestrians)
     * @param scenario Scenario
     * @return DECISION
     *
     */
    public static Decision decide(Scenario scenario)  {
         ArrayList<Character> tempPassengers,tempPedestrians;
         ArrayList<Person> myPassengers = new ArrayList<>();
         ArrayList<Animal> myAnimalsPassengers = new ArrayList<>();
         ArrayList<Person> myPedestrians = new ArrayList<>();
         ArrayList<Animal> myAnimalsPedestrians= new ArrayList<>();

        Character[] arrPassenger,arrPedestrian;

        arrPassenger = scenario.getPassengers();
        arrPedestrian = scenario.getPedestrians();


        Person myObj1;
        Animal myObj2;


        for (Character tempPassenger : arrPassenger) {
            if (tempPassenger instanceof Person) {
                myObj1 = (Person) tempPassenger;
                myPassengers.add(myObj1);
            }
            if (tempPassenger instanceof Animal) {
                myObj2 = (Animal) tempPassenger;
                myAnimalsPassengers.add(myObj2);
            }
        }
        for (Character tempPedestrian : arrPedestrian) {
            if (tempPedestrian instanceof Person) {
                myObj1 = (Person) tempPedestrian;
                myPedestrians.add(myObj1);
            }
            if (tempPedestrian instanceof Animal) {
                myObj2 = (Animal) tempPedestrian;
                myAnimalsPedestrians.add(myObj2);
            }
        }

        //


        boolean hasYouInCar = scenario.hasYouInCar();
        double personSaveFactor = 0.5;
        double animalSaveFactor = 0.25;
        double saveFactor = 1.0;
        double legalCrossingFactor = 2.0;

        double passengerSaveScore = (myPassengers.size()*personSaveFactor)+myAnimalsPassengers.size()*animalSaveFactor;


        if (hasYouInCar)
            passengerSaveScore = passengerSaveScore + saveFactor;

        for (Person psgr : myPassengers) {
            if (psgr.isPregnant() || psgr.getAgeCategory()== Character.AgeCategory.CHILD || psgr.getAgeCategory() == Character.AgeCategory.BABY || psgr.isPregnant())
                passengerSaveScore = passengerSaveScore + saveFactor;
            if (psgr.getProfession().equals(Person.Profession.DOCTOR) || psgr.getProfession().equals(Person.Profession.CEO) || psgr.getProfession().equals(Person.Profession.ENGINEER) || psgr.getProfession() == Person.Profession.STUDENT)
                passengerSaveScore = passengerSaveScore + saveFactor;
        }
        for (Animal animal : myAnimalsPassengers){
            if (animal.isPet())
                passengerSaveScore = passengerSaveScore + saveFactor;
        }



        boolean isLegalCrossing = scenario.isLegalCrossing();
        boolean hasYouInLane = scenario.hasYouInLane();
        double pedestrianSaveScore = (myPedestrians.size()*personSaveFactor)+ (myAnimalsPedestrians.size()*animalSaveFactor);

        if (isLegalCrossing)
            pedestrianSaveScore = pedestrianSaveScore + legalCrossingFactor;
        if (hasYouInLane)
            pedestrianSaveScore = pedestrianSaveScore + saveFactor;
        for (Person pdstn : myPedestrians) {
            if (pdstn.isPregnant() || pdstn.getAgeCategory() == Character.AgeCategory.BABY || pdstn.getAgeCategory() == Character.AgeCategory.CHILD || pdstn.isPregnant())
                pedestrianSaveScore = pedestrianSaveScore + saveFactor;
            if (pdstn.getProfession().equals(Person.Profession.DOCTOR) || pdstn.getProfession().equals(Person.Profession.CEO) || pdstn.getProfession().equals(Person.Profession.ENGINEER) || pdstn.getProfession() == Person.Profession.STUDENT)
                pedestrianSaveScore = pedestrianSaveScore + saveFactor;
        }
        for (Animal animal : myAnimalsPedestrians){
            if (animal.isPet())
                pedestrianSaveScore = pedestrianSaveScore + saveFactor;
        }

        Decision saved = null;

        if (pedestrianSaveScore > passengerSaveScore)
            saved = Decision.PEDESTRIANS;

        if (pedestrianSaveScore < passengerSaveScore)
            saved = Decision.PASSENGERS;

        if (pedestrianSaveScore == passengerSaveScore){
            if (arrPassenger.length > arrPedestrian.length)
                saved = Decision.PASSENGERS;
            else if( arrPassenger.length < arrPedestrian.length)
                saved = Decision.PEDESTRIANS;
            else {
                if (myPassengers.size() > myPedestrians.size())
                    saved = Decision.PASSENGERS;
                else if (myPassengers.size() < myPedestrians.size())
                    saved = Decision.PEDESTRIANS;
                else{
                    Random r = new Random();
                    int num = r.nextInt(Decision.values().length);
                    if (num == 0 )
                        saved = Decision.PEDESTRIANS;
                    else
                        saved = Decision.PASSENGERS;
                }
            }
        }
        return saved;
    }

    /**
     * Print welcome.ascii
     * @return void
     */

    public void welcomeMessagePrint(){
        String welcomeFilePath = "C:\\Users\\jony0\\IdeaProjects\\FinalProject\\welcome.ascii";
        BufferedReader objReader = null;
        try{
            String strCurrentLine;
            objReader = new BufferedReader(new FileReader("welcome.ascii"));
            while ((strCurrentLine = objReader.readLine()) != null){
                System.out.println(strCurrentLine);
            }
        }catch (IOException e){
            System.out.println("Welcome file not found");
            System.exit(0);
        }
    }

    /**
     * Exception Class for Invalid Data Format
     */

    public static class InvalidDataFormatException extends Exception {

        public InvalidDataFormatException(){
            super("WARNING: invalid data format in config file in line ");
        }
        public InvalidDataFormatException(String msg){
            super(msg);
        }
    }
    /**
     * Exception class for Invalid Characteristics format
     */

    public static class InvalidCharacteristicException extends Exception{
        public InvalidCharacteristicException(){
            super("WARNING: invalid characteristic in config file in line ");
        }
        public InvalidCharacteristicException(String msg){
            super(msg);
        }
    }
}
