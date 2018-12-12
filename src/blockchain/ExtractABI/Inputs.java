package blockchain.ExtractABI;


public class Inputs {
    private String name;
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    private Components components;
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public Components getComponents(){
        return components;
    }
}
