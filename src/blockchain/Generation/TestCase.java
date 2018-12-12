package blockchain.Generation;

import java.util.List;

public class TestCase {
    //  it("The 1th Random Test for Function transferFrom", function() {
    //    return UranBank.deployed().then(function(instance) {
    //      return instance.transferFrom(accounts[0],accounts[0],0X303);
    //    })
    //  });

    private String FileName;//ContractName
    private String FunctionName;
    private int TestNum;
    private String testcontent;
    private List<String> TypeList;

    public TestCase(String functionName, int testNum, String testcontent) {
        FunctionName = functionName;
        TestNum = testNum;
        this.testcontent = testcontent;
    }

    public TestCase(String testcontent) {
        this.testcontent = testcontent;
        this.FileName = getFileNameFromContent(testcontent);
        this.FunctionName = getFunctionNameFromContent(testcontent);
    }

    public static String getFileNameFromContent(String testcontent){
        try {
            String[] temp1 = testcontent.split("return ");
            String[] temp2 = temp1[1].split(".deployed");
            return temp2[0].toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Wrong";
        }
    }

    public static String getFunctionNameFromContent(String testcontent){
        String[] temp1 = testcontent.split("Function ");
        String[] temp2 = temp1[1].split("\"");
        return temp2[0].toString();
    }

    public String getFunctionName() {
        return FunctionName;
    }

    public String getFileName() {
        return FileName;
    }

    public List<String> getTypeList() {
        return TypeList;
    }

    public int getTestNum() {
        return TestNum;
    }
    public int getTestNum(String testcontent){
        try {
            String[] temp1 = testcontent.split("The ");
            String[] temp2 = temp1[1].split("th");
            int tn = Integer.parseInt(temp2[0]);
            return tn;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getTestcontent() {
        return testcontent;
    }

    public void setFunctionName(String functionName) {
        FunctionName = functionName;
    }

    public void setTestNum(int testNum) {
        TestNum = testNum;
    }

    public void setTestcontent(String testcontent) {
        this.testcontent = testcontent;
    }

    @Override
    public String toString(){
        return testcontent;
    }
}
