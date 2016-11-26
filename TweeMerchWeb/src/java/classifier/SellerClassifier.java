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

/**
 *
 * @author Ahmad
 */
public class SellerClassifier {
    
    private Classifier myClassifier;
    private Instances myInstances;
    
    private Instances loadData(String dataset) throws Exception {
        DataSource data = new DataSource(dataset);
        Instances instances = data.getDataSet();
        if (instances.classIndex() == -1) {
            instances.setClassIndex(instances.numAttributes() - 1);
        }
        
        return instances;
    }
    
    public void loadModel(String path) throws Exception {
        myClassifier = (Classifier) SerializationHelper.read(path);
    }
    
    private Instances startFeatureExtraction(Instances raw) throws Exception {
        StringToWordVector filter = new StringToWordVector();
        Tokenizer tokenizer = new WordTokenizer();
        filter.setTokenizer(tokenizer);
        filter.setInputFormat(raw);
        
        return Filter.useFilter(raw, filter);
    }
    
    public void buildModel(String dataset) {
        try {
            myInstances = startFeatureExtraction(loadData(dataset));
            System.out.println(myInstances);
            myClassifier = new RandomForest();
            
            // build the model
            myClassifier.buildClassifier(myInstances);
            SerializationHelper.write("model/seller_classify_model.model", myClassifier);
        } catch (Exception ex) {
            Logger.getLogger(SellerClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double classifyTweet(MyStatus tweet) {
        try {
            String processedTweet = Preprocessor.preProcessString(tweet.getTweet());
            Instance instance = readToInstance(processedTweet);
            System.out.println(instance);
            double tweetClass = myClassifier.classifyInstance(instance);
            
            tweet.setClassification((int) tweetClass);
                    
            return tweetClass;
        } catch (Exception ex) {
            Logger.getLogger(SellerClassifier.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
    }
    
    private Instance readToInstance(String str) throws Exception {
        File f = new File("buffer.arff");
        try (FileWriter fw = new FileWriter(f, false)) {
            fw.write("@relation buffer\n\n@attribute\ttweet\tstring\n@attribute\tclass\t{0,1}\n@data\n");
            fw.write("\"" + str + "\",1\n");
        }
        
        Instances instances = startFeatureExtraction(loadData("buffer.arff"));
        return instances.firstInstance();
    }
}
