package schach.controller.Interfaces;

import schach.model.Board;

import java.util.Scanner;

public class InputVsAi {

    private Board board;

    private boolean playerWhite;

    private boolean playersTurn;

    private void init(){
        this.board = new Board();
        this.playerWhite = askForWhite();
        this.playersTurn = true;
        if (!playerWhite){
            this.playersTurn = false;
        }
    }

    public void runningRoutine(){
        init();
        boolean running = true;
        while (running){
            if (playersTurn){

            } else {

            }
        }
    }

    private boolean askForWhite(){
        System.out.println("Which color do you want to play with? (white/black)");
        boolean running = true;
        while (running){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("white")){
                return true;
            }
            if (input.equals("black")){
                return false;
            }
        }
        return false;
    }

    private void playerInput(){

    }
}
