
        import aitsi.m3spin.commons.*;

        import java.util.*;
        import java.util.regex.Pattern;

public class Parser {

    public void parse(){

        {

//[{{]
            while(code) {
                for (int i = 0; i < parts.length; i++) {
                    System.out.println(parts[i]);


                    Pattern pattern = Pattern.compile("[a-z]");
                    if (pattern.matcher(parts[i]).matches()) {
                        if ((parts[i - 1].equals(";") || parts[i - 1].equals("{")) && parts[i + 1].equals("=")) {
                            nodeList.add(EntityType.ASSIGN);
                            nodeList.add(new VARimpl(parts[i]));
                        }
                    }
                }
            }


    }




}