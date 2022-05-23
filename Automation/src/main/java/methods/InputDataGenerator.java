package methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public class InputDataGenerator {

    public static Map<String,Map<String,String>> inputDataSet=new HashMap<>();

    public static void getInputData() throws FileNotFoundException {
        String key="";
        Map<String,String> inputValue=new HashMap<>();
        File directoryPath=new File(Constants.INPUTDATA_PATH);
        FilenameFilter filenameFilter= new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".yaml"))
                    return true;
                else
                    return false;
            }
        };
        String filesList[] = directoryPath.list(filenameFilter);
        for(String file:filesList)
        {
            if(file.contains("environments"))
                continue;
            Map<String,Map<String,String>> rawInputData;
            rawInputData=new YAMLReader(directoryPath+"/"+file).getYmlInputDataSet();
            file=file.replace(".yaml","");
            for(Map.Entry<String,Map<String,String>> map:rawInputData.entrySet())
            {
                inputDataSet.put(file+"."+map.getKey(),map.getValue());
            }
        }
    }
}
