package Model;

import java.time.LocalDateTime;

public class TimeHolder {

    LocalDateTime currentTime;
    LocalDateTime tableauStart;
    LocalDateTime tableauEnd;

    public TimeHolder(){
        currentTime = LocalDateTime.now();
        tableauStart = currentTime.minusHours(12);
        tableauEnd = currentTime.plusHours(12);
    }

    public String getStartDateString(){
        return  tableauStart.toLocalDate().toString();
    }

    public String getEndDateString(){
        return tableauEnd.toLocalDate().toString();
    }

    public boolean isBeforeTableauStart(String startTime){
        startTime = startTime.substring(0, startTime.length() - 1);
        LocalDateTime programStart = LocalDateTime.parse(startTime);

        return programStart.isBefore(tableauStart);
    }

    public boolean isAfterTableauEnd(String startTime){
        startTime = startTime.substring(0, startTime.length() - 1);
        LocalDateTime programEnd = LocalDateTime.parse(startTime);

        return programEnd.isAfter(tableauEnd);
    }

}
