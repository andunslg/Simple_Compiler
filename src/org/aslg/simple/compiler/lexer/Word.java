package org.aslg.simple.compiler.lexer;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word extends Token {

    public String lexeme = "";

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    public String toString() {
        return lexeme;
    }
}
