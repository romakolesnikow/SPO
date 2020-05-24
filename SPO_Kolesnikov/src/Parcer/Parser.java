package Parcer;

import java.util.LinkedList;
import java.util.Queue;
import Lexer.*;

public class Parser {
    private static Token token;
    private static Token tempVar;
    private static LinkedList<Token> tokens;
    private static int pos = 0;
    public static void parse(LinkedList<Token> input) throws Exception {
        tokens = new LinkedList<>(input);

        while(pos < tokens.size()) {
            lang();
        }
    }

    private static void match() {
        token = tokens.get(pos);
    }

    private static int lang() throws Exception {
        return expr(pos);
    }

    private static int expr(int startPos) throws Exception {
        try {
        //    System.out.println(1);
            return whileExpr(pos);
        }
        catch (Exception ex) {
            try{
                pos = startPos;
          //      System.out.println(2);
                return ifExpr(pos);
            }
            catch (Exception e){
                try {
            //        System.out.println(3);
                    pos = startPos;
                    return declarationExpr(pos);
                }
                catch (Exception ex1) {
                    try {
              //          System.out.println(4);
                        pos = startPos;
                        return funcExpr(pos);
                    }
                    catch (Exception ex2) {
                //        System.out.println(5);
                        pos = startPos;
                        return assignExpr(pos);

                    }
                }
            }
        }
    }

    private static int funcExpr(int startPos) throws Exception {
        try
        {
            pos = var(pos);
            pos = assignOp(pos);
            pos = var(pos);
            pos = method(pos);
            pos = func(pos);
            pos = lb(pos);
            pos = arExpr(pos);
            pos = rb(pos);
            pos = cOp(pos);
            return pos;

        }catch (Exception e){
            pos = startPos;
            pos = var(pos);
            pos = method(pos);
            pos = func(pos);
            pos = lb(pos);
            pos = arExpr(pos);
            pos = rb(pos);
            pos = cOp(pos);
            return pos;
        }
    }

    private static int assignOp1(int startPos) throws Exception {
        match();
        if (!token.type.equals(Lexem.ASSIGN_OP1)) {
            throw new Exception("Вместо assignOp1 найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }
    private static int method(int startPos) throws Exception
    {
        match();
        if (token.type != Lexem.METHOD) {
            throw new Exception("Вместо METHOD найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }
    private static int whileExpr(int startPos) throws Exception {
        pos = whileW(pos);
        pos = term(pos);
        pos = body(pos);
        return pos;
    }
    private static int ifExpr(int startPos) throws Exception {
        pos = ifW(pos);
        pos = term(pos);
        pos = body(pos);
        return pos;
    }

    private static int term(int startPos) throws Exception {
        pos = lb(pos);
        pos = boolExpr(pos);
        pos = rb(pos);
        return pos;
    }

    private static int boolExpr(int startPos) throws Exception {
        try {
            pos = arExpr(pos);
        }
        catch (Exception ex) {
            pos = startPos;
            pos = operand(pos);
        }
        pos = boolOp(pos);
        startPos = pos;
        try {
            pos = arExpr(pos);
        }
        catch (Exception ex) {
            pos = startPos;
            pos = operand(pos);
        }
        return pos;
    }

    private static int operand(int startPos) throws Exception {
        try {
            return var(pos);
        }
        catch (Exception ex) {
            try {
                pos = startPos;
                return num(pos);
            }
            catch (Exception ex2) {
                pos = startPos;
                return bracketExpr(pos);
            }
        }
    }

    private static int bracketExpr(int startPos) throws Exception {
        pos = lb(pos);
        pos = inBrackets(pos);
        pos = rb(pos);
        return pos;
    }

    private static int inBrackets(int startPos) throws Exception {
        try {
            pos = bracketExpr(pos);
        }
        catch (Exception ex) {
            pos = startPos;
            pos = arExpr(pos);
        }
        return pos;
    }

    private static int arExpr(int startPos) throws Exception {
        int tmp = startPos;
        pos = operand(pos);
        startPos = pos;
        while (true) {
                try {
                    pos = arOp(pos);
                }catch (Exception e)
                {
                    break;
                }
                try {
                    pos = operand(pos);
                }catch (Exception e)
                {
                    throw new Exception(e.getMessage());
                }
        }
        return pos;
    }

    private static int body(int startPos) throws Exception {
        pos = lcb(pos);
        pos = bodyExpr(pos);
        pos = rcb(pos);
        return  pos;
    }

    private static int bodyExpr(int startPos) throws Exception {
        pos = expr(pos);
        startPos = pos;
        while (true) {
            try {
                pos = expr(pos);
                startPos = pos;
            } catch (Exception ex) {
                pos = startPos;
                break;
            }
        }
        return pos;
    }

    private static int assignExpr(int startPos) throws Exception {

        pos = var(pos);
        startPos = pos;
        try {
        pos = assignOp(pos);
        }catch (Exception ex)
        {
            pos = startPos;
            pos = incAndDec(pos);
            pos = cOp(pos);
            return pos;
        }
        startPos = pos;
        try {
            pos = arExpr(pos);
        }
        catch (Exception ex) {
            pos  = startPos;
            pos = operand(pos);
        }

        pos = cOp(pos);
        return pos;

    }
    private static int incAndDec(int startPos) throws Exception{
        try {
        pos = inc(startPos);
        }catch (Exception ex)
        {
            pos = startPos;
            pos = dec(startPos);
        }
        return pos;
    }

    private static int declarationExpr(int startPos) throws Exception {
        pos = var(pos);
        pos = typeW(pos);
        pos = type(pos);
        pos = cOp(pos);
        return pos;
    }

    private static int whileW(int startPos) throws Exception {
        match();
        if (token.type != Lexem.WHILE) {
            throw new Exception("Вместо while найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }
    private static int ifW(int startPos) throws Exception {
        match();
        if (token.type != Lexem.IF) {
            throw new Exception("Вместо if найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int typeW(int startPos) throws Exception {
        match();
        if (token.type != Lexem.TYPE_W) {
            throw new Exception("Вместо new найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int type(int startPos) throws Exception {
        match();
        if (token.type != Lexem.TYPE) {
            throw new Exception("Вместо type найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int lb(int startPos) throws Exception {
        match();
        if (token.type != Lexem.LB) {
            throw new Exception("Вместо lb найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int rb(int startPos) throws Exception {
        match();
        if (token.type != Lexem.RB) {
            throw new Exception("Вместо rb найдено " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int boolOp(int startPos) throws Exception {
        match();
        if (token.type != Lexem.BOOL_OP) {
            throw new Exception("Вместо boolOp найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int func(int startPos) throws Exception {
        match();
        if (token.type != Lexem.FUNC_OP) {
            throw new Exception("Вместо func найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int var(int startPos) throws Exception {
        match();
        if (token.type != Lexem.VAR) {
            throw new Exception("Вместо var найдено: " + token.type + " " + token.data + " " + startPos);
        }
        return startPos + 1;
    }
    private static int inc(int startPos) throws Exception {
        match();
        if (token.type != Lexem.INC) {
            throw new Exception("Вместо inc найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }
    private static int dec(int startPos) throws Exception {
        match();
        if (token.type != Lexem.DEC) {
            throw new Exception("Вместо dec найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }
    private static int num(int startPos) throws Exception {
        match();
        if (token.type != Lexem.NUM) {
            throw new Exception("Вместо num найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int arOp(int startPos) throws Exception {
        match();
        if (token.type != Lexem.OP) {
            throw new Exception("Вместо arOp найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int lcb(int startPos) throws Exception {
        match();
        if (token.type != Lexem.LCB) {
            throw new Exception("Вместо lcb найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int rcb(int startPos) throws Exception {
        match();
        if (token.type != Lexem.RCB) {
            throw new Exception("Вместо rcb найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int assignOp(int startPos) throws Exception {
        match();
        if (token.type != Lexem.ASSIGN_OP) {
            throw new Exception("Вместо assignOp найдено: " + token.type + " " + token.data);
        }
        return startPos + 1;
    }

    private static int cOp(int startPos) throws Exception {
        match();
        if (token.type != Lexem.C_OP) {
            throw new Exception("Вместо cOp найдено: " + token.type + " " + token.data + " " + pos);
        }
        return startPos + 1;
    }
}
