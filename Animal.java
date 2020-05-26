
package CIS484.HW1;

import java.time.*;
import java.util.ArrayList;

public class Animal {
    
    public static int nextID = 0;
    private String name;
    private String breed;
    private int age;
    private String gender;
    private int animalID;
    private LocalDate intakeDate;
    private LocalDate outtakeDate;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    
    
    
    
    public Animal( String name, String breed, int age, String gender, String intakeDate)
    {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.animalID = nextID++;
        this.intakeDate = LocalDate.parse(intakeDate);
    }
    
    public void addAppointment(Appointment appointment)
    {
        this.appointments.add(appointment);
    }
    
    public ArrayList<Appointment> getAppointments()
    {
        return this.appointments;
    }
    @Override
    public String toString(){
        String outtake = "";
        if (this.outtakeDate != null){
            outtake = String.format("Outtake Date: %s\n", this.outtakeDate);
        }
        return String.format(
                "Name: %s\n"
                + "Breed: %s\n"
                + "Age: %d\n"
                + "Gender: %s\n"
                + "Intake Date: %s\n"
                + outtake, this.name, this.breed, this.age, this.gender, this.intakeDate);
    }
    
}
