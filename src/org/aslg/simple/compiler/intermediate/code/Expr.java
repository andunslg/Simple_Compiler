package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.lexer.Token;
import org.aslg.simple.compiler.symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Expr extends Node{

    public Token op;
    public Type type;

    public Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }

    public Expr gen() {
        return this;
    }

    public Expr reduce() {
        return this;
    }

    public String toString() {
        return op.toString();
    }

}
