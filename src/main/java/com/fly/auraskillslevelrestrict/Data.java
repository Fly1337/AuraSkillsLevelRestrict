package com.fly.auraskillslevelrestrict;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    public boolean place;
    public boolean breakBlock;


    public String placeMessage;
    public String breakMessage;
    public HashMap<String, Integer> levels = new HashMap<>();
    public ArrayList<String> deniedWorlds = new ArrayList<>();
}
