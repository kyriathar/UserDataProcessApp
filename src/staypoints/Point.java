package staypoints;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Point {
    protected double latitude ;
    protected double longitude ;
    private Date timestamp  ;

    static DateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Point() {
        this.latitude = 0.0 ;
        this.longitude = 0.0;
        this.timestamp = null ;
    }

    public Point (double latitude ,double longitude ) {
        this.latitude = latitude ;
        this.longitude = longitude;
        this.timestamp = null;
    }

    public Point (double latitude ,double longitude ,Date timestamp) throws ParseException {
        this.latitude = latitude ;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = format.parse(timestamp);
    }

    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
    public Date getTimestamp() {
        return timestamp;
    }
}
