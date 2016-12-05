package battery;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 7/9/15.
 */
public class Diagram extends Region {
    private WebView webView;
    private WebEngine webEngine;
    private boolean ready;

    private JSObject doc;

    public Diagram() {
        initBatteryDiagram();
        initCommunication();
        getChildren().add(webView);

    }

    private void initBatteryDiagram()
    {
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("chart.html").toExternalForm());
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
                    doc.setMember("app", Diagram.this);
                }
            }
        });
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

    public void addLevel(byte level) {
        String sLevel = Byte.toString(level);
        invokeJS("addLevel("+sLevel+")");
    }

    public void addTimestamp(Date timestamp) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sTimestamp = formatter.format(timestamp);
        invokeJS("addTimestamp(" +  "\""  + sTimestamp  + "\"" + ")");
    }

    public void createDiagram(){
        invokeJS("createDiagram()");
    }

    public void destroyChart() {
        invokeJS("destroyChart()");
    }

    public void noOfPeoples(int level) {
        String sLevel = Integer.toString(level);
        invokeJS("addLevel("+sLevel+")");
    }

    public void createBarDiagram(){
        invokeJS("createBarDiagram()");
    }

    public void addOperator(String operator) {
        invokeJS("addOperator("  +  "\"" + operator + "\"" +")");
    }

    public void addClients(int clients) {
        String sClients = Integer.toString(clients);
        invokeJS("addClients("+sClients+")");
    }

    public void createOperatorDiagram(){
        invokeJS("createOperatorDiagram()");
    }

}
