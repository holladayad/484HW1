/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIS484.HW1;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author amber
 */
public class Treatment {
    public static int nextID = 0;
    private String treatName;
    private int treatmentID;
    public static ArrayList<String> allTreatments = new ArrayList<>();
    
    public Treatment(String treatName)
    {
        this.treatName = treatName;
        this.treatmentID = nextID++;
        allTreatments.add(treatName);
        
    }
    
    public boolean treatmentExists(String treatName)
    {
        for (int i = 0; i < allTreatments.size(); i++)
        {
            if (allTreatments.get(i).equals(treatName))
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return this.treatName;
    }
    
}
