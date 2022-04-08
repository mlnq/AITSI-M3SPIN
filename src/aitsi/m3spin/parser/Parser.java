package aitsi.m3spin.parser;

import aitsi.m3spin.pkb.Interfaces.AST;

public class Parser {
    private String rawCode;
    private AST ast = new AstImpl();

    public Parser(String rawCode) {
        this.rawCode = rawCode;
    }

    public void parse() {
        parseProcedure();
    }

    private void parseProcedure() {
        parseProcedureTag();
        Procedure procedure = new Procedure(parseName());

        ast.setRoot(procedure);

        parseStartingBrace();
        parseStmtList();
        parseEndingBrace();
    }

    private void parseStmtList() {
        while (rawCode)
    }
}
//package aitsi.m3spin.parser;
//
//import aitsi.m3spin.commons.*;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.sql.SQLOutput;
//import java.util.*;
//import java.util.regex.Pattern;
//
//public class Parser {
//
//    Dictionary<String, Object>  parserDict = new Hashtable<String, Object>();
//    List<String> programList =  new ArrayList<>();
//    List<TNODE> nodeList =  new ArrayList<>();
//
//
//    public void parseAST(){
//    File file = new File("procedura.txt");
//        try (Scanner sc = new Scanner(file))
//        {
//            Pattern pattern = Pattern.compile("[a-z]");
////[{{]
//
//            while (sc.hasNextLine()) {
//                programList.add(sc.nextLine());
//            }
////            while(programList){
////                String line = sc.nextLine();
////                String[] parts = line.split(" ");
////                for(int i=0; i< parts.length; i++){
////                    System.out.println(parts[i]);
////                    if(parts[i].equals("procedure")){
////                        nodeList.add(new PROCimpl(parts[i+1]));
////                    }
////                    if(parts[i].equals("{")){
////
////                    }
////                    if(pattern.matcher(parts[i]).matches()){
////                        if ((parts[i-1].equals(";") || parts[i-1].equals("{")) && parts[i+1].equals("=")){
////                            nodeList.add(ENTITY_TYPE.ASSIGN);
////                            nodeList.add(new VARimpl(parts[i]));
////                        }
////                    }
////                }
////            }
//            System.out.println();
//            System.out.println();
//            System.out.println(programList);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//}