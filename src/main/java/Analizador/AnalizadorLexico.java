/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 *
 * @author ordson
 */
public class AnalizadorLexico {
    private LinkedList<Token> tokens = new LinkedList<>();
        private LinkedList<Token> errores = new LinkedList<>();
        final String estadoInicial = "S0";
        String estadoActual = "S0";
        char charActual;
        String valor;
        Token.Tipo tipo;
        Boolean aceptacion;
        AnalizadorSintactico analizador = new AnalizadorSintactico();

        /* L = Letra
         * E = Entero
         * P = Punto
         * B = Blank
         * U = Guion bajo
         * C = Comillas
         * S = Simbolo
         */
        String[][] funcionTransicion = { {"S0", "E", "S1"},//transicion a cada automata
                                        {"S0", "C", "S5"},
                                        {"S0", "L", "S7"},
                                        {"S0" ,"A" ,"S9"},
                                        {"S0", "<", "S10"},
                                        {"S0", ">", "S11"},
                                        {"S0", "!", "S12"},
                                        {"S0", "=", "S13"},
                                        {"S0","&","S15" },
                                        {"S0","|","S15" },

                                        {"S1", "E", "S1"},//automata numeros
                                        {"S1", "P", "S2"},
                                        {"S2", "E", "S3"},
                                        {"S3", "E", "S3"},

                                                        //automata cadena
                                        {"S5", "E", "S5"},
                                        {"S5", "L", "S5"},
                                        {"S5", "B", "S5"},
                                        {"S5", "S", "S5"},
                                        {"S5" ,"A" ,"S5"},
                                        {"S5", "<", "S5"},
                                        {"S5", ">", "S5"},
                                        {"S5", "!", "S5"},
                                        {"S5", "=", "S5"},
                                        {"S5","&","S5" },
                                        {"S5","|","S5" },
                                        {"S5", "C", "S6"},

                                        {"S7", "L", "S8"},//automata identificador
                                        {"S8", "L", "S8"},
                                        {"S8", "U", "S8"},
                                        {"S8", "E", "S8"},

                                        
                                        {"S10", "=", "S14"},//automata relacional
                                        {"S11", "=", "S14"},
                                        {"S12", "=", "S14"},
                                        {"S13", "=", "S14"},

                                        {"S15","&","S16" },
                                        {"S15","|","S17" }
                                         };
        
        String[] estadosAceptacion = {"S1","S3","S6","s7","S8","S9","S10",
            "S11","S13","S12","S14","15","16","17"};
        /* S1 = ENTERO
         * S3 = DECIMAL
         * S6 = CADENA
         * S8 = IDENTIFICADOR
         */

        /* L = Letra
         * E = Entero
         * P = Punto
         * B = Blank
         * U = Guion bajo
         * C = Comillas
         * S = Simbolo
         */
//        private AnalizadorSintactico analizador;
        public AnalizadorLexico() {
//            this.analizador = new AnalizadorSintactico();
        }
        public void analizar(JTextPane textBox){
            tokens.clear();
            errores.clear();
//            analizador = new AnalizadorSintactico();
            char[] chars = textBox.getText().toCharArray();
            int posicion = 0;
            int tamañoTemporal = 0;
            Boolean regreso = false;
            if (textBox.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Sin texto que analizar");
                return;
            }
            estadoActual = estadoInicial;
            String[] lines = textBox.getText().split("\\r?\\n");
            for (int i = 0; i < lines.length; i++) {
                char[] linea = lines[i].toCharArray();
                for (int j = 0; j < linea.length ; j++) {
                    preToken preToken = new preToken();
                    preToken.setValue(linea[j]);
                    char temporal = linea[j];

                    
                    if (preToken.setTipo(temporal) == false && !preToken.isSymbol()) {

                    }
                     else {
                        if (EncontrarSiguiente(preToken.getTipo()) ) {
                            valor += temporal;
                            tamañoTemporal++;
                            if (j == linea.length-1 && aceptacion) {
                                System.out.println("Si llegue a GUARDAR TOKEN" + tamañoTemporal);
                                Token token = new Token(tipo, valor, valor.length(), posicion - valor.length(),i+1);
                                tamañoTemporal=guardarToken(token, tamañoTemporal);
                            }
                        } else {
                            if (aceptacion) {
                                Token token = new Token(tipo, valor, valor.length(), posicion - valor.length(),i+1);
                                tamañoTemporal = guardarToken(token, tamañoTemporal);
                                j--;
                                posicion--;
                                
                                regreso = true;
                            }else if (preToken.isSymbol()) {
                                Token token = new Token(preToken.GetTipo(), temporal + "", 1, posicion - valor.length(),i+1);
                                tamañoTemporal = guardarToken(token, tamañoTemporal);
                            } else {
                                if (temporal != (char)32 && temporal != (char)9) {
                                    valor += temporal;
                                    System.out.println("Guarde un error!!!");
                                    Token token = new Token(preToken.GetTipo(), temporal + "", 1, posicion - valor.length(), i+1);
                                    errores.addLast(token);
                                    valor = "";
                                    estadoActual = "S0";
                                    tamañoTemporal = 0;
                                }
                            }
                        }
                    }
                    posicion++;
                }
                posicion++;
            }
            imprimirTokens();
            
//            Pintor pintor = new Pintor();
//            pintor.pintar(textBox,tokens);
            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Errores lexicos encontrados");
            }else{
                analizador.analizar(tokens);
            }
                
            
            }
        public int guardarToken(Token token, int tamañoTemporal) {
            tokens.addLast(token);
            valor = "";
            estadoActual = "S0";
            tamañoTemporal = 0;
            return  tamañoTemporal;
        }
        public void imprimirTokens() {
            for (int i = 0; i < tokens.size(); i++) {
                tokens.get(i).revisarTipo();
                System.err.println(tokens.get(i).toString());
            }
        }
        public Boolean EncontrarSiguiente(String tipo) {
            
            for (int i = 0; i<funcionTransicion.length;i++) {
                
                String c1 = this.funcionTransicion[i][0];
                String c2 = this.funcionTransicion[i][1];
                String c3 = this.funcionTransicion[i][2];
     
                if (c1.equals(estadoActual) && c2.equals(tipo)) {
                    estadoActual = c3;
                    verificarAceptacion(estadoActual);
                    return true;
                }
                verificarAceptacion(estadoActual);
            }
            
            return false;
        }
        public void verificarAceptacion(String estado) {

            if (estado.equals("S1")) {
                tipo = Token.Tipo.VALOR_ENTERO;
                aceptacion = true;
            } else if (estado.equals("S3")) {
                tipo = Token.Tipo.VALOR_DECIMAL;
                aceptacion = true;
            } else if (estado.equals("S6")) {
                tipo = Token.Tipo.VALOR_CADENA;
                aceptacion = true;

            }  else if (estado.equals("S8")) {
                tipo = Token.Tipo.IDENTIFICADOR;
                aceptacion = true;

            } else if (estado.equals("S12")) {
                tipo = Token.Tipo.OPERADOR_LOGICO;
                aceptacion = true;
            } 
            else if (estado.equals("S10") || estado.equals("S11") ||
             estado.equals("S14")) {
                tipo = Token.Tipo.OPERADOR_RELACIONAL;
                aceptacion = true;

            } else if (estado.equals("S13")) {
                tipo = Token.Tipo.ASIGNACION;
                aceptacion = true;
            } else if (estado.equals("S15")|| estado.equals("S16")|| estado.equals("S17")) {
                tipo = Token.Tipo.OPERADOR_LOGICO;
                aceptacion = true;
            } else {
                tipo = Token.Tipo.ERROR;
                aceptacion = false;
            }
            
        }
        public void mostrarErrores() {
            LinkedList<String> StringErrores = new LinkedList<>();
            for (int i = 0; i < errores.size(); i++) {
                StringErrores.addLast(errores.get(i).toString());
            }
//            StringErrores.addFirst("ERRORES LEXICOS:");
//            Error error = new Error(StringErrores);
//            error.Visible = true;
        }

    public LinkedList<Token> getTokens() {
        if (errores.isEmpty()) {
            return tokens;
        }
        return errores;
    }

    public void setTokens(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }

    public LinkedList<Token> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Token> errores) {
        this.errores = errores;
    }
    
    public LinkedList<String> getResultados(){
        return this.analizador.mostrarResultados();
    }
        
        
}
