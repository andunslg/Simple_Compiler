package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.lexer.Word;
import org.aslg.simple.compiler.symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Id extends Expr {

    public int offset;     // relative address

    public Id(Word id, Type p, int b) {
        super(id, p);
        offset = b;
    }

    @Override
    public String toString(){
        Word w = (Word)op;
        return w.lexeme;
    }
}
