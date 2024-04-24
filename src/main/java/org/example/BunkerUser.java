package org.example;

import java.util.ArrayList;
import java.util.List;

public class BunkerUser {
    static String[] proffesions = {};
    static String[] description = {};
    String userName;
    boolean isAlive;
    long  tgId;
    int id;
    int mеssageId;
    int messageLsId;
    int[] bio = new int[3];
    int proffesion;
    List<Integer> health = new ArrayList<>();
    int hobby;
    int phobia;
    List<Integer> additional = new ArrayList<>();
    List<Integer> inventory = new ArrayList<>();
    int card;
    int bodyType;
    int trait;
    boolean[] isOpen = new boolean[9];
    BunkerUser(int id, long tgId,String name){
        this.id = id;
        this.tgId = tgId;
        this.isAlive = true;
        this.userName = name;
        generateFirst();
        generateProffesion();
        generateHealth();
        generatehobby();
        generatePhobia();
        generateAdditional();
        generateInventory();
        generateCard();
        generateBodyType();
        generateTrait();
    }
    public void generateCur(int curId){
        switch (curId){
            case 1:
                generateFirst();
                break;
            case 2:
                generateProffesion();
                break;
            case 3:
                health.clear();
                generateHealth();
                break;
            case 4:
                generatehobby();
                break;
            case 5:
                generatePhobia();
                break;
            case 6:
                generateBodyType();
                break;
            case 7:
                generateTrait();
                break;
            case 8:
                generateAdditional();
                break;
            case 9:
                inventory.clear();
                generateInventory();
                break;

        }
    }
    public void changeCur(int curId, int num){
        switch (curId){
            case 1:
                bio[2] = num;
                break;
            case 2:
                proffesion = num;
                break;
            case 3:
                health.clear();
                health.add(num);
                break;
            case 4:
                hobby = num;
                break;
            case 5:
                phobia = num;
                break;
            case 6:
                bodyType = num;
                break;
            case 7:
                trait = num;
                break;
            case 8:
                additional.clear();
                additional.add(num);
                break;
            case 9:
                inventory.clear();
                inventory.add(num);
                break;

        }
    }
    public void addCur(int curId, int num){
        if (curId == 0){
            health.add(num);
        }else if (curId == 1){
            inventory.add(num);
        }else {
            additional.add(num);
        }
    }
    public void generateFirst(){
        this.bio[1]= random(140,200);
        int ageRand = random(1,7);
        if (ageRand == 1){
            this.bio[0] = random(10,16);
        }else if (ageRand == 7){
           this.bio[0] = random(55,80);
        }else {
            this.bio[0] = random(16,54);
        }
        this.bio[2] = random(0,1);
    }
    public void generateProffesion(){
        this.proffesion = random(0,bunkerData.profSize()-1);
    }
    public void generateHealth(){
        this.health.add(random(0,bunkerData.healthSize()-1));
    }
    public void generatehobby(){
        this.hobby = random(0,bunkerData.hobbySize()-1);
    }
    public void generatePhobia(){
        this.phobia = random(0,bunkerData.phobiaSize()-1);
    }
    public void generateAdditional(){this.additional.add(random(0,bunkerData.additSize()-1));}
    public void generateInventory(){
        this.inventory.add(random(0,bunkerData.inventorySize()-1));
    }
    public void generateCard (){
        this.card = random(0,bunkerData.cardSize()-1);
    }
    public void generateBodyType(){
        this.bodyType = random(0,bunkerData.bodyTypeSize()-1);
    }
    public void generateTrait(){
        this.trait = random(0,bunkerData.traitSize()-1);
    }
    public String printBio (){
        return bio[0]+"/"+bio[1]+"/"+((bio[2]==0)?("муж"):("жен"));
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
    public static void set(int id, int num){
        switch (id){
            case 1:

        }
    }
}
