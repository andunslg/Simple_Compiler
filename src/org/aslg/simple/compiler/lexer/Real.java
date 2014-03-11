package org.aslg.simple.compiler.lexer;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */

public class Real extends Token {

    public final float value;

    public Real(float v) {
        super(Tag.REAL);
        value = v;
    }

    public String toString() {
        return "" + value;
    }
}
