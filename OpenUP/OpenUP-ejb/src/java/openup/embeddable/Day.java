/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Day {
    
    private int Day;

    /**
     * Get the value of Day
     *
     * @return the value of Day
     */
    public int getDay() {
        return Day;
    }

    /**
     * Set the value of Day
     *
     * @param Day new value of Day
     */
    public void setDay(int Day) {
        this.Day = Day;
    }

    private int EffortLeft;

    /**
     * Get the value of EffortLeft
     *
     * @return the value of EffortLeft
     */
    public int getEffortLeft() {
        return EffortLeft;
    }

    /**
     * Set the value of EffortLeft
     *
     * @param EffortLeft new value of EffortLeft
     */
    public void setEffortLeft(int EffortLeft) {
        this.EffortLeft = EffortLeft;
    }

    private int ChangeRelativeToPreviousWeek;

    /**
     * Get the value of ChangeRelativeToPreviousWeek
     *
     * @return the value of ChangeRelativeToPreviousWeek
     */
    public int getChangeRelativeToPreviousWeek() {
        return ChangeRelativeToPreviousWeek;
    }

    /**
     * Set the value of ChangeRelativeToPreviousWeek
     *
     * @param ChangeRelativeToPreviousWeek new value of
     * ChangeRelativeToPreviousWeek
     */
    public void setChangeRelativeToPreviousWeek(int ChangeRelativeToPreviousWeek) {
        this.ChangeRelativeToPreviousWeek = ChangeRelativeToPreviousWeek;
    }

}
