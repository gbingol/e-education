import java.util.*;
import java.io.*;
public class MHTable {
//This class is very useful cause it stores all values to the specified rows*columns matrice
//and stores them in a file and read back in the format of MHCells
//and this format gives us the flexibility of using MHCells class' functions
/********************************IMPORTANT NOTE ***************************
All methods first were designed to be static but then it turns out that if methods
are static then multiple users share the same MHTable, as a result of this
if one takes his/her values then others meaninglessly seems to take theirs .
In order to avoid this, static behaviour isn't employed in.*/
//
  public MHCells mhcells[][];
  public int rows,columns;
  public String cellnote;
  public MHTable(int rows,int columns){
  //constructs a table with specified rows and columns
    this.columns=columns;this.rows=rows;
    mhcells=new MHCells[rows][columns];
    for(int j=0;j<rows;j++){
      for(int k=0;k<columns;k++){
        mhcells[j][k]=new MHCells("");
        mhcells[j][k].setNote("");
      }
    }
  }
  public void setCellValue(int varrow,int varcolumn,String varvalue){
    mhcells[varrow][varcolumn].setValue(varvalue);
  }
  public String getCellValue(int varrow,int varcolumn){
    return mhcells[varrow][varcolumn].getValue();
  }
  public void setCellNote(int varrow,int varcolumn,String varvalue){
    mhcells[varrow][varcolumn].setNote(varvalue);
  }
  public String getCellNote(int varrow,int varcolumn){
    return mhcells[varrow][varcolumn].getNote ();
  }
  public void readFile(String filename) throws FileNotExists{
    try{
      ObjectInputStream objin=new ObjectInputStream(new FileInputStream(filename+".dat"));
      MHCells[][]  newValue=(MHCells[][])objin.readObject();
      //Lets populate mhcells array
      for(int r=0;r<rows;r++){
        for(int c=0;c<columns;c++){
          mhcells[r][c].setValue(newValue[r][c].getValue());
          mhcells[r][c].setNote(newValue[r][c].getNote());
        }//for(int c=0
      }//for(int r=0
      objin.close ();
    }catch(Exception e){}
  }
  public void writeFile(String filename){
    try{
    ObjectOutputStream obj=new ObjectOutputStream(new FileOutputStream(filename+".dat"));
    obj.writeObject(mhcells);
    obj.close ();
    }catch(Exception e){}
  }
}
class MHCells implements Serializable{
  public String value;
  public String note;
  public MHCells(String value){
    this.value=value;
  }
  public String getValue(){
    return value;
  }
  public void setValue(String s){
    value=s;
  }
  public void setNote(String n){
    note=n;
  }
  public String getNote(){
    return note;
  }
}
class FileNotExists extends IOException{
  public FileNotExists(){}
  public FileNotExists(String s){
    super(s);
  }
}