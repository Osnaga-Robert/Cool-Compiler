package cool.structures;

import cool.AST.ASTNode;
import cool.AST.ASTVisitor;

import java.util.HashSet;
import java.util.Set;

public class ResolutionPassVisitor implements ASTVisitor<Symbol> {

    @Override
    public Symbol visit(ASTNode.Id id) {
        if(id.getToken().getText().equals("self")){
            return SymbolTable.globals.lookup("SELF_TYPE");
        }
        if(id.getScope() instanceof DefaultScope){
            Scope scope = id.getScope();
            var classScope = scope;
            while(!(classScope instanceof ClassSymbol)){
                classScope = classScope.getParent();
            }
            if(!((DefaultScope)scope).getSymbols().containsKey(id.getToken().getText())){
                var functionScope = scope;
                while(!(functionScope instanceof FunctionSymbol)){
                    functionScope = functionScope.getParent();
                }
                if(functionScope.lookup(id.getToken().getText()) == null){
                    SymbolTable.error(((ClassSymbol)classScope).getCtx(),id.getToken(), "Undefined identifier " + id.getToken().getText());
                }
            }
        }
        if(id.getScope() != null){
            id.setSymbol((IdSymbol) id.getScope().lookup(id.getToken().getText()));
        }
        if(id.getSymbol() == null){
            Scope classScope = id.getScope();
            if (classScope == null)
                return null;
            while(!(classScope instanceof ClassSymbol)){
                classScope = classScope.getParent();
            }

        }
        if(id.getSymbol() == null){
            return null;
        }

        return id.getSymbol().typeSymbol;
    }

    @Override
    public Symbol visit(ASTNode.IntLiteral intLiteral) {
        return SymbolTable.globals.lookup("Int");
    }

    @Override
    public Symbol visit(ASTNode.BoolLiteral boolLiteral) {
        return SymbolTable.globals.lookup("Bool");
    }

    @Override
    public Symbol visit(ASTNode.LogicOp logicOp) {
        var symbolLeft = logicOp.left.accept(this);
        var symbolRight = logicOp.right.accept(this);
        var scope = logicOp.getScope();
        while(!(scope instanceof ClassSymbol)){
            scope = scope.getParent();
        }

        if(logicOp.getToken().getText().equals("=")){
            if(symbolLeft != null && symbolRight != null){
                if(!symbolLeft.getName().equals(symbolRight.getName())){
                    if(symbolLeft.getName().equals("Int") || symbolRight.getName().equals("Int"))
                        SymbolTable.error(((ClassSymbol)scope).getCtx(),logicOp.getToken(), "Cannot compare " + symbolLeft.getName() + " with " + symbolRight.getName());
                    if(symbolLeft.getName().equals("String") || symbolRight.getName().equals("String"))
                        SymbolTable.error(((ClassSymbol)scope).getCtx(),logicOp.getToken(), "Cannot compare " + symbolLeft.getName() + " with " + symbolRight.getName());
                    if(symbolLeft.getName().equals("Bool") || symbolRight.getName().equals("Bool"))
                        SymbolTable.error(((ClassSymbol)scope).getCtx(),logicOp.getToken(), "Cannot compare " + symbolLeft.getName() + " with " + symbolRight.getName());
                }
            }
        }
        else{
            if(symbolLeft != null && symbolRight != null){
                if(!symbolLeft.getName().equals("Int"))
                    SymbolTable.error(((ClassSymbol)scope).getCtx(),logicOp.left.getToken(), "Operand of " + logicOp.getToken().getText() + " has type " + symbolLeft + " instead of Int");
                if(!symbolRight.getName().equals("Int"))
                    SymbolTable.error(((ClassSymbol)scope).getCtx(),logicOp.right.getToken(), "Operand of " + logicOp.getToken().getText() + " has type " + symbolRight + " instead of Int");
            }
        }

        return SymbolTable.globals.lookup("Bool");
    }

    @Override
    public Symbol visit(ASTNode.Arithmetic arithmetic) {
        var symbolLeft = arithmetic.left.accept(this);
        var symbolRight = arithmetic.right.accept(this);
        var scope = arithmetic.getScope();
        while(!(scope instanceof ClassSymbol)){
            scope = scope.getParent();
        }

        if(symbolLeft != null && !symbolLeft.getName().equals("Int"))
            SymbolTable.error(((ClassSymbol)scope).getCtx(),arithmetic.left.getToken(), "Operand of " + arithmetic.getToken().getText() + " has type " + symbolLeft + " instead of Int");
        if(symbolRight != null && !symbolRight.getName().equals("Int"))
            SymbolTable.error(((ClassSymbol)scope).getCtx(),arithmetic.right.getToken(), "Operand of " + arithmetic.getToken().getText() + " has type " + symbolRight + " instead of Int");


        return SymbolTable.globals.lookup("Int");

    }

    @Override
    public Symbol visit(ASTNode.IsVoid isVoid) {
        isVoid.expression.accept(this);
        return SymbolTable.globals.lookup("Bool");
    }

    @Override
    public Symbol visit(ASTNode.Neg neg) {
        var symbol = neg.expression.accept(this);
        var classScope = neg.getScope();
        while(!(classScope instanceof ClassSymbol)){
            classScope = classScope.getParent();
        }
        if(symbol == null){
            SymbolTable.globals.lookup("Int");
        }
        else{
            if(symbol.getName().equals("Int")){
                return SymbolTable.globals.lookup("Int");
            }
            else
                SymbolTable.error(((ClassSymbol)classScope).getCtx(),neg.expression.getToken(), "Operand of ~ has type " + symbol.getName() + " instead of Int");
        }
        return SymbolTable.globals.lookup("Int");
    }

    @Override
    public Symbol visit(ASTNode.While aWhile) {
        Symbol cond = aWhile.cond.accept(this);
        if(cond != null && !cond.getName().equals("Bool")){
            var classScope = aWhile.getScope();
            while(!(classScope instanceof ClassSymbol)){
                classScope = classScope.getParent();
            }
            SymbolTable.error(((ClassSymbol)classScope).getCtx(),aWhile.cond.getToken(), "While condition has type " + cond.getName() + " instead of Bool");
        }
        aWhile.body.accept(this);
        return SymbolTable.globals.lookup("Object");
    }

    @Override
    public Symbol visit(ASTNode.If anIf) {
        var cond = anIf.cond.accept(this);
        var classScope = anIf.getScope();
        while(!(classScope instanceof ClassSymbol)){
            classScope = classScope.getParent();
        }
        if(cond != null && !cond.getName().equals("Bool")){
            SymbolTable.error(((ClassSymbol)classScope).getCtx(),anIf.cond.getToken(), "If condition has type " + cond.getName() + " instead of Bool");
        }
        var thenB = anIf.thenB.accept(this);
        var elseB = anIf.elseB.accept(this);

        thenB = SymbolTable.globals.lookup(thenB.getName());
        elseB = SymbolTable.globals.lookup(elseB.getName());

        if(thenB.getName().equals("SELF_TYPE") && !elseB.getName().equals("SELF_TYPE"))
            thenB = SymbolTable.globals.lookup(((ClassSymbol)classScope).getName());
        if(elseB.getName().equals("SELF_TYPE") && !thenB.getName().equals("SELF_TYPE"))
            elseB = SymbolTable.globals.lookup(((ClassSymbol)classScope).getName());


        if(thenB.getName().equals(elseB.getName()))
            return thenB;
        else{
            return findCommonAncestor(thenB,elseB);
        }
    }

    private Symbol findCommonAncestor(Symbol class1, Symbol class2) {
        Set<String> ancestorsClass1 = new HashSet<>();
        while (class1 != null) {
            ancestorsClass1.add(class1.getName());
            if(class1 instanceof ClassSymbol)
                class1 = SymbolTable.globals.lookup(((ClassSymbol) class1).getInheritedClass());
            else
                class1 = null;
        }

        while (class2 != null) {
            if (ancestorsClass1.contains(class2.getName())) {
                return class2;
            }
            if (class2 instanceof ClassSymbol)
                class2 = SymbolTable.globals.lookup(((ClassSymbol) class2).getInheritedClass());
            else
                class2 = null;
        }

        return SymbolTable.globals.lookup("Object");
    }

    @Override
    public Symbol visit(ASTNode.New aNew) {
        Scope scope = aNew.getScope();
        while(!(scope instanceof ClassSymbol)){
            scope = scope.getParent();
        }

        if(SymbolTable.globals.lookup(aNew.type.getToken().getText()) == null){
            SymbolTable.error(((ClassSymbol)scope).getCtx(),aNew.type.getToken(), "new is used with undefined type " + aNew.type.getToken().getText());
        }

        return SymbolTable.globals.lookup(aNew.type.getToken().getText());
    }

    @Override
    public Symbol visit(ASTNode.Assignation assignation) {
        var exprSymbol = assignation.expression.accept(this);
        var idSymbol = assignation.id.accept(this);
        var classScope = assignation.getScope();
        while(!(classScope instanceof ClassSymbol)){
            classScope = classScope.getParent();
        }

        if(idSymbol == null){
            Symbol inheritaced = SymbolTable.globals.lookup(((ClassSymbol)classScope).getInheritedClass());
            while(inheritaced != null){
                if(inheritaced instanceof ClassSymbol){
                    if(((ClassSymbol)inheritaced).getSymbols().containsKey(assignation.id.getToken().getText())){
                        idSymbol = ((ClassSymbol)inheritaced).getSymbols().get(assignation.id.getToken().getText());
                        break;
                    }
                    inheritaced = SymbolTable.globals.lookup(((ClassSymbol)inheritaced).getInheritedClass());
                }
                else
                    inheritaced = null;
            }
        }
        if (idSymbol instanceof IdSymbol){
           if(exprSymbol != null){
                if(!((IdSymbol) idSymbol).getType().getName().equals(exprSymbol.getName())){
                     Symbol inheritaced = SymbolTable.globals.lookup(exprSymbol.getName());
                     while(inheritaced != null){
                          if(inheritaced.getName().equals(idSymbol.getName())){
                            return exprSymbol;
                          }
                          if(inheritaced instanceof ClassSymbol)
                            inheritaced = SymbolTable.globals.lookup(((ClassSymbol)inheritaced).getInheritedClass());
                          else
                            inheritaced = null;
                     }
                     SymbolTable.error(((ClassSymbol)classScope).getCtx(),assignation.expression.getToken(), "Type " + exprSymbol.getName() + " of assigned expression is incompatible with declared type " + ((IdSymbol) idSymbol).getType().getName() + " of identifier " + assignation.id.getToken().getText());
                }
           }
           return exprSymbol;
        }

        if(idSymbol != null && exprSymbol != null){
            if(!idSymbol.getName().equals(exprSymbol.getName())){
                Symbol inheritaced = SymbolTable.globals.lookup(exprSymbol.getName());
                while(inheritaced != null){
                    if(inheritaced.getName().equals(idSymbol.getName())){
                        return exprSymbol;
                    }
                    if(inheritaced instanceof ClassSymbol)
                        inheritaced = SymbolTable.globals.lookup(((ClassSymbol)inheritaced).getInheritedClass());
                    else
                        inheritaced = null;
                }
                SymbolTable.error(((ClassSymbol)classScope).getCtx(),assignation.expression.getToken(), "Type " + exprSymbol.getName() + " of assigned expression is incompatible with declared type " + idSymbol.getName() + " of identifier " + assignation.id.getToken().getText());
            }
        }
        return exprSymbol;
    }

    @Override
    public Symbol visit(ASTNode.Reverse reverse) {
        var symbol = reverse.expression.accept(this);
        var classScope = reverse.getScope();
        while(!(classScope instanceof ClassSymbol)){
            classScope = classScope.getParent();
        }
        if(symbol != null && !symbol.getName().equals("Bool")){
            SymbolTable.error(((ClassSymbol)classScope).getCtx(),reverse.expression.getToken(), "Operand of not has type " + symbol.getName() + " instead of Bool");
        }
        return SymbolTable.globals.lookup("Bool");
    }

    @Override
    public Symbol visit(ASTNode.SelfLiteral selfLiteral) {
        return null;
    }

    @Override
    public Symbol visit(ASTNode.TrueLitetar trueLitetar) {
        return SymbolTable.globals.lookup("Bool");
    }

    @Override
    public Symbol visit(ASTNode.StringLiteral stringLiteral) {
        return SymbolTable.globals.lookup("String");
    }

    @Override
    public Symbol visit(ASTNode.Type type) {
        Scope scope = type.getScope();
        if(type.getToken() != null){
            if(type.getToken().getText().equals("SELF_TYPE")){
                return SymbolTable.globals.lookup("SELF_TYPE");
            }
            if(SymbolTable.globals.lookup(type.getToken().getText()) == null){
                return null;
            }
            return SymbolTable.globals.lookup(type.getToken().getText());
        }
        return null;
    }

    @Override
    public Symbol visit(ASTNode.LRParen lrParen) {
        Symbol symbol = null;
        for(var expr : lrParen.expressions){
            symbol = expr.accept(this);
        }

        return symbol;
    }

    @Override
    public Symbol visit(ASTNode.Case aCase) {

        var caseExprSymbol = aCase.caseExpr.accept(this);
        var scope = aCase.getScope();
        while(!(scope instanceof ClassSymbol)){
            scope = scope.getParent();
        }
        var commontype = aCase.bodies.get(0).accept(this);
        for(int i = 0 ; i < aCase.ids.size() ; i++){
            aCase.ids.get(i).accept(this);
            if(SymbolTable.globals.lookup(aCase.types.get(i).getToken().getText()) == null){
                SymbolTable.error(((ClassSymbol)scope).getCtx(),aCase.types.get(i).getToken(), "Case variable " + aCase.ids.get(i).getToken().getText() + " has undefined type " + aCase.types.get(i).getToken().getText());
            }
            aCase.types.get(i).accept(this);
            var type = aCase.bodies.get(i).accept(this);
            if(type != null)
                type = SymbolTable.globals.lookup(type.getName());
            else
                type = SymbolTable.globals.lookup("Object");
            commontype = findCommonAncestor(commontype,type);
        }
        return commontype;
    }

    @Override
    public Symbol visit(ASTNode.Let let) {

        var symbol = let.getScope();
        boolean ok = true;
        while(ok){
            if(symbol instanceof LetSymbol)
                symbol = symbol.getParent();
            else
                ok = false;
        }

        for(int i = 0 ; i < let.ids.size() ; i++){
            let.ids.get(i).accept(this);
            if(SymbolTable.globals.lookup(let.types.get(i).getToken().getText()) == null){
                ClassSymbol classSymbol = (ClassSymbol) symbol.getParent();
                SymbolTable.error(classSymbol.getCtx(),let.types.get(i).getToken(), "Let variable " + let.ids.get(i).getToken().getText() + " has undefined type " + let.types.get(i).getToken().getText());
            }
            Symbol typeSymbol = let.types.get(i).accept(this);
            if(let.ids.get(i).getSymbol() != null)
                let.ids.get(i).getSymbol().setType(typeSymbol);
            if(let.inits.get(i) != null){
                Symbol initSymbol = let.inits.get(i).accept(this);
                if(initSymbol != null && SymbolTable.globals.lookup(let.types.get(i).getToken().getText()) != null){
                    if(!initSymbol.getName().equals(let.types.get(i).getToken().getText())){
                        var common = findCommonLeftAncestor(initSymbol,SymbolTable.globals.lookup(let.types.get(i).getToken().getText()));
                        if(common == null)
                            SymbolTable.error(((ClassSymbol)symbol.getParent()).getCtx(),let.inits.get(i).getToken(), "Type " + initSymbol.getName() + " of initialization expression of identifier " + let.ids.get(i).getToken().getText() + " is incompatible with declared type " + let.types.get(i).getToken().getText());
                    }
                }
            }

        }

        return let.body.accept(this);
    }

    private Object findCommonLeftAncestor(Symbol class1, Symbol class2) {
        if(class2 == null)
            return null;
        while (class1 != null) {
            if (class1.getName().equals(class2.getName())) {
                return class1;
            }
            if (class1 instanceof ClassSymbol) {
                class1 = SymbolTable.globals.lookup(((ClassSymbol) class1).getInheritedClass());
            } else {
                class1 = null;
            }
        }
        return null;
    }

    @Override
    public Symbol visit(ASTNode.Call call) {
        call.id.accept(this);
        Scope scope = call.getScope();
        Symbol exprSymbol = call.expression.accept(this);
        Symbol typeSymbol = null;
        Scope currentClassScope = scope;
        while(!(currentClassScope instanceof ClassSymbol)){
            currentClassScope = currentClassScope.getParent();
        }

        if(call.id.getToken().getText().equals("copy")){
            if(!call.expressions.isEmpty()){
                SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.expressions.get(0).getToken(), "Method copy is applied to wrong number of arguments");
            }
            return SymbolTable.globals.lookup("SELF_TYPE");
        }
        if(call.id.getToken().getText().equals("abort")){
            if(!call.expressions.isEmpty()){
                SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.expressions.get(0).getToken(), "Method abort is applied to wrong number of arguments");
            }
            return SymbolTable.globals.lookup("Object");
        }
        if(call.id.getToken().getText().equals("type_name")){
            if(!call.expressions.isEmpty()){
                SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.expressions.get(0).getToken(), "Method type_name is applied to wrong number of arguments");
            }
            return SymbolTable.globals.lookup("String");
        }

        if(exprSymbol == null){
            exprSymbol = SymbolTable.globals.lookup(((ClassSymbol)currentClassScope).getName());
        }

        if(exprSymbol.getName().equals("SELF_TYPE") && call.expression instanceof ASTNode.Id){
            exprSymbol = SymbolTable.globals.lookup(((ClassSymbol)currentClassScope).getName());
        }

        boolean isCall = call.expression instanceof ASTNode.Call;

        if(call.type.getToken() != null) {
            if(call.type.getToken().getText().equals("SELF_TYPE")){
                SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.type.getToken(), "Type of static dispatch cannot be SELF_TYPE");
                return null;
            }
            if(isSuperclass(SymbolTable.globals.lookup(((ClassSymbol)currentClassScope).getName()),SymbolTable.globals.lookup(call.type.getToken().getText()))){
                SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.type.getToken(), "Type " + call.type.getToken().getText() + " of static dispatch is not a superclass of type " + (exprSymbol).getName());
                return null;
            }
            if(SymbolTable.globals.lookup(call.type.getToken().getText()) == null){
                SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.type.getToken(), "Type " + call.type.getToken().getText() + " of static dispatch is undefined");
                return null;
            }
            typeSymbol = call.type.accept(this);
            if (typeSymbol != null && SymbolTable.globals.lookup(typeSymbol.getName()) != null) {
                Symbol classTypeSymbol = SymbolTable.globals.lookup(typeSymbol.getName());
                if(isCall && !(classTypeSymbol instanceof ClassSymbol)){
                    classTypeSymbol = typeSymbol;
                }
                if (classTypeSymbol instanceof ClassSymbol) {
                    Symbol functionSymbol = checkFunctionInherited(classTypeSymbol, call.id.getToken().getText());
                    if (functionSymbol == null) {
                        if(exprSymbol.getName().equals("SELF_TYPE")) {
                            SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.id.getToken(), "Undefined method " + call.id.getToken().getText() + " in class " + ((IdSymbol) scope.lookup(call.getSymbol().getName())).getType());
                            return null;
                        }
                        SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.id.getToken(), "Undefined method " + call.id.getToken().getText() + " in class " + typeSymbol.getName());
                        return null;
                    }
                    if(((FunctionSymbol)functionSymbol).getFormals().size() != call.expressions.size()){
                        SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.id.getToken(), "Method " + call.id.getToken().getText() + " of class " + classTypeSymbol.getName() + " is applied to wrong number of arguments");
                        return SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType());
                    }

                    int index = 0;
                    var formalIterator = ((FunctionSymbol)functionSymbol).getFormals().values().iterator();
                    var actualIterator = call.expressions.iterator();

                    while(formalIterator.hasNext() && actualIterator.hasNext()){
                        var formal = formalIterator.next();
                        var actual = actualIterator.next();

                        if(formal instanceof IdSymbol){
                            if(!((IdSymbol) formal).getType().getName().equals(actual.accept(this).getName())){
                                var common = findCommonLeftAncestor(SymbolTable.globals.lookup(actual.accept(this).getName()),SymbolTable.globals.lookup(((IdSymbol) formal).getType().getName()));
                                if(common == null) {
                                    SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.expressions.get(index).getToken(), "In call of method " + call.id.getToken().getText() + " of class " + typeSymbol.getName() + ", actual type " + actual.accept(this).getName() + " of formal parameter " + ((IdSymbol) formal).getName() + " is incompatible with declared type " + ((IdSymbol) formal).getType().getName());
                                    return SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType());
                                }
                            }
                        }
                        index++;
                    }
                    if(SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType()).getName().equals("SELF_TYPE"))
                    {
                        return SymbolTable.globals.lookup(((ClassSymbol)currentClassScope).getName());
                    }
                    return SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType());
                }
            }
        }
        else
        {
            if(exprSymbol != null && SymbolTable.globals.lookup(exprSymbol.getName()) != null){
                Symbol classSymbol = SymbolTable.globals.lookup(exprSymbol.getName());
                if(isCall && !(classSymbol instanceof ClassSymbol)){
                    classSymbol = scope.lookup(call.getSymbol().getName());
                    classSymbol = ((IdSymbol)classSymbol).getType();
                }
                if(classSymbol instanceof ClassSymbol){
                    Symbol functionSymbol = checkFunctionInherited(classSymbol,call.id.getToken().getText());
                    if(functionSymbol == null) {
                        if(exprSymbol.getName().equals("SELF_TYPE")) {
                            SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.id.getToken(), "Undefined method " + call.id.getToken().getText() + " in class " + ((IdSymbol) scope.lookup(call.getSymbol().getName())).getType());
                            return null;
                        }
                        SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.id.getToken(), "Undefined method " + call.id.getToken().getText() + " in class " + exprSymbol.getName());
                        return null;
                    }
                    if(((FunctionSymbol)functionSymbol).getFormals().size() != call.expressions.size()){
                        SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.id.getToken(), "Method " + call.id.getToken().getText() + " of class " + classSymbol.getName() + " is applied to wrong number of arguments");
                        return SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType());
                    }

                    int index = 0;
                    var formalIterator = ((FunctionSymbol)functionSymbol).getFormals().values().iterator();
                    var actualIterator = call.expressions.iterator();

                    while(formalIterator.hasNext() && actualIterator.hasNext()){
                        var formal = formalIterator.next();
                        var actual = actualIterator.next();

                        if(formal instanceof IdSymbol){
                            if(!((IdSymbol) formal).getType().getName().equals(actual.accept(this).getName())){
                                var common = findCommonLeftAncestor(SymbolTable.globals.lookup(actual.accept(this).getName()),SymbolTable.globals.lookup(((IdSymbol) formal).getType().getName()));
                                if(common == null) {
                                    SymbolTable.error(((ClassSymbol) currentClassScope).getCtx(), call.expressions.get(index).getToken(), "In call to method " + call.id.getToken().getText() + " of class " + classSymbol.getName() + ", actual type " + actual.accept(this).getName() + " of formal parameter " + ((IdSymbol) formal).getName() + " is incompatible with declared type " + ((IdSymbol) formal).getType().getName());
                                    return SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType());
                                }
                            }
                        }
                        index++;
                    }

                    if(call.expression instanceof ASTNode.New ){
                        return SymbolTable.globals.lookup(exprSymbol.getName());
                    }

                    if(SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType()).getName().equals("SELF_TYPE"))
                        return SymbolTable.globals.lookup("SELF_TYPE");
                    return SymbolTable.globals.lookup(((FunctionSymbol)functionSymbol).getType());
                }
            }
        }
        return null;
    }

    private boolean isSuperclass(Symbol superclass, Symbol subclass) {
        while (subclass != null) {
            if (subclass.getName().equals(superclass.getName())) {
                return true;
            }
            if (subclass instanceof ClassSymbol) {
                subclass = SymbolTable.globals.lookup(((ClassSymbol) subclass).getInheritedClass());
            } else {
                subclass = null;
            }
        }
        return false;
    }

    private Symbol checkFunctionInherited(Symbol lookup, String text) {
        if(lookup == null)
            return null;
        if(lookup instanceof ClassSymbol){
            Symbol functionSymbol = ((ClassSymbol) lookup).getSymbols().get(text + "_func");
            if(functionSymbol != null)
                return functionSymbol;
            else{
                return checkFunctionInherited(SymbolTable.globals.lookup(((ClassSymbol) lookup).getInheritedClass()),text);
            }
        }
        return null;
    }

    @Override
    public Symbol visit(ASTNode.Formal formal) {

        return null;
    }

    @Override
    public Symbol visit(ASTNode.Function function) {

        var scope = function.getScope();
        if(scope instanceof FunctionSymbol){
            if(SymbolTable.globals.lookup(function.type.getToken().getText()) == null){
                SymbolTable.error(((ClassSymbol)scope.getParent()).getCtx(),function.type.getToken(), "Class " + ((ClassSymbol)scope.getParent()).getName() + " has undefined return type " + function.type.getToken().getText());
                return null;
            }

            var inherited = SymbolTable.globals.lookup(((ClassSymbol)((FunctionSymbol)scope).getParent()).getInheritedClass());

            if(inherited != null){
                Symbol func = ((ClassSymbol)inherited).getSymbols().get(function.id.getToken().getText() + "_func");
                if(func != null){
                    if(func instanceof FunctionSymbol){
                        if(((FunctionSymbol)func).getFormals().size() != function.formals.size()){
                            SymbolTable.error(((ClassSymbol)scope.getParent()).getCtx(),function.id.getToken(), "Class " + ((ClassSymbol)scope.getParent()).getName() + " overrides method " + function.id.getToken().getText() + " with different number of formal parameters");
                            return null;
                        }

                        var index = 0;
                        var formalIterator = ((FunctionSymbol)func).getFormals().values().iterator();
                        var actualIterator = function.formals.iterator();

                        while(formalIterator.hasNext() && actualIterator.hasNext()){
                            var formal = formalIterator.next();
                            var actual = actualIterator.next();

                            if(formal instanceof IdSymbol){
                                if(!((IdSymbol) formal).getType().getName().equals(actual.type.getToken().getText())){
                                    SymbolTable.error(((ClassSymbol)scope.getParent()).getCtx(),function.formals.get(index).type.getToken(), "Class " + ((ClassSymbol)scope.getParent()).getName() + " overrides method " + function.id.getToken().getText() + " but changes type of formal parameter " + function.formals.get(index).id.getToken().getText() + " from " + ((IdSymbol) formal).getType().getName() + " to " + actual.type.getToken().getText());
                                    return null;
                                }
                            }
                            index++;

                        }

                        if(!((FunctionSymbol) func).getType().equals(function.type.getToken().getText())){
                            SymbolTable.error(((ClassSymbol)scope.getParent()).getCtx(),function.type.getToken(), "Class " + ((ClassSymbol)scope.getParent()).getName() + " overrides method " + function.id.getToken().getText() + " but changes return type from " + ((FunctionSymbol) func).getType() + " to " + function.type.getToken().getText());
                            return null;
                        }

                    }
                }
            }

            for(int i = 1 ; i < function.formals.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (function.formals.get(i).id.getToken().getText().equals(function.formals.get(j).id.getToken().getText())) {
                        SymbolTable.error(((ClassSymbol) scope.getParent()).getCtx(), function.formals.get(i).id.getToken(), "Method " + function.id.getToken().getText() + " of class " + ((ClassSymbol) scope.getParent()).getName() + " redefines formal parameter " + function.formals.get(i).id.getToken().getText());
                        return null;
                    }
                }
            }

            for(var formal : function.formals){
                if(SymbolTable.globals.lookup(formal.type.getToken().getText()) == null){
                    SymbolTable.error(((ClassSymbol)scope.getParent()).getCtx(),formal.type.getToken(), "Method " + function.id.getToken().getText() + " of class " + ((ClassSymbol)scope.getParent()).getName() + " has formal parameter " + formal.id.getToken().getText() + " with undefined type " + formal.type.getToken().getText());
                    return null;
                }
            }


        }

        function.id.accept(this);
        function.formals.forEach(f -> f.accept(this));
        Symbol typeSymbol = function.type.accept(this);
        Symbol funcSymbol = function.expression.accept(this);

        if(funcSymbol != null && typeSymbol != null){
            if(!funcSymbol.getName().equals(typeSymbol.getName())){
                if(funcSymbol.getName().equals("SELF_TYPE"))
                    funcSymbol = SymbolTable.globals.lookup(((ClassSymbol)scope.getParent()).getName());
                var common = findCommonLeftAncestor(SymbolTable.globals.lookup(funcSymbol.getName()),SymbolTable.globals.lookup(typeSymbol.getName()));
                if(common == null && !typeSymbol.getName().equals("Object"))
                    SymbolTable.error(((ClassSymbol)scope.getParent()).getCtx(),function.expression.getToken(), "Type " + funcSymbol.getName() + " of the body of method " + function.id.getToken().getText() + " is incompatible with declared return type " + typeSymbol.getName());
            }
        }

        return null;
    }

    @Override
    public Symbol visit(ASTNode.Variable variable) {

        var scope = variable.getScope();

        if(scope != null && SymbolTable.globals.lookup(variable.type.getToken().getText()) == null){
            SymbolTable.error(((ClassSymbol)scope).getCtx(),variable.type.getToken(), "Class " + ((ClassSymbol)scope).getName() + " has attribute " + variable.id.getToken().getText() + " with undefined type " + variable.type.getToken().getText());
        }

        if(scope != null && ((ClassSymbol)scope).redefinedinheritenceType(variable.id.getToken().getText())){
            SymbolTable.error(((ClassSymbol)scope).getCtx(),variable.id.getToken(), "Class " + ((ClassSymbol)scope).getName() + " redefines inherited attribute " + variable.id.getToken().getText());
            return null;
        }
        Symbol idSymbol = variable.id.accept(this);
        Symbol typeSymbol = variable.type.accept(this);
        if(variable.expression != null){
            Symbol expr = variable.expression.accept(this);
            if(expr != null && SymbolTable.globals.lookup(variable.type.getToken().getText()) != null){
                if(variable.expression.getToken().getText().equals("self") && variable.type.getToken().getText().equals("SELF_TYPE")){
                    return null;
                }
                if(!expr.getName().equals(variable.id.getSymbol().getType().getName())){
                    var common = findCommonLeftAncestor(expr,SymbolTable.globals.lookup(variable.id.getSymbol().getType().getName()));
                    if(common == null)
                        SymbolTable.error(((ClassSymbol)scope).getCtx(),variable.expression.getToken(), "Type " + expr.getName() + " of initialization expression of attribute " + variable.id.getToken().getText() + " is incompatible with declared type " + variable.type.getToken().getText());
                }
            }
        }


        return null;
    }

    @Override
    public Symbol visit(ASTNode.Program program) {

        program.stmts.forEach(stmt -> stmt.accept(this));

//        System.out.println(SymbolTable.globals);

//        if(SymbolTable.globals.lookup("Main") == null)
//            SymbolTable.error("No method main in class Main");

        return null;
    }

    @Override
    public Symbol visit(ASTNode.Class aClass) {

        var classScope = (ClassSymbol) aClass.getScope();

        if(classScope != null && classScope.getInherited()){
            if(SymbolTable.globals.lookup(classScope.getInheritedClass()) == null){
                SymbolTable.error(aClass.ctx,aClass.iType.getToken(),"Class " + aClass.type.getToken().getText() + " has undefined parent " + classScope.getInheritedClass());
            }

            Symbol lookup = SymbolTable.globals.lookup(classScope.getInheritedClass());
            if(lookup != null && lookup.getName() != null && (lookup.getName().equals("Int") || lookup.getName().equals("String") || lookup.getName().equals("Bool") || lookup.getName().equals("SELF_TYPE"))){
                SymbolTable.error(aClass.ctx,aClass.iType.getToken(),"Class " + aClass.type.getToken().getText() + " has illegal parent " + classScope.getInheritedClass());
            }

            if(classScope.isCycle()){
                SymbolTable.error(aClass.ctx,aClass.type.getToken(),"Inheritance cycle for class " + aClass.type.getToken().getText());
            }
        }

        aClass.type.accept(this);
        aClass.iType.accept(this);
        aClass.features.forEach(f -> f.accept(this));

        return null;
    }

    @Override
    public Symbol visit(ASTNode.Method method) {
        Symbol symbolMethod = method.id.accept(this);
        Scope scope = method.getScope();
        Scope classScope = scope;

        while (!(classScope instanceof ClassSymbol)) {
            classScope = classScope.getParent();
        }

        if(method.id.getToken().getText().equals("abort")){
            if(method.expressions.size() != 0){
                SymbolTable.error(((ClassSymbol) classScope).getCtx(), method.expressions.get(0).getToken(), "Method abort of class " + classScope + " is applied to wrong number of arguments");
            }
            return SymbolTable.globals.lookup("Object");
        }
        if(method.id.getToken().getText().equals("type_name")){
            if(method.expressions.size() != 0){
                SymbolTable.error(((ClassSymbol) classScope).getCtx(), method.expressions.get(0).getToken(), "Method type_name of class " + classScope + " is applied to wrong number of arguments");
            }
            return SymbolTable.globals.lookup("String");
        }
        if(method.id.getToken().getText().equals("copy")){
            if(method.expressions.size() != 0){
                SymbolTable.error(((ClassSymbol) classScope).getCtx(), method.expressions.get(0).getToken(), "Method copy of class " + classScope + " is applied to wrong number of arguments");
            }
            return SymbolTable.globals.lookup(((ClassSymbol) classScope).getName());
        }

        if(((ClassSymbol) classScope).getSymbols().containsKey(method.id.getToken().getText() + "_func")){
            int index = 0;

            var formalIterator = ((FunctionSymbol)((ClassSymbol) classScope).getSymbols().get(method.id.getToken().getText() + "_func")).getFormals().values().iterator();
            var actualIterator = method.expressions.iterator();

            while(formalIterator.hasNext() && actualIterator.hasNext()){
                var formal = formalIterator.next();
                var actual = actualIterator.next();

                if(formal instanceof IdSymbol){
                    if(!((IdSymbol) formal).getType().getName().equals(actual.accept(this).getName())){
                        var common = findCommonLeftAncestor(SymbolTable.globals.lookup(actual.accept(this).getName()),SymbolTable.globals.lookup(((IdSymbol) formal).getType().getName()));
                        if(common == null) {
                            SymbolTable.error(((ClassSymbol) classScope).getCtx(), method.expressions.get(index).getToken(), "In call to method " + method.id.getToken().getText() + " of class " + classScope + ", actual type " + actual.accept(this).getName() + " of formal parameter " + ((IdSymbol) formal).getName() + " is incompatible with declared type " + ((IdSymbol) formal).getType().getName());
                            return SymbolTable.globals.lookup(((FunctionSymbol)((ClassSymbol) classScope).getSymbols().get(method.id.getToken().getText() + "_func")).getType());
                        }
                    }
                }
                index++;
            }
            return SymbolTable.globals.lookup(((FunctionSymbol)((ClassSymbol) classScope).getSymbols().get(method.id.getToken().getText() + "_func")).getType());
        }
        else{
            Symbol inheritaced = SymbolTable.globals.lookup(((ClassSymbol) classScope).getInheritedClass());
            while(inheritaced != null){
                if(inheritaced instanceof ClassSymbol){
                    if(((ClassSymbol)inheritaced).getSymbols().containsKey(method.id.getToken().getText() + "_func")){
                        int index = 0;

                        var formalIterator = ((FunctionSymbol)((ClassSymbol)inheritaced).getSymbols().get(method.id.getToken().getText() + "_func")).getFormals().values().iterator();
                        var actualIterator = method.expressions.iterator();

                        while(formalIterator.hasNext() && actualIterator.hasNext()){
                            var formal = formalIterator.next();
                            var actual = actualIterator.next();

                            if(formal instanceof IdSymbol){
                                if(!((IdSymbol) formal).getType().getName().equals(actual.accept(this).getName())){
                                    var common = findCommonLeftAncestor(SymbolTable.globals.lookup(actual.accept(this).getName()),SymbolTable.globals.lookup(((IdSymbol) formal).getType().getName()));
                                    if(common == null) {
                                        SymbolTable.error(((ClassSymbol) classScope).getCtx(), method.expressions.get(index).getToken(), "In call to method " + method.id.getToken().getText() + " of class " + classScope + ", actual type " + actual.accept(this).getName() + " of formal parameter " + ((IdSymbol) formal).getName() + " is incompatible with declared type " + ((IdSymbol) formal).getType().getName());
                                        return SymbolTable.globals.lookup(((FunctionSymbol)((ClassSymbol)inheritaced).getSymbols().get(method.id.getToken().getText() + "_func")).getType());
                                    }
                                }
                            }
                            index++;
                        }
                        return SymbolTable.globals.lookup(((FunctionSymbol)((ClassSymbol)inheritaced).getSymbols().get(method.id.getToken().getText() + "_func")).getType());
                    }
                    inheritaced = SymbolTable.globals.lookup(((ClassSymbol)inheritaced).getInheritedClass());
                }
                else
                    inheritaced = null;
            }
        }

        return null;
    }

    @Override
    public Symbol visit(ASTNode.LRBrace lrBrace) {
        Scope scope = lrBrace.getScope();
        while(!(scope instanceof ClassSymbol)){
            scope = scope.getParent();
        }
        Symbol symbol = null;
        for(var expr : lrBrace.expressions){
            symbol = expr.accept(this);
        }
        return symbol;
    }

    @Override
    public Symbol visit(ASTNode.FalseLiteral falseLiteral) {
        return SymbolTable.globals.lookup("Bool");
    }
}
