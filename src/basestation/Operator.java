package basestation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyriakos on 18/9/15.
 */
public class Operator {
    private String name ;
    private Map<String ,Byte> usersMap ;

    public Operator(String name ,String userID){
        this.name= name ;
        usersMap = new HashMap<>();
        usersMap.put(userID,new Byte((byte) 0));
    }
    public void add(String userID){
        Byte b;
        usersMap.put(userID,new Byte((byte) 0));
    }

    public String getName() {
        return name;
    }
    public int getNoClients(){
        return usersMap.size();
    }
}
