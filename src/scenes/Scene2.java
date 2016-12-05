package scenes;

import basestation.BaseStatEntriesHolder;
import basestation.BaseStatEntry;
import battery.Diagram;
import battery.BatteryEntriesHolder;
import battery.BatteryEntry;
import gps.GpsEntriesHolder;
import gps.GpsEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxgooglemaps.GoogleMap;
import sample.Main;
import wifi.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyriakos on 15/9/15.
 */
public class Scene2 implements EventHandler<ActionEvent> {

    Scene scene2 ;
    Diagram batteryDiagram;
    TextField startDateText,endDateText,userIdText;
    GoogleMap googleMapWifi,googleMapGps, googleMapBase ;
    public static GoogleMap googleMapNear ;
    Button buttonGoogleMapsWifi,buttonGoogleMapsGps,buttonBatDiag,buttonGoogleMapsBase ,buttonGetData;

    public void display(){
        //Scene for GooglemapsWIFI
        googleMapWifi = new GoogleMap();
        //Scene for GooglemapsGPS
        googleMapGps = new GoogleMap();
        //Scene for Battery Diagram
        batteryDiagram = new Diagram();
        //Scene for GooglemapsBASE
        googleMapBase = new GoogleMap();
        //Scene for GooglemapNear
        googleMapNear = new GoogleMap();


        //Scene for question 2
        Label label = new Label("Erwtima 2");
        Label labelstartDate = new Label("Arxiki Imerominia (yyyy-MM-dd hh:mm:ss) : ");
        startDateText = new TextField();
        startDateText.setText("2010-01-01 00:00:00");
        Label labelendDate = new Label("Teliki Imerominia (yyyy-MM-dd hh:mm:ss) : ");
        endDateText = new TextField();
        endDateText.setText("2020-01-01 00:00:00");
        Label labeluserId = new Label("ID Xristi : ");
        userIdText = new TextField();
        userIdText.setText("user56");
        buttonGetData = new Button("Ok");
        buttonGetData.setOnAction(this);
        buttonGoogleMapsWifi = new Button("Anoikse Xarti (wifi markers)");
        buttonGoogleMapsWifi.setOnAction(this);
        buttonGoogleMapsGps = new Button("Anoikse Xarti (gps polyline)");
        buttonGoogleMapsGps.setOnAction(this);
        buttonBatDiag = new Button("Anoikse Diagrama Mpatarias");
        buttonBatDiag.setOnAction(this);
        buttonGoogleMapsBase = new Button("Anoikse Xarti (Base stations)");
        buttonGoogleMapsBase.setOnAction(this);
        Button button = new Button("Pigene sto Erwtima 3.1");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Scene31 scene31 = new Scene31();
                scene31.display();
            }
        });
        GridPane layout = new GridPane();
        layout.add(label ,0,0);
        layout.add(labelstartDate, 0, 1);
        layout.add(startDateText ,1,1);
        layout.add(labelendDate ,0,2);
        layout.add(endDateText ,1,2);
        layout.add(labeluserId ,0,3);
        layout.add(userIdText ,1,3);
        layout.add(buttonGetData ,1,4);
        layout.add(buttonGoogleMapsWifi ,0,5);
        layout.add(buttonGoogleMapsGps ,1,5);
        layout.add(buttonBatDiag ,0,6);
        layout.add(buttonGoogleMapsBase ,1,6);
        layout.add(button, 1, 9);
        layout.setVgap(5);
        scene2 = new Scene(layout,400,300);
        Main.window.setScene(scene2);
        Main.window.show();
    }

    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() ==  buttonGetData) {

            Date historyDate = null;
            Date futureDate = null ;
            DateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                historyDate = format.parse(startDateText.getText());
                futureDate = format.parse(endDateText.getText());
            } catch (ParseException e) {
                //e.printStackTrace();
                userIdText.setText("username");
                userIdText.setStyle("-fx-text-inner-color: red;");
                startDateText.setText("yyyy-MM-dd hh:mm:ss");
                startDateText.setStyle("-fx-text-inner-color: red;");
                endDateText.setText("??:2008-06-20 11:12:13");
                endDateText.setStyle("-fx-text-inner-color: red;");
                Main.window.setScene(scene2);
                Main.window.show();
            }
            //Wifi code...
            UserHolder itUser ;
            itUser = Main.mapsHolder.getUserMap().get(userIdText.getText());

            if(itUser != null) {
                MacAddressHolder itMac ;
                Map<String, RssiFrequencyHolder> rssiFrequencyMap =itUser.getRssiFrequencyMap();
                List<WifiEntry> entries = itUser.getEntries();
                double correctLat;
                double correctLng;
                for(WifiEntry e : entries) {
                    if (e.getTimestamp().after(historyDate) && e.getTimestamp().before(futureDate)) {
                        correctLat = Main.mapsHolder.getMacAddressMap().get(e.getBSSID()).getLat();
                        correctLng = Main.mapsHolder.getMacAddressMap().get(e.getBSSID()).getLon();
                        googleMapWifi.addMarker(correctLat, correctLng, rssiFrequencyMap.get(e.getBSSID()).getRssi(), rssiFrequencyMap.get(e.getBSSID()).getFrequency());

                    }
                }
            }
            //Gps code...
            GpsEntriesHolder gpsEntriesHolder;
            gpsEntriesHolder = Main.GpsMap.get(userIdText.getText());
            //4.2...
            WifiNearGps wifiNearGps = new WifiNearGps();
            wifiNearGps.createMap(userIdText.getText());
            SmallWifiEntry smallWifiEntry ;


            if(gpsEntriesHolder != null){
                List <GpsEntry> gpsEntries = gpsEntriesHolder.getGpsEntries();
                for(GpsEntry e : gpsEntries){
                    if(e.getTimestamp().after(historyDate) && e.getTimestamp().before(futureDate)){
                        googleMapGps.addPoint(e.getLatitude(), e.getLongtitude());
                        //4.2....
                        googleMapNear.addPoint(e.getLatitude(), e.getLongtitude());
                        smallWifiEntry = wifiNearGps.find(e.getStimestamp());
                        if(smallWifiEntry != null){
                            Map<String, RssiFrequencyHolder> rssiFrequencyMap =itUser.getRssiFrequencyMap();
                            double correctLat;
                            double correctLng;
                            correctLat = Main.mapsHolder.getMacAddressMap().get(smallWifiEntry.getBssid()).getLat();
                            correctLng = Main.mapsHolder.getMacAddressMap().get(smallWifiEntry.getBssid()).getLon();
                            googleMapNear.addMarker(correctLat, correctLng,rssiFrequencyMap.get(smallWifiEntry.getBssid()).getRssi(), rssiFrequencyMap.get(smallWifiEntry.getBssid()).getFrequency());
                        }
                    }

                }
            }
            googleMapGps.doPolyline();
            googleMapNear.doPolyline();

            //Battery code...
            BatteryEntriesHolder batteryEntriesHolder;
            batteryEntriesHolder = Main.BatteryMap.get(userIdText.getText());

            if(batteryEntriesHolder != null){
                List <BatteryEntry> batteryEntries = batteryEntriesHolder.getBatteryEntries();
                for(BatteryEntry e : batteryEntries){
                    if(e.getTimestamp().after(historyDate) && e.getTimestamp().before(futureDate)){
                        batteryDiagram.addLevel(e.getLevel());
                        batteryDiagram.addTimestamp(e.getTimestamp());
                    }
                }
            }
            batteryDiagram.createDiagram();

            //Base stations code...
            BaseStatEntriesHolder baseStatEntriesHolder;
            baseStatEntriesHolder = Main.BaseMap.get(userIdText.getText());
            if(baseStatEntriesHolder != null){
                List <BaseStatEntry> baseStatEntries = baseStatEntriesHolder.getBaseStatEntries();
                for(BaseStatEntry e : baseStatEntries){
                    if(e.getTimestamp().after(historyDate) && e.getTimestamp().before(futureDate)){
                        googleMapBase.addMarker(e.getLatitude(),e.getLongitude(),0,0);
                    }
                }
            }
        }

        if(actionEvent.getSource() == buttonGoogleMapsWifi) {
            final Stage stage = new Stage();
            stage.setTitle("Wifi GoogleMaps");
            Button buttonClearMarkers = new Button("Clear Markers");
            buttonClearMarkers.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    googleMapWifi.clearMarkers();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(googleMapWifi,buttonClearMarkers,buttonBackToScene2);
            Scene googleMapsScene = new Scene(layoutGmaps, 800, 700);
            stage.setScene(googleMapsScene);
            stage.show();
        }

        if(actionEvent.getSource() ==buttonGoogleMapsGps) {
            final Stage stage = new Stage();
            stage.setTitle("Gps GoogleMaps");
            Button buttonClearPoly = new Button("Clear Polyline");
            buttonClearPoly.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    googleMapGps.clearPolyline();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(googleMapGps,buttonClearPoly,buttonBackToScene2);
            Scene googleMapsGpsScene = new Scene(layoutGmaps, 800, 700);
            stage.setScene(googleMapsGpsScene);
            stage.show();
        }

        if(actionEvent.getSource() ==buttonBatDiag) {
            final Stage stage = new Stage();
            stage.setTitle("Battery Diagram");
            Button buttonClearBatDiag = new Button("Clear Diagram");
            buttonClearBatDiag.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    batteryDiagram.destroyChart();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(batteryDiagram,buttonClearBatDiag,buttonBackToScene2);
            Scene batDiagScene = new Scene(layoutGmaps, 800, 300);
            stage.setScene(batDiagScene);
            stage.show();
        }

        if(actionEvent.getSource() == buttonGoogleMapsBase) {
            final Stage stage = new Stage();
            stage.setTitle("Base Stations GoogleMaps");
            Button buttonClearMarkers = new Button("Clear Markers");
            buttonClearMarkers.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    googleMapBase.clearMarkers();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(googleMapBase,buttonClearMarkers,buttonBackToScene2);
            Scene googleMapsBaseScene = new Scene(layoutGmaps, 800, 700);
            stage.setScene(googleMapsBaseScene);
            stage.show();
        }
    }

}
