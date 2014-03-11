package org.aslg.simple.compiler.lexer;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Num extends Token {
    public final int value;

    public Num(int v) {
        super(Tag.NUM);
        value = v;
    }

    public String toString() {
        return "" + value;
    }
}
