package pl.lumbago.tictactoe;

import java.util.Scanner;

public final class TicTacToe {

  private static final Scanner scanner = new Scanner(System.in);
  private static char playerOneSign;
  private static char playerTwoSign;
  private static final char[] states = new char[9];
  private static int turns = 0;


  private TicTacToe() {
  }


  public static void main(String[] args) {
    while (true) {
      resetState();

      while (true) {
        printBoard();

        if (turns == 9) {
          System.out.println("Draw!");
          askPlayAgain();
          break;
        }

        int playerNumber = (turns % 2) == 1 ? 2 : 1;
        char playerChar = playerNumber > 1 ? playerTwoSign : playerOneSign;

        choice:
        while (true) {
          System.out.println("P" + playerNumber + " (" + playerChar + "): choose your spot (1 - 9)");
          String spot = scanner.nextLine().toUpperCase();
          switch (spot) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
              int index = Integer.parseInt(spot) - 1;
              if (states[index] != 'X' && states[index] != 'O') {
                states[index] = playerChar;
                break choice;
              }
              System.out.println("Spot " + spot + " is already taken!");
            }
            case "Q" -> System.exit(0);
            default -> System.out.println("Invalid input!");
          }
        }

        char winner = whoWon();
        if (winner > 0) {
          printBoard();
          System.out.println("P" + playerNumber + " (" + winner + ") won!\nThe game took " + (turns + 1) + " turns.\n");
          askPlayAgain();
          break;
        }

        turns++;
      }
    }
  }

  private static void askPlayAgain() {
    while (true) {
      System.out.println("Play again? (y / n)");
      String choice = scanner.nextLine().toUpperCase();
      switch (choice) {
        case "Y" -> {
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
      String temp = scanner.nextLine().toUpperCase();
      switch (temp) {
        case "X", "O" -> one = temp.charAt(0);
        default -> System.out.println("Invalid input!");
      }
    }

    switch (one) {
      case 'X' -> two = 'O';
      case 'O' -> two = 'X';
    }

    System.out.println("P2 gets \"" + two + "\" as their sign!");

    playerOneSign = one;
    playerTwoSign = two;
  }

  private static void printBoard() {
    String str = String.format("\n %s | %s | %s \n---+---+---\n %s | %s | %s \n---+---+---\n %s | %s | %s ",
            states[0], states[1], states[2], states[3], states[4], states[5], states[6], states[7], states[8]);

    System.out.println(str);
  }

  private static void resetState() {
    for (int i = 0; i < states.length; i++) states[i] = (i + 1 + "").charAt(0);
    turns = 0;
    askAndSetPlayerSigns();
  }

  private static char whoWon() {
    char winner = 0;
    char playerSign = playerOneSign;
    for (int i = 0; i < 2; i++) {
      if ((states[0] == playerSign && states[1] == playerSign && states[2] == playerSign)
              || (states[3] == playerSign && states[4] == playerSign && states[5] == playerSign)
              || (states[6] == playerSign && states[7] == playerSign && states[8] == playerSign)
              || (states[0] == playerSign && states[3] == playerSign && states[6] == playerSign)
              || (states[1] == playerSign && states[4] == playerSign && states[7] == playerSign)
              || (states[2] == playerSign && states[5] == playerSign && states[8] == playerSign)
              || (states[0] == playerSign && states[4] == playerSign && states[8] == playerSign)
              || (states[2] == playerSign && states[4] == playerSign && states[6] == playerSign)) {
        winner = playerSign;
      }
      playerSign = playerTwoSign;
    }
    return winner;
  }
}
