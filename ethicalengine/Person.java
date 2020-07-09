package ethicalengine;


/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class Person extends Character{

    private boolean isPregnant, isYou;
    private Profession profession;


    /**
     * default constructor to set values to all default, considered isPregnant and isYou as false
     */
    public Person(){
        super();
        profession = Profession.UNKNOWN;
        isPregnant = false;
        isYou = false;
    }

    /**
     * constructor to set up age, gender and bodytype
     * @param age age
     * @param gender gender
     * @param bodyType bodyTpye
     */
    public Person(int age, Gender gender, BodyType bodyType){
        super.setAge(age);
        super.setBodyType(bodyType);
        super.setGender(gender);
    }

    /**
     * Main Constructor
     * @param age Age
     * @param profession Profession
     * @param gender Gender
     * @param bodyType Bodytype
     * @param isPregnant Pregnancy Status
     */

    public Person(int age, Profession profession, Gender gender, BodyType bodyType, boolean isPregnant) {
        super(age, gender, bodyType);

        if (AgeCategory.getAgeCategory(age) == AgeCategory.ADULT)
            this.profession = profession;
        else
            this.profession = Profession.NONE;

        if (getGender() == Gender.FEMALE)
            this.isPregnant = isPregnant;

        if (AgeCategory.getAgeCategory(age).equals(AgeCategory.BABY) || AgeCategory.getAgeCategory(age).equals(AgeCategory.CHILD))
            this.isPregnant = false;

        if ( AgeCategory.getAgeCategory(age).equals(AgeCategory.SENIOR))
            this.isPregnant = false;

    }

    /**
     * Copy Constructor
     * @param otherPerson Another Person
     */

    public Person(Person otherPerson){
        super(otherPerson);
        if (otherPerson != null){
            this.profession = otherPerson.profession;
            this.isPregnant = otherPerson.isPregnant;
        }
    }

    /**
     * return age category from age
     * @return AgeCategory
     */
    public AgeCategory getAgeCategory(){

        int myAge = getAge();
        AgeCategory temp = null;

        if (myAge >= 0 && myAge <= 4)
            temp = AgeCategory.BABY;
        if (myAge >=5 && myAge <=16 )
            temp =  AgeCategory.CHILD;
        if (myAge >=17 && myAge <=68 )
            temp =  AgeCategory.ADULT;
       if  (myAge >68)
            temp =  AgeCategory.SENIOR;

        return temp;
    }

    /**
     * Getter Profession
     * @return Profession
     */

    public Profession getProfession() {
        return this.profession;
    }

    public boolean isPregnant(){
        if (getGender().equals(Gender.FEMALE))
            return this.isPregnant;
        else
            return false;
    }
    public void setPregnant(boolean pregnant){
        this.isPregnant = pregnant;
    }
    public boolean isYou(){
        return this.isYou;
    }
    public void setAsYou(boolean isYou){
        this.isYou = isYou;
    }

    /**
     * To String method
     * @return String
     */
    @Override
    public String toString() {
        String printout="";

        if (isYou && getAgeCategory() == AgeCategory.ADULT && isPregnant )
            printout = "you "+this.getBodyType()+" "+this.getAgeCategory()+" "+this.profession+" "+this.getGender()+" pregnant";
        if (isYou && getAgeCategory() == AgeCategory.ADULT && !isPregnant )
            printout = "you "+this.getBodyType()+" "+this.getAgeCategory()+" "+this.profession+" "+this.getGender();
        if (isYou && getAgeCategory() != AgeCategory.ADULT && isPregnant )
            printout = "you "+this.getBodyType()+" "+this.getAgeCategory()+" "+this.getGender()+" pregnant";
        if (isYou && getAgeCategory() != AgeCategory.ADULT && !isPregnant )
            printout = "you "+this.getBodyType()+" "+this.getAgeCategory()+" "+this.getGender();

        if (!isYou && getAgeCategory() == AgeCategory.ADULT && isPregnant )
            printout = this.getBodyType()+" "+this.getAgeCategory()+" "+this.profession+" "+this.getGender()+" pregnant";
        if (!isYou && getAgeCategory() != AgeCategory.ADULT && isPregnant )
            printout = this.getBodyType()+" "+this.getAgeCategory()+" "+this.getGender()+" pregnant";
        if (!isYou && getAgeCategory() == AgeCategory.ADULT && !isPregnant )
            printout = this.getBodyType()+" "+this.getAgeCategory()+" "+this.profession+" "+this.getGender();
        if (!isYou && getAgeCategory() != AgeCategory.ADULT && !isPregnant )
            printout = this.getBodyType()+" "+this.getAgeCategory()+" "+this.getGender();

        return  printout.toLowerCase();
    }

    /**
     * Enumeration type Profession with set of professions
     */

    public enum Profession {
        DOCTOR(0),
        CEO(1),
        CRIMINAL(2),
        HOMELESS(3),
        UNEMPLOYED(4),
        ENGINEER(5),
        STUDENT(6),
        RETIRED(7),
        UNKNOWN(8),
        NONE(9);


        private int profNum;

        Profession(int profNum){
            this.profNum = profNum;
        }
        public int getProfNum(){
            return this.profNum;
        }


        /**
         *
         * @param profNum number
         * @return Profession
         */

        public static Profession getProfession(int profNum){
            Profession profession = null;
            for (Profession temp: Profession.values()){
                if (temp.getProfNum() == profNum)
                    profession = temp;
            }
            return profession;
        }

        /**
         *
         * @param profession profession
         * @param age age
         * @return Profession
         */

        public static Profession getProf(String profession, int age){
            Profession myProfession = null;
            AgeCategory agc = AgeCategory.getAgeCategory(age);

            if (profession.isEmpty()){
                if (agc == AgeCategory.ADULT)
                    myProfession = Profession.UNKNOWN;
                else
                    myProfession = Profession.NONE;
            }

            for (Profession tempProf: Profession.values()){
                if (tempProf.name().equalsIgnoreCase(profession)){
                        myProfession = tempProf;
                }
            }

            return myProfession;
        }

    }

}
