package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class FileReader {

    public Profile getDataFromFile(File file){
        FileInputStream in = null;
        StringBuilder result = new StringBuilder();
        try {
            in = new FileInputStream(file.getPath());
            int c;
            while ((c = in.read()) != -1) {
                result.append((char) c);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(result);
        Map<String, String> keyValueMap = Arrays.stream(result.toString().split("\n"))
                .map(kv -> kv.split(": "))
                .filter(kvArray -> kvArray.length == 2)
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
        keyValueMap.replaceAll((k,v)->v.replaceAll("\\p{Cntrl}", ""));
        /* we use a newline as the delimiter to divide the input string into an array of key-value pairs.
        Then, we further divide each pair using the equals symbol (=) by using the map procedure.
        Finally, we remove any pairings that do not include exactly two elements
        and compile the remaining pairs into a Map with associated keys and values.
         */
        Profile profileFromData = new Profile();
        profileFromData.setAge(Integer.parseInt(keyValueMap.get("Age")));
        profileFromData.setName(keyValueMap.get("Name"));
        profileFromData.setEmail(keyValueMap.get("Email"));
        profileFromData.setPhone(Long.parseLong(keyValueMap.get("Phone")));
        return profileFromData;
    }
}
