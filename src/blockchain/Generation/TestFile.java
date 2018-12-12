package blockchain.Generation;

import java.util.ArrayList;
import java.util.List;

public class TestFile {
    private String FileName;
    private String TestHead;
    private List<TestCase> TestCases;
    private String TestEnd = "\n});";
    private int TestNum;

    public TestFile(String fileName) {
        FileName = fileName;
        TestHead = getTestHead(fileName);
        TestCases = new ArrayList<>();
    }

    public TestFile(String fileName, String testHead, List<TestCase> testCases) {
        FileName = fileName;
        TestHead = testHead;
        TestCases = testCases;
    }

    public TestFile(List<TestCase> testCases){
        TestCase tc1 = testCases.get(0);
        String fileName = tc1.getFunctionName();
        new TestFile(fileName,getTestHead(fileName),testCases);
        setTestNum(testCases.size());
    }

    public TestFile(String fileName, String testHead, List<TestCase> testCases, String testEnd, int testNum) {
        FileName = fileName;
        TestHead = testHead;
        TestCases = testCases;
        TestEnd = testEnd;
        TestNum = testNum;
    }

    public String getFileName() { return FileName; }

    public String getTestHead() {
        return TestHead;
    }

    public int getTestNum() {
        return TestNum;
    }

    public void setTestCases(List<TestCase> testCases) {
        TestCases = testCases;
    }

    public void setTestNum(int testNum) {
        TestNum = testNum;
    }
    public void setTestNum(){
        TestNum = TestCases.size();
    }

    public List<TestCase> getTestCases() {
        return TestCases;
    }

    public String getTestEnd() {
        return TestEnd;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public void setTestHead(String testHead) {
        TestHead = testHead;
    }

    public void addTestCase(TestCase testCase) {
        TestCases.add(testCase);
    }

    public void addTestCase(int index, TestCase testcase){
        TestCases.add(index,testcase);
    }

    public void setTestEnd(String testEnd) {
        TestEnd = testEnd;
    }

    public TestCase getTestCase(int index){
        return TestCases.get(index);
    }

    @Override
     public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(getTestHead());
        for(int i = 0; i < TestCases.size(); i++){
            sb.append(TestCases.get(i).toString());
        }
        sb.append(TestEnd);
        return sb.toString();
     }

     public static String getTotalString(TestFile testFile){
        return testFile.toString();
     }

     public static String getTestcasesString(List<TestCase> testCases){
         StringBuffer sb = new StringBuffer();
         for(int i = 0; i < testCases.size(); i++){
             sb.append(testCases.get(i).toString());
         }
         return sb.toString();
     }

     public static String getTestHead(String fileName){
         StringBuffer br = new StringBuffer();
         br.append("var ");
         br.append(fileName);
         br.append(" = artifacts.require(\"");
         br.append(fileName);
         br.append("\");\n");
         br.append("contract(\'");
         br.append(fileName);
         br.append("\',function(accounts) {\n\n");
        return br.toString();

     }




}
