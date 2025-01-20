import java.io.*;
public class Timer extends Thread {
  Timed timed;
  int interval;
  public Timer(Timed vartimed,int varinterval){
    timed=vartimed;
    interval=varinterval;
    setDaemon(true);
  }
  
  public void run(){
    try{
      while(true){
        sleep(interval);
        timed.timeElapsed ();
      }
    }catch(InterruptedException iex){}
  }
}