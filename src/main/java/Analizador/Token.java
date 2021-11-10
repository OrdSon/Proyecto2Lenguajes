/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.awt.Color;

/**
 *
 * @author ordson
 */
public class Token {

    public enum Tipo {
        PRINCIPAL,
        IDENTIFICADOR,//PALABRA CUALQUIERA
        VALOR_ENTERO,//INT
        BOOLEAN,
        CADENA,
        VALOR_DECIMAL,
        VALOR_CADENA,
        VALOR_BOOLEAN,
        OPERADOR_ARITMETICO,//+ - / ++ -=
        OPERADOR_RELACIONAL,// == != < > <= >=
        OPERADOR_LOGICO,//|| && ! | &
        PARENTESIS_APERTURA,
        PARENTESIS_CIERRE,
        LLAVE_APERTURA,
        LLAVE_CIERRE,
        ASIGNACION,//SIGNO IGUAL
        SI,
        MIENTRAS,
        REPETIR,
        HACER,
        DESDE,
        HASTA,
        TRUE,
        FALSE,
        PRINT,
        COMMAS,
        QUOTE,
        Painter,
        PAIR,
        FIN,
        NEGACION,
        INICIAR,
        ENTONCES,
        ERROR
    }

    private Tipo tipoToken;
    private String valor;
    int size;
    int posicion;
    int linea;
    Color color;

    public Token(Tipo tipoToken, String valor, int size, int posicion, int linea) {
        this.tipoToken = tipoToken;
        this.valor = valor;
        this.size = size;
        this.posicion = posicion;
        this.linea = linea;

    }

    public Token() {

    }

    public Token.Tipo GetTipoToken() {
        return this.tipoToken;
    }

    public Color getColor() {
        return this.color;
    }

    public int getLinea() {
        return this.linea;
    }

    public void revisarTipo() {
        Token.Tipo tipoActual = tipoToken;
        String valorActual = valor;
        if (tipoActual.equals(Token.Tipo.IDENTIFICADOR)) {
            switch (valorActual) {
                case "SI":
                    this.tipoToken = Tipo.SI;
                    break;
                case "MIENTRAS":
                    this.tipoToken = Tipo.MIENTRAS;
                    break;
                case "HACER":
                    this.tipoToken = Tipo.HACER;
                    break;
                case "DESDE":
                    this.tipoToken = Tipo.DESDE;
                    break;
                case "HASTA":
                    this.tipoToken = Tipo.HASTA;
                    break;
                case "INICIAR":
                    this.tipoToken = Tipo.INICIAR;
                    break;
                case "Cadena":
                    this.tipoToken = Tipo.CADENA;
                    break;
                case "Boolean":
                    this.tipoToken = Tipo.BOOLEAN;
                    break;

                case "VERDADERO":
                    this.tipoToken = Tipo.TRUE;
                    break;
                case "FALSO":
                    this.tipoToken = Tipo.FALSE;
                    break;
                case "ESCRIBIR":
                    this.tipoToken = Tipo.PRINT;
                    break;

                case "Principal":
                    this.tipoToken = Tipo.PRINCIPAL;
                    break;
                case "FIN":
                    this.tipoToken = Tipo.FIN;
                    break;
                case "ENTONCES":
                    this.tipoToken = Tipo.ENTONCES;
                    break;
                case "REPETIR":
                    this.tipoToken = Tipo.REPETIR;
                    break;
                default:

                    break;
            }
        }

    }

    public String getValor() {
        return valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getSize() {
        return size;
    }

    public String getTipoString() {
        return this.tipoToken.toString();
    }

    public String getTipo() {
        switch (tipoToken) {

            case IDENTIFICADOR:
                this.color = new Color(144, 188, 239);
                return "identificador";
            case VALOR_ENTERO:
                this.color = new Color(153, 102, 255);
                return "valor entero";
            case VALOR_DECIMAL:
                this.color = new Color(0, 102, 204);
                return "valor decimal";
            case VALOR_CADENA:
                this.color = Color.yellow;
                return "valor cadena";
            case VALOR_BOOLEAN:
                this.color = Color.orange;
                return "valor boolean";

            case OPERADOR_ARITMETICO:
                this.color = new Color(0, 102, 204);
                return "operador aritmetico";
            case OPERADOR_RELACIONAL:
                this.color = new Color(0, 102, 204);
                return "operador relacional";
            case OPERADOR_LOGICO:
                this.color = new Color(0, 102, 204);
                return "operador logico";
            case PARENTESIS_APERTURA:
                this.color = new Color(0, 102, 204);
                return "parentesis apertura";
            case PARENTESIS_CIERRE:
                this.color = new Color(0, 102, 204);
                return "parentesis cierre";

            case ASIGNACION:
                this.color = new Color(0, 102, 204);
                return "asignacion";
            case SI:
                this.color = new Color(0, 153, 51);
                return "SI";

            case MIENTRAS:
                this.color = new Color(0, 153, 51);
                return "MIENTRAS";
            case HACER:
                this.color = new Color(0, 153, 51);
                return "HACER";
            case DESDE:
                this.color = new Color(0, 153, 51);
                return "DESDE";
            case HASTA:
                this.color = new Color(0, 153, 51);
                return "HASTA";

            case FIN:
                this.color = Color.black;
                return "FIN";
            case COMMAS:
                this.color = Color.black;
                return "coma";

            case BOOLEAN:
                this.color = Color.black;
                return "Boolean";

            case TRUE:
                this.color = Color.black;
                return "verdadero";
            case FALSE:
                this.color = Color.black;
                return "falso";
            case PRINT:
                this.color = Color.black;
                return "imprimir";

            case PRINCIPAL:
                this.color = Color.cyan;
                return "Principal";
            case CADENA:
                this.color = Color.yellow;
                return "Cadena";
            case NEGACION:
                this.color = Color.yellow;
                return "Negacion";
            default:
                return "ERROR";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TOKEN: ");
        if (tipoToken == null) {
            sb.append("ERROR");
        } else {
            sb.append("[Tipo=").append(tipoToken);

        }
        sb.append(", valor=").append(valor);
        sb.append(", size=").append(size);
        sb.append(", linea=").append(linea);
        sb.append(']');
        return sb.toString();
    }

}
