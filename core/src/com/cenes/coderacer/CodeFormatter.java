package com.cenes.coderacer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CodeFormatter {
    public static String format(String input){
        try {
            System.out.println("girdi: \n" + input);
            String[] command = {"./formatScript.sh", input};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            StringBuilder sonuc = new StringBuilder();
            System.out.println("Script output: ");
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
                sonuc.append(s).append('\n');
            }

            return sonuc.toString();
        } catch (Exception e){
            e.printStackTrace();
            return input;
        }
    }
}
