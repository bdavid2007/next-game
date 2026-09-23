import java.util.Scanner;
import java.util.Random;

public class NextGame {
    public String name = "What's Next";
    private String correctAnswer;
    private String input;
    private String possibleInputs = "12345";
    private int curRound = 0, sequenceLength = 5;
    boolean isGameRunning = true;
    private Scanner sc;
    private User curUser;


    public void initialize() {
        String inputUser;
        sc = new Scanner(System.in);
        correctAnswer = generateCorrectNumberSequence();

        System.out.println("Enter a user name: ");
        inputUser = sc.next().trim().toLowerCase();

        curUser = new User(inputUser);


        System.out.println("Running game...\n\n");
        System.out.println("Game: " + name);
        System.out.println("Objective: Identify the Sequence of 5 numbers between 1 and 5 using the fewest turns. If you wish to quit guessing and give up, enter a ZERO for one of your guesses and the game will display the solution and quit.");
        System.out.println("GOOD LUCK!!!");

        while (isGameRunning) {
            startRound();
        }

        System.out.printf("\n\nGame Number Sequence\n%s\n\n", correctAnswer);
    }

    String generateCorrectNumberSequence()
    {
        Random rand = new Random();
        String answer = "";
        int i;

        for(i = 0; i < sequenceLength; i++)
        {
            answer += (1 + rand.nextInt(sequenceLength));    
        }

        return answer;
    }

    private void startRound()
    {
        curRound++;
        int i, j;
        boolean inputProvenValid = false, characterProvenValid;
        
        while(!inputProvenValid) // We goin to get an input outta you boy
        {
            System.out.printf("\n== Turn %s == Number Sequence: ", curRound);
            inputProvenValid = true;

            input = sc.next().trim().toLowerCase();

            if(input.equals("zero") || input.equals("0")){
                isGameRunning = false;
                return;
            }

            for(i = 0; i < input.length(); i++) { // For each character in the input...
                characterProvenValid = false;

                for(j = 0; j < possibleInputs.length(); j++) { // For each character in the possible input characters...
                    if(input.charAt(i) == possibleInputs.charAt(j)){ // If the input matches an input character...
                        characterProvenValid = true;
                    }
                }

                if(characterProvenValid == false) { // If no matching character was found...
                    inputProvenValid = false;
                }
            }

            if(input.length() < sequenceLength){
                inputProvenValid = false;
                System.out.printf("\n\n%s is too short of an input. Please try again.\n", input);
            }

            if(input.length() > sequenceLength){
                inputProvenValid = false;
                System.out.printf("\n\n%s is too long of an input. Please try again.\n", input);
            }

            if(inputProvenValid == false) // If the input couldn't be validated...
            {
                System.out.printf("\n\n%s is not a valid input. Please try again.\n", input);
            }
        }

        if(checkAnswer(input))
        {
            System.out.printf("\nYou guessed the sequence in %s turns.\n", curRound);
            curUser.SetScore(curRound);
            isGameRunning = false;
            Sound.Play("Fire")
        }
    }

    private boolean checkAnswer(String input)
    {
        int correctCount = 0;
        int i;

        for(i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) == correctAnswer.charAt(i))
            {
                correctCount++;
            }
        }

        if(correctCount == sequenceLength)
        {
            System.out.printf("\nAll %s numbers are correct!\n", correctCount);
            return true;
        }

        System.out.printf("\n%s numbers are correct.\n", correctCount);
        return false;
    }
}
