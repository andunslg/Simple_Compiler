package org.aslg.simple.compiler.lexer;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Token {

    public final int tag;

    public Token(int t) {
        tag = t;
    }

    public String toString() {
        return "" + (char) tag;
    }
}
