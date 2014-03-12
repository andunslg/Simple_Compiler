package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.lexer.Lexer;
import org.aslg.simple.compiler.parser.Parser;

import java.io.IOException;

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

    public void writeThreeAddressCode(String s) {
        System.out.println("\t" + s);
        try {
            Parser.threeAddressWriter.write(s);
            Parser.threeAddressWriter.newLine();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
