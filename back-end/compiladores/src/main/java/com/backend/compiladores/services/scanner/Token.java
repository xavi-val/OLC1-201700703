package com.backend.compiladores.services.scanner;

public class Token {
    private TokenConstant tokenType;
    private String lexema;

    public Token(TokenConstant tokenType, String lexema) {
        this.tokenType = tokenType;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", lexema='" + lexema + '\'' +
                '}';
    }

    public TokenConstant getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenConstant tokenType) {
        this.tokenType = tokenType;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
}
