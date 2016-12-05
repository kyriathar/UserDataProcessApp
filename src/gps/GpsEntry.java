package gps;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 5/9/15.
 */
public class GpsEntry {
    private int id ;
    private String email ;
    private double latitude ;
    private double longtitude ;
    private Date timestamp  ;

    static DateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public GpsEntry(){
        id = 0 ;
        email = null ;
        latitude = 0.0;
        longtitude = 0.0;
        timestamp = null ;
    }

    //Setters
    public void setId(int id) {this.id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongtitude(double longtitude) {this.longtitude = longtitude;}
    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = format.parse(timestamp);
    }

    //Getters
    public int getId() {return id;}
    public String getEmail() {return email;}
    public double getLatitude() {return latitude;}
    public double getLongtitude() {return longtitude;}
    public Date getTimestamp() {return timestamp;}
    public String getStimestamp(){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timestamp);
    }
}
