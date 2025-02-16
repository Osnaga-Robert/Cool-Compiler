package cool.AST;

public interface ASTVisitor<T> {
    T visit(ASTNode.Id id);

    T visit(ASTNode.IntLiteral intLiteral);

    T visit(ASTNode.BoolLiteral boolLiteral);

    T visit(ASTNode.LogicOp logicOp);

    T visit(ASTNode.Arithmetic arithmetic);

    T visit(ASTNode.IsVoid isVoid);

    T visit(ASTNode.Neg neg);

    T visit(ASTNode.While aWhile);

    T visit(ASTNode.If anIf);

    T visit(ASTNode.New aNew);

    T visit(ASTNode.Assignation assignation);

    T visit(ASTNode.Reverse reverse);

    T visit(ASTNode.SelfLiteral selfLiteral);

    T visit(ASTNode.TrueLitetar trueLitetar);

    T visit(ASTNode.StringLiteral stringLiteral);

    T visit(ASTNode.Type type);

    T visit(ASTNode.LRParen lrParen);

    T visit(ASTNode.Case aCase);

    T visit(ASTNode.Let let);

    T visit(ASTNode.Call call);

    T visit(ASTNode.Formal formal);

    T visit(ASTNode.Function function);

    T visit(ASTNode.Variable variable);

    T visit(ASTNode.Program program);

    T visit(ASTNode.Class aClass);

    T visit(ASTNode.Method method);

    T visit(ASTNode.LRBrace lrBrace);

    T visit(ASTNode.FalseLiteral falseLiteral);
}
