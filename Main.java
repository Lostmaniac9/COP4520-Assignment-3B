
// create an array 8x60 that stores the temps that each thread records
// when any thread writes a value, they potentially store it in a separate area where they store the five highest and lowest values
// use new github link to find five highest and lowest values
// there needs to be a command algorithm to sweep the array and grab the needed values for the output
// a ninth thread is out of the question, but an existing thread could handle this as the "command thread"
// this runs the small risk of writes occuring during reading but this can be solved with a small delay before caluculation after the final minute mark
//

import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Recorder implements Runnable
{
  private boolean[][] temperatureHistory;
  boolean command;
  int column;
  
  // contructor
  public Recorder(int[][] temperatureHistory, boolean command, int column)
  {
    this.temperatureHistory = temperatureHistory;
    this.command = command;
    this.column = column;
  }
  
  public void run()
  {
    try
    {
      // wait one minute
      // generate random number
      // add random number to slot in array at column value
      // increment counter
      // at 60, if command, calculate needed values after a second or two
      // use github algorithm to grab biggest values
      // use inchworm method to find biggest ten minute interval and ship to another array
      // the value shipped can be a counter that starts an INT_MIN and get overwritten whenever a bigger value is found
      
    }
    catch (Exception e)
    {
      System.out.println("Exception has occured" + e);
    }
    
    return;
  }
}


// main class
class Main
{
  public static void main(String args[])
  {
    int[][] temperatureHistory = new int[8][60];

    Thread recorder1 = new Thread(new Recorder(temperatureHistory, true, 1));
    Thread recorder2 = new Thread(new Recorder(temperatureHistory, false, 2));
    Thread recorder3 = new Thread(new Recorder(temperatureHistory, false, 3));
    Thread recorder4 = new Thread(new Recorder(temperatureHistory, false, 4));
    Thread recorder5 = new Thread(new Recorder(temperatureHistory, false, 5));
    Thread recorder6 = new Thread(new Recorder(temperatureHistory, false, 6));
    Thread recorder7 = new Thread(new Recorder(temperatureHistory, false, 7));
    Thread recorder8 = new Thread(new Recorder(temperatureHistory, false, 8));
    
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
