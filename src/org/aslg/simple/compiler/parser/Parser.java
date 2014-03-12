package org.aslg.simple.compiler.parser;

import org.aslg.simple.compiler.intermediate.code.*;
import org.aslg.simple.compiler.lexer.*;
import org.aslg.simple.compiler.symbols.SymbolTable;
import org.aslg.simple.compiler.symbols.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    private Lexer lex;
    private Token look;

    private Expr e;
    private int offset = 0;

    private Stack<Float> results = new Stack<Float>();
    private Hashtable<Token,Float> var= new Hashtable<Token,Float>();

    private SymbolTable symbolTable=new SymbolTable();

    public Parser (Lexer l) throws IOException {
        postFixWriter =new BufferedWriter(new FileWriter("Compiled_PostFix.txt"));
        threeAddressWriter =new BufferedWriter(new FileWriter("Compiled_ThreeAddress.txt"));

        lex = l;
        move();
    }

    void move() throws IOException {
           look = lex.scan();
    }

    void error(String s){
        throw new Error ("near line" +Lexer.line+" : " +s);
    }
    void match(int t) throws IOException {
        if(look.tag == t){
            move();
        }
        else error ("syntax error") ;
    }

    public void program() throws IOException{
        declaration();
        list();
    }

    private void declaration() throws IOException {
        if(look.tag != Tag.BASIC){
            error ("syntax error") ;
        }
        while(look.tag == Tag.BASIC){
            Type t = type();
            id(t);
            match(';');
        }
    }
    private Type type() throws IOException{
        Token tok = look;
        match(Tag.BASIC);
        Type p = (Type)tok;
        return p;

    }
    private void id(Type t) throws IOException{
        Token tok = look;
        match(Tag.ID);
        Id id = new Id((Word)tok,t,offset);
        symbolTable.add(tok,id);
        offset += t.width;
        idOne(t);
    }
    private void idOne(Type t) throws IOException{
        while(look.tag == ','){
            move();
            Token tok = look;
            match(Tag.ID);
            Id id = new Id((Word)tok,t,offset);
            symbolTable.add(tok,id);
        }
    }

    private void list() throws IOException{
        while(look.tag != Tag.EOF){
            Expr e = statment();
            match(';');

            emit(postFixWriter, "; ");
            Float num = results.pop();

            if(e.type == Type.Int){
                emit(postFixWriter,Integer.toString(num.intValue()));
            }else if(e.type == Type.Float){
                emit(postFixWriter,num.toString());
            }

            postFixWriter.newLine();
        }
        postFixWriter.close();
        threeAddressWriter.close();
    }

    private Expr statment() throws IOException{

        if(look.tag == '(' || look.tag == Tag.NUM || look.tag == Tag.REAL){
            expression();
            Expr t = e.gen();
            return t.reduce();
        }else if( look.tag == Tag.ID){
            Token temp = look;
            match(Tag.ID);
            Word w = (Word)temp;
            if(look.tag == '='){
                emit(postFixWriter,w.lexeme+" ");
                move();
                Id id = symbolTable.get(temp);
                if(id == null){
                    error (w.lexeme + " not defined") ;
                }
                Stmt s =new Set(id,expression());
                emit(postFixWriter,"= ");
                var.put(w, results.peek());
                return s.gen();
            }else{
                look = temp;
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
        while(look.tag == '+'){
            Token t = look;
            move();
            e = new Arith(t, e, term());
            emit(postFixWriter,"+ ");
            getOperationResult(t);
        }
        return e;
    }
    private Expr term() throws IOException{
        e = factor();
        return termOne(e);
    }
    private Expr termOne(Expr e) throws IOException{
        while(look.tag == '*'){
            Token t = look;
            move();
            e = new Arith(t, e, factor());
            emit(postFixWriter,"* ");
            getOperationResult(t);
        }
        return e;
    }
    private Expr factor() throws IOException{
        Expr x = null;
        switch(look.tag){
            case '(':
                match('(');
                x = expression();
                match(')');
                return x;
            case Tag.NUM:                   //case for integers
                Num num = (Num)look;
                emit(postFixWriter,num.value+" ");
                x = new Expr(look,Type.Int);
                results.push((float) num.value);
                move();
                return x;
            case Tag.ID:
                x = symbolTable.get(look);
                Word w = (Word)look;
                if(x == null){
                    error (w.lexeme + " not defined") ;
                }
                emit(postFixWriter,w.lexeme+" ");
                float val = 0;
                if(var.get(w) != null){
                    val=var.get(w);
                }
                results.push(val);
                move();         //case for identifiers
                return x;
            case Tag.REAL:
                Real real = (Real)look;
                emit(postFixWriter,real.value+" ");
                x = new Expr(look, Type.Float);
                results.push((float) real.value);
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

    private void getOperationResult(Token operator){
        float num1 =  results.pop(), num2 = results.pop(), result = 0;
        switch(operator.tag){
            case '+':
                result = num1+num2;
                break;
            case '*':
                result = num1*num2;
                break;
            default:
                error("something went wrong!");

        }
        results.push(result);
    }
}
