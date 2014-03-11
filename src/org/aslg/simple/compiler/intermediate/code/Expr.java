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

    public void jumping(int t, int f) {
        emitjumps(toString(), t, f);
    }

    public void emitjumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        } else if (t != 0) emit("if " + test + " goto L" + t);
        else if (f != 0) emit("iffalse " + test + " goto L" + f);
        else ; // nothing since both t and f fall through
    }

    public String toString() {
        return op.toString();
    }

}
