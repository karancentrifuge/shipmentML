import com.google.gson.*;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ParseJson {
    private String path;
    private String[] removeFeats;

    public ParseJson(String filePath, String[] trivialFeats) throws Exception {
        if (!(filePath instanceof String)){
            throw new Exception("Path is not a string.");
        }
        if (!(trivialFeats instanceof String[])){
            throw new Exception("Features to remove are not an array of strings.");

        }
        path = filePath;
        removeFeats = trivialFeats;

    }

    private JsonObject convertJsonElementToJsonObject() throws Exception {
        JsonParser parser;
        JsonElement jsonTree;
        JsonObject jsonObj = null;

        if (!(path instanceof String)) {
            throw new Exception("Path is not a string.");
        }
        parser = new JsonParser();
        jsonTree = parser.parse(new FileReader(path));
        jsonObj = jsonTree.getAsJsonObject();
        return jsonObj;
    }


    private INDArray convertToNpArray() throws Exception {
        Set<String> setOfTrivialFeats;
        JsonObject json;
        double[][] featureValues;
        Gson gson;
        int counter;

        setOfTrivialFeats = new HashSet<String>(Arrays.asList(removeFeats));

        json = new ParseJson(path, new String[]{"BaseDateTime", "VesselName",  "IMO", "CallSign", "Status", "timeDiff"}).convertJsonElementToJsonObject();
        featureValues = new double[1][json.size()];

        gson = new GsonBuilder().setPrettyPrinting().create();
        counter = 0;
        for (Map.Entry<String, JsonElement> entry : json.entrySet()){
            if (!setOfTrivialFeats.contains(entry.getKey())){
                String value = gson.fromJson(entry.getValue(), String.class);
                featureValues[0][counter] = Double.parseDouble(value);
                counter += 1;
            }
        }
        INDArray npArray = Nd4j.create(featureValues);
        return npArray;
    }

    public static void main(String[] args) throws Exception {
        ParseJson result = new ParseJson("C:\\Users\\18567\\shipmentML\\Prediction\\src\\main\\resources\\shipment.json", new String[] {"BaseDateTime", "VesselName", "IMO", "CallSign", "Status", "timeDiff"});
        System.out.println(result.convertToNpArray());
    }
}