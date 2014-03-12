package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.lexer.Token;
import org.aslg.simple.compiler.symbols.Type;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Arith extends Expr {
    public Expr exprl , expr2;
    public static Hashtable<String,Temp> expressions = new Hashtable<String,Temp>();
    public Arith (Token tok , Expr x1 , Expr x2) {
        super(tok , Type.max(x1.type, x2.type));
        exprl = x1; expr2 = x2;
    }

    @Override
    public Expr gen(){
        return new Arith (op, exprl.reduce(), expr2.reduce());
    }
    @Override
    public Expr reduce(){
        exprl = widen(exprl, exprl.type, type);
        expr2 = widen(expr2, expr2.type, type);
        Expr x = gen();

        Temp t = (Temp)expressions.get(x.toString());
        if(t == null){
            t = new Temp(type);
            emit(t.toString()+ " = " + x.toString());
            expressions.put(x.toString(), t);
            return t;
        }
        return t;
    }
    @Override
    public String toString () {
        return exprl.toString()+" "+op.toString()+" "+expr2.toString();
    }
    public Expr widen(Expr expr1,Type t1, Type t2){
        if(t1.lexeme.equals(t2.lexeme)) return expr1;
        else if(t1 == Type.Int && t2 == Type.Float){
            Temp temp = (Temp)expressions.get(expr1.toString());
            if(temp == null){
                temp = new Temp(type);
                emit(temp.toString() + " = (float)" + expr1.toString());
                expressions.put(expr1.toString(), temp);
                return temp;
            }
            return temp;
        }
        else error("Type mismatch");
        return null;
    }
}
