/**
 * Created on 20/11/17
 * File: TimeHolder.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class that handles the time limits of the tableau. Keeps track of the
 * current time, the start time and ending time of the tableau.
 */

package model;

import java.time.LocalDateTime;

public class TimeHolder {

    private LocalDateTime tableauStart;
    private LocalDateTime tableauEnd;

    /**
     * Constructor that sets the current time, the start time and the
     * end time according to the specification of the tableau.
     */
    public TimeHolder(){
        LocalDateTime currentTime = LocalDateTime.now();
        tableauStart = currentTime.minusHours(12);
        tableauEnd = currentTime.plusHours(12);
    }

    /**
     * Returns a string containing the start time.
     * @return a string containing the start time.
     */
    public String getStartDateString(){
        return  tableauStart.toLocalDate().toString();
    }

    /**
     * Returns a string containing the end time.
     * @return a string containing the end time.
     */
    public String getEndDateString(){
        return tableauEnd.toLocalDate().toString();
    }

    /**
     * Returns true if the given time is before the start time.
     * @param startTime the start time for the program.
     * @return true if the program starts before the start of the
     * tableau, otherwise false.
     */
    public boolean isBeforeTableauStart(String startTime){
        startTime = startTime.substring(0, startTime.length() - 1);
        LocalDateTime programStart = LocalDateTime.parse(startTime);

        return programStart.isBefore(tableauStart);
    }

    /**
     * Returns true if the given time is after the end time.
     * @param startTime the start time for the program.
     * @return true if the program starts after the end of the
     * tableau, otherwise false.
     */
    public boolean isAfterTableauEnd(String startTime){
        startTime = startTime.substring(0, startTime.length() - 1);
        LocalDateTime programEnd = LocalDateTime.parse(startTime);

        return programEnd.isAfter(tableauEnd);
    }
}
