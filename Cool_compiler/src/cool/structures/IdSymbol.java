package cool.structures;

public class IdSymbol extends Symbol{

    public boolean isGlobal;
    public Symbol typeSymbol;

    public IdSymbol(String name) {
        super(name);
    }

    public void setType(Symbol symbol) {
        this.typeSymbol = symbol;
    }

    public Symbol getType() {
        return typeSymbol;
    }
}
