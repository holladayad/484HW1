
package CIS484.HW1;

import java.util.ArrayList;

public class Shelter {
    
    public static int nextID = 0;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private int shelterID;
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private ArrayList<Worker> workers = new ArrayList<Worker>();
    
    
    
    public Shelter(String street, String city, String state, String zipCode, String phoneNumber, String email)
    {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.shelterID = nextID++;
    }
    
    public void addWorker(Worker newPerson)
    {
        this.workers.add(newPerson);
    }
    
    public void addAnimal(Animal newAnimal)
    {
        this.animals.add(newAnimal);
    }
    
    public ArrayList<Worker> getWorkers()
    {
        return this.workers;
    }
    
    public ArrayList<Animal> getAnimals()
    {
        return this.animals;
    }
    
    @Override
    public String toString()
    {
        String ret = String.format("Address: %s, %s, %s, %s\n"
                + "Phone Number: %s\n"
                + "Email: %s\n"
                + "Number of Workers: %d\n"
                + "Number of Animals: %d",
                this.street, this.city, this.state, this.zipCode, this.phoneNumber, this.email,
                this.workers.size(),this.animals.size());
        return ret;
    }
    
}
