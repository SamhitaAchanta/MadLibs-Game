/* Program: Mad Libs
 * This: MadLibs.java
 * Date: 29 April 2019
 * Author: Samhita Achanta
 * Purpose: The user will get choose from three stories. The program will then print out the story with the users input both to a screen and to a text file. 
 */
package madlibs;

/**
 *
 * @author Samhita
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class MadLibs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Hello! Which story would you like?\n Choose: \n 1: Male Personal Ad \n "
                + "2: Romeo and Juliet Prologue \n 3: Vacations \n Or Press any key to quit \n Please enter here: "); // user input to choose which story
        String scribe = sc.nextLine();
        String newChar = " ";
        //String newLine = "";
        int beginIndex = 0;  // begin "/"
        int endIndex = 0;    // ending "/"
        int qaIndex = 0;     // question-answer-index (used in string array)
        int newLineBeginIndex = 0; // used to capture first part of the sentence
        int newLineEndIndex = 0;   // same as above but it is the first "/" in a sentence
        int fullLineBeginIndex = 0; // used to capture the end part of the sentence after last "/"

        Scanner reader = new Scanner(new File("Male Personal Ad.txt"));
        PrintWriter writer = new PrintWriter(new File("Dummy File.txt"));

        if (scribe.equals("1")) {
            reader = new Scanner(new File("Male Personal Ad.txt")); // reads from file
            writer = new PrintWriter(new File("Male Personal AdNew.txt")); // prints to a new text file
        }
        if (scribe.equals("2")) {
            reader = new Scanner(new File("Romeo and Juliet Prologue.txt"));
            writer = new PrintWriter(new File("Romeo and Juliet Prologue New.txt"));
        }
        if (scribe.equals("3")) {
            reader = new Scanner(new File("Vacations.txt"));
            writer = new PrintWriter(new File("Vacations New.txt"));
        }
        if (scribe.equals("1") || scribe.equals("2") || scribe.equals("3")) {
            // main logic starts here
            String[] userQuestions = new String[50]; // stores the questions
            String[] userAnswers = new String[50]; // stores the user's answers

            while (reader.hasNext()) {
                String str = reader.nextLine(); // reads each line of text file
                fullLineBeginIndex = 0; // initialize for every new sentence from the file

                int i = 0;
                //below while loop is to capture the string between two slashes
                while (true) {
                    newLineBeginIndex = i;
                    // below while loop is to look for first "/" of a pair
                    while (i < str.length()) {
                        newChar = str.substring(i, i + 1);
                        if (newChar.equals("/")) { // looks for the first slash of a pair
                            beginIndex = i;
                            //newLine += str.substring(i, beginIndex);
                            newLineEndIndex = i;
                            // prints the first part of the sentence before first slash or any text NOT between 2 slashes
                            writer.print(str.substring(newLineBeginIndex, newLineEndIndex)); // write to file
                            i++;
                            break;  //  breaks out of this loop 
                        }
                        i++;
                    }
                    // below while loop is to look for second "/" of a pair
                    while (i < str.length()) {
                        newChar = str.substring(i, i + 1);
                        if (newChar.equals("/")) { // looks for  the ending of slash
                            endIndex = i;
                            i++;
                            fullLineBeginIndex = i; // this is to capture the last slash of the sentence
                            break;  // breaks out of the loop after capturing 2nd "/" of the pair
                        }
                        i++;
                    }
                    // logic flows here after capturing text between one pair of slashes (parts of speech)
                    if (beginIndex > 0 && endIndex > 0) {
                        // below line is to capture the word(s) between 2 slashes
                        userQuestions[qaIndex] = str.substring(beginIndex + 1, endIndex);
                        System.out.println("Please enter " + userQuestions[qaIndex] + ": "); // user input for each of the parts of speech
                        userAnswers[qaIndex] = sc.nextLine();
                        writer.print(userAnswers[qaIndex]);  // write to file
                        qaIndex++;
                        beginIndex = 0; // reset
                        endIndex = 0;   // reset
                    }
                    if (i >= str.length()) {
                        break;
                    }
                }   // end of while(true)
                writer.println(str.substring(fullLineBeginIndex)); // to write the last part of the sentence after last "/"
            }
            reader.close();
            writer.close();
            // below logic is to re-open the new file as reader and display the contents to screen
            if (scribe.equals("1")) {
                reader = new Scanner(new File("Male Personal AdNew.txt")); // reads from file
            }
            if (scribe.equals("2")) {
                reader = new Scanner(new File("Romeo and Juliet Prologue New.txt"));
            }
            if (scribe.equals("3")) {
                reader = new Scanner(new File("Vacations New.txt"));
            }
            while (reader.hasNext()) {
                String strNew = reader.nextLine(); // reads from the new text file with the replaced parts of speech
                System.out.println(strNew); // prints to the screen the full complete story
            }
        } else {
            System.out.println("Program ending.");  // user entered a key other than 1, 2 or 3.
        }
    }
}
