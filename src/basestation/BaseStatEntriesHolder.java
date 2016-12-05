package basestation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyriakos on 9/9/15.
 */
public class BaseStatEntriesHolder {
    private List<BaseStatEntry> baseStatEntries ;

    public BaseStatEntriesHolder(BaseStatEntry baseStatEntry){
        baseStatEntries = new ArrayList<>();
        baseStatEntries.add(baseStatEntry);
    }

    public void addBaseStatEntry(BaseStatEntry baseStatEntry)throws NullPointerException{
        baseStatEntries.add(baseStatEntry);
    }

    public List<BaseStatEntry> getBaseStatEntries() {
        return baseStatEntries;
    }
}
