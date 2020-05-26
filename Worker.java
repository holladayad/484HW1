/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIS484.HW1;

import java.util.ArrayList;

/**
 *
 * @author amber
 */
public class Worker {
    public static int nextID = 0;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String workStatus;
    private double hourlyPay;
    private String email;
    private Worker supervisor;
    private ArrayList<Animal> animalsCaredFor;
    private int workerId;
    private ArrayList<Worker> supervisees;
    private Shift schedule;
    
    public Worker(String firstName, String lastName, String phoneNumber, 
            String workStatus, double hourly, String email)
    {
        this.firstName = firstName;
        this.lastName= lastName;
        this.phoneNumber = phoneNumber;
        this.workStatus = workStatus;
        this.hourlyPay = hourly;
        this.email = email;
        this.supervisor = null;
        this.schedule = null;
        this.workerId = nextID++;
        this.animalsCaredFor = new ArrayList<>();
        if (workStatus.equals("Supervisor")){
            this.supervisees = new ArrayList<>();
        }else{
            this.supervisees = null;
        }
    }
    public String getWorkStatus(){
        return this.workStatus;
    }
    public boolean setSupervisor(Worker supervisor){
        if(supervisor.getWorkStatus().equals("Supervisor"))
        {
            this.supervisor = supervisor;
            supervisor.addSupervisee(this);
            return true;
        }else{
            return false;
        }
    }
    public void addSupervisee(Worker worker){
        this.supervisees.add(worker);
    }
    public void setSchedule(Shift schedule)
    {
        this.schedule = schedule;
    }
    public String getName(){
        return this.lastName + ", " + this.firstName;
    }
    public void addAnimal(Animal animal){
        this.animalsCaredFor.add(animal);
    }
    @Override
    public String toString()
    {
        String schedule = "Schedule: Not Set\n";
        String supervisor = "Supervisor: Not Assigned";
        if (this.schedule != null)
        {
            schedule = String.format("Schedule: %s\n", this.schedule);
        }
        if (this.supervisor != null)
        {
            supervisor = String.format("Supervisor: %s", this.supervisor.getName());
        }
        return String.format("First Name: %s\n"
                + "Last Name: %s\n"
                + "Phone Number: %s\n"
                + "Work Status: %s\n"
                + "Hourly: %d\n"
                + "Email: %s\n"
                + schedule + supervisor, this.firstName, this.lastName, 
                this.phoneNumber, this.workStatus, this.hourlyPay, this.email);
    }
    
}
