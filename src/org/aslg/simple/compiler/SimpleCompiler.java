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
public class SimpleCompiler {
    public static void main(String[] args) throws Exception {
        FileInputStream sourceFileInputStream=null;
        if (args.length >0 ) {
            sourceFileInputStream = new FileInputStream(new File(args[0]));
        }
        else{
            throw new Exception("Source code file location is missing.");
        }
        Lexer lexer = new Lexer(sourceFileInputStream);
        Parser parser = new Parser(lexer);
        parser.program();
    }
}
