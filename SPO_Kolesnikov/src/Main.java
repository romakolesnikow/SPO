import Lexer.Lexer;
import Lexer.Token;
import Parcer.Parser;
import Parcer.Poliz;
import Stack_Maschine.PolizCalculation;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        LinkedList<Token> tokens = lexer.lex("c = 1; i = 0; a new List; a.add(5+5); a.add(10); a.add(5); b new Set; b.add(3); b.add(3); c = a.get(1); while(i < 5){i = i + 1;} c++; c++;" );
        System.out.println("Токены:");
        for (int i = 0; i < tokens.size(); i++)
        {
            System.out.println(tokens.get(i));
        }
        try {
            Parser.parse(tokens);
        }catch ( Exception ex)
        { System.err.println(ex);
            System.exit(1);
        }
        System.out.println("ОПЗ:");
        LinkedList<Token> testPoliz = Poliz.makePoliz(tokens);
        int i = 0;
        for (Token token : testPoliz) {
            System.out.println(i + " " + token);
            i++;
        }
        System.out.println("Таблица переменных:");
        PolizCalculation.calculate(testPoliz);

    }
}
