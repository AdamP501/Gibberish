/**
 Adam Pei
 16th January 2024
 Gibberish
 This program turns text into gibberish based off syllables inputed by the user.
 **/


import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;


public class main {


    public static void main(String[] args) throws Exception {
        int gameStatus = 1;

        Scanner scanner = new Scanner(System.in);


        System.out.println("Hello there! This is gibberish program!.");
        System.out.println("In this program, you will input two syllables. The first syllable will be added to the first vowel in every word, and the second syllable will be added to each subsequent vowel present in the word.");
        System.out.println("You can also add the wildcard(*) at the beginning of a syllable. After the text has been turned into gibberish, the wildcard turns into the vowel in front of the syllable.");
        System.out.println("You may pick from one of three text files: poem.txt (Still I Rise by Maya Angelou), play.txt (Romeo and Juliet by William Shakespeare), and speech.txt(I am Prepared to Die by Nelson Mandela)");
        System.out.println("After you have turned a text file into gibberish, you may choose whether or not you want to store that gibberish in a new text file.");
        System.out.println("Have fun with the program!" + "\n");


        String[] fileChoices = new String[99];
        fileChoices[0] = "poem.txt";
        fileChoices[1] = "play.txt";
        fileChoices[2] = "speech.txt";



        while (gameStatus == 1) //while the user has not quit
        {
            //Ask user for their file
            String chosenFile = chooseFile(fileChoices);

            String[] syllArray = new String[2];

            char[] acceptedLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};




            System.out.println("Enter the first Gibberish syllable (add * for the vowel substitute): ");
            syllArray[0] = scanner.next();

            //check if the syllable is valid
            while (!validSyllable(syllArray[0]))
            {
                if (!validSyllable(syllArray[0]))
                {
                    System.out.println("Syllable must only contain letters or a wildcard ('*'), which needs to be at the beginning of the syllable.");
                    System.out.println("Please reenter your syllable: ");
                    syllArray[0] = scanner.next();
                }
            }



            System.out.println("Enter the second Gibberish syllable (* for vowel substitute): ");
            syllArray[1] = scanner.next();

            //check if the syllable is valid
            while (!validSyllable(syllArray[1]))
            {
                if (!validSyllable(syllArray[1]))
                {
                    System.out.println("Syllable must only contain letters or a wildcard ('*'), which needs to be at the beginning of the syllable.");
                    System.out.println("Please reenter your syllable: ");
                    syllArray[1] = scanner.next();
                }
            }



            String[] finalArray = convert(syllArray, chosenFile);
            System.out.println("\n" + "Your translated contests are: ");
            for (String i : finalArray) {
                System.out.println(i);
            }


            //Ask user whether they want to store the converted data into a text file
            System.out.println("\n" + "Would you like to store this gibberish into a text file? Respond with 1 for yes. Respond with 2 for no.");
            int storeResponse = scanner.nextInt();
            while (storeResponse != 1 && storeResponse != 2) {
                System.out.println("\n" + "Please respond with 1 or 2.");
                storeResponse = scanner.nextInt();
            }
            if (storeResponse == 1)
            {
                storeFile(finalArray, fileChoices);
            }
            else
            {
                System.out.println("\n" + "Noted. The gibberish will not be stored in a text file");
            }


            //Ask user whether they want to continue or quit
            System.out.println("\n" + "Would you like to turn another file into gibberish? Respond with 1 for yes. Response with 2 to quit.");
            gameStatus = scanner.nextInt();
            while (gameStatus != 1 && gameStatus != 2)
            {
                System.out.println("\n" + "Please respond with 1 or 2.");
                gameStatus = scanner.nextInt();
            }
            if (gameStatus == 2)
            {
                System.out.println("Thanks for using this program. Goodbye.");
            }
        }

    }

    public static boolean validSyllable(String syll)
    {
        if (syll.charAt(0) != '*' && !Character.isLetter(syll.charAt(0)))
        {
            return false;
        }
        for (int i = 1; i < syll.length(); i++)
        {
            if (!Character.isLetter(syll.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static String chooseFile(String[] fileChoices) throws FileNotFoundException {
        boolean validResponse = false;
        String fileChoice = ""; //Will be used to store the name of the user's chosen file
        Scanner scanner = new Scanner(System.in);



        //Display the names of existing files
        System.out.println("The current existing text files are:");
        for (String i : fileChoices)
        {
            if(i != null)
            {
                System.out.println(i);
            }
        }


        System.out.println("\n" + "Enter the name of the file you want to turn into gibberish");
        fileChoice = scanner.next();


        while(!validResponse)
        {
            //Check if the inputted file name exists
            for (int i = 0; i < fileChoices.length; i++) {
                if (fileChoices[i] != null && fileChoices[i].equals(fileChoice)) {
                    validResponse = true;
                }
            }
            //Prompt the user again for a file name if the file name doesn't exist
            if (!validResponse)
            {
                System.out.println("File does not exist. Please enter the name of a file that does exist.");
                fileChoice = scanner.next();
            }
        }

        return(fileChoice);
    }




    public static String[] convert(String syllArray[], String filename) throws Exception {
        char[] letters = {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};
        String[] originalArray = readFileIntoArray(filename); //the original text file will be stored in this array
        String[] finalArray = new String[originalArray.length]; //the encrypted/decrypted text will be stored in this array
        int index = 0; //used to represent the index of finalArray
        boolean isVowel = false;
        boolean behindIsVowel = false;
        boolean hasWildCard = false;
        int count = 0;

        for (String line : originalArray)
        {
            String temp = "";
            String[] splitString = line.split(" ");


            //for each word in a line
            for (String word : splitString)
            {
                count = 0; //go back to the first syllable at the beginning of each word

                for (int i = 0; i < word.length(); i++) //for each character in the word
                {

                    //reset the boolean values
                    isVowel = false;
                    behindIsVowel = false;

                    //check if the syllable has a wildcard
                    if (syllArray[count].charAt(0) == '*')
                    {
                        hasWildCard = true;
                    }
                    else
                    {
                        hasWildCard = false;
                    }


                    //check if the character is a vowel
                    for (char letter : letters)
                    {
                        if (word.charAt(i) == letter)
                        {
                            isVowel = true;
                        }
                    }

                    //if the first character of the word is a vowel
                    if (isVowel == true && i == 0)
                    {
                        if (hasWildCard == true)
                        {
                            temp += word.charAt(i) + syllArray[count].substring(1);
                        }
                        else
                        {
                            temp += syllArray[count];
                        }
                        count = 1;
                    }

                    //if any character after the first is a vowel
                    else if (isVowel == true)
                    {
                        //check if the character behind is a vowel
                        for (char letter : letters)
                        {
                            if (word.charAt(i-1) == letter)
                            {
                                behindIsVowel = true;
                            }
                        }

                        //if the character behind is not a vowel then add the syllable
                        if (behindIsVowel != true)
                        {
                            if (hasWildCard == true)
                            {
                                temp += word.charAt(i) + syllArray[count].substring(1);
                            }
                            else
                            {
                                temp += syllArray[count];
                            }
                            count = 1;
                        }
                    }

                    temp += word.charAt(i);
                }
                temp += " "; //we need to add the space back because we got rid of it with the split function

            }

            finalArray[index] = temp; //add the gibberish line into the finalArray
            index++;
        }

        return finalArray;

    }



    public static void storeFile(String[] finalArray, String[] fileChoices) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);


        System.out.print("\n" + "Please enter the name of the new file: ");
        String fileName = scanner.next();


        //Store the elements of the encrypted/decrypted array into a file
        PrintStream store = new PrintStream(fileName);
        for (String i : finalArray)
        {
            store.println(i);
        }
        store.close();


        //Store the name of the new file into the array of existing file names
        for (int i = 0; i < fileChoices.length; i++)
        {
            if (fileChoices[i] == null)
            {
                fileChoices[i] = fileName;
                break;
            }


        }
    }


    public static int countLinesInFile(String nameOfFile) throws FileNotFoundException {


        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);


        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        }
        return lineCount;
    }


    public static String[] readFileIntoArray(String nameOfFile) throws Exception {


        int linesInFile = countLinesInFile(nameOfFile);
        String[] array = new String[linesInFile];


        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);


        int index = 0;
        while (scanner.hasNextLine()) //iterate to the end of the file
        {
            array[index++] = scanner.nextLine(); //put each line of the file into an element in the array
        }
        return array;
    }


}

