# NextGame

Create a program that will implement the What's Next Game according to the following rules and examples.  Use Object-Oriented Design techniques. You will have at least one class, NextGame.  Run with a Driver Program.

## This is a Heading h2

This is a simple game where the player must guess the correct sequence of five numbers (values: 1-5). The game consists of an unlimited number of turns where the player can enter their guess sequence for the 5 numbers. The game will then compare the guess sequence entered by the player with the randomly generated solution sequence of five numbers (values 1 through 5) stored in a single dimension array. The game will alert the user of how many numbers in the guess sequence were correct, but not which ones were correct.  The game proceeds to the next turn where the player enters another sequence of numbers. The game ends when the player guesses the correct sequence of numbers or gives up by entering a ZERO (0) as one of the numbers in the sequence. The game displays the solution sequence at the end of the game. Each solution sequence will contain the values 1,2,3,4, and 5 in random order. Example solution sequences could include: {2,1,4,3,5}, {5,3,4,1,2}, and {3,1,4,2,5}. The game should save the 5 Highest Scores (Fewest Turns) with Name. 

# Example One

```
Game: Who's Next
Objective: Identify the Sequence of 5 numbers between 1 and 5 using the fewest turns. If you wish to quit guessing and give up, enter a ZERO for one of your guesses and the game will display the solution and quit. 
 GOOD LUCK!!! 

== Turn 1 == Number Sequence: 1 2 3 4 5 
You have 0 numbers correct 
== Turn 2 == Number Sequence: 2 3 4 5 1 

You guessed the sequence in 2 turns 

Game Number Sequence 
--------------------- 
| 2 | 3 | 4 | 5 | 1 | 
--------------------- 

High Scores 
--------------- 
 3 – Joe 
10 - Master 
12 - Joe 
```

# Example Two

```
Game: Who's Next 
Objective: Identify the Sequence of 5 numbers between 1 and 5 using the fewest turns. If you wish to quit guessing and give up, enter a ZERO for one of your guesses and the game will display the solution and quit. 
 GOOD LUCK!!! 

== Turn 1 == Number Sequence: 3 2 1 4 5 
You have 1 numbers correct 
== Turn 2 == Number Sequence: 5 4 3 2 1 
You have 0 numbers correct 
== Turn 3 == Number Sequence: 0 1 2 3 4 
You have 1 numbers correct 

Game Number Sequence 
--------------------- 
| 2 | 3 | 1 | 5 | 4 | 
--------------------- 

High Scores 
--------------- 
 3 – Joe 
10 - Master 
12 - Joe
```