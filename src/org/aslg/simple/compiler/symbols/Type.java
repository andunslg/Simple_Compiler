package org.aslg.simple.compiler.symbols;

import org.aslg.simple.compiler.lexer.Tag;
import org.aslg.simple.compiler.lexer.Word;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */

public class Type extends Word {

    public int width = 0;          // width is used for storage allocation
    public Type(String s, int tag, int w) {
        super(s, tag);
        width = w;
    }

    public static final Type
            Int = new Type("int", Tag.BASIC, 4),
            Float = new Type("float", Tag.BASIC, 8);

    public static boolean numeric(Type p) {
        if (p == Type.Int || p == Type.Float) return true;
        else return false;
    }

    public static Type max(Type p1, Type p2) {
        if (!numeric(p1) || !numeric(p2)) return null;
        else if (p1 == Type.Float || p2 == Type.Float) return Type.Float;
        else return Type.Int;
    }

}