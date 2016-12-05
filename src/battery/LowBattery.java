package battery;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kyriakos on 18/9/15.
 */

class LowBatteryPerHour{
    Map<String,Byte> usersMap;

    public LowBatteryPerHour() {
        usersMap = new HashMap<>();
    }


    public void add(String userID){
        usersMap.put(userID, new Byte((byte) 0));
    }

    public int getUsersMapSize() {
        return usersMap.size();
    }
}


class LowBatteryPerDay {
    private LowBatteryPerHour[] lowBatteryPerHours;

    private int dateToHour(Date date){
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        return calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
    }

    public LowBatteryPerDay(){
        lowBatteryPerHours = new LowBatteryPerHour[24];
        for(int i =0;i<24;i++) {
            lowBatteryPerHours[i] = new LowBatteryPerHour();
        }
    }

    public void add(Date date ,String userID ){
        lowBatteryPerHours[dateToHour(date)].add(userID);
    }

    public List<Integer> getArrayOfUsers(){
        List<Integer> array= new ArrayList<>();
        for(int i=0;i <24;i++)
        {
            array.add((lowBatteryPerHours[i].getUsersMapSize()));
        }
        return array;
    }

}


public class LowBattery{
    Map<String,LowBatteryPerDay> lowBatteryPerDaysMap ;
    static Format formatter = new SimpleDateFormat("yyyy-MM-dd");

    public LowBattery(){
        lowBatteryPerDaysMap = new HashMap<>();
    }

    public void add(Date date ,String userID){
        String sDate = formatter.format(date);

        LowBatteryPerDay lowBatteryPerDay;
        if((lowBatteryPerDay = lowBatteryPerDaysMap.get(sDate))!=null){
            lowBatteryPerDay.add(date, userID);
        }
        else {
            LowBatteryPerDay lb = new LowBatteryPerDay();
            lb.add(date,userID);
            lowBatteryPerDaysMap.put(sDate, lb);
        }
    }

    public List<Integer> getArrayOfUsers(Date date){
        String sDate = formatter.format(date);
        List<Integer> array ;
        LowBatteryPerDay lowBatteryPerDay;
        if((lowBatteryPerDay = lowBatteryPerDaysMap.get(sDate))!=null){
            return lowBatteryPerDay.getArrayOfUsers();
        }
        else {
            return null;
        }
    }

}