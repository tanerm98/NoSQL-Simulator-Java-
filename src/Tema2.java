/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;

/**
 * Main class.
 * @author taner
 */
public class Tema2 {

    /**
     * Reads the input file name from the command line and calls the method that
     * executes all the commands.
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        
        Comenzi exec = new Comenzi(args[0]);
        exec.executa();

    }
    
}
