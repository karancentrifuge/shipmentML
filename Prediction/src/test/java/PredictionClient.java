import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.IOException;

public class PredictionClient {
    public static void main(String[] args) throws Exception {
        // Load json and create nd4j array
        String liveDataPath = PredictionClient.class.getResource("shipment.json").getFile();
        ParseJson array = new ParseJson(liveDataPath, new String[] {"BaseDateTime", "VesselName", "IMO", "CallSign", "Status", "timeDiff"});
        INDArray npArray = array.convertToNpArray();
        // Load model and make prediction
        String modelPath = new ClassPathResource("/trainedModels/AIS_model_3.h5").getFile().getPath();
//        String modelPath = PredictionClient.class.getResource("AIS_model_3.h5").getFile();
        loadModel model = new loadModel(modelPath);
        System.out.println(model.output(npArray));

    }



}
