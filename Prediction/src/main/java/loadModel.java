import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.IOException;

public class loadModel {
    MultiLayerNetwork model;

    public loadModel(String modelPath) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
        model = KerasModelImport.importKerasSequentialModelAndWeights(modelPath);
    }

    public INDArray output(INDArray array){
        INDArray results;
        results = model.output(array);
        return results;
    }

}
