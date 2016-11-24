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

    List<String> techValues = new ArrayList<>();
    public static final int JAVA = 1;
    public static final int DOT_NET = 2;
    public static final int BI = 3;
    public static final int ANGULAR = 4;
    public static final int NODE = 4;

    public Technology(){
        techValues.add(0,"Select Technology");
        techValues.add(1,"Java");
        techValues.add(2,".Net");
        techValues.add(3,"Business Intelligence");
        techValues.add(4,"Angular");
        techValues.add(5,"Node");
    }

    public String getTechnology(int tech){
        return techValues.get(tech);
    }

    public List<String> getTechList(){
        return techValues;
    }

}
