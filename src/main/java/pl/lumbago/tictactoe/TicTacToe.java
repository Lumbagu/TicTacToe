package pl.lumbago.tictactoe;

import java.util.Scanner;

public final class TicTacToe {

  private static final Scanner scanner = new Scanner(System.in);
  private static char playerOneSign, playerTwoSign;
  // this represents the 3x3 game board
  private static char[] state;
  // current game turns counter
  private static int turns;


  private TicTacToe() {
  }


  public static void main(String[] args) {
    // main game loop
    while (true) {
      resetState();

      // current game loop
      while (true) {
        printBoard();

        // checking if all possible spots are taken
        if (turns == 9) {
          System.out.println("Draw!");
          askPlayAgain();
          // breaking from current game loop - starting a new game
          break;
        }

        // can be 1 or 2
        int playerNumber = (turns % 2) == 1 ? 2 : 1;
        // can be X or O
        char playerChar = playerNumber > 1 ? playerTwoSign : playerOneSign;

        choice:
        while (true) {
          System.out.println("P" + playerNumber + " (" + playerChar + "): choose your spot (1 - 9)");
          // asking player for spot number
          String spot = scanner.nextLine().toUpperCase();
          switch (spot) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
              // getting index from player input
              int index = Integer.parseInt(spot) - 1;
              // performing an action only if a given spot is free
              if (state[index] != 'X' && state[index] != 'O') {
                //setting chosen spot to current player char (X or O)
                state[index] = playerChar;
                // job done - breaking from the while loop
                break choice;
              }
              System.out.println("Spot " + spot + " is already taken!");
            }
            // a way to quickly quit
            case "Q" -> System.exit(0);
            default -> System.out.println("Invalid input!");
          }
        }

        // will be 0 if there is no winner yet
        char winner = whoWon();
        if (winner > 0) {
          printBoard();
          System.out.println("P" + playerNumber + " (" + winner + ") won!\nThe game took " + (turns + 1) + " turns.\n");
          askPlayAgain();
          // breaking from current game loop - starting a new game
          break;
        }

        // counting current game loop iterations - or current game turns
        turns++;
      }
    }
  }

  private static void askPlayAgain() {
    while (true) {
      System.out.println("Play again? (y / n)");
      // getting input from player
      String choice = scanner.nextLine().toUpperCase();
      switch (choice) {
        case "Y" -> {
          // doing nothing - "play again" logic
          // is handled in the main method
          return;
        }
        case "N" -> System.exit(0);
        default -> System.out.println("Invalid input!");
      }
    }
  }

  private static void askAndSetPlayerSigns() {
    char one = 0;
    char two = 0;

    while (one == 0) {
      System.out.print("Choose the sign for P1 (X or O): ");
      // player input
      String temp = scanner.nextLine().toUpperCase();
      switch (temp) {
        // getting the first char from one-char-long string
        // just a way to convert it to char type
        case "X", "O" -> one = temp.charAt(0);
        default -> System.out.println("Invalid input!");
      }
    }

    // determining P2 char based on P1 char
    switch (one) {
      case 'X' -> two = 'O';
      case 'O' -> two = 'X';
    }

    System.out.println("P2 gets \"" + two + "\" as their sign!");

    // setting class fields for use in the main method
    playerOneSign = one;
    playerTwoSign = two;
  }

  private static void printBoard() {
    String str = String.format("\n %s | %s | %s \n---+---+---\n %s | %s | %s \n---+---+---\n %s | %s | %s ",
            state[0], state[1], state[2], state[3], state[4], state[5], state[6], state[7], state[8]);

    System.out.println(str);
  }

  private static void resetState() {
    // setting spots numbers as their values
    state = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    turns = 0;
    askAndSetPlayerSigns();
  }

  // this might need a rework
  private static char whoWon() {
    // winner char - will be X or O - or 0 if no one won yet
    char winner = 0;
    // using this temp var and a for loop below to check both player chars
    char temp = playerOneSign;
    for (int i = 0; i < 2; i++) {
      if ((state[0] == temp && state[1] == temp && state[2] == temp)
              || (state[3] == temp && state[4] == temp && state[5] == temp)
              || (state[6] == temp && state[7] == temp && state[8] == temp)
              || (state[0] == temp && state[3] == temp && state[6] == temp)
              || (state[1] == temp && state[4] == temp && state[7] == temp)
              || (state[2] == temp && state[5] == temp && state[8] == temp)
              || (state[0] == temp && state[4] == temp && state[8] == temp)
              || (state[2] == temp && state[4] == temp && state[6] == temp)) {
        winner = temp;
      }
      temp = playerTwoSign;
    }
    // returning a winner player char or 0
    return winner;
  }
}
