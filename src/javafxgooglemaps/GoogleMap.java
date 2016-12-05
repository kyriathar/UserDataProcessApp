package javafxgooglemaps;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 1/9/15.
 */

public class GoogleMap extends Region {

    private WebView webView;
    private WebEngine webEngine;
    private boolean ready;

    private JSObject doc;
    private EventHandler<MapEvent> onMapLatLngChanged;


    public GoogleMap() {
        initMap();
        initCommunication();
        getChildren().add(webView);
        //setMarkerPosition(0,0);
        //setMapCenter(0, 0);
        //switchTerrain();
    }

    private void initMap()
    {
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("index.html").toExternalForm());
        ready = false;
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                final Worker.State oldState,
                                final Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    ready = true;
                }
            }
        });
    }

    private void initCommunication() {
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                final Worker.State oldState,
                                final Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    doc = (JSObject) webEngine.executeScript("window");
                    doc.setMember("app", GoogleMap.this);
                }
            }
        });
    }

    public void setOnMapLatLngChanged(EventHandler<MapEvent> eventHandler) {
        onMapLatLngChanged = eventHandler;
    }

    //TO BE DELETED
    public void handle(double lat, double lng) {
        if(onMapLatLngChanged != null) {
            MapEvent event = new MapEvent(this, lat, lng);
            onMapLatLngChanged.handle(event);
        }
    }

    private void invokeJS(final String str) {
        if(ready) {
            doc.eval(str);
        } else {
            webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                    final Worker.State oldState,
                                    final Worker.State newState) {
                    if (newState == Worker.State.SUCCEEDED) {
                        doc.eval(str);
                    }
                }
            });
        }
    }

    public void setMarkerPosition(double lat, double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("setMarkerPosition(" + sLat + ", " + sLng + ")");
    }

    public void setMapCenter(double lat, double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("setMapCenter(" + sLat + ", " + sLng + ")");
    }

    public void switchTerrain() {
        invokeJS("switchTerrain()");
    }

    public void addMarker(double lat, double lng, int rssi, int frequency) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        String sRssi = Integer.toString(rssi);
        String sfrequency = Integer.toString(frequency);
        invokeJS("addMarker(" + sLat + ", " + sLng + ", "  + sRssi + ", " + sfrequency +")");
    }

    public void clearMarkers(){
        invokeJS("clearMarkers()");
    }

    public void addPoint(double lat,double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("addPoint(" + sLat + ", " + sLng + ")");
    }

    public void doPolyline(){
        invokeJS("doPolyline()");
    }

    public void clearPolyline(){
        invokeJS("clearPolyline()");
    }

    public void addStayPoint(double lat,double lng,Date timestamp1 ,Date timestamp2){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        String sTimestamp1 = formatter.format(timestamp1);
        String sTimestamp2 = formatter.format(timestamp2);
        invokeJS("addStayPoint(" + sLat + ", " + sLng + ", " +  "\""  + sTimestamp1  + "\"" + ", " + "\"" + sTimestamp2  + "\"" + ")");
    }

    public void addPointsOfInterest(double lat,double lng){
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("addPointsOfInterest(" + sLat + ", " + sLng + ")");
    }

    public void createRectangle(){
        invokeJS("createRectangle()");
    }

    public void deleteRectangle(){
        invokeJS("deleteRectangle()");
    }

    public void deleteAllRectangles() {
        invokeJS("deleteAllRectangles()");
    }
}
