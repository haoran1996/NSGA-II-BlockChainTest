package blockchain.Generation;

import NSGA.datastructure.Chromosome;
import blockchain.CMDRedirect.Main;
import blockchain.JDBC.MysqlUtil;
import org.junit.Test;
import org.moeaframework.problem.DTLZ.BCdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 将生成的测试用例集写入到正确的文件
 */
public class WriteUtil {


    public static void main(String[] args){
        try {
            Main.ProjectPath = "F:\\blockchain\\Test20181124\\UranBank";
            Main.DirPath = "F:\\blockchain\\Test20181124\\UranBank\\test";
            List<BCdata> listbcdata = MysqlUtil.GetBCdataFromTable("UranBank");
            BCdata bcdata = listbcdata.get(0);
            Chromosome chromosome = new Chromosome(bcdata);
            List<TestCase> testCases = chromosome.getTestCases();
            List<TestFile> testFiles = ClassifyTestCases(testCases);
            for(int i = 0; i < testFiles.size(); i++){
                WriteFileFromTestFile(testFiles.get(i));
                System.out.println("生成:testName = " + testFiles.get(i).getFileName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     * @param path
     * @param content
     */
    public static void WriteToFile(String path, String content){
        try {
            File dir = new File(Main.DirPath);
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(path);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取写文件的路径
     * @param JsonFile
     * @return
     */
    public  static String GetWritePath(File JsonFile){
        //JsonFile = "F:\\blockchain\\SmartContrat\\metacoin\\build\\contracts\\**.json"
        StringBuffer sb = new StringBuffer();
        String JsonName = JsonFile.getName();
        String file_name = JsonName.substring(0, JsonName.lastIndexOf("."));
        String ContractPath = JsonFile.getParentFile().getParentFile().getParentFile().getPath();
        sb.append(ContractPath);
        sb.append("\\test");
        Main.DirPath = sb.toString();
        sb.append("\\");
        sb.append(file_name);
        sb.append(".js");
        String WritePath = sb.toString();
        sb = null;
        return WritePath;
    }

    /**
     * 清空文件内容
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TestFile> ClassifyTestCases(List<TestCase> testCaseList){
        List<TestFile> TestFileList = new ArrayList<>();
        for(int i = 0; i < testCaseList.size(); i++){
            boolean bFound = false;
            TestCase tc = testCaseList.get(i);
            String FileName = tc.getFileName();
            for(int j = 0; j < TestFileList.size(); j++){
                if(TestFileList.get(j).getFileName().equals(FileName)){
                    TestFileList.get(j).addTestCase(tc);
                    bFound = true;
                    break;
                }
            }
            if(!bFound){
                TestFile tf = new TestFile(FileName);
                tf.addTestCase(tc);
                TestFileList.add(tf);
            }
        }
        return TestFileList;
    }

    public static String getWritePathTestFile(TestFile testFile){
        StringBuffer sb = new StringBuffer();
        sb.append(Main.ProjectPath);
        sb.append("\\test\\");
        sb.append(testFile.getFileName());
        sb.append(".js");
        return sb.toString();
    }

    public static void WriteFileFromTestFile(TestFile testFile){
        String WritePath = getWritePathTestFile(testFile);
        String TotalString = TestFile.getTotalString(testFile);
        WriteToFile(WritePath,TotalString);
    }

    /**
     * 删除文件
     *
     * @param pathname
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String pathname){
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
            System.out.println("文件已经被成功删除");
        }
        return result;
    }
}
