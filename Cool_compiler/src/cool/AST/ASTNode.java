package cool.AST;

import cool.parser.CoolParser;
import cool.structures.ClassSymbol;
import cool.structures.IdSymbol;
import cool.structures.Scope;
import cool.structures.Symbol;
import org.antlr.v4.runtime.Token;

import java.util.LinkedList;

public abstract class ASTNode {

    protected Token token;
    public String debugStr = null;

    ASTNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }


    public static abstract class Expression extends ASTNode {
        Expression(Token token) {
            super(token);
        }
    }

    public static class Id extends Expression {
        public IdSymbol symbol;
        public Scope scope;

        Id(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public IdSymbol getSymbol() {
            return symbol;
        }

        public void setSymbol(IdSymbol symbol) {
            this.symbol = symbol;
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }

    public static class IntLiteral extends Expression {
        public IntLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class BoolLiteral extends Expression {
        public BoolLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class StringLiteral extends Expression {
        public StringLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class TrueLitetar extends Expression {
        public TrueLitetar(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class FalseLiteral extends Expression {
        public FalseLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class SelfLiteral extends Expression {
        public SelfLiteral(Token token) {
            super(token);
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }


    public static class If extends Expression {
        public Expression cond;
        public Expression thenB;
        public Expression elseB;
        Scope scope;

        public If(Token token, Expression cond, Expression thenB, Expression elseB) {
            super(token);
            this.cond = cond;
            this.thenB = thenB;
            this.elseB = elseB;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class While extends Expression {
        public Expression cond;
        public Expression body;
        Scope scope;

        public While(Token token, Expression cond, Expression body) {
            super(token);
            this.cond = cond;
            this.body = body;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Neg extends Expression {
        public Expression expression;
        Scope scope;

        public Neg(Token token, Expression expression) {
            super(token);
            this.expression = expression;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class IsVoid extends Expression {
        public Expression expression;
        public Scope scope;

        public IsVoid(Token token, Expression expression) {
            super(token);
            this.expression = expression;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Arithmetic extends Expression {
        public Expression left;
        public Expression right;
        Scope scope;

        public Arithmetic(Token token, Expression left, Expression right) {
            super(token);
            this.left = left;
            this.right = right;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }

    public static class LogicOp extends Expression {
        public Expression left;
        public Expression right;
        public Scope scope;

        public LogicOp(Token token, Expression left, Expression right) {
            super(token);
            this.left = left;
            this.right = right;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class New extends Expression {
        public Type type;
        Scope scope;

        public New(Token token, Type type) {
            super(token);
            this.type = type;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Reverse extends Expression {
        public Expression expression;
        Scope scope;

        public Reverse(Token token, Expression expression) {
            super(token);
            this.expression = expression;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Assignation extends Expression {
        public Id id;
        public Expression expression;
        Scope scope;

        public Assignation(Token token, Id id, Expression expression) {
            super(token);
            this.id = id;
            this.expression = expression;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Call extends Expression {
        public Expression expression;
        public Id id;
        public Type type;
        public LinkedList<Expression> expressions;
        Scope scope;
        Symbol symbol;

        public Call(Token token, Expression expression, Id id, Type type, LinkedList<Expression> expressions) {
            super(token);
            this.expression = expression;
            this.id = id;
            this.expressions = expressions;
            this.type = type;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }

        public void setSymbol(Symbol symbol) {
            this.symbol = symbol;
        }

        public Symbol getSymbol() {
            return symbol;
        }
    }

    public static class Let extends Expression {
        public LinkedList<Id> ids;
        public LinkedList<Type> types;
        public LinkedList<Expression> inits;
        public Expression body;
        public Scope scope;

        public Let(Token token, LinkedList<Id> ids, LinkedList<Type> types, LinkedList<Expression> inits, Expression body) {
            super(token);
            this.ids = ids;
            this.types = types;
            this.inits = inits;
            this.body = body;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }

    public static class Case extends Expression {
        public Expression caseExpr;
        public LinkedList<Id> ids;
        public LinkedList<Type> types;
        public LinkedList<Expression> bodies;
        public Scope scope;

        public Case(Token token, Expression caseExpr, LinkedList<Id> ids, LinkedList<Type> types, LinkedList<Expression> bodies) {
            super(token);
            this.caseExpr = caseExpr;
            this.ids = ids;
            this.types = types;
            this.bodies = bodies;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }

    public static class LRParen extends Expression {
        public LinkedList<Expression> expressions;

        public LRParen(Token token, LinkedList<Expression> expressions) {
            super(token);
            this.expressions = expressions;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Method extends Expression {
        public Id id;
        public LinkedList<Expression> expressions;
        Scope scope;

        public Method(Token token, Id id, LinkedList<Expression> expressions) {
            super(token);
            this.id = id;
            this.expressions = expressions;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class LRBrace extends Expression {
        public LinkedList<Expression> expressions;
        Scope scope;

        public LRBrace(Token token, LinkedList<Expression> expressions) {
            super(token);
            this.expressions = expressions;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Feature extends ASTNode {
        public Feature(Token token) {
            super(token);
        }
    }

    public static class Type extends ASTNode {
        Type(Token token) {
            super(token);
        }
        Scope scope;

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Formal extends ASTNode {
        public Id id;
        public Type type;

        public Formal(Token token, Id id, Type type) {
            super(token);
            this.id = id;
            this.type = type;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Function extends Feature {
        public Id id;
        public LinkedList<Formal> formals;
        public Type type;
        public Expression expression;
        public Scope scope;

        public Function(Token token, Id id, LinkedList<Formal> formals, Type type, Expression expression) {
            super(token);
            this.id = id;
            this.formals = formals;
            this.type = type;
            this.expression = expression;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public void setScope(Scope currentScope) {
            this.scope = currentScope;
        }

        public Scope getScope() {
            return scope;
        }
    }

    public static class Variable extends Feature {
        public Id id;
        public Type type;
        public Expression expression;
        public Scope scope;

        public Variable(Token token, Id id, Type type, Expression expression) {
            super(token);
            this.id = id;
            this.type = type;
            this.expression = expression;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }

    public static class Program extends ASTNode {
        public LinkedList<Class> stmts;

        Program(LinkedList<Class> stmts, Token token) {
            super(token);
            this.stmts = stmts;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Class extends ASTNode {
        public LinkedList<Feature> features;
        public Type type;
        public Type iType;
        public ClassSymbol symbol;
        public Scope scope;
        public CoolParser.ClassContext ctx;

        public Class(Token token, LinkedList<Feature> features, Type type, Type iType, CoolParser.ClassContext ctx) {
            super(token);
            this.features = features;
            this.type = type;
            this.iType = iType;
            this.ctx = ctx;
        }

        public <T> T accept(ASTVisitor<T> visitor) {
            return visitor.visit(this);
        }

        public ClassSymbol getSymbol() {
            return symbol;
        }

        public void setSymbol(ClassSymbol symbol) {
            this.symbol = symbol;
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }
}
                 
                 