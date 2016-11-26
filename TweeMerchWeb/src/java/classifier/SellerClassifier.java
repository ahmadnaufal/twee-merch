/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import preprocessor.Preprocessor;

import java.util.logging.Level;
import java.util.logging.Logger;
import twitter.MyStatus;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.Tokenizer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.SerializationHelper;
import weka.filters.unsupervised.attribute.Standardize;

/**
 *
 * @author Ahmad
 */
public class SellerClassifier {
    
    private Classifier myClassifier;
    private Instances myInstances;
    private Filter myFilter;
    
    public static final String modelPath = "ref\\seller_classify_model.model";
    public static final String refPath = "ref\\seller_classify_filter.filter";
    
    private Instances loadData(String dataset) throws Exception {
        DataSource data = new DataSource(dataset);
        Instances instances = data.getDataSet();
        if (instances.classIndex() == -1) {
            instances.setClassIndex(instances.numAttributes() - 1);
        }
        
        return instances;
    }
    
    private void loadModelFile(String path) throws Exception {
        myClassifier = (Classifier) SerializationHelper.read(path);
    }
    
    private void loadFilterFile(String path) throws Exception {
        myFilter = (Filter) SerializationHelper.read(path);
    }
    
    private Instances startFeatureExtraction(Instances raw) throws Exception {
        myFilter = new StringToWordVector();
        myFilter.setInputFormat(raw);
        
        return Filter.useFilter(raw, myFilter);
    }
    
    public void rebuildModel(String dataset) {
        try {
            myInstances = startFeatureExtraction(loadData(dataset));
            myClassifier = new RandomForest();
            
            // build the model
            myClassifier.buildClassifier(myInstances);
            SerializationHelper.write(modelPath, myClassifier);
            SerializationHelper.write(refPath, myFilter);
        } catch (Exception ex) {
            Logger.getLogger(SellerClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double classifyTweet(MyStatus tweet) {
        try {
            String processedTweet = Preprocessor.preProcessString(tweet.getTweet());
            System.out.println(processedTweet);
            Instance instance = readToInstance(processedTweet);
            System.out.println(instance);
            double tweetClass = myClassifier.classifyInstance(instance);
                    
            return tweetClass;
        } catch (Exception ex) {
            Logger.getLogger(SellerClassifier.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
    }
    
    private Instance readToInstance(String str) throws Exception {
        File f = new File("buffer.arff");
        try (FileWriter fw = new FileWriter(f, false)) {
            fw.write("@relation buffer\n\n@attribute\ttweet\tstring\n@attribute\tclass\t{0,1}\n\n@data\n");
            fw.write("\"" + str + "\",1\n");
        }
        
        Instances instances = loadData("buffer.arff");
        
        System.out.println(instances);
        
        assert (instances != null);
        assert (myFilter != null);
        Instances ins = Filter.useFilter(instances, myFilter);
        
        return ins.firstInstance();
    }
    
    public void initModel(String dataset) throws Exception {
        File modelFile = new File(modelPath);
        if (modelFile.exists()) {
            loadModelFile(modelPath);
            loadFilterFile(refPath);
        } else {
            rebuildModel(dataset);
        }
    }
}
