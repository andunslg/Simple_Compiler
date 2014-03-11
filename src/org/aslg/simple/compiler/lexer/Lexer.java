package org.aslg.simple.compiler.lexer;

import org.aslg.simple.compiler.symbols.Type;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Lexer {

    InputStream inFile;
    public static int line = 1;
    char peek = ' ';
    Hashtable words = new Hashtable();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer( InputStream inFile) {
        this.inFile=inFile;
        reserve(Type.Int);
        reserve(Type.Float);
    }

    void readch() throws IOException {
        peek = (char) inFile.read();
    }

    boolean readch(char c) throws IOException {
        readch();
        if (peek != c) return false;
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        for (; ; readch()) {
            if (peek == ' ' || peek == '\t') continue;
            else if (peek == '\n') line = line + 1;
            else break;
        }
        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                readch();
            } while (Character.isDigit(peek));
            if (peek != '.') return new Num(v);
            float x = v;
            float d = 10;
            for (; ; ) {
                readch();
                if (!Character.isDigit(peek)) break;
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
            }
            return new Real(x);
        }
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                readch();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = (Word) words.get(s);
            if (w != null) return w;
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }
        Token tok = new Token(peek);
        peek = ' ';
        return tok;
    }
}
