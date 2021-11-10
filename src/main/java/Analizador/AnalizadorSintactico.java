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
public class AnalizadorSintactico {

    boolean aceptacion;
    String[][] funcionTransicion = {{"S0", "PRINT", "S1"},
    {"S1", "VALOR_CADENA", "S2"},
    {"S2", "FIN", "S3"},//S3 ACEPTACION

    {"S0", "REPETIR", "S4"},
    {"S4", "IDENTIFICADOR", "S5"},
    {"S4", "VALOR_ENTERO", "S5"},
    {"S5", "INICIAR", "S6"},
    {"S6", "PRINT", "S7"},
    {"S7", "VALOR_CADENA", "S8"},
    {"S8", "FIN", "S9"},
    {"S9", "PRINT", "S7"},
    {"S9", "FIN", "S10"},//S10 ACEPTACION

    {"S0", "SI", "S11"},
    {"S11", "TRUE", "S12"},
    {"S11", "FALSE", "S12"},
    {"S12", "ENTONCES", "S13"},
    {"S13", "PRINT", "S14"},
    {"S14", "VALOR_CADENA", "S15"},
    {"S15", "FIN", "S16"},
    {"S16", "FIN", "S17"},//S17 ACEPTACION

    {"S0", "IDENTIFICADOR", "S19"},
    {"S19", "ASIGNACION", "S20"},
    {"S20", "PARENTESIS_APERTURA", "S21"},
    {"S20", "VALOR_DECIMAL", "S22"},
    {"S20", "VALOR_ENTERO", "S22"},
    {"S20", "IDENTIFICADOR", "S22"},
    {"S22", "OPERADOR_ARITMETICO", "S23"},
    {"S23", "VALOR_DECIMAL", "S22"},
    {"S23", "VALOR_ENTERO", "S22"},
    {"S23", "IDENTIFICADOR", "S22"},
    {"S23", "PARENTESIS_APERTURA", "S21"},
    {"S21", "PARENTESIS_APERTURA", "S21"},
    {"S21", "OPERADOR_ARITMETICO", "S23"},
    {"S21", "VALOR_DECIMAL", "S22"},//S22 ACEPTACION
    {"S21", "VALOR_ENTERO", "S22"},
    {"S21", "IDENTIFICADOR", "S22"},
    {"S22", "PARENTESIS_CIERRE", "S24"},
    {"S24", "PARENTESIS_CIERRE", "S24"},//S24 ACEPTACION
    {"S24", "OPERADOR_ARITMETICO", "S23"},
    {"S22", "FIN", "S25"},
    {"S24", "FIN", "S25"},};
    String[] estadosAceptacion = {"S3", "S10", "S17"};
    Token tokenActual;
    LinkedList<String> valoresEsperados = new LinkedList<>();
    LinkedList<Token> temporal = new LinkedList<>();
    LinkedList<LinkedList<Token>> estructuras = new LinkedList<>();
    final String estadoInicial = "S0";
    String estadoActual = "S0";
    String ultimoCorrecto = "S0";
    int parentesis = 0;

    public void analizar(LinkedList<Token> tokens) {
        this.estadoActual = estadoInicial;
        this.ultimoCorrecto = estadoInicial;
        this.aceptacion = false;
        this.estructuras.clear();
        this.temporal.clear();
        this.tokenActual = new Token();
        this.valoresEsperados.clear();
        this.parentesis = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (EncontrarSiguiente(tokens.get(i))) {
                temporal.add(tokens.get(i));
                if (i == tokens.size() - 1 && aceptacion) {
                    estructuras.add((LinkedList<Token>) temporal.clone());
                    temporal.clear();
                    estadoActual = estadoInicial;
                }
            } else {
                if (aceptacion) {
                    estructuras.add((LinkedList<Token>) temporal.clone());
                    temporal.clear();
                    estadoActual = estadoInicial;
                    i--;
                } else {
                    mostrarError();
                    return;
                }
            }
            if (i == tokens.size() - 1 && !aceptacion) {
                mostrarError();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "¡ANALISIS EXITOSO!");
        imprimirEstructuras();
    }

    public void mostrarError() {
        JOptionPane.showMessageDialog(null, "Error sintactico\n Se esperaba: " + buscarEsperados() + "\n"
                + " Se recibio: " + tokenActual.getTipo() + " " + tokenActual.getValor() + "\n"
                + "En linea: " + tokenActual.getLinea() + "\n"
                + "Posicion: " + tokenActual.getPosicion());
    }

    public Boolean EncontrarSiguiente(Token actual) {
        String tipo = actual.getTipoString();
        this.tokenActual = actual;
        for (String[] funcionTransicion1 : funcionTransicion) {
            String c1 = funcionTransicion1[0];
            String c2 = funcionTransicion1[1];
            String c3 = funcionTransicion1[2];
            if (c1.equals(estadoActual) && c2.equals(tipo)) {
                estadoActual = c3;
                verificarAceptacion(estadoActual);
                if (c2.equals("PARENTESIS_APERTURA")) {
                    parentesis++;
                } else if (c2.equals("PARENTESIS_CIERRE")) {
                    parentesis--;
                }
                this.ultimoCorrecto = c3;
                return true;
            }
            verificarAceptacion(estadoActual);
        }

        tokenActual = actual;
        return false;
    }

    public String buscarEsperados() {
        for (String[] funcionTransicion1 : funcionTransicion) {
            String c1 = funcionTransicion1[0];
            String c2 = funcionTransicion1[1];
            if (c1.equals(ultimoCorrecto)) {
                valoresEsperados.add(c2);
            }
        }

        String error = "";
        error = valoresEsperados.stream().map(valor -> "|" + valor + "|").reduce(error, String::concat);
        return error;
    }

    public void verificarAceptacion(String estado) {
        aceptacion = estado.equals("S3") || estado.equals("S10") || estado.equals("S17") || estado.equals("S25");
        if (parentesis != 0) {
            aceptacion = false;
        }
    }

    public LinkedList<String> imprimirEstructuras() {
        
        int size = estructuras.size();
        LinkedList<String> resultados = new LinkedList<>();
        JTextPane textPane = new JTextPane();

        try {
            for (int i = 0; i < size; i++) {
                String resultado = "";
                String swap = "";
                LinkedList<Token> subList = estructuras.get(i);
                for (int j = 0; j < subList.size(); j++) {
                    String tipo = subList.get(j).getTipoString();

                    if (tipo.equals("VALOR_CADENA") && !subList.get(1).getTipoString().equals("FALSE")) {
                        resultado += subList.get(j).getValor() + "\n";
                        swap = resultado.replace("\"", "");
                    }
                }
                textPane.setText(textPane.getText()+swap);
            }
        } catch (IndexOutOfBoundsException ex) {

        }
        if (!textPane.getText().isBlank()) {
            Export export = new Export();
            export.SaveAs(textPane, null,".html");
            export.SaveAs(textPane, null,".txt");
        }
        return resultados;
    }

    public LinkedList<String> mostrarResultados() {
        int size = estructuras.size();
        LinkedList<String> resultados = new LinkedList<>();
        try {

            for (int i = 0; i < size; i++) {
                String result = "";

                LinkedList<Token> subList = estructuras.get(i);
                for (int j = 0; j < subList.size(); j++) {
                    result += subList.get(j).getValor() + " ";
                }
                result += "\n";
                resultados.add(result);
                System.out.println(result);
            }
        } catch (IndexOutOfBoundsException ex) {

        }
        return resultados;
    }
}
