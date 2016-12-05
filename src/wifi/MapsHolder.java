package wifi;

import wifi.MacAddressHolder;
import wifi.UserHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyriakos on 3/9/15.
 */
public class MapsHolder {
    private Map<String, MacAddressHolder> MacAddressMap;
    private Map<String, UserHolder> UserMap;

    public MapsHolder() {
        MacAddressMap = new HashMap<String, MacAddressHolder>();
        UserMap = new HashMap<String, UserHolder>();
    }

    public Map<String, MacAddressHolder> getMacAddressMap() {
        return MacAddressMap;
    }

    public Map<String, UserHolder> getUserMap() {
        return UserMap;
    }
}
