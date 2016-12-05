package sample;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Kyriakos on 16/9/15.
 */

class WifiThread extends Thread{
    public void run(){
        try {
            Main.mapsHolder =  MyIOs.wifiToMapsHolder("myFiles/wifi.csv");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

class GpsThread extends Thread{
    public void run(){
        try {
            Main.GpsMap = MyIOs.gpsToGpsMap("myFiles/gps.csv");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

class BatteryThread extends Thread{
    public void run(){
        try {
            Main.BatteryMap = MyIOs.batteryToBatteryMap("myFiles/battery.csv");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

class BaseStationThread extends Thread{
    public void run(){
        try {
            Main.BaseMap = MyIOs.baseStatToBaseMap("myFiles/base_station.csv");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


public class Threads {
    private WifiThread wifiThread ;
    private GpsThread gpsThread ;
    private BatteryThread batteryThread ;
    private BaseStationThread baseStationThread;

    public Threads(){
        this.wifiThread = new WifiThread();
        this.gpsThread = new GpsThread();
        this.batteryThread = new BatteryThread();
        this.baseStationThread = new BaseStationThread();
    }

    public void runAll(){
        this.wifiThread.start();
        this.gpsThread.start();
        this.batteryThread.start();
        this.baseStationThread.start();
    }

    public void joinAll() throws InterruptedException {
        this.gpsThread.join();
        this.batteryThread.join();
        this.baseStationThread.join();
        this.wifiThread.join();
    }

}
