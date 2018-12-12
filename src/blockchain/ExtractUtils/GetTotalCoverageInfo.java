package blockchain.ExtractUtils;

import java.io.*;

public class GetTotalCoverageInfo {
	
	public static String filepath;//F:\\blockchain\\metacoin
	/**
	 * 
	 * @param filepath
	 * @return All files coverage
	 */
	public static double GetTotalCoverage(String filepath) {
		  StringBuffer sb = new StringBuffer();
		  sb.append(filepath);
		  sb.append("\\CovInfo.txt");
		  String CovInfoPath = sb.toString();
		  BufferedReader br=null;
		  double TotalStmtsCoverage = 0;
		  double TotalBranchCoverage = 0;
		  double TotalFuncsCoverage = 0;
		  double TotalLinesCoverage = 0;
		  try {
		   InputStreamReader isr = new InputStreamReader(new FileInputStream(CovInfoPath));
		   br=new BufferedReader(isr);     
		   String line = null;        
		   while((line=br.readLine())!= null){   
			   if(line.contains("All files")){
				   String[] temp = line.split("\\|");
	//				   for(int i=0; i<temp.length; i++){
	//					   System.out.println(i + temp[i]);
	//				   }
	//				   System.out.println(temp[2].trim());
				   String StmtsCoverage = temp[1].trim().toString();
				   String BranchCoverage = temp[2].trim().toString();
				   String FuncsCoverage = temp[3].trim().toString();
				   String LinesCoverage = temp[2].trim().toString();
				   try {
					    TotalStmtsCoverage = Double.parseDouble(StmtsCoverage);
					    TotalBranchCoverage = Double.parseDouble(BranchCoverage);
					    TotalFuncsCoverage = Double.parseDouble(FuncsCoverage);
					    TotalLinesCoverage = Double.parseDouble(LinesCoverage);
					} catch (NumberFormatException e) {
					    e.printStackTrace();
					}
				    System.out.println("TotalStmtsCoverage = " + TotalStmtsCoverage);
				    System.out.println("TotalBranchCoverage = " + TotalBranchCoverage);
				    System.out.println("TotalFuncsCoverage = " + TotalFuncsCoverage);
				    System.out.println("TotalLinesCoverage = " + TotalLinesCoverage);
			   }
		   }    
		    br.close();
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return TotalBranchCoverage;
		 }
}
