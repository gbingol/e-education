import java.io.*;
public abstract class Questions{
  protected SuOzellikleri su=new SuOzellikleri();
  protected HavaOzellikleri hava=new HavaOzellikleri();
  protected double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25};
  private double QuesRange=Double.valueOf(SystemSettings.getCommandParameter ("ANSWER_VALID_RANGE").trim()).doubleValue ();
  protected final double upperRange=QuesRange,lowerRange=QuesRange ;//estimates the lower and upper range for the answer
  public double results[]; //computed results of the variables
  public boolean IsValuesGiven;//a flag to detect whether a value has been given or not
  public String username;//username of the student
  public double[] values; //number of the variables given to student
  public Students students;
  protected MHTable mhtable;
  public Questions(){
    IsValuesGiven=false;
  }
  public abstract void print(PrintWriter pw);
  public abstract void printRandomValues(PrintWriter pw);
  public void setUsername(Students s){
      students=s;
      username=s.getUsername();
      mhtable=students.getMHTable();
  }
  protected void updateCell(int varrow,int varcol, int newValue){
    if(mhtable.getCellValue(varrow,varcol).equals("")){
      mhtable.setCellValue(varrow,varcol,String.valueOf(newValue));
    }else{
      int temp=Integer.valueOf(mhtable.getCellValue(varrow,varcol)).intValue();
      newValue=newValue+temp;
      mhtable.setCellValue(varrow,varcol,String.valueOf(newValue));
    }
  }
  protected abstract void CalculateWriteResults();
  public abstract Questions getQuestion();
  public abstract void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw);
}



