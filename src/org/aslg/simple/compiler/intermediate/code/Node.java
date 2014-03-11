package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.lexer.Lexer;
import org.aslg.simple.compiler.parser.Parser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    int lexline = 0;

    Node() {
        lexline = Lexer.line;
    }

    void error(String s) {
        throw new Error("near line " + lexline + ": " + s);
    }

    static int labels = 0;

    public int newlabel() {
        return ++labels;
    }

    public void emitlabel(int i) {
        System.out.print("L" + i + ":");
    }

    public void emit(String s) {
        System.out.println("\t" + s);
        try {
            Parser.bw2.write(s);
            Parser.bw2.newLine();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
