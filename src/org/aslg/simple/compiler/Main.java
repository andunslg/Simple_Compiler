package org.aslg.simple.compiler;

import org.aslg.simple.compiler.lexer.Lexer;
import org.aslg.simple.compiler.parser.Parser;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        File inFile = null;
        if (0 < args.length) {
            inFile = new File(args[0]);
        }
        else{
            throw new Exception("input source file not specified");
        }
        Lexer l = new Lexer(new FileInputStream(inFile));
        Parser p = new Parser(l);
        p.program();
    }
}
