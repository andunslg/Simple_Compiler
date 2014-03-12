package org.aslg.simple.compiler.symbols;

import org.aslg.simple.compiler.intermediate.code.Id;
import org.aslg.simple.compiler.lexer.Token;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SymbolTable {
    public Hashtable table = new Hashtable();

    public void add(Token t, Id i){
        table.put(t, i);
    }
    public Id get(Token t){
        Id id = (Id)table.get(t);
        if(id!=null) return id;
        else return null;
    }
}
