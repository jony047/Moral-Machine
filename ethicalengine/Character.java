package ethicalengine;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public abstract class Character {

    private int age;
    private Gender gender;
    private BodyType bodyType;
    public static final int DEFAULT_AGE = 25;


    /**
     * default constructor to set values to all default
     */
    public Character() {
        this.age = DEFAULT_AGE;
        this.gender = Gender.UNKNOWN;
        this.bodyType = BodyType.UNSPECIFIED;
    }

    /**
     *
     * Constructor to set age,gender,bodyType
     * @param age Age
     * @param gender Gender
     * @param bodyType BodyType
     */

    public Character(int age, Gender gender, BodyType bodyType){
        if (age < 0)
            this.age = DEFAULT_AGE;
        else
            this.age = age;
        this.gender = gender;
        this.bodyType = bodyType;
    }

    /**
     *
     * Copy Constructor
     * @param otherCharacter Character
     */
    public Character(Character otherCharacter){
        if(otherCharacter != null){
            this.age = otherCharacter.age;
            this.gender = otherCharacter.gender;
            this.bodyType = otherCharacter.bodyType;
        }
    }

    /**
     * Getter Methods to return age
     * @return age
     */
    public int getAge() {
        return this.age;
    }
    /**
     * Getter Methods to return Gender Enum
     * @return Gender
     */
    public Gender getGender() {
        return this.gender;
    }
    /**
     * Getter Methods to return bodyType
     * @return BodyType
     */
    public BodyType getBodyType() {
        return this.bodyType;
    }

    /**
     *
     * Setter methods to set age, age is a class invariant, if less than zero should set to a default value
     * @param age Age
     */
    public void setAge(int age){
        if (age < 0)
            this.age = DEFAULT_AGE;
        else
            this.age = age;
    }
    /**
     *
     * Setter methods to set Gender
     * @param gender Gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    /**
     *
     * Setter methods to set bodyType
     * @param bodyType Bodytype
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    /**
     *
     * Enumeration type Gender (Female,Male,Unknown)
     */

    public enum Gender {
       FEMALE(0),
       MALE(1),
        UNKNOWN(2);

       private int genderNum;

       Gender(int num){
           this.genderNum = num;
       }
       public int getNum(){
           return this.genderNum;
       }

        /**
         * Get Gender type
         * @param passNum Numbers
         * @return Gender
         */

       public static Gender getGender(int passNum){
           Gender gender = null;
           for (Gender tempGender: Gender.values()){
               if (tempGender.getNum() == passNum)
                   gender = tempGender;
           }
           return gender;
       }

        /**
         *
         * @param gender String
         * @return Gender
         */
        public static Gender getGender(String gender){
           Gender myGender = null;
            for (Gender tempGender: Gender.values()){
                if (tempGender.name().equalsIgnoreCase(gender))
                    myGender = tempGender;
            }
            return  myGender;
        }

   }
    /**
     *
     * Enumeration type AgeCategory (Baby,Child,Adult,Senior)
     */
    public enum AgeCategory {

        BABY(0),
        CHILD(1),
        ADULT(2),
        SENIOR(3);

        public int age_c;

        /**
         *
         * @param age_c int
         */

        AgeCategory(int age_c){
            this.age_c = age_c;
        }
        public int getAge_c(){
            return this.age_c;
        }

        /**
         * Return ageCategory from age
         * @param age int
         * @return AgeCategory
         */
        public static AgeCategory getAgeCategory(int age){
            AgeCategory ageCategory = null;

            if (age == 0 || age > 0 && age < 4 || age == 4)
                ageCategory = AgeCategory.BABY;
            else if (age >=5 && age <=16 )
                ageCategory =  AgeCategory.CHILD;
            else if (age >=17 && age <=68 )
                ageCategory =  AgeCategory.ADULT;
            else
                ageCategory =  AgeCategory.SENIOR;

            return ageCategory;
        }
    }

    /**
     *
     * Enumeration type BodyType (Average,Athletic,Overweight,Unspecified)
     */

    public enum BodyType {

        AVERAGE(0),
        ATHLETIC(1),
        OVERWEIGHT(2),
        UNSPECIFIED(3);

        public int bdt;

        /**
         *
         * @param bdt int
         */

        BodyType(int bdt){
            this.bdt = bdt;
        }
        public int getBdt(){
            return this.bdt;
        }

        /**
         *
         * @param bdt int
         * @return BodyType
         */

        public static BodyType getBodyType(int bdt){
            BodyType bodyType = null;
            for (BodyType temp: BodyType.values()){
                if (temp.getBdt() == bdt)
                    bodyType = temp;
            }
            return bodyType;
        }

        /**
         *
         * @param bodyType String
         * @return BodyType
         */
        public static BodyType getBdt(String bodyType){
            BodyType myBodyType = null;
            for (BodyType tempBdt: BodyType.values()){
                if (tempBdt.name().equalsIgnoreCase(bodyType))
                    myBodyType = tempBdt;
            }
            return myBodyType;
        }

    }
}
