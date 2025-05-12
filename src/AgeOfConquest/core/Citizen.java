package AgeOfConquest.core;

import AgeOfConquest.exceptions.MalformedException;

public class Citizen {
     private int idle;
     private int working;

     public void addCitizens(int n) {
          this.idle += n;
     }

     public void initialize(int idle, int working) {
          this.idle = idle;
          this.working = working;
     }



     public int getIdle(){
          return this.idle;
     }
     public int getWorking(){
          return this.working;
     }
     public Citizen() {
          this.idle = 10;
          this.working = 0;
     }

     public void setIdle(int x){
          this.idle = x;
     }

     public void setWorking(int x){
          this.working = x;
     }

     public Citizen(int newidle, int newworking){
          this.idle = newidle;
          this.working = newworking;
     }

     public int getpop(){
          return idle + working;
     }


     public void gain(int n) throws MalformedException {
          try {
               if (n>idle || n > 5){
                    throw new MalformedException();
               }
               this.idle -= n;
               this.working += n;
          } catch (Exception e) {
               System.err.println("Wrong number! " + e.getMessage());
               e.printStackTrace();
          }

     }
     public String fullString(){
          String s = this.idle + "%%" + this.working;
          return s;
     }

}
