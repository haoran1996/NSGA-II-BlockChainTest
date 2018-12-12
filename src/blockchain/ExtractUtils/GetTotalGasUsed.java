package blockchain.ExtractUtils;

import java.io.*;

public class GetTotalGasUsed {
	
	 public static String filepath;//F:\\blockchain\\metacoin
	 /**
	  * 
	  * @param filepath
	  * @return TotalGas
	  */
	 public static long GetTotalGas(String filepath) {
		  StringBuffer sb = new StringBuffer();
		  sb.append(filepath);
		  sb.append("\\GasInfo.txt");
		  String GasInfoPath = sb.toString();
		  BufferedReader br=null;
		  long TotalGas = 0;
		  try {
		   InputStreamReader isr = new InputStreamReader(new FileInputStream(GasInfoPath));
		   br=new BufferedReader(isr);     
		   String line = null;        
		   while((line=br.readLine())!= null){   
			   if(line.contains("Gas usage")){
				   String[] temp = line.split(" ");
//				   for(int i=0; i<temp.length; i++){
//					   System.out.println(i + temp[i]);
//				   }
				   String gas = temp[4].toString();
				   try {
					    long gasused = Long.parseLong(gas);
//					    System.out.println(gasused);
					    TotalGas = TotalGas + gasused; 
					} catch (NumberFormatException e) {
					    e.printStackTrace();
					}
			   }
		   }    
		   br.close();
		   isr.close();
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return TotalGas;
		 }
}
