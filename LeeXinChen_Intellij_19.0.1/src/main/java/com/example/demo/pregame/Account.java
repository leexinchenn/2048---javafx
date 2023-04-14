/**
 * This is a class that use to set and control the account of user
 * @author XinChen Lee-modified
 */
package com.example.demo.pregame;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Account implements Comparable<Account> {
    private long score = 0, highest=0;
    private static String userName ;
    private static final ArrayList<Account> accounts = new ArrayList<>();

    public Account(String userName){
        Account.userName =userName;
    }

    @Override
    public int compareTo(Account o) {
        return Long.compare(o.getScore(), score);
    }

    private long getScore() {
        return score;
    }

    private String getUserName() {
        return userName;
    }

    private static boolean existed=false;

    private static final Path path = Paths.get("UserInfo.txt");
    private static final String filename;

    static {
        try {
            filename = String.valueOf(path.toRealPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This is a method use to check the existence of username in Account class
     * @param userName This is a parameter use to store the username of user
     * @return the value of account
     */
    static Account accountHaveBeenExist(String userName){
        for(Account account : accounts){
            if(account.getUserName().equals(userName)){
                return account;
            }
        }
        return null;
    }

    /**
     * This is a method use to create a new Account for user that is not exist
     * @param userName This is a parameter use to store the username of user
     * @return the value of account
     */
    static Account makeNewAccount(String userName){
        Account account = new Account(userName);
        accounts.add(account);
        return account;
    }

    /**
     * This is a method use to save the records(username and highest score) of user into text file
     * @param score This is a parameter use to store the score of user
     */
    public static void saveRecord(long score){
        try {
            if(existed){
                File currfile = new File(filename);
                File tmpfile=new File("tmpFile.txt");
                FileWriter fw = new FileWriter(tmpfile);
                BufferedWriter bw = new BufferedWriter(fw);
                Scanner x=new Scanner(currfile);

                while (x.hasNextLine()){
                    String currentline=x.nextLine();
                    String[] data= currentline.split(",");
                    if(userName.equalsIgnoreCase(data[0])){
                        if(score>Long.parseLong(data[1]))
                            currentline=userName+","+score;
                    }
                    bw.write(currentline+System.getProperty("line.separator"));
                }
                bw.close();
                x.close();
                currfile.delete();
                tmpfile.renameTo(currfile);
            }
            else {
                FileWriter fw = new FileWriter(filename,  true);
                PrintWriter printWriter = new PrintWriter(fw);

                printWriter.println(userName+","+score);
                printWriter.close();
                fw.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This is a method use to get the value of lines in a text file
     * @return value of lines
     */
    long getLines(){
        Path path = Paths.get(filename);
        try {
            return Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This is a method use to get the record from text file and save it into Account
     */
    void getRecord(){
        String currentLine;

        String [] uuname = new String[(int) getLines()];
        long[] sscore = new long[(int) getLines()];

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            currentLine=br.readLine();
            int k=0;
            while (currentLine!=null){
                String[] data=currentLine.split(",");
                uuname[k]=data[0];
                sscore[k]= Long.parseLong(data[1]);

                currentLine= br.readLine();
                k++;
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for(int i=0; i<getLines(); i++){
            if(userName.equalsIgnoreCase(uuname[i])){
                Account.accountHaveBeenExist(userName);
                highest=sscore[i];
                existed=true;
                break;
            }
        }
        if(!existed){
            Account.makeNewAccount(userName);
            highest=0;
            existed=false;
        }
    }

    /**
     * This is a getter method use to get the highest score of user
     * @return value of the highest score of user
     */
    long getHighScore(){
        return highest;
    }
}
