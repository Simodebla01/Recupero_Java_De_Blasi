package org.example;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;
import com.google.gson.Gson;

public class Elaborazione extends Thread{

    Socket clientSocket=null;
    ServerSocket serverSocket=null;


    Elaborazione(){}

    Elaborazione(Socket clientSocket, ServerSocket serverSocket){
        this.clientSocket=clientSocket;
        this.serverSocket=serverSocket;
    }

    @Override
    public void run(){
        BufferedReader input=null;
        try {
            input=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Errore buffered reader\n"+e.toString());
        }
        String messaggio="";
        PrintWriter output=null;
        try {
            output=new PrintWriter(clientSocket.getOutputStream(),true);
        } catch (IOException e) {
            System.out.println("Errore printwriter \n"+e.toString());
        }
        output.println("CONNESSO AL SERVER.");

        try {
            while ((messaggio = input.readLine()) != null) {
                output.println(elaborazione_messaggio(messaggio.toLowerCase(Locale.ROOT)));
            }
        }catch(IOException e){
            System.out.println("Errore nella lettura\n"+e.toString());
        }
        System.out.println("Client disconnesso");
    }

    String elaborazione_messaggio(String digitato){
        Gson gson=new Gson();
        Contenuto_Stringa result=new Contenuto_Stringa();
        result.letterCount=digitato.length();
        String splitted[]=digitato.split(" ");
        result.replacedString="";
        result.wordsCount=0;

        for(int i = 0 ; i < splitted.length; i++){
            if(i+1== splitted.length) result.replacedString= result.replacedString+splitted[i];
            else result.replacedString= result.replacedString+splitted[i]+"-";
            result.wordsCount=result.wordsCount+1;
        }
        if(digitato.equals("")) result.wordsCount=0;
        return gson.toJson(result,Contenuto_Stringa.class);
    }






}