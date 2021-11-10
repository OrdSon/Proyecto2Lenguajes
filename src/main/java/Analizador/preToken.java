/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

/**
 *
 * @author ordson
 */
public class preToken {

    char valor;
    private Tipo tipo;
    Token.Tipo tokenTipo;

    public preToken() {

    }

    public enum Tipo {
        L,
        E,
        P,
        B,
        U,
        S,
        C,
        F,
        Mayor,
        Menor,
        Admiracion,
        Igual,
        And,
        Or
    }

    public String getTipo() {
        switch (tipo) {
            case L:
                return "L";
            case E:
                return "E";
            case P:
                return "P";
            case B:
                return "B";
            case U:
                return "U";
            case S:
                return "S";
            case C:
                return "C";
            case Menor:
                return "<";
            case Mayor:
                return ">";
            case Igual:
                return "=";
            case Admiracion:
                return "!";
            case And:
                return "&";
            case Or:
                return "|";
            default:
                return "F";
        }

    }

    public void setValues(char valor, preToken.Tipo tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public void setValue(char valor) {
        this.valor = valor;
    }

    public void setTipo(preToken.Tipo tipo) {
        this.tipo = tipo;
    }

    public Boolean encontrarFin() {
        char actual = valor;
        int ascii = valor;
        return ascii == 42 || ascii == 43 || ascii == 45 || ascii == 47
                || ascii == 60 || ascii == 61 || ascii == 62;
    }

    public Boolean isSymbol() {
        switch (valor) {
            case '\'':
                this.tokenTipo = Token.Tipo.QUOTE;
                return true;
            case ':':
                this.tokenTipo = Token.Tipo.PAIR;
                return true;
            case '+':
                this.tokenTipo = Token.Tipo.OPERADOR_ARITMETICO;
                return true;

            case '-':
                this.tokenTipo = Token.Tipo.OPERADOR_ARITMETICO;
                return true;

            case ';':
                this.tokenTipo = Token.Tipo.FIN;
                return true;

            case '*':
                this.tokenTipo = Token.Tipo.OPERADOR_ARITMETICO;
                return true;

            case '/':
                this.tokenTipo = Token.Tipo.OPERADOR_ARITMETICO;
                return true;

            case '{':
                this.tokenTipo = Token.Tipo.LLAVE_APERTURA;
                return true;

            case '}':
                this.tokenTipo = Token.Tipo.LLAVE_CIERRE;
                return true;

            case '(':
                this.tokenTipo = Token.Tipo.PARENTESIS_APERTURA;
                return true;

            case ')':
                this.tokenTipo = Token.Tipo.PARENTESIS_CIERRE;
                return true;
            case ',':
                this.tokenTipo = Token.Tipo.COMMAS;
                return true;
            case '<':
                this.tokenTipo = Token.Tipo.OPERADOR_LOGICO;
                return true;
            case '>':
                this.tokenTipo = Token.Tipo.OPERADOR_LOGICO;
                return true;
            default:
                return false;

        }
    }

    public Token.Tipo GetTipo() {
        return this.tokenTipo;
    }

    public Boolean setTipo(char charActual) {
        this.valor = charActual;
        if (Character.isDigit(charActual)) {
            this.tipo = Tipo.E;
            return true;
        } else if (Character.isAlphabetic(charActual)) {
            this.tipo = Tipo.L;
            return true;
        } else if (Character.isWhitespace(charActual)) {
            this.tipo = Tipo.B;
            return true;
        } else if ((charActual) == (char) 46) {
            this.tipo = Tipo.P;
            return true;
        } else if ((charActual) == (char) 34) {
            this.tipo = Tipo.C;
            return true;
        } else if ((charActual) == (char) 95) {
            this.tipo = Tipo.U;
            return true;
        } else if (charActual == (char) 60) {
            this.tipo = Tipo.Menor;
            return true;
        } else if (charActual == (char) 61) {
            this.tipo = Tipo.Igual;
            return true;
        } else if (charActual == (char) 62) {
            this.tipo = Tipo.Mayor;
            return true;
        } else if (charActual == (char) 33) {
            this.tipo = Tipo.Admiracion;
            return true;
        } else if (charActual == (char) 38) {
            this.tipo = Tipo.And;
            return true;
        } else if (charActual == (char) 124) {
            this.tipo = Tipo.Or;
            return true;
        } else if (charActual == (char) 59) {
            this.tipo = Tipo.S;
            return true;
        } else if (isSymbol() || Character.isUnicodeIdentifierPart(charActual)) {
            this.tipo = Tipo.S;
            return true;
        } else {
            this.tipo = Tipo.F;
            return false;
        }

    }

}
