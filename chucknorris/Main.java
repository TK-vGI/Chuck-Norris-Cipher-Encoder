package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean key = true;
        do {
            showMenu();
            String input = userInput(scanner);
            switch (input){
                case "encode": {
                    System.out.println("Input string:");
                    String s0 = userInput(scanner);
                    String s1 = convertToBinaryString(s0);
                    String s2 = convertToChuckNorris(s1);
                    System.out.println("Encoded string:");
                    System.out.println(s2);
                    System.out.println();
                    break;

                }
                case "decode": {
                    System.out.println("Input encoded string:");
                    String s3 = userInput(scanner);
                    if (!checkUserInput(s3)) {break;}
                    String[] s4 = parseStringToArray(s3);
                    if (!checkEncodedInput(s4)) {break;}
                    String s5 = arrangeToBlocks(s4);
                    if (!checkEncodedAscii(s5)) {break;}
                    String s6 = arrangeAsciiBits(s5);
                    System.out.println("Decoded string:");
                    System.out.println(arrangeToCharacters(s6));
                    System.out.println();
                    break;
                }
                case "exit": {
                    System.out.println("Bye!");
                    key = false;
                    break;
                }
                default:{
                    System.out.println("There is no '" + input + "' operation");
                }
            }

        }while (key);
    }

    private static boolean checkUserInput(String s) {
        char[] check_s = s.toCharArray();
        for (char c : check_s) {
            boolean bool1 = !(c == '0' || c == ' ');
            if (bool1) {
                System.out.println("Encoded string is not valid.");
                return false;
            }
        }
        return true;
    }

    private static boolean checkEncodedAscii(String s) {
        boolean bool1 = s.length() % 7 != 0;
        if (bool1) {
            System.out.println("Encoded string is not valid.");
            return false;
        }
        return true;
    }

    private static boolean checkEncodedInput(String[] s) {
        boolean bool1 = false;
        boolean bool2 = s.length % 2 != 0;

        for (int i =0;i < s.length;i +=2) {
            if (!(s[i].equals("0") || s[i].equals("00"))) {
                bool1 = true;
                break;
            }
        }

        if (bool1 || bool2) {
            System.out.println("Encoded string is not valid.");
            return false;
        }
        return true;
    }

    private static String convertToChuckNorris(String s) {
        StringBuilder builder = new StringBuilder();
//      Assigning 1st character to variable cPrev, assigning encoding '0'/'00' to 1 block.
        char cPrev = s.charAt(0);
        String block1 = cPrev == '1' ? "0": "00";
        builder.append(block1).append(" ");

        for (int i = 0; i < s.length(); i++) {
            char cNext = s.charAt(i);
            if (cNext == cPrev) {
                builder.append("0");
            }
            if (cNext != cPrev) {
                builder.append(" ");
                block1 = cNext == '1' ? "0" : "00";
                builder.append(block1).append(" ").append("0");
            }

            cPrev = s.charAt(i);
        }

        return String.valueOf(builder);
    }

    private static String convertToBinaryString(String s) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            String binary_c = String.format("%7s", Integer.toBinaryString(s.charAt(i))).replace(" ", "0");
            binaryString.append(binary_c);
        }
        return String.valueOf(binaryString);
    }

    private static String arrangeAsciiBits(String s) {
        StringBuilder ascii = new StringBuilder();
        for (int i = 0;i < s.length();i++) {
            if (i%7 == 0 && i !=0) {
                ascii.append(" ");
            }
//            System.out.print(s.charAt(i));
            ascii.append(s.charAt(i));
        }
        return String.valueOf(ascii);
    }

    private static String arrangeToCharacters(String s) {
        String[] ascii = parseStringToArray(s);
//        System.out.println(Arrays.toString(ascii));
        StringBuilder builder = new StringBuilder();
        for (String string : ascii) {
            int a = Integer.parseInt(String.valueOf(string), 2);
            builder.append((char) a);
        }
        return String.valueOf(builder);
    }

    private static String arrangeToBlocks(String[] sArray) {
        String cChar = "0";
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < sArray.length; i += 2) {
            if (sArray[i].equals("0")) {
                cChar = "1";
            } else if (sArray[i].equals("00")) {
                cChar = "0";
            }
            int count = sArray[i + 1].length();
            sequence.append(cChar.repeat(count));
        }
        return String.valueOf(sequence);
    }

    private static String[] parseStringToArray(String s) {
        return s.split(" " );
    }

    private static String userInput(Scanner scanner) {
        return scanner.nextLine();
    }
    private static void showMenu() {
        System.out.println("Please input operation (encode/decode/exit):");
    }
}