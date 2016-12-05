package sample;

import basestation.BaseStatEntriesHolder;
import basestation.BaseStatEntry;
import battery.BatteryEntriesHolder;
import battery.BatteryEntry;
import battery.LowBattery;
import gps.GpsEntriesHolder;
import gps.GpsEntry;
import wifi.WifiEntry;
import wifi.MacAddressHolder;
import wifi.MapsHolder;
import wifi.UserHolder;

import java.util.HashMap;
import java.util.Map;


import java.io.*;
import java.text.ParseException;

/**
 * Created by Kyriakos on 25/8/15.
 */
public class MyIOs {

    public static MapsHolder wifiToMapsHolder(String filename) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();

        // create our maps
        MapsHolder mapsHolder = new MapsHolder();

        System.out.println("Start reading file : " +filename);
        while (line != null) {
            String[] tokens = line.split("\t");

            WifiEntry wifiEntry = new WifiEntry();
            wifiEntry.setId(Integer.parseInt(tokens[0]));
            wifiEntry.setEmail(tokens[1]);
            wifiEntry.setSSID(tokens[2]);
            wifiEntry.setBSSID(tokens[3]);
            wifiEntry.setRSSI(Integer.parseInt(tokens[4]));
            wifiEntry.setFrequency(Integer.parseInt(tokens[5]));
            wifiEntry.setLatitude(Double.parseDouble(tokens[6]));
            wifiEntry.setLongtitude(Double.parseDouble(tokens[7]));
            wifiEntry.setTimestamp(tokens[8]);

            //pass wifiEntry to MacAddressMap
            MacAddressHolder itCont ;
            if((itCont = mapsHolder.getMacAddressMap().get(wifiEntry.getBSSID()))!=null){
                itCont.addEntry(wifiEntry);                                                              //Container exists in map
            }
            else {
                mapsHolder.getMacAddressMap().put(wifiEntry.getBSSID(), new MacAddressHolder(wifiEntry.getBSSID(), wifiEntry));   //Container doesnt exist in map
            }

            //pass wifiEntry to UserMap
            UserHolder itUser ;
            if((itUser = mapsHolder.getUserMap().get(wifiEntry.getEmail()))!=null){
                itUser.addEntry(wifiEntry);                                                              //Container exists in map
            }
            else {
                mapsHolder.getUserMap().put(wifiEntry.getEmail(), new UserHolder(wifiEntry.getEmail(), wifiEntry));   //Container doesnt exist in map
            }


            line = br.readLine();
        }
        System.out.println("Completed reading file : " +filename);

        //Calc final lat ,lon for all Access Points
        for (MacAddressHolder iterCont : mapsHolder.getMacAddressMap().values()) {
            iterCont.calcLatLon();
        }
        System.out.println("\"Correct\" Latitutes and Longtitudes were calculated !");
        return mapsHolder ;
    }

    public static Map<String, GpsEntriesHolder> gpsToGpsMap(String filename) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();

        // create our map
        Map<String, GpsEntriesHolder> GpsMap = new HashMap<>();
        System.out.println("Start reading file : " +filename);

        while (line != null) {
            String[] tokens = line.split("\t");

            GpsEntry gpsEntry = new GpsEntry();
            gpsEntry.setId(Integer.parseInt(tokens[0]));
            gpsEntry.setEmail(tokens[1]);
            gpsEntry.setLatitude(Double.parseDouble(tokens[2]));
            gpsEntry.setLongtitude(Double.parseDouble(tokens[3]));
            gpsEntry.setTimestamp(tokens[4]);

            //pass entry to GpsMap
            GpsEntriesHolder gpsEntriesHolder ;
            if((gpsEntriesHolder = GpsMap.get(gpsEntry.getEmail()))!=null){
                gpsEntriesHolder.addGpsEntry(gpsEntry);
            }
            else {
                GpsMap.put(gpsEntry.getEmail(), new GpsEntriesHolder(gpsEntry));
            }

            line = br.readLine();
        }
        System.out.println("Completed reading file : " +filename);
        return GpsMap ;
    }

    public static Map<String, BatteryEntriesHolder> batteryToBatteryMap(String filename) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();

        //create LowBattery
        Main.lowBattery = new LowBattery();

        // create our map
        Map<String, BatteryEntriesHolder> BatteryMap = new HashMap<>();
        System.out.println("Start reading file : " +filename);

        while (line != null) {
            String[] tokens = line.split("\t");

            BatteryEntry batteryEntry = new BatteryEntry();
            batteryEntry.setEmail(tokens[1]);
            batteryEntry.setLevel(Byte.parseByte(tokens[2]));
            batteryEntry.setPlugged(Byte.parseByte(tokens[3]));
            batteryEntry.setTemperature(Short.parseShort(tokens[4]));
            batteryEntry.setVoltage(Short.parseShort(tokens[5]));
            batteryEntry.setTimestamp(tokens[6]);

            //batteryEntry.print();
            //populate LowBattery
            if(batteryEntry.getLevel() <= 15)
                Main.lowBattery.add(batteryEntry.getTimestamp(),batteryEntry.getEmail());

            //pass entry to BatteryMap
            BatteryEntriesHolder batteryEntriesHolder ;
            if((batteryEntriesHolder = BatteryMap.get(batteryEntry.getEmail()))!=null){
                batteryEntriesHolder.addBatteryEntry(batteryEntry);
            }
            else {
                BatteryMap.put(batteryEntry.getEmail(), new BatteryEntriesHolder( batteryEntry));
            }

            line = br.readLine();
        }
        System.out.println("Completed reading file : " +filename);
        return BatteryMap ;
    }

    public static Map<String, BaseStatEntriesHolder> baseStatToBaseMap(String filename) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();

        // create our map
        Map<String, BaseStatEntriesHolder> BaseMap = new HashMap<>();
        System.out.println("Start reading file : " +filename);

        while (line != null) {
            String[] tokens = line.split("\t");

            if(tokens[7].equals("No Latitude") || tokens[7].equals("No latitude yet") || tokens[8].equals("No longitude") || tokens[8].equals("No longitude yet") )
            {
                line = br.readLine();
                continue;
            }

            BaseStatEntry baseStatEntry = new BaseStatEntry();
            baseStatEntry.setEmail(tokens[1]);
            baseStatEntry.setOperator(tokens[2]);
            baseStatEntry.setMcc(Short.parseShort(tokens[3]));
            baseStatEntry.setMnc(Short.parseShort(tokens[4]));
            baseStatEntry.setCid(Integer.parseInt(tokens[5]));
            baseStatEntry.setLac(Short.parseShort(tokens[6]));
            baseStatEntry.setLatitude(Double.parseDouble(tokens[7]));
            baseStatEntry.setLongitude(Double.parseDouble(tokens[8]));
            baseStatEntry.setTimestamp(tokens[9]);

            //baseStatEntry.print();

            //pass entry to BaseMap
            BaseStatEntriesHolder baseStatEntriesHolder ;
            if((baseStatEntriesHolder = BaseMap.get(baseStatEntry.getEmail()))!=null){
                baseStatEntriesHolder.addBaseStatEntry(baseStatEntry);
            }
            else {
                BaseMap.put(baseStatEntry.getEmail(), new BaseStatEntriesHolder( baseStatEntry));
            }

            line = br.readLine();
        }
        System.out.println("Completed reading file : " +filename);
        return BaseMap ;
    }
}
