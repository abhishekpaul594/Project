package methods;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PageObjectGenerator {
    public static Map<String, String> pageObjects= new HashMap<String,String>();
    public static void getPageObject() throws FileNotFoundException {
        File directoryPath=new File(Constants.PAGEOBJECT_PATH);
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
            Map<String,String> elementObject=new YAMLReader(directoryPath+"/"+file).getYmlData();
            file=file.replace(".yaml","");
            for(Map.Entry<String,String> map:elementObject.entrySet())
            {
                pageObjects.put(file+"."+map.getKey(),map.getValue());
            }
        }

    }
}

