package org.example;

public class test {
    public static void main(String[] args) {
      String [] data = bunkerData.inventor;
      for (int i = 0; i < data.length;i++){
          System.out.print(i + " " + data[i] + ";   ");
          if (i%5 == 0){
              System.out.println();
          }
      }
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
    }
}
