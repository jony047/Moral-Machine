package ethicalengine;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class ScenarioGenerator {

    public static final int DEFAULT_SEED_HELPER = 100000;
    private  int AGE_LIMIT_PERSON = 110;
    private  int AGE_LIMIT_ANIMAL = 15;

    private int passengerCountMinimum;
    private int passengerCountMaximum;
    private int pedestrianCountMinimum;
    private int pedestrianCountMaximum;
    private long seed;
    Random r = new Random();

    /**
     * Default Constructor setting seed to a random number
     */

    public ScenarioGenerator(){
        r.setSeed((long)Math.floor(Math.random()*DEFAULT_SEED_HELPER));
        passengerCountMinimum = 1;
        passengerCountMaximum = 5;
        pedestrianCountMinimum = 1;
        pedestrianCountMaximum = 5;
    }

    /**
     * set seed value to a predefined value
     * @param seed long
     *
     */
    public ScenarioGenerator(long seed){
        this.seed = seed;
    }

    public long getSeed(){
        return this.seed;
    }

    /**
     *
     * Constructor to set seed and other min/max values
     * @param seed long
     * @param passengerCountMinimum int
     * @param passengerCountMaximum int
     * @param pedestrianCountMinimum int
     * @param pedestrianCountMaximum int
     *
     */
    public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum, int pedestrianCountMinimum, int pedestrianCountMaximum){
        this.seed = seed;
        this.passengerCountMaximum = passengerCountMaximum;
        this.passengerCountMinimum = passengerCountMinimum;
        this.pedestrianCountMinimum = pedestrianCountMinimum;
        this.pedestrianCountMaximum = pedestrianCountMaximum;

    }

    /**
     * set passenger counts to min value
     * @param min int
     *
     */
    public void setPassengerCountMin(int min){
        if (min > passengerCountMaximum)
            passengerCountMinimum = 1;
        else
            passengerCountMinimum = min;
    }

    /**
     * set pedestrian counts to min value
     * @param min int
     *
     */
    public void setPedestrianCountMin(int min){
        if ( min > pedestrianCountMaximum )
            pedestrianCountMinimum = 1;
        else
            pedestrianCountMinimum = min;
    }

    /**
     * set passenger counts to max value
     * @param max int
     *
     */
    public void setPassengerCountMax(int max){
        passengerCountMaximum = max;
    }
    /**
     * set pedestrian counts to max value
     * @param max int
     *
     */
    public void setPedestrianCountMax(int max){
        pedestrianCountMaximum = max;
    }

    /**
     * getting a new Person
     * @return Person
     */

    public Person getRandomPerson(){

        int age;
        r.setSeed(r.nextLong());
        age = r.nextInt(AGE_LIMIT_PERSON)+1;
        boolean isPregnant = r.nextBoolean();

        Character.Gender gender = Character.Gender.getGender(r.nextInt(Character.Gender.values().length));
        Person.Profession profession = Person.Profession.getProfession(r.nextInt(Person.Profession.values().length));
        Character.BodyType bodyType = Character.BodyType.getBodyType(r.nextInt(Character.BodyType.values().length));

        return new Person(age, profession, gender, bodyType, isPregnant);
    }

    /**
     * Getting a new animal
     * @return Animal
     */

    public Animal getRandomAnimal(){
        ArrayList<String> animalList = new ArrayList<>();
        animalList.add("cat");
        animalList.add("dog");
        animalList.add("bird");
        animalList.add("rabbit");
        animalList.add("ferret");

        r.setSeed(r.nextLong());

        int age = r.nextInt(AGE_LIMIT_ANIMAL);
        Character.Gender gender = Character.Gender.getGender(r.nextInt(Character.Gender.values().length));
        Character.BodyType bodyType = Character.BodyType.getBodyType(r.nextInt(Character.Gender.values().length));

        int rand = r.nextInt(animalList.size());
        boolean isPet = r.nextBoolean();
        Animal animal = new Animal(animalList.get(rand));
        animal.setPet(isPet);
        return animal;
    }

    /**
     * new Scenario to be generated
     * @return Scenario
     */


    public Scenario generate(){

        Scenario scenario = new Scenario();

        //r.setSeed(this.seed);

        boolean hasYouInCar = false;
        boolean hasYouInLane = false;


        boolean isLegalCrossing = r.nextBoolean();
        scenario.setLegalCrossing(isLegalCrossing);

        int personInCar, animalInCar, totalPassengers, youIndex;

        totalPassengers = r.nextInt(passengerCountMaximum - passengerCountMinimum + 1) + passengerCountMinimum;
        personInCar = r.nextInt(totalPassengers - passengerCountMinimum + 1) + passengerCountMinimum;
        animalInCar = totalPassengers - personInCar;

        ArrayList<Character> myPassengers = new ArrayList<>();

        for (int i=0; i<personInCar;i++){
            myPassengers.add(getRandomPerson());
        }
        for (int i=0; i<animalInCar;i++){
            myPassengers.add(getRandomAnimal());
        }

        int personInLane,animalInLane,totalPedestrians;

        totalPedestrians = r.nextInt(pedestrianCountMaximum - pedestrianCountMinimum + 1) + pedestrianCountMinimum;
        personInLane = r.nextInt(totalPedestrians - pedestrianCountMinimum + 1) + pedestrianCountMinimum;
        animalInLane = totalPedestrians - personInLane;

        ArrayList<Character> myPedestrians = new ArrayList<>();
        for (int i=0; i<personInLane;i++)
            myPedestrians.add(getRandomPerson());
        for (int i=0; i<animalInLane;i++)
            myPedestrians.add(getRandomAnimal());

        //get isYou randomly

        boolean isYou = r.nextBoolean();

        if (isYou){
            hasYouInCar = r.nextBoolean();
            hasYouInLane = !hasYouInCar;
        }

        if (hasYouInCar && personInCar > 0 ){
            youIndex = r.nextInt(personInCar);
            Person person;
            person = (Person) myPassengers.get(youIndex);
            person.setAsYou(hasYouInCar);
        }

        if (hasYouInLane && personInLane > 0){
            youIndex = r.nextInt(personInLane);
            Person person;
            person = (Person) myPedestrians.get(youIndex);
            person.setAsYou(hasYouInLane);
        }

        //two new Character array for passengers and pedestrians

        Character[] passengers = new Character[myPassengers.size()];
        Character[] pedestrian = new Character[myPedestrians.size()];

        //copy both passenger and pedestrian array list to array

        myPassengers.toArray(passengers);
        myPedestrians.toArray(pedestrian);

        return new Scenario(passengers,pedestrian,isLegalCrossing);

    }
}
