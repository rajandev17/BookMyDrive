package com.ggktech.bookmydrive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajan.kali on 11/16/2016.
 * status of drive
 */
public class Status {
    private Map<Integer,String> statusList;

    public static final int CREATED = 1;
    public static final int PENDING = 2;
    public static final int COMPLETED = 3;

    public Status(){
        statusList = new HashMap<>();
        statusList.put(1,"Created");
        statusList.put(2,"Pending");
        statusList.put(3,"Completed");
    }

    public String getStatus(int tech){
        return statusList.get(tech);
    }

    public List<String> getStatusList(){
        List<String> statusValues = new ArrayList<>(statusList.values());
        statusValues.add(0,"Select Status");
        return statusValues;
    }
}
