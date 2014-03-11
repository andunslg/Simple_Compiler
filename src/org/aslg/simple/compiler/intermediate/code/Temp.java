package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.lexer.Tag;
import org.aslg.simple.compiler.lexer.Word;
import org.aslg.simple.compiler.symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Temp extends Expr {

    static int count = 0;
    int number = 0;
    public Temp(Type p) {
        super(new Word("t",Tag.TEMP),p);
        number = count++;
    }
    @Override
    public String toString(){
        return "t" + number;
    }
}
