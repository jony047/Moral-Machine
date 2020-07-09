package ethicalengine;

/**
 * @author Jony Saha
 * @author jonys,Student ID: 1134030
 * @version 2.0
 */

public class Animal extends Character{

    String species;
    boolean isPet;

    /**
     * Default Animal Constructor to set species and isPet
     */
    public Animal(){
        species = "default";
        isPet = false;
    }
    public Animal(String species){
        this.species = species;
    }

    /**
     *
     * Copy constructor to create new instance of Class Animal
     * @param otherAnimal Other Animal to copy
     */

    public Animal(Animal otherAnimal){
        super(otherAnimal);
        if (otherAnimal != null){
            this.species = otherAnimal.species;
        }
    }

    /**
     * Getter method of species
     * @return String
     *
     */
    public String getSpecies() {
        return this.species;
    }

    /**
     *
     * Setter method of species
     * @param species as String
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     *
     * getter method of isPet
     * @return boolean
     */

    public boolean isPet(){
        return this.isPet;
    }

    /**
     *
     * setter method of isPet
     * @param isPet as boolean
     */
    public void setPet(boolean isPet){
        this.isPet = isPet;
    }

    /**
     *
     * toString method from Animal Class
     * @return String
     */

    @Override
    public String toString(){
        String printOut = "";
        if (this.isPet)
            printOut = this.species+" "+"is pet";
        else
            printOut = this.species;
        return printOut.toLowerCase();
    }

}
