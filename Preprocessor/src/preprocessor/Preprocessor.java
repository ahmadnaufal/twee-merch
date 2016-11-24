package preprocessor;

import IndonesianNLP.IndonesianSentenceFormalization;
import IndonesianNLP.IndonesianStemmer;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author husni
 */
public class Preprocessor {
    public static void main(String[] args) {
        ArrayList<Instance> data = new ArrayList<>();
        try {
            // Read data into csv
            data = (ArrayList<Instance>) readCSVToList("dataset.csv");
            
            // Preprocessing
            normalizeData(data);
            stemData(data);
            
            // Output
            outputDataToCSV(data, "data-preprocessed");

            
        } catch (IOException ex) {
            Logger.getLogger(Preprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param file filename to be read.
     * @return List of Instance
     * @throws java.io.FileNotFoundException 
     * @throws java.io.IOException 
     */
    public static List<Instance> readCSVToList(String file) throws FileNotFoundException, IOException {
        List<Instance> data = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;
        
        while ((nextLine = reader.readNext()) != null) {
            Instance instance = new Instance(nextLine[1], Integer.parseInt(nextLine[3]));
            data.add(instance);
        }
        
        return data;
    }
    
    public static void stemData(List<Instance> data) {
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
    public static void normalizeData(List<Instance> data) {
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
    public static void outputDataToCSV(List<Instance> data, String filename) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filename + ".csv"));
        for (Instance instance : data) {
            String[] instanceString = {instance.getTweet(), Integer.toHexString(instance.getClassNum())};
            writer.writeNext(instanceString);
        }
    }
    
    public static String removeCurrency(String sentence) {
        sentence = sentence.toLowerCase();
        String removedCurrency = sentence.replaceAll("(harga|rp|idr)(\\.|,| )*(\\d)(\\d+|\\.|,|ribu|ratus|juta|jt|k|rb| )*", " nozharga ");
        return removedCurrency;
    }
}