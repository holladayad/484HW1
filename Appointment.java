
package CIS484.HW1.484HW1;

import java.time.LocalDate;


public class Appointment {
    public static int nextID = 0;
    private Treatment treatment;
    private String vetName;
    private LocalDate appDate;
    private int appID;
    
    public Appointment(Treatment treatment, String vetName, String appDate)
    {
        this.treatment = treatment;
        this.vetName = vetName;
        this.appDate = LocalDate.parse(appDate);
        appID = nextID++;
    }
    
    @Override
    public String toString()
    {
        return String.format("Treatment: %s\n"
                + "Veterinarian Name: %s\n"
                + "Appointment Date: %s", this.treatment, this.vetName, this.appDate);
    }
}
