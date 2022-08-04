import java.util.*; 
import java.io.*; 
public class Main{
    static int numWrong; 
    static Scanner kb = new Scanner(System.in);
    static PrintStream out = System.out; 
    public static void main(String[] args) throws FileNotFoundException{
        out.println("\n\nWELCOME TO HANGMAN. \n1 or 2 players ? Type 1 or 2");
        String numOfPlayers = kb.nextLine(); 

        //fail-safe in case user does not follow instructions. 
        while(numOfPlayers.charAt(0)!='1' && numOfPlayers.charAt(0)!= '2'){
            out.println("Please enter 1 or 2");
            numOfPlayers = kb.nextLine();
        }

        String word = "";  //stores the correct word for display and iteration purposes. 
        List<String> wordList = new ArrayList<>();// stores correct word(1 player) or series of random words for selection in the case of 1 player 

        if(numOfPlayers.charAt(0) == '1' ){
            //file reading
            Scanner sc = new Scanner(new File("words.txt"));
            //add all words to List
            while(sc.hasNext()){
                wordList.add(sc.next());
            }
            //end of file reading
            out.println("\nWhat level word would you like: \n\tLEVEL 1 length = 2-4 \n\tLEVEL 2 length = 5-6\n\tLEVEL 3 length = 7+.\nEnter 1, 2 or 3:");
            word  = new String( wordList.get ( (int)(Math.random()*wordList.size()))); //select random word.
            String level = kb.nextLine(); 

            //get random word till level constraits are met
            while(level.equals("1") && !(word.length() >= 2 && word.length() <=4)){ 
                word = new String( wordList.get ( (int)(Math.random()*wordList.size()))); //select random word constraits. 
            } 
            while(level.equals("2") && !(word.length() >= 5 && word.length() <=6)){
                 word = new String( wordList.get ( (int)(Math.random()*wordList.size()))); //select random word that matches level constraits. 
            } 
            while(level.equals("3") && !(word.length() >= 7)){ 
                word = new String( wordList.get ( (int)(Math.random()*wordList.size()))); //select random word that matches level constraits. 
            }
            
        


        }
        else{
            System.out.println("Player 1, please enter your word:");
            word = kb.nextLine();
            wordList.add(word.toLowerCase());
            for(int i=0; i< 50; i++){
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }
            System.out.println("Ready for player 2! Good luck!");
            
        }
      
       // out.println(word);  ---> DISPLAYS CORRECT WORD 
        
        List<Character> guesses = new ArrayList<>(); //list of character that user guesses
        String charGuess = new String("");
        playGame(charGuess, guesses, word, wordList);
        

    }
    public static void playGame(String charGuess, List<Character> guesses , String word, List<String> words){
        out.println("Guess the word. You only have 6 attempts possible!");
        while(true){
            takeAGuess(charGuess, guesses, word); 
            if(display(guesses,word) == true){ // returns true when user guesses every character
                out.println("YOU WON!");
                break;
            }
            if(numWrong>6){ //user has gone past guess limit (6). 
                out.println("YOU LOSE! BETTER LUCK NEXT TIME");
                out.println("The word was: " + word);
                break;
        
            }

        }
        playAgain(words, word, guesses, charGuess);

        
    }
    public static void playAgain(List<String> words, String word, List<Character> guesses, String charGuess){
        out.println("Play again: Y/N");
        String choice = new String(kb.nextLine());
        if(choice.charAt(0)=='Y'){
            word  = new String( words.get ( (int)(Math.random()*words.size())));
            out.println(word);
            playGame(charGuess, guesses, word, words);

        }
        else{
            out.println("\nSo that's a no then. Thank you for your participation");
        }
    }

    public static void takeAGuess(String charGuess, List<Character> guesses, String word){
        boolean failedOnce = false; 
        //fail-safe in case player inputs multiple characters
        do{
           
            //if the user follows instructions 
            if(failedOnce==false){
            out.println("Please enter a single-letter:");
            charGuess = new String(kb.nextLine());
            failedOnce = true; 
            }
            //if the user has not followed instructions
            else if(failedOnce == true){
                out.println("Please make sure to enter only a single-letter:");
                charGuess = new String(kb.nextLine());

            }
                }
        while(charGuess.length()>1);
        guesses.add(charGuess.charAt(0));

    }

        
    
    public static boolean display(List<Character> guesses, String word){
        int count=0; 
        for(int i = 0; i<word.length(); i++){
            char current = word.charAt(i);
            if(guesses.contains(current)){
                out.print(current); 
                count++; 
            }
            else{
                out.print("_");
            }

        }
        //that guess was completely incorrect since the new guess added is not even in the word
        String added = guesses.get(guesses.size()-1) + ""; 
        if(!word.contains(added)){
            out.println("\nThat wasn't quite right");
            displayhangMan(numWrong++); 

        }
       

        out.println();
        
        return count==word.length(); //the user has guessed every character in word. 



      
    }
    //method for display hanged man. 
    public static void displayhangMan(int numWrong){
        System.out.println(" -------");
        System.out.println(" |     |");
        if (numWrong >= 1) {
            System.out.println(" O");
          }
          
          if (numWrong >= 2) {
            System.out.print("\\ ");
            if (numWrong >= 3) {
              System.out.println("/");
            }
            else {
              System.out.println("");
            }
          }
          
          if (numWrong >= 4) {
            System.out.println(" |");
          }
          
          if (numWrong >= 5) {
            System.out.print("/ ");
            if (numWrong >= 6) {
              System.out.println("\\");
            }
            else {
              System.out.println("");
            }
          }
    }
}