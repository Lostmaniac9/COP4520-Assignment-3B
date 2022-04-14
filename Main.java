
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

class Recorder implements Runnable
{
  public static final int INT_MIN = Integer.MIN_VALUE;
  public static final int INT_MAX = Integer.MAX_VALUE;
  
  private int[][] tempHis;
  private int[] tempDiff;
  boolean command;
  int column;
  public static volatile boolean flag;
  
  // contructor
  public Recorder(int[][] tempHis, int[] tempDiff, boolean command, int column, boolean flag)
  {
    this.tempHis = tempHis;
    this.tempDiff = tempDiff;
    this.command = command;
    this.column = column;
    this.flag = flag;
  }
  
  public void run()
  {
    try
    {
      // this program will run infinitely because the description gave no instructions to conclude it
      while(true)
      {
        // one thread is designated "command" and it will lead the others in their action and perform some special calculations
        // command thread will set a flag to true so that calculating can begin for all threads at once
        if(command)
          flag = true;
        
        // non command threads will get here and busy wait until command gets to the above line
        while(!flag){}
        
        for(int i = 0; i < 60; i++)
        {
          // wait a minute before collecting a value
          Thread.sleep(60000);
          // generate the random temperature
          // this is the linearization point of the threads
          tempHis[column][i] = ThreadLocalRandom.current().nextInt(-100, 71);
        }
        
        // one thread is designated "command" and it does the caluculation of the five largest and smallest values
        int h1 = INT_MIN, h2 = INT_MIN, h3 = INT_MIN, h4 = INT_MIN, h5 = INT_MIN;
        int l1 = INT_MAX, l2 = INT_MAX, l3 = INT_MAX, l4 = INT_MAX, l5 = INT_MAX;
        if(command)
        {
          // algorithm from GitHub link in ReadMe to find five highest and lowest values without sorting
          for(int i = 0; i < 8; i++)
          {
            for(int j = 0; j < 60; j++)
            {
              // five highest values
              if(tempHis[i][j] > h1) {
                h5 = h4; h4 = h3; h3 = h2; h2 = h1; h1 = tempHis[i][j];
              }
              else if(tempHis[i][j] > h2) {
                h5 = h4; h4 = h3; h3 = h2; h2 = tempHis[i][j];
              }
              else if(tempHis[i][j] > h3) {
                h5 = h4; h4 = h3; h3 = tempHis[i][j];
              }
              else if(tempHis[i][j] > h4) {
                h5 = h4; h4 = tempHis[i][j];
              }
              else if(tempHis[i][j] > h5) {
                h5 = tempHis[i][j];
              }
              
              // five lowest values
              if(tempHis[i][j] < l1) {
                l5 = l4; l4 = l3; l3 = l2; l2 = l1; l1 = tempHis[i][j];
              }
              else if(tempHis[i][j] < l2) {
                l5 = l4; l4 = l3; l3 = l2; l2 = tempHis[i][j];
              }
              else if(tempHis[i][j] < l3) {
                l5 = l4; l4 = l3; l3 = tempHis[i][j];
              }
              else if(tempHis[i][j] < l4) {
                l5 = l4; l4 = tempHis[i][j];
              }
              else if(tempHis[i][j] < l5) {
                l5 = tempHis[i][j];
              }
            }
          }
        }
        
        // find largest temperature difference recorded by this thread and send it to array
        // my assumption from reading the description of the problem is that I am to take two values ten minutes apart
        // and compare their absolute difference, while ignoring the values in between these "bookend" values
        // and then simply select the largest difference
        int finalDiff = INT_MIN;
        for(int i = 10; i < 60; i++)
        {
          int diff = Math.abs(tempHis[column][i] - tempHis[column][i-10]);
          if(diff > finalDiff)
            finalDiff = diff;
        }
        // plug the result into the secondary array
        tempDiff[column] = finalDiff;
        
        // command thread will now select the highest value in this secondary array
        int highestDiff = INT_MIN;
        if(command)
        {
          for(int i = 0; i < 8; i++)
          {
            if(tempDiff[i] > highestDiff)
              highestDiff = tempDiff[i];
          }
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("Exception has occured" + e);
    }
    
    // as stated previously, the description gave no instructions for ending the program,
    // and so it will never reach this return line
    // additionally, there was also no instructions given for outputting the values collected,
    // and so they are not
    return;
  }
}


// main class
class Main
{
  public static final int INT_MIN = Integer.MIN_VALUE;
  public static final int INT_MAX = Integer.MAX_VALUE;
  
  public static void main(String args[])
  {
    int[][] temperatureHistory = new int[8][60];
    int[] tempDiff = new int[8];

    // I tried putting these in a loop but I got a weird error for doing it and
    // couldn't quite figure out what was wrong
    // it might have to do with how I create my thread objects, 
    // but if that's the problem then I have no idea how to avoid it 
    Thread recorder1 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 0, false));
    Thread recorder7 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 1, false));
    Thread recorder2 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 2, false));
    Thread recorder3 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 3, false));
    Thread recorder4 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 4, false));
    Thread recorder5 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 5, false));
    Thread recorder6 = new Thread(new Recorder(temperatureHistory, tempDiff, false, 6, false));
    Thread recorder8 = new Thread(new Recorder(temperatureHistory, tempDiff, true, 7, false));
    
    try
    {
      recorder1.start();
      recorder2.start();
      recorder3.start();
      recorder4.start();
      recorder5.start();
      recorder6.start();
      recorder7.start();
      recorder8.start();
      
      recorder1.join();
      recorder2.join();
      recorder3.join();
      recorder4.join();
      recorder5.join();
      recorder6.join();
      recorder7.join();
      recorder8.join();
      
    }
    catch (Exception e)
    {
      System.out.println("Exception has occured" + e);
    }
  }
}
