package preprocessor;

import IndonesianNLP.IndonesianSentenceFormalization;
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
            data = (ArrayList<Instance>) readCSVToList("dataset.csv");
            
            normalizeData(data);
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
    
    /**
     * Normalize list of Instance
     * @param data list of Instance
     */
    public static void normalizeData(List<Instance> data) {
        IndonesianSentenceFormalization sentenceFormalization = new IndonesianSentenceFormalization();
        for (int i = 0; i < data.size(); i++) {
            String normalizedSentence = sentenceFormalization.normalizeSentence(data.get(i).getTweet());
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
}