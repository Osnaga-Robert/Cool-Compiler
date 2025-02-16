package cool.structures;

import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionSymbol extends Symbol implements Scope{
    protected Scope parent;

    protected Map<String, Symbol> symbols = new LinkedHashMap<>();

    String type;


    public FunctionSymbol(Scope parent, String name, String type) {
        super(name);
        this.parent = parent;
        this.type = type;
    }

    @Override
    public boolean add(Symbol sym) {
        if (symbols.containsKey(sym.getName())) return false;

        symbols.put(sym.getName(), sym);

        return true;
    }

    @Override
    public Symbol lookup(String s) {
        var sym = symbols.get(s);

        if (sym != null) return sym;

        if (parent != null) return parent.lookup(s);

        return null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    public Map<String, Symbol> getFormals() {
        return symbols;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
