package cool.structures;

import java.io.File;

import org.antlr.v4.runtime.*;

import cool.compiler.Compiler;
import cool.parser.CoolParser;

public class SymbolTable {
    public static Scope globals;
    
    private static boolean semanticErrors;
    
    public static void defineBasicClasses() {
        globals = new DefaultScope(null);
        semanticErrors = false;
        
        // TODO Populate global scope.
        globals.add(new ClassSymbol("Object"));
        globals.add(new ClassSymbol("IO"));
        globals.add(new ClassSymbol("Int"));
        globals.add(new ClassSymbol("String"));
        globals.add(new ClassSymbol("Bool"));
        globals.add(new Symbol("SELF_TYPE"));

        // TODO Add methods to classes.
        ClassSymbol object = (ClassSymbol) globals.lookup("Object");

        FunctionSymbol abort = new FunctionSymbol(object, "abort_func", "Object");
        object.add(abort);

        FunctionSymbol type_name = new FunctionSymbol(object, "type_name_func", "String");
        object.add(type_name);

        FunctionSymbol copy = new FunctionSymbol(object, "copy_func", "SELF_TYPE");
        object.add(copy);

        ClassSymbol io = (ClassSymbol) globals.lookup("IO");

        FunctionSymbol out_string = new FunctionSymbol(io, "out_string_func", "SELF_TYPE");
        IdSymbol out_string_param = new IdSymbol("x");
        out_string_param.setType(globals.lookup("String"));
        out_string.add(out_string_param);
        io.add(out_string);

        FunctionSymbol out_int = new FunctionSymbol(io, "out_int_func", "SELF_TYPE");
        IdSymbol out_int_param = new IdSymbol("x");
        out_int_param.setType(globals.lookup("Int"));
        out_int.add(out_int_param);
        io.add(out_int);

        FunctionSymbol in_string = new FunctionSymbol(io, "in_string_func", "String");
        io.add(in_string);

        FunctionSymbol in_int = new FunctionSymbol(io, "in_int_func", "Int");
        io.add(in_int);

        ClassSymbol string = (ClassSymbol) globals.lookup("String");

        FunctionSymbol length = new FunctionSymbol(string, "length_func", "Int");
        string.add(length);

        FunctionSymbol concat = new FunctionSymbol(string, "concat_func", "String");
        IdSymbol concat_param = new IdSymbol("s");
        concat_param.setType(globals.lookup("String"));
        concat.add(concat_param);
        string.add(concat);

        FunctionSymbol substr = new FunctionSymbol(string, "substr_func", "String");
        IdSymbol substr_param1 = new IdSymbol("i");
        substr_param1.setType(globals.lookup("Int"));
        IdSymbol substr_param2 = new IdSymbol("l");
        substr_param2.setType(globals.lookup("Int"));
        substr.add(substr_param1);
        substr.add(substr_param2);
        string.add(substr);


    }
    
    /**
     * Displays a semantic error message.
     * 
     * @param ctx Used to determine the enclosing class context of this error,
     *            which knows the file name in which the class was defined.
     * @param info Used for line and column information.
     * @param str The error message.
     */
    public static void error(ParserRuleContext ctx, Token info, String str) {
        while (! (ctx.getParent() instanceof CoolParser.ProgramContext))
            ctx = ctx.getParent();
        
        String message = "\"" + new File(Compiler.fileNames.get(ctx)).getName()
                + "\", line " + info.getLine()
                + ":" + (info.getCharPositionInLine() + 1)
                + ", Semantic error: " + str;
        
        System.err.println(message);
        
        semanticErrors = true;
    }
    
    public static void error(String str) {
        String message = "Semantic error: " + str;
        
        System.err.println(message);
        
        semanticErrors = true;
    }
    
    public static boolean hasSemanticErrors() {
        return semanticErrors;
    }
}
