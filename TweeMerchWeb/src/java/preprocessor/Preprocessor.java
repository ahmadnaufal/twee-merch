package preprocessor;

import IndonesianNLP.IndonesianSentenceFormalization;
import IndonesianNLP.IndonesianStemmer;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;

/**
 *
 * @author husni
 */
public class Preprocessor {
    public static void main(String[] args) {
        ArrayList<MyInstance> data = new ArrayList<>();
        try {
            // Read data into csv
            data = (ArrayList<MyInstance>) readCSVToList("dataset.csv");
            
            // Preprocessing
            normalizeData(data);
            stemData(data);
            
            // Output
            outputDataToCSV(data, "data-preprocessed");
//            outputDataToArff("data-preprocessed.csv", "dataset.arff");

            
        } catch (IOException ex) {
            Logger.getLogger(Preprocessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            
        }
    }
    
    /**
     * 
     * @param file filename to be read.
     * @return List of Instance
     * @throws java.io.FileNotFoundException 
     * @throws java.io.IOException 
     */
    public static List<MyInstance> readCSVToList(String file) throws FileNotFoundException, IOException {
        List<MyInstance> data = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;
        
        while ((nextLine = reader.readNext()) != null) {
            MyInstance instance = new MyInstance(nextLine[1], Integer.parseInt(nextLine[3]));
            data.add(instance);
        }
        
        return data;
    }
    
    public static void stemData(List<MyInstance> data) {
        IndonesianStemmer stemmer = new IndonesianStemmer();
        for (int i = 0; i < data.size(); i++) {
            String stemmedSentence = stemmer.stemSentence(data.get(i).getTweet());
            data.get(i).setTweet(stemmedSentence);
        }
    }
    
    /**
     * Normalize and remove stop words
     * @param data list of Instance
     */
    public static void normalizeData(List<MyInstance> data) {
        IndonesianSentenceFormalization sentenceFormalization = new IndonesianSentenceFormalization();
        sentenceFormalization.initStopword();
        
        String normalizedSentence;
        for (int i = 0; i < data.size(); i++) {
            normalizedSentence = removeCurrency(data.get(i).getTweet());
            normalizedSentence = sentenceFormalization.normalizeSentence(normalizedSentence);
            normalizedSentence = sentenceFormalization.deleteStopword(normalizedSentence);
            
            data.get(i).setTweet(normalizedSentence);
        }
    }
    
    /**
     * Output list of Instance to .csv file.
     * @param data
     * @param filename
     * @throws java.io.IOException
     */
    public static void outputDataToCSV(List<MyInstance> data, String filename) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filename + ".csv"));
        String[] header = {"tweet", "class"};
        writer.writeNext(header);
        for (MyInstance instance : data) {
            String[] instanceString = {instance.getTweet(), Integer.toHexString(instance.getClassNum())};
            writer.writeNext(instanceString);
        }
    }
    
    public static void outputDataToArff(String csvdata, String filename) throws IOException, Exception {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(csvdata));
        Instances data = loader.getDataSet();
        
        NominalToString filter1 = new NominalToString();
        filter1.setInputFormat(data);
        data = Filter.useFilter(data, filter1);

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(filename));
        saver.writeBatch();
    }
    
    public static String removeCurrency(String sentence) {
        sentence = sentence.toLowerCase();
        String removedCurrency = sentence.replaceAll("(harga|rp|idr)(\\.|,| )*(\\d)(\\d+|\\.|,|ribu|ratus|juta|jt|k|rb| )*", " nozharga ");
        return removedCurrency;
    }
    
    
    public static String preProcessString(String data) {
        IndonesianSentenceFormalization sentenceFormalization = new IndonesianSentenceFormalization();
        sentenceFormalization.initStopword();
        
        String preprocessed = data;
        preprocessed = removeCurrency(preprocessed);
        preprocessed = sentenceFormalization.normalizeSentence(preprocessed);
        preprocessed = sentenceFormalization.deleteStopword(preprocessed);
        
        IndonesianStemmer stemmer = new IndonesianStemmer();
        preprocessed = stemmer.stemSentence(preprocessed);
        
        return preprocessed;
    }
}