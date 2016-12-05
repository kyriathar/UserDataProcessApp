package battery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 7/9/15.
 */
public class BatteryEntry {
    private String email ;
    private byte level;
    private byte plugged;
    private short temperature;
    private short voltage;
    private Date timestamp  ;

    static DateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public BatteryEntry(){
        email = null ;
        level = 0;
        plugged = 0;
        temperature = 0;
        voltage=0;
        timestamp=null;
    }
    //Setters
    public void setEmail(String email) {this.email = email;}
    public void setLevel(byte level) {this.level = level;}
    public void setPlugged(byte plugged) {this.plugged = plugged;}
    public void setTemperature(short temperature) {this.temperature = temperature;}
    public void setVoltage(short voltage) {this.voltage = voltage;}
    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = format.parse(timestamp);
        //System.out.println(this.timestamp);
    }

    //Getters
    public String getEmail() {return email;}
    public byte getLevel() {return level;}
    public byte getPlugged() {return plugged;}
    public short getTemperature() {return temperature;}
    public short getVoltage() {return voltage;}
    public Date getTimestamp() {return timestamp;}

    //print Battery Entry
    public void print(){
        String fs ;
        fs = email + " " + level + " " + plugged + " " +
                temperature + " " + voltage + " " + timestamp;
        System.out.println(fs);
    }
}
