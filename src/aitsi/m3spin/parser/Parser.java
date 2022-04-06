package aitsi.m3spin.parser;

import aitsi.m3spin.commons.PROC;
import aitsi.m3spin.commons.PROCimpl;
import aitsi.m3spin.commons.TNODE;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

public class Parser {

    Dictionary<String, Object>  parserDict = new Hashtable<String, Object>();
    List<String> programList =  new ArrayList<>();
    List<TNODE> nodeList =  new ArrayList<>();


    public void parseAST(){
    File file = new File("procedura.txt");
        try (Scanner sc = new Scanner(file))
        {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");
                for(int i=0; i< parts.length; i++){
                    System.out.println(parts[i]);
                    if(parts[i].equals("procedure")){
                        nodeList.add(new PROCimpl(parts[i+1]));
                    }
                    if(parts[i].equals("{")){

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




}

