package blockchain.ExtractABI;

import java.io.File;
import java.util.List;

public class JsonFile {

    private File file;
    private List<Function> functionList;
    private int[] TCNumforEachFun;
    private int funNum;
    private int TotalTestCaseNum;

    public JsonFile(File file, List<Function> functionList) {
        this.file = file;
        this.functionList = functionList;
        this.funNum = functionList.size();
        this.TCNumforEachFun = new int[funNum];
    }

    public File getFile() {
        return file;
    }

    public List<Function> getFunctionList() {
        return functionList;
    }

    public int getTCNumforEachFun(int i) {
        return TCNumforEachFun[i];
    }

    public int getFunNum() {
        return funNum;
    }

    public void setFunNum(List<Function> functionList) {
        this.funNum = functionList.size();
    }

    public void setTCNumforEachFun(int funNum) {
        this.TCNumforEachFun = new int[funNum+1];
    }


    public void setFile(File file) {
        this.file = file;
    }

    public void setFunctionList(List<Function> functionList) {
        this.functionList = functionList;
    }

    public void setTCNumforEachFun(int i, int TCNum) {
        try {
            this.TCNumforEachFun[i]  = TCNum;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalTestCaseNum() {
        TotalTestCaseNum = 0;
        for(int i=0; i<TCNumforEachFun.length; i++){
            TotalTestCaseNum = TotalTestCaseNum + TCNumforEachFun[i];
        }
        return TotalTestCaseNum;
    }
}
