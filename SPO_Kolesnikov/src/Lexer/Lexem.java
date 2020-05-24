package Lexer;

import java.util.regex.Pattern;

public enum Lexem {
/*  +  FUNC_OP("add|remove|get|contains"),
    +TYPE_W("type"), TYPE("Set|List"),
    +WHILE("while"),
    +VAR("[a-z][a-zA-Z0-9_]*"),
    +NUMBER("0|[1-9][0-9]*"),
    +BOOL_OP("<|>|<=|>=|==|!="),
    +ASSIGN_OP("="),
    C_OP(";"),
  +  AR_OP("[*|/|+|-]"),
   + O_BRACE("\\{"),
   + C_BRACE("\\}"),
   + O_BRACKET("\\("),
   + C_BRACKET("\\)"),
    GOTO(""),
    GOTO_INDEX(""),
    +BOOL("true|false");*/

    ASSIGN_OP("="),
    ASSIGN_OP1(":="),
    METHOD("\\."),
    FUNC_OP("add|remove|get|contains"),
    TYPE_W("new"),
    TYPE("Set|List"),
    WHILE("while"),
    FOR("for"),
    IF("if"),
    ELSE("else"),
    PRINT("print"),
    BOOL_OP("<|>|<=|>=|==|!="),
    BOOL("true|false"),
    VAR("[a-zA-Z][a-zA-Z0-9_]*"),
    INC("\\+\\+"),
    DEC("\\--"),
    OP("[*|/|+|-]"),
    NUM("0|[1-9]{1}[0-9]*"),
    LCB("\\{"),
    RCB("\\}"),
    LB("\\("),
    RB("\\)"),
    C_OP(";"),
    GOTO(""),
    GOTO_INDEX("");

    private final Pattern pattern;

    Lexem(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
    public Pattern getPattern()
    {
        return pattern;
    }
}
