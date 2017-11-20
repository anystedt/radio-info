import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeHolder {

    LocalDateTime currentTime;
    LocalDateTime scheduleStart;
    LocalDateTime scheduleEnd;

    public TimeHolder(){
        currentTime = LocalDateTime.now();
        scheduleStart = currentTime.minusHours(12);
        scheduleEnd = currentTime.plusHours(12);
    }

    public LocalDateTime getCurrentTime(){ return currentTime;}

    public LocalDateTime getScheduleStart(){ return scheduleStart;}

    public LocalDateTime getScheduleEnd(){ return scheduleEnd;}

    public String getStartDateString(){
        return scheduleStart.toLocalDate().toString();
    }

    public String getEndDateString(){
        return scheduleEnd.toLocalDate().toString();
    }

    public boolean isBeforeScheduleStart(String startTime){
        startTime = startTime.substring(0, startTime.length() - 1);
        LocalDateTime programStart = LocalDateTime.parse(startTime);

        if(programStart.isBefore(scheduleStart)){
            return true;
        }

        return false;
    }

    public boolean isAfterScheduleEnd(String startTime){
        startTime = startTime.substring(0, startTime.length() - 1);
        LocalDateTime programEnd = LocalDateTime.parse(startTime);

        if(programEnd.isAfter(scheduleEnd)){
            return true;
        }

        return false;
    }

}
