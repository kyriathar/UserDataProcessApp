package basestation;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


public class OperatorWrapper{
    public static Map<String ,Operator> operatorMap =new HashMap<>();

    public OperatorWrapper(){

    }

    public static void generateOperators(String filename) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();
        while (line != null) {
            String[] tokens = line.split("\t");
            Operator operator;
            if((operator = operatorMap.get(tokens[2]))!=null){
                operator.add(tokens[1]);
            }
            else {
                operatorMap.put(tokens[2], new Operator( tokens[2],tokens[1]));
            }
            line = br.readLine();
        }
        System.out.println(operatorMap.get("VODAFONEGR"));
    }
}