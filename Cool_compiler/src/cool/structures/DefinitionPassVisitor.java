package cool.structures;

import cool.AST.ASTNode;
import cool.AST.ASTVisitor;

public class DefinitionPassVisitor implements ASTVisitor<Void> {

    Scope currentScope = null;

    @Override
    public Void visit(ASTNode.Id id) {

        var idSymbol = new IdSymbol(id.getToken().getText());

        id.setScope(currentScope);
        id.setSymbol(idSymbol);

        return null;
    }

    @Override
    public Void visit(ASTNode.IntLiteral intLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.BoolLiteral boolLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.LogicOp logicOp) {
        logicOp.left.accept(this);
        logicOp.right.accept(this);
        logicOp.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.Arithmetic arithmetic) {
        arithmetic.left.accept(this);
        arithmetic.right.accept(this);
        arithmetic.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.IsVoid isVoid) {
        isVoid.expression.accept(this);
        isVoid.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.Neg neg) {
        neg.expression.accept(this);
        neg.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.While aWhile) {
        aWhile.cond.accept(this);
        aWhile.body.accept(this);
        aWhile.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.If anIf) {
        anIf.cond.accept(this);
        anIf.thenB.accept(this);
        anIf.elseB.accept(this);
        anIf.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.New aNew) {
        aNew.type.accept(this);
        aNew.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.Assignation assignation) {
        assignation.setScope(currentScope);
        if(assignation.id.getToken().getText().equals("self")){
            Scope symbol = currentScope;
            while(!(symbol instanceof ClassSymbol)){
                symbol = symbol.getParent();
            }
            SymbolTable.error(((ClassSymbol)symbol).getCtx(),assignation.id.getToken(),"Cannot assign to self");
            return null;
        }
        assignation.id.accept(this);
        assignation.expression.accept(this);
        return null;
    }

    @Override
    public Void visit(ASTNode.Reverse reverse) {
        reverse.expression.accept(this);
        reverse.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.SelfLiteral selfLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.TrueLitetar trueLitetar) {
        return null;
    }

    @Override
    public Void visit(ASTNode.StringLiteral stringLiteral) {
        return null;
    }

    @Override
    public Void visit(ASTNode.Type type) {
        type.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.LRParen lrParen) {
        lrParen.expressions.forEach(s -> s.accept(this));
        return null;
    }

    @Override
    public Void visit(ASTNode.Case aCase) {
        aCase.caseExpr.accept(this);
        aCase.setScope(currentScope);

        Scope scope = currentScope;
        while (!(scope instanceof ClassSymbol)) {
            scope = scope.getParent();
        }

        for(int i = 0 ; i < aCase.ids.size() ; i++){
            if(aCase.ids.get(i).getToken().getText().equals("self")){
                SymbolTable.error(((ClassSymbol)scope).getCtx(),aCase.ids.get(i).getToken(),"Case variable has illegal name self");
            }
            aCase.ids.get(i).accept(this);
            if(aCase.types.get(i).getToken().getText().equals("SELF_TYPE")){
                SymbolTable.error(((ClassSymbol)scope).getCtx(),aCase.types.get(i).getToken(),"Case variable " + aCase.ids.get(i).getToken().getText() + " has illegal type SELF_TYPE");
            }
            aCase.types.get(i).accept(this);
            aCase.bodies.get(i).accept(this);
        }

        return null;
    }

    @Override
    public Void visit(ASTNode.Let let) {

        Scope letScope = new LetSymbol(currentScope, "let");

        let.setScope(letScope);

        currentScope = letScope;
        for(int i = 0; i < let.ids.size(); i++){
            if(let.ids.get(i).getToken().getText().equals("self")){
                ClassSymbol symbol = (ClassSymbol) currentScope.getParent().getParent();
                SymbolTable.error(symbol.getCtx(),let.ids.get(i).getToken(),"Let variable has illegal name self");
                currentScope = currentScope.getParent();
                return null;
            }
            let.ids.get(i).accept(this);
            currentScope.add(let.ids.get(i).getSymbol());
            let.types.get(i).accept(this);
            if (let.inits.get(i) != null) {
                currentScope = new DefaultScope(currentScope);
                for(int j = 0; j < i; j++){
                    currentScope.add(let.ids.get(j).getSymbol());
                }
                let.inits.get(i).accept(this);
                currentScope = currentScope.getParent();
            }
        }
        let.body.accept(this);

        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(ASTNode.Call call) {
        call.setScope(currentScope);
        call.expression.accept(this);
        if(call.type != null)
            call.type.accept(this);
        call.id.accept(this);
        if(call.expression instanceof ASTNode.Call){
            call.setSymbol(((ASTNode.Call) call.expression).getSymbol());
        }
        else{
            if(call.expression instanceof ASTNode.Id)
                call.setSymbol(((ASTNode.Id)(call.expression)).getSymbol());
            else
                call.setSymbol(call.id.getSymbol());
        }
        call.expressions.forEach(e -> e.accept(this));
        return null;
    }

    @Override
    public Void visit(ASTNode.Formal formal) {

        if(formal.id.getToken().getText().equals("self")){
            ClassSymbol symbol = (ClassSymbol) currentScope.getParent();
            String name = ((FunctionSymbol)currentScope).name;
            name = name.substring(0,name.length()-5);
            SymbolTable.error(symbol.getCtx(),formal.id.getToken(),"Method " + name + " of class " + symbol.getName() + " has formal parameter with illegal name self");
            return null;
        }

        return processVarStructure(formal.id, formal.type, null);
    }

    @Override
    public Void visit(ASTNode.Function function) {

        var id = function.id;
        var type = function.type;
        var functionSymbol = new FunctionSymbol(currentScope,id.getToken().getText() + "_func",type.getToken().getText());

        if(!currentScope.add(functionSymbol)) {
            SymbolTable.error(((ClassSymbol) currentScope).getCtx(), function.id.getToken(), "Class " + ((ClassSymbol) currentScope).getName() + " redefines method " + function.id.getToken().getText());
            return null;
        }

        currentScope = functionSymbol;
        function.setScope(currentScope);

        function.formals.forEach(f -> f.accept(this));
        function.type.accept(this);
        function.expression.accept(this);

        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(ASTNode.Variable variable) {

        var id = variable.id;
        var type = variable.type;

        variable.id.accept(this);
        variable.type.accept(this);

        setTypeSymbol(variable, type);

        if(variable.expression != null)
            variable.expression.accept(this);

        if(variable.id.getToken().getText().equals("self")){
            SymbolTable.error(((ClassSymbol)currentScope).getCtx(),variable.id.getToken(),"Class " + ((ClassSymbol)currentScope).getName() + " has attribute with illegal name self");
            return null;
        }

        if(!currentScope.add(id.getSymbol())){
            SymbolTable.error(((ClassSymbol)currentScope).getCtx(),variable.id.getToken(), "Class " + ((ClassSymbol)currentScope).getName() + " redefines attribute " + variable.id.getToken().getText());
            return null;
        }

//        if(currentScope.getParent().lookup(type.getToken().getText()) == null){
//            SymbolTable.error(((ClassSymbol)currentScope).getCtx(),variable.type.getToken(), "Class " + ((ClassSymbol)currentScope).getName() + " has attribute " + variable.id.getToken().getText() + " with undefined type " + variable.type.getToken().getText());
//            return null;
//        }

        variable.setScope(currentScope);

        return null;
    }

    private void setTypeSymbol(ASTNode.Variable variable, ASTNode.Type type) {
        if(variable.type.getToken().getText().equals("SELF_TYPE")){
            variable.id.getSymbol().setType(SymbolTable.globals.lookup("SELF_TYPE"));
            return;
        }

        Symbol typeSymbol = currentScope.getParent().lookup(type.getToken().getText());

        if(typeSymbol != null) {
            variable.id.getSymbol().setType(typeSymbol);
        }
        else{
            variable.id.getSymbol().setType(new Symbol(type.getToken().getText()));
        }

    }

    @Override
    public Void visit(ASTNode.Program program) {

        currentScope = SymbolTable.globals;

        program.stmts.forEach(s -> s.accept(this));

        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(ASTNode.Class aClass) {

        var classVar = aClass.type;
        var parentClass = aClass.iType;

        if (classVar.getToken().getText().equals("SELF_TYPE")){
            SymbolTable.error(aClass.ctx,classVar.getToken(), "Class has illegal name SELF_TYPE");
            return null;
        }

        var classSymbol = new ClassSymbol(currentScope, classVar.getToken().getText(), aClass.ctx);
        if(parentClass.getToken() != null){
            classSymbol.setInheritedClass(parentClass.getToken().getText());
            classSymbol.setInherited(true);
        }

        currentScope = classSymbol;
        aClass.setScope(currentScope);
        aClass.setSymbol(classSymbol);

        if(currentScope.getParent().lookup(classVar.getToken().getText()) != null){
            SymbolTable.error(aClass.ctx, aClass.type.getToken(),"Class " + classVar.getToken().getText() + " is redefined");
            currentScope = currentScope.getParent();
            return null;
        }

        if(!currentScope.getParent().add(classSymbol)){
            SymbolTable.error(aClass.ctx,aClass.type.getToken(),"Class " + classVar.getToken().getText() + " is redefined");
            currentScope = currentScope.getParent();
            return null;
        }

        aClass.type.accept(this);
        aClass.iType.accept(this);
        aClass.features.forEach(a -> a.accept(this));

        currentScope = currentScope.getParent();
        
        return null;
    }

    @Override
    public Void visit(ASTNode.Method method) {
        method.id.accept(this);
        method.expressions.forEach(e -> e.accept(this));
        method.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.LRBrace lrBrace) {
        lrBrace.expressions.forEach(s -> s.accept(this));
        lrBrace.setScope(currentScope);
        return null;
    }

    @Override
    public Void visit(ASTNode.FalseLiteral falseLiteral) {
        return null;
    }

    Void processVarStructure(ASTNode.Id id, ASTNode.Type type, ASTNode.Expression initValue) {

        var symbol = new IdSymbol(id.getToken().getText());

        Symbol typeSymbol = SymbolTable.globals.lookup(type.getToken().getText());

        if(typeSymbol != null && typeSymbol.getName().equals("SELF_TYPE")) {
            ClassSymbol classSymbol = (ClassSymbol) currentScope.getParent();
            String name = ((FunctionSymbol) currentScope).name;
            name = name.substring(0, name.length() - 5);
            SymbolTable.error(classSymbol.getCtx(), type.getToken(), "Method " + name + " of class " + ((ClassSymbol) currentScope.getParent()).getName() + " has formal parameter " + id.getToken().getText() + " with illegal type SELF_TYPE");
            symbol.setType(SymbolTable.globals.lookup("SELF_TYPE"));
            id.setSymbol(symbol);
            return null;
        }

        symbol.setType(typeSymbol);
        if(symbol.getType() == null)
            symbol.setType(new Symbol(type.getToken().getText()));

        id.setSymbol(symbol);

        if (!currentScope.add(symbol) && (symbol.getName().equals("Int") || symbol.getName().equals("String") || symbol.getName().equals("Bool") || symbol.getName().equals("SELF_TYPE"))) {
            ClassSymbol classSymbol = (ClassSymbol) currentScope.getParent();
            SymbolTable.error(classSymbol.getCtx(),id.getToken(), "Method " + ((FunctionSymbol)currentScope).name + " of class " + classSymbol.getName() + " has formal parameter " + id.getToken().getText() + " with undefined type " + type.getToken().getText());
            return null;
        }

        if (initValue != null) {
            initValue.accept(this);
        }

        return null;
    }
}
