package wifi;

/**
 * Created by Kyriakos on 3/9/15.
 */

public class RssiFrequencyHolder {
    private int rssi ;
    private int frequency ;

    public RssiFrequencyHolder(int rssi ,int frequency){
        this.rssi =rssi ;
        this.frequency = frequency ;
    }

    public int getRssi() {
        return rssi;
    }
    public int getFrequency() {
        return frequency;
    }

    public void update(int rssi ,int frequency){
        this.rssi = (this.rssi +rssi)/2;
        this.frequency = (this.frequency + frequency)/2;
    }
}
