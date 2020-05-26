/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIS484.HW1;

/**
 *
 * @author amber
 */
public class Shift {
    private String[] startTimes;
    private String[] endTimes;
    
    public Shift(String[] starts, String[] ends){
        this.startTimes = starts;
        this.endTimes = ends;
    }
    
    @Override
    public String toString(){
        return String.format("Sunday: %s-%s\n"
                + "Monday: %s-%s\n"
                + "Tuesday: %s-%s\n"
                + "Wednesday: %s-%s\n"
                + "Thursday: %s-%s\n"
                + "Friday: %s-%s\n"
                + "Saturday: %s-%s\n", startTimes[0], endTimes[0], 
                startTimes[1], endTimes[1], startTimes[2], endTimes[2], 
                startTimes[3], endTimes[3], startTimes[4], endTimes[4], 
                startTimes[5], endTimes[5], startTimes[6], endTimes[6]);
    }
}
