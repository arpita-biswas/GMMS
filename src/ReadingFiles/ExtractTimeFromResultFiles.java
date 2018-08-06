package ReadingFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExtractTimeFromResultFiles {

    private static BufferedReader in;

    public static void main(String args[]) throws IOException{
        String last="", line;
        String [] filenames = {"Uniform", "UniformOrdered", "Gaussian", "GaussianOrdered"};
        //File dir = new File("results_EF1L");
        File dir = new File("results_EF1L_GWF_2");
        dir.mkdirs();
        
        //for(int q=2; q<=2; q++){
            int q=0;
            for(int i=3; i<=5; i++){
                int n=i;      
                
                for(int j=i; j<=11; j++){
                    int m=j;               
                
                    File file = new File(dir, "result_GWF"+filenames[q]+"_"+n+"_"+m+".txt");
                    file.createNewFile();
                    in = new BufferedReader(new FileReader(file));
                    while ((line = in.readLine()) != null) {
                        last = line;
                    }
                    //System.out.println(last);
                    List<String> splitLast = Arrays.asList(last.split(","));
                    //System.out.println(n+","+m+","+filenames[q]+":"+splitLast.get(2));
                    System.out.println(returnAvgTime(splitLast));
                }
            }
        //}       
        
    }
    
    public static String returnAvgAlpha(List<String> splitLast){
        List<String> avg = Arrays.asList(splitLast.get(2).split("="));
        return avg.get(1);
    }
    
    public static String returnMinAlpha(List<String> splitLast){
        List<String> min = Arrays.asList(splitLast.get(1).split("="));
        return min.get(1);
    }
    
    public static String returnAvgTime(List<String> splitLast){
        List<String> time = Arrays.asList(splitLast.get(0).split("="));
        List<String> spaceTime = Arrays.asList(time.get(1).split(" "));
        return spaceTime.get(1);
    }
    
    public static String returnAvgTimeTaken(List<String> splitLast){
        List<String> time = Arrays.asList(splitLast.get(splitLast.size()-1).split(" "));
        //System.out.println(time);
        return time.get(5);
    }
}
