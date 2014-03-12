package org.aslg.simple.compiler.parser;

import org.aslg.simple.compiler.intermediate.code.*;
import org.aslg.simple.compiler.lexer.*;
import org.aslg.simple.compiler.symbols.SymbolTable;
import org.aslg.simple.compiler.symbols.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    public static BufferedWriter postFixWriter;
    public static BufferedWriter threeAddressWriter;

    public static String tempPostFixString="";
    public static ArrayList<String> postFixStrings=new ArrayList<String>();

    private SymbolTable symbolTable=new SymbolTable();

    private Lexer lexer;
    private Token lookAheadToken;

    private Expr e;
    private int offset = 0;

    public Parser (Lexer l) throws IOException {
        postFixWriter =new BufferedWriter(new FileWriter("Compiled_PostFix.txt"));
        threeAddressWriter =new BufferedWriter(new FileWriter("Compiled_ThreeAddress.txt"));

        lexer = l;
        move();
    }

    void move() throws IOException {
           lookAheadToken = lexer.scan();
    }

    void error(String s){
        throw new Error ("near line" +Lexer.line+" : " +s);
    }
    void match(int t) throws IOException {
        if(lookAheadToken.tag == t){
            move();
        }
        else error ("syntax error") ;
    }

    public void program() throws IOException{
        declaration();
        list();
    }

    private void declaration() throws IOException {
        if(lookAheadToken.tag != Tag.BASIC){
            error ("syntax error") ;
        }
        while(lookAheadToken.tag == Tag.BASIC){
            Type t = type();
            id(t);
            match(';');
        }
    }
    private Type type() throws IOException{
        Token tok = lookAheadToken;
        match(Tag.BASIC);
        Type p = (Type)tok;
        return p;

    }
    private void id(Type t) throws IOException{
        Token tok = lookAheadToken;
        match(Tag.ID);
        Id id = new Id((Word)tok,t,offset);
        symbolTable.add(tok,id);
        offset += t.width;
        idOne(t);
    }
    private void idOne(Type t) throws IOException{
        while(lookAheadToken.tag == ','){
            move();
            Token tok = lookAheadToken;
            match(Tag.ID);
            Id id = new Id((Word)tok,t,offset);
            symbolTable.add(tok,id);
        }
    }

    private void list() throws IOException{
        while(lookAheadToken.tag != Tag.EOF){
            Expr e = statement();
            match(';');

            emit(postFixWriter, "; ");
            postFixWriter.newLine();
        }
        postFixWriter.close();
        threeAddressWriter.close();
    }

    private Expr statement() throws IOException{

        if(lookAheadToken.tag == '(' || lookAheadToken.tag == Tag.NUM || lookAheadToken.tag == Tag.REAL){
            expression();
            Expr t = e.gen();
            return t.reduce();
        }else if( lookAheadToken.tag == Tag.ID){
            Token temp = lookAheadToken;
            match(Tag.ID);
            Word w = (Word)temp;
            if(lookAheadToken.tag == '='){
                emit(postFixWriter,w.lexeme+" ");
                move();
                Id id = symbolTable.get(temp);
                if(id == null){
                    error (w.lexeme + " not defined") ;
                }
                Stmt s =new Set(id,expression());
                emit(postFixWriter,"= ");
                return s.gen();
            }else{
                lookAheadToken = temp;
                expression();
                Expr t = e.gen();
                return t.reduce();
            }
        }else
            error ("syntax error") ;
        return null;
    }

    private Expr expression() throws IOException{
        e =term();
        e = expressionOne(e);
        return e;
    }

    private Expr expressionOne(Expr e) throws IOException{
        while(lookAheadToken.tag == '+'){
            Token t = lookAheadToken;
            move();
            e = new Arith(t, e, term());
            emit(postFixWriter,"+ ");
        }
        return e;
    }
    private Expr term() throws IOException{
        e = factor();
        return termOne(e);
    }
    private Expr termOne(Expr e) throws IOException{
        while(lookAheadToken.tag == '*'){
            Token t = lookAheadToken;
            move();
            e = new Arith(t, e, factor());
            emit(postFixWriter,"* ");
        }
        return e;
    }
    private Expr factor() throws IOException{
        Expr x = null;
        switch(lookAheadToken.tag){
            case '(':
                match('(');
                x = expression();
                match(')');
                return x;
            case Tag.NUM:                   //case for integers
                Num num = (Num) lookAheadToken;
                emit(postFixWriter,num.value+" ");
                x = new Expr(lookAheadToken,Type.Int);
                move();
                return x;
            case Tag.ID:
                x = symbolTable.get(lookAheadToken);
                Word w = (Word) lookAheadToken;
                if(x == null){
                    error (w.lexeme + " not defined") ;
                }
                emit(postFixWriter,w.lexeme+" ");
                move();         //case for identifiers
                return x;
            case Tag.REAL:
                Real real = (Real) lookAheadToken;
                emit(postFixWriter,real.value+" ");
                x = new Expr(lookAheadToken, Type.Float);
                move();        //case for floating point number
                return x;
            default:
                error ("syntax error");
                return x;
        }
    }

    private void emit(BufferedWriter bw,String s) throws IOException{
        bw.write(s);
    }
}
