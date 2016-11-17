package com.ggktech.bookmydrive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajan.kali on 11/16/2016.
 * platform of drive
 */
public class Technology {

    private Map<Integer,String> technologies;
    public static final int JAVA = 1;
    public static final int DOT_NET = 2;
    public static final int BI = 3;
    public static final int ANGULAR = 4;
    public static final int NODE = 4;

    public Technology(){
        technologies = new HashMap<>();
        technologies.put(1,"Java");
        technologies.put(2,".Net");
        technologies.put(3,"Business Intelligence");
        technologies.put(4,"Angular");
        technologies.put(5,"Node");
    }

    public String getTechnology(int tech){
        return technologies.get(tech);
    }

    public List<String> getTechList(){
        List<String> techValues = new ArrayList<>(technologies.values());
        techValues.add(0,"Select Technology");
        return techValues;
    }

}
