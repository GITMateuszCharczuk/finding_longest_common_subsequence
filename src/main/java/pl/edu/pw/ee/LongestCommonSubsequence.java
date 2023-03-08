package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LongestCommonSubsequence {

    private String topStr;

    private String leftStr;

    private List<Node> list;

    private List<Node> outputList;

    public LongestCommonSubsequence(String topStr, String leftStr) {

        validateInput(topStr, leftStr);
        topStr = validateInputStrings(topStr);
        leftStr = validateInputStrings(leftStr);

        this.topStr = topStr;
        this.leftStr = leftStr;
        int cols = topStr.length() + 1;
        int rows = leftStr.length() + 1;
        list = new ArrayList<>();
        outputList = null;

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                if (i == 0) {

                    list.add(new Node('"', 0));
                } else if (j == 0) {

                    list.add(new Node('"', 0));
                } else if (topStr.charAt(j - 1) == leftStr.charAt(i - 1)) {

                    list.add(new Node('\\', list.get(((i - 1) * cols) + j - 1).getNum() + 1));
                } else {
                    if (list.get((i * cols) + j - 1).getNum() > list.get(((i - 1) * cols) + j).getNum()) {

                        list.add(new Node('<', list.get((i * cols) + j - 1).getNum()));
                    } else {

                        list.add(new Node('^', list.get(((i - 1) * cols) + j).getNum()));
                    }
                }
            }
        }
    }

    private static class Node {

        private char sign;

        private int num;

        public Node(char sign, int num) {
            
            this.sign = sign;
            this.num = num;
        }

        public char getSign() {
            
            return sign;
        }

        public void setSign(char sign) {
            
            this.sign = sign;
        }

        public int getNum() {
            
            return num;
        }

        public void setNum(int num) {
            
            this.num = num;
        }

    }

    public String findLCS() {

        outputList =  new ArrayList<Node>(Collections.nCopies(list.size(), null));
        StringBuilder reversedOutputMessage = new StringBuilder();
        String outputMessage = "";
        int cols = topStr.length() + 1;
        
        for (int i = list.size() - 1; list.get(i).getSign() != '"';) {

            outputList.set(i, list.get(i));

            if (list.get(i).getSign() == '^') {

                i -= cols;
            } else if (list.get(i).getSign() == '\\') {

                outputMessage += i % cols != 0 ? topStr.charAt(i%cols - 1)  : topStr.charAt(topStr.length());
                i -= (cols+1);
            } else if (list.get(i).getSign() == '<') {

                i -= 1;
            }

        }
        reversedOutputMessage.append(outputMessage);
        reversedOutputMessage.reverse();

        return reversedOutputMessage.toString();
    }

    public void display() {

        int cols = topStr.length() + 1;
        int rows = leftStr.length() + 1;

        if (outputList == null) {
            findLCS();
        }

        topStr = " " + topStr;
        leftStr = " " + leftStr;

        for (int i = 0; i < list.size(); i++) {

            if (outputList.get(i) == null) {

                Node nodeToAdd = new Node('"', list.get(i).getNum());
                outputList.set(i, nodeToAdd);
            }
        }
        
        list = outputList;
        System.out.println(getTopStringSection(cols));

        for (int i = 0; i < rows; i++) {
            System.out.println(getSpacer(cols));
            System.out.println(getBlankSpacer(i, cols));
            System.out.println(getContentLine(i, cols, leftStr.substring(i,i+1)));
            System.out.println(getBlankSpacer(0, cols));
        }

        System.out.println(getSpacer(cols));
    }

    private String getSpacer(int spacesCount) {

        String output = "";
        output += "+-------";

        for (int i = 0; i < spacesCount; i++) {
            output += "+-----";
        }

        return output + "+";
    }

    private String getBlankSpacer(int rowIndx, int cols) {//do refaktoryzacji spacescount i cols

        String output = "";
        output += "|       ";

        for (int i = 0; i < cols; i++) {

            output += "|";

            if (list.get((rowIndx * cols) + i).getSign() == '\\') {

                output += "\\    ";
            } else if (list.get((rowIndx * cols) + i).getSign() == '^') {

                output += "  ^  ";
            } else {

                output += "     ";
            }

        }

        return output + "|";
    }

    private String getContentLine(int rowIndx, int cols, String character) {

        String output = "";
        output += "|   " + prepareOutputString(character,4);

        for (int i = 0; i < cols; i++) {

            output += "|";

            if (list.get((rowIndx * cols) + i).getSign() == '<') {

                output += "< ";
            } else {

                output += "  ";
            }

            output += prepareOutputString(String.valueOf(list.get((rowIndx * cols) + i).getNum()),3);
        }

        return output + "|";
    }

    private String getTopStringSection(int cols) {

        String output = "";
        output += getSpacer(cols) + "\n";
        output += getBlankSpacer(0, cols) + "\n";
        output += "|       ";

        for (int i = 0; i < cols; i++) {

            output += "|  ";
            output += prepareOutputString(topStr.substring(i,i+1),3);
        }

        output += "|\n" + getBlankSpacer(0, cols);

        return output;
    }

    private String prepareOutputString(String message, int count) {
        
        String isSpecialCharacter = getStringSubstitute(message.charAt(0));
        message =  isSpecialCharacter == "E" ? message : isSpecialCharacter;

        return message.length() < count ? prepareOutputString(message + " ", count) : message;
    }

    private void validateInput(String s1, String s2) {

        if (s1 == null || s2 == null) {

            throw new IllegalArgumentException("Input arguments can't be null!!!");
        }
        if (s1 == "" || s2 == "") {

            throw new IllegalArgumentException("Input arguments can't be empty strings!!!");
        }
    }

    private String validateInputStrings(String s) {

        char [] chars = s.toCharArray();

        for(int i = 1 ; i < chars.length;i++){

            if(chars[i-1] == '\\' ){
                
                char charToChange = getCharSubstitute(chars[i]);
                
                if(charToChange != 'E'){
                    
                    s = s.replace("\\"+chars[i], charToChange+"");
                }  
            }
        }

        return s;
    }
    private char getCharSubstitute(char c){
        
        switch (c){
            
            case 't':
                
                return '\t';
            case 'n':
                
                return '\n';
            case 'r':
                
                return '\r';
            case 'b':
                
                return '\b';
            case 'f':
                
                return '\f';
            default:
                
                return 'E';
        }
        
    }

    private String getStringSubstitute(char c){

        switch (c){

            case '\t':

                return "\\t";
            case '\n':

                return "\\n";
            case '\r':

                return "\\r";
            case '\b':

                return "\\b";
            case '\f':

                return "\\f";
            default:

                return "E";
        }

    }

}
