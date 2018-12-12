package blockchain.ExtractABI;
import java.util.List;

public class Function {

    private String name;
    private String type;
    private List<Inputs> inputs;
    private int TestCaseNum;

    public int getTestCaseNum() {
        return TestCaseNum;
    }

    public void setTestCaseNum(int testCaseNum) {
        TestCaseNum = testCaseNum;
    }

    public void setOutputs(List<Outputs> outputs) {
        this.outputs = outputs;
    }

    public List<Outputs> getOutputs() {
        return outputs;
    }

    private List<Outputs> outputs;
    private boolean payable;
    private boolean constant;

    public void setInputs(List<Inputs> inputs) {
        this.inputs = inputs;
    }

    public List<Inputs> getInputs() {
        return inputs;
    }

    private String stateMutability;
    public String getName(){
        return name;
    }
    public void setName(String name){ this.name = name;}
    public String getType(){
        return type;
    }


    public boolean isPayable() {
        return payable;
    }

    public boolean isConstant() {
        return constant;
    }

    public String getStateMutability() {
        return stateMutability;
    }
    public void setType(String type) {
        this.type = type;
    }



    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public void setStateMutability(String stateMutability) {
        this.stateMutability = stateMutability;
    }

}
