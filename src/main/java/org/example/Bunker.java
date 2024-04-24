package org.example;

import java.util.ArrayList;
import java.util.List;

public class Bunker {
    int size;
    int place;
    int timeSpent;
    int countFood;
    int countWater;
    List<Integer> itemInBunker = new ArrayList<>();
    public void generateSize(){
        this.size = random(0,bunkerData.bunkerTypeSize()-1);
    }
    public void generateTime (){
        this.timeSpent = random(2,36);
    }
    public void generateFood(){
        this.countFood = random(0,24);

    }
    public void generateWater(){
        this.countWater = random(0,24);
    }
    public void generatePlace(){
        this.place = random(0,bunkerData.bunkerPlaceSize()-1);
    }
    public void generateItems(){
        int count = random(2,7);
        for (int i = 0; i < count; i++){
            itemInBunker.add(random(0,bunkerData.inventorySize()-1));
        }
    }
    Bunker(){
        generateFood();
        generateWater();
        generateSize();
        generateTime();
        generateItems();
    }
    public static int random(int min,int max){
        max -= min;
        int res = (int) (Math.random() * ++max) + min;
        if (res == max){
            return random(min,max);
        }else {
            return res;
        }
    }

}
