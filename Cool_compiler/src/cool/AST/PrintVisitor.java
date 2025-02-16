package cool.AST;

public class PrintVisitor implements ASTVisitor<Void>{

    int indent = 0;

    @Override
    public Void visit(ASTNode.Id id) {
        if(id.token != null)
            printIndent(id.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.IntLiteral intLiteral) {
        printIndent(intLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.BoolLiteral boolLiteral) {
        if(boolLiteral.token != null)
            printIndent(boolLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.LogicOp logicOp) {
        printIndent(logicOp.getToken().getText());
        indent += 2;
        logicOp.left.accept(this);
        logicOp.right.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Arithmetic arithmetic) {
        printIndent(arithmetic.getToken().getText());
        indent += 2;
        arithmetic.left.accept(this);
        arithmetic.right.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.IsVoid isVoid) {
        printIndent("isvoid");
        indent += 2;
        isVoid.expression.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Neg neg) {
        printIndent("~");
        indent += 2;
        neg.expression.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.While aWhile) {
        printIndent("while");
        indent += 2;
        aWhile.cond.accept(this);
        aWhile.body.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.If anIf) {
        printIndent("if");
        indent += 2;
        anIf.cond.accept(this);
        anIf.thenB.accept(this);
        anIf.elseB.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.New aNew) {
        printIndent("new");
        indent += 2;
        aNew.type.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Assignation assignation) {
        printIndent("<-");
        indent += 2;
        assignation.id.accept(this);
        assignation.expression.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Reverse reverse) {
        printIndent("not");
        indent += 2;
        reverse.expression.accept(this);
        indent -= 2;
        return  null;
    }

    @Override
    public Void visit(ASTNode.SelfLiteral selfLiteral) {
        if(selfLiteral.token != null)
            printIndent(selfLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.TrueLitetar trueLitetar) {
        if(trueLitetar.token != null)
            printIndent(trueLitetar.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.StringLiteral stringLiteral) {
        if(stringLiteral.token != null)
            printIndent(stringLiteral.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.Type type) {
        if(type.token != null)
            printIndent(type.token.getText());
        return null;
    }

    @Override
    public Void visit(ASTNode.LRParen lrParen) {
        lrParen.expressions.forEach(s -> s.accept(this));
        return null;
    }

    @Override
    public Void visit(ASTNode.Case aCase) {
        printIndent("case");
        indent += 2;
        aCase.caseExpr.accept(this);
        for(int i = 0 ; i < aCase.ids.size() ; i++){
            printIndent("case branch");
            indent += 2;
            aCase.ids.get(i).accept(this);
            aCase.types.get(i).accept(this);
            aCase.bodies.get(i).accept(this);
            indent -= 2;
        }
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Let let) {
        printIndent("let");
        for(int i = 0 ; i < let.ids.size() ; i++){
            indent += 2;
            printIndent("local");
            indent += 2;
            let.ids.get(i).accept(this);
            let.types.get(i).accept(this);
            if (let.inits.get(i) != null) {
                let.inits.get(i).accept(this);
            }
            indent -= 2;
            indent -= 2;
        }
        indent += 2;
        let.body.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Call call) {
        printIndent(".");
        indent += 2;
        call.expression.accept(this);
        call.type.accept(this);
        call.id.accept(this);
        call.expressions.forEach(s -> s.accept(this));
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Formal formal) {
        printIndent("formal");
        indent += 2;
        formal.id.accept(this);
        formal.type.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Function function) {
        printIndent("method");
        indent += 2;
        function.id.accept(this);
        function.formals.forEach(s -> s.accept(this));
        function.type.accept(this);
        function.expression.accept(this);
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Variable variable) {
        printIndent("attribute");
        indent += 2;
        variable.id.accept(this);
        variable.type.accept(this);
        if(variable.expression != null)
            variable.expression.accept(this);
        indent -= 2;

        return null;
    }

    @Override
    public Void visit(ASTNode.Program program) {
        printIndent("program");
        indent += 2;
        program.stmts.forEach(s -> s.accept(this));
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Class aClass) {
        printIndent("class");
        indent += 2;
        aClass.type.accept(this);
        aClass.iType.accept(this);
        aClass.features.forEach(s -> s.accept(this));
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.Method method) {
        printIndent("implicit dispatch");
        indent += 2;
        method.id.accept(this);
        method.expressions.forEach(s -> s.accept(this));
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.LRBrace lrBrace) {
        printIndent("block");
        indent += 2;
        lrBrace.expressions.forEach(s -> s.accept(this));
        indent -= 2;
        return null;
    }

    @Override
    public Void visit(ASTNode.FalseLiteral falseLiteral) {
        if(falseLiteral.token != null)
            printIndent(falseLiteral.token.getText());
        return null;
    }

    void printIndent(String str) {
        for (int i = 0; i < indent; i++)
            System.out.print(" ");
        System.out.println(str);
    }
}
