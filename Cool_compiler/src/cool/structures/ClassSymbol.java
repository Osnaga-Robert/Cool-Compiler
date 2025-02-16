package cool.structures;

import cool.parser.CoolParser;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ClassSymbol extends Symbol implements Scope {

    protected Scope parent;
    protected Map<String, Symbol> symbols = new LinkedHashMap<>();
    private CoolParser.ClassContext ctx;
    private boolean isInherited;
    private String inheritedClass;


    public ClassSymbol(Scope parent,String name, CoolParser.ClassContext ctx) {
        super(name);
        this.parent = parent;
        this.ctx = ctx;
        this.isInherited = false;
        inheritedClass = "";
    }

    public ClassSymbol(String name) {
        super(name);
    }

    @Override
    public boolean add(Symbol sym) {
        if (symbols.containsKey(sym.getName())) return false;

        symbols.put(sym.getName(), sym);

        return true;
    }

    @Override
    public Symbol lookup(String str) {
        var sym = symbols.get(str);

        if (sym != null) return sym;

        if (parent != null) return parent.lookup(str);

        return null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public CoolParser.ClassContext getCtx() {
        return ctx;
    }

    public void setInherited(boolean isInherited) {
        this.isInherited = isInherited;
    }

    public boolean getInherited() {
        return isInherited;
    }

    public void setInheritedClass(String inheritedClass) {
        this.inheritedClass = inheritedClass;
    }

    public String getInheritedClass() {
        return inheritedClass;
    }

    public boolean isCycle(){
        var sym = parent.lookup(inheritedClass);
        Set<String> visited = new HashSet<>();
        if(getName().equals("Int") || getName().equals("String") || getName().equals("Bool") || getName().equals("SELF_TYPE") || getName().equals("IO")){
            return false;
        }
        visited.add(getName());
        while(sym != null){
            if(visited.contains(sym.getName())){
                return true;
            }
            if(sym.getName().equals("Int") || sym.getName().equals("String") || sym.getName().equals("Bool") || sym.getName().equals("SELF_TYPE") || sym.getName().equals("IO")){
                return false;
            }
            visited.add(sym.getName());
            sym = parent.lookup(((ClassSymbol)sym).getInheritedClass());
        }
        return false;
    }

    public boolean redefinedinheritenceType(String type) {
        var sym = parent.lookup(getInheritedClass());
        while(sym != null && (sym instanceof ClassSymbol)){
            if(((ClassSymbol)sym).symbols.containsKey(type)){
                return true;
            }
            sym = parent.lookup(((ClassSymbol)sym).getInheritedClass());
        }
        return false;
    }

    public Symbol containsInheritedFunction(String text) {
        var sym = parent.lookup(getInheritedClass());
        while(sym != null){
            if(((ClassSymbol)sym).symbols.containsKey(text)){
                return sym;
            }
            sym = parent.lookup(((ClassSymbol)sym).getInheritedClass());
        }
        return null;
    }

}
