package blockchain.ExtractABI;


public class Outputs {
    private String name;
    private String type;
    private Components components;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

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

