package cool.AST;

import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;

import java.util.LinkedList;

public class ASTContructionVisitor extends CoolParserBaseVisitor {
    @Override
    public ASTNode visitFalse(CoolParser.FalseContext ctx) {
        return new ASTNode.FalseLiteral(ctx.FALSE().getSymbol());
    }

    @Override
    public ASTNode visitProgram(CoolParser.ProgramContext ctx) {
        LinkedList<ASTNode.Class> stmts = new LinkedList<>();
        for (var child : ctx.classes) {
            stmts.add((ASTNode.Class) visit(child));
        }
        return new ASTNode.Program(stmts, ctx.start);
    }

    @Override
    public ASTNode visitClass(CoolParser.ClassContext ctx) {
        LinkedList<ASTNode.Feature> features = new LinkedList<>();
        for(var feature : ctx.features){
            features.add((ASTNode.Feature)visit(feature));
        }

        return new ASTNode.Class(ctx.start,
                        features,
                        new ASTNode.Type(ctx.type_id),
                        new ASTNode.Type(ctx.inherits),
                        ctx);
    }

    @Override
    public ASTNode visitFunction(CoolParser.FunctionContext ctx) {
        LinkedList<ASTNode.Formal> formals = new LinkedList<>();
        for(var formal : ctx.formals){
            formals.add((ASTNode.Formal)visit(formal));
        }

        return new ASTNode.Function(ctx.start,
                            new ASTNode.Id(ctx.id),
                            formals,
                            new ASTNode.Type(ctx.type),
                            (ASTNode.Expression)visit(ctx.expression)
                            );
    }

    @Override
    public ASTNode visitVariable(CoolParser.VariableContext ctx) {
        if(ctx.expression == null)
            return new ASTNode.Variable(ctx.start,
                    new ASTNode.Id(ctx.id),
                    new ASTNode.Type(ctx.type),
                    null);
        return new ASTNode.Variable(ctx.start,
                            new ASTNode.Id(ctx.id),
                            new ASTNode.Type(ctx.type),
                            (ASTNode.Expression)visit(ctx.expression));
    }

    @Override
    public ASTNode visitFormal(CoolParser.FormalContext ctx) {
        return new ASTNode.Formal(ctx.start,
                            new ASTNode.Id(ctx.id),
                            new ASTNode.Type(ctx.type));
    }

    @Override
    public ASTNode visitNew(CoolParser.NewContext ctx) {
        return new ASTNode.New(ctx.NEW().getSymbol(),
                        new ASTNode.Type(ctx.type));
    }

    @Override
    public ASTNode visitMethod(CoolParser.MethodContext ctx) {
        LinkedList<ASTNode.Expression> expressions = new LinkedList<>();
        for(var expression : ctx.args){
            expressions.add((ASTNode.Expression) visit(expression));
        }
        return new ASTNode.Method(ctx.start,
                            new ASTNode.Id(ctx.method),
                            expressions);
    }

    @Override
    public ASTNode visitAssignation(CoolParser.AssignationContext ctx) {
        return new ASTNode.Assignation(ctx.start,
                                new ASTNode.Id(ctx.var),
                                (ASTNode.Expression)visit(ctx.expression));
    }

    @Override
    public ASTNode visitString(CoolParser.StringContext ctx) {
        return new ASTNode.StringLiteral(ctx.STRING().getSymbol());
    }

    @Override
    public ASTNode visitLrparen(CoolParser.LrparenContext ctx) {
        LinkedList<ASTNode.Expression> expressions = new LinkedList<>();
        for(var expression : ctx.exprs){
            expressions.add((ASTNode.Expression)visit(expression));
        }
        return new ASTNode.LRParen(ctx.start,
                            expressions);
    }

    @Override
    public ASTNode visitIsvoid(CoolParser.IsvoidContext ctx) {
        return new ASTNode.IsVoid(ctx.op,
                        (ASTNode.Expression)visit(ctx.expression));
    }

    @Override
    public ASTNode visitArithmetic(CoolParser.ArithmeticContext ctx) {
        return new ASTNode.Arithmetic(ctx.op,
                (ASTNode.Expression)visit(ctx.left),
                (ASTNode.Expression)visit(ctx.right));
    }

    @Override
    public ASTNode visitWhile(CoolParser.WhileContext ctx) {
        return new ASTNode.While(ctx.start,
                (ASTNode.Expression)visit(ctx.cond),
                (ASTNode.Expression)visit(ctx.body));
    }

    @Override
    public ASTNode visitReverse(CoolParser.ReverseContext ctx) {
        return new ASTNode.Reverse(ctx.NOT().getSymbol(),
                (ASTNode.Expression) visit(ctx.expression));
    }

    @Override
    public ASTNode visitLrbrace(CoolParser.LrbraceContext ctx) {
        LinkedList<ASTNode.Expression> expressions = new LinkedList<>();
        for (var expression : ctx.seq){
            expressions.add((ASTNode.Expression)visit(expression));
        }
        return new ASTNode.LRBrace(ctx.start, expressions);
    }

    @Override
    public ASTNode visitInt(CoolParser.IntContext ctx) {
        return new ASTNode.IntLiteral(ctx.INT().getSymbol());
    }

    @Override
    public ASTNode visitCall(CoolParser.CallContext ctx) {
        LinkedList<ASTNode.Expression> expressions = new LinkedList<>();
        for(var expression : ctx.args){
            expressions.add((ASTNode.Expression)visit(expression));
        }
        return new ASTNode.Call(ctx.start,
                (ASTNode.Expression)visit(ctx.calls),
                new ASTNode.Id(ctx.method),
                new ASTNode.Type(ctx.castType),
                expressions);

    }

    @Override
    public ASTNode visitNeg(CoolParser.NegContext ctx) {
        return new ASTNode.Neg(ctx.op,(ASTNode.Expression)visit(ctx.expression));
    }

    @Override
    public ASTNode visitTrue(CoolParser.TrueContext ctx) {
        return new ASTNode.TrueLitetar(ctx.TRUE().getSymbol());
    }

    @Override
    public ASTNode visitLogicop(CoolParser.LogicopContext ctx) {
        return new ASTNode.LogicOp(ctx.op,
                (ASTNode.Expression) visit(ctx.left),
                (ASTNode.Expression) visit((ctx.right)));
    }

    @Override
    public ASTNode visitLet(CoolParser.LetContext ctx) {
        LinkedList<ASTNode.Id> ids = new LinkedList<>();
        LinkedList<ASTNode.Type> types = new LinkedList<>();
        LinkedList<ASTNode.Expression> expressions = new LinkedList<>();

        for (var varDec : ctx.varDecs) {
            ids.add(new ASTNode.Id(varDec.ID().getSymbol()));
            types.add(new ASTNode.Type(varDec.TYPE_ID().getSymbol()));

            if (varDec.init != null) {
                expressions.add((ASTNode.Expression) visit(varDec.init));
            } else {
                expressions.add(null);
            }
        }
        return new ASTNode.Let(ctx.start, ids, types, expressions, (ASTNode.Expression) visit(ctx.body));
    }

    @Override
    public ASTNode visitId(CoolParser.IdContext ctx) {
        return new ASTNode.Id(ctx.ID().getSymbol());
    }

    @Override
    public ASTNode visitIf(CoolParser.IfContext ctx) {
        return new ASTNode.If(ctx.start,
                (ASTNode.Expression) visit(ctx.cond),
                (ASTNode.Expression) visit(ctx.then),
                (ASTNode.Expression) visit(ctx.elseBranch));
    }

    @Override
    public ASTNode visitCase(CoolParser.CaseContext ctx) {
        LinkedList<ASTNode.Id> ids = new LinkedList<>();
        LinkedList<ASTNode.Type> types = new LinkedList<>();
        LinkedList<ASTNode.Expression> expressions = new LinkedList<>();

        for(var id : ctx.caseVars)
            ids.add(new ASTNode.Id(id));
        for(var type : ctx.caseTypes)
            types.add(new ASTNode.Type(type));
        for(var expression : ctx.caseBodies)
            expressions.add((ASTNode.Expression)visit(expression));
        return new ASTNode.Case(ctx.start,
                (ASTNode.Expression) visit(ctx.caseExpr),
                ids,
                types,
                expressions);
    }
}
