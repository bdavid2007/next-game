import java.util.Scanner;
import java.util.Random;

public class NextGame {
    public String name = "What's Next";
    private String correctAnswer;
    private String input;
    private String possibleInputs;
    private int curRound = 0, sequenceLength = 5;
    boolean isGameRunning = true;
    private Scanner sc;
    private User curUser;


    public void initialize() {
        String inputUser;
        sc = new Scanner(System.in);
        possibleInputs = gatherPossibleInputs();

        
        correctAnswer = generateCorrectNumberSequence();

        System.out.println("Enter a user name: ");
        inputUser = sc.nextLine().trim().toLowerCase();

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
        String unsortedNums = possibleInputs;
        String answer = "";
        String temp = "";
        int i;
        int index;

        for(i = 0; i < sequenceLength; i++)
        {
            index = rand.nextInt(unsortedNums.length());
            answer += unsortedNums.charAt(index);
            temp = unsortedNums.substring(index + 1);

            unsortedNums = unsortedNums.substring(0, index) + temp;
        }

        return answer;
    }

    String gatherPossibleInputs()
    {
        String tempPossibleInputs = "";
        int i;

        for(i = 1; i <= sequenceLength; i++)
        {
            tempPossibleInputs += i;
        }

        return tempPossibleInputs;
    }

    private void startRound()
    {
        int i, j;
        boolean inputProvenValid = false, 
            characterProvenValid;
        String unprocessedInput;
        String tempEvilInput, tempEvilInput2;
        curRound++;

        
        while(!inputProvenValid) // We goin to get an input outta you boy
        {
            System.out.printf("\n== Turn %s == Number Sequence: ", curRound);
            inputProvenValid = true;
            tempEvilInput = "";

            unprocessedInput = sc.nextLine().trim().toLowerCase();

            if(unprocessedInput.split(" ").length > 1)
            {
                for(i = 0; i < unprocessedInput.split(" ").length; i++)
                {
                    tempEvilInput2 = unprocessedInput.split(" ")[i];
                    if(tempEvilInput2.equals("one")) tempEvilInput += 1;
                    else if(tempEvilInput2.equals("two")) tempEvilInput += 2;
                    else if(tempEvilInput2.equals("three")) tempEvilInput += 3;
                    else if(tempEvilInput2.equals("four")) tempEvilInput += 4;
                    else if(tempEvilInput2.equals("five")) tempEvilInput += 5;
                    else if(tempEvilInput2.equals("six")) tempEvilInput += 6;
                    else if(tempEvilInput2.equals("seven")) tempEvilInput += 7;
                    else if(tempEvilInput2.equals("eight")) tempEvilInput += 8;
                    else if(tempEvilInput2.equals("nine")) tempEvilInput += 9;
                    else tempEvilInput += tempEvilInput2;
                }

                input = tempEvilInput;
            }
            else input = unprocessedInput;



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
                    System.out.printf("\n\n%s is not a valid input. Please try again.\n", input);
                    return;
                }
            }

            if(input.length() < sequenceLength){
                inputProvenValid = false;
                System.out.printf("\n\n%s is too short of an input. Please try again.\n", input);
                return;
            }

            if(input.length() > sequenceLength){
                inputProvenValid = false;
                System.out.printf("\n\n%s is too long of an input. Please try again.\n", input);
                return;
            }
        }

        if(checkAnswer(input))
        {
            System.out.printf("\nYou guessed the sequence in %s turns.\n", curRound);
            curUser.endGame(curRound);
            isGameRunning = false;
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
