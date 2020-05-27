
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
    private ArrayList<Animal> adopted = new ArrayList<Animal>();
    
    
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
    
    public void transfer(Shelter newShelter, int animalID)
    {
        for (int i = 0; i<animals.size(); i++)
        {
            Animal current = animals.get(i);
            if (current.getAnimalID()== animalID)
            {
                newShelter.addAnimal(current);
                animals.remove(i);
                break;
            }
        }
    }
    
    public boolean adopt(int animalID)
    {
        for (int i = 0; i<animals.size(); i++)
        {
            Animal current = animals.get(i);
            if (current.getAnimalID()== animalID)
            {
                current.adopted();
                animals.remove(i);
                adopted.add(current);
                return true;
            }
        }
        return false;
        
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
