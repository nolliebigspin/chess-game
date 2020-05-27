package schach.controller.interfaces;

import schach.model.Board;
import schach.model.Piece;

import java.util.Scanner;

public class HumanVsComputer {

    private Board board;

    private PlayerInput playerInput;

    private AiInteface aiInteface;

    private boolean playerIsWhite;

    private boolean playersTurn;

    private void init(){
        this.board = new Board();
        board.initLineUp();

        this.playerIsWhite = askForWhite();
        this.playersTurn = true;
        if (!playerIsWhite){
            this.playersTurn = false;
        }

        this.playerInput = new PlayerInput(board, playerIsWhite);
        this.aiInteface = new AiInteface(board, !playerIsWhite);

        board.printBoard();
    }

    public void runningRoutine(){
        init();
        boolean running = true;
        while (running){
            if (playersTurn){
                playerMove();
                playersTurn = false;
            } else {
                computerMove();
                delayPrint();
                playersTurn = true;
            }
            board.printBoard();
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

    private void playerMove(){
        String playerMove = playerInput.inputRoutine();
        String startDenotation = playerMove.substring(0,2);
        String targetDenotation = playerMove.substring(3,5);
        Piece piece = board.squareByDenotation(startDenotation).getOccupier();

        board.movePiece(startDenotation, targetDenotation);
        if (piece.isValidMove()){
            if (playerInput.isPromotion()){
                String prom = playerMove.substring(5);
                //piece.doPromotion(prom, board.squareByDenotation(targetDenotation));
            }
            return;
        } else {
            System.out.println("!Move not allowed");
            playerMove();
        }

    }

    private void computerMove(){
        System.out.println("\n this would be a computer move");


        String computerMove = aiInteface.getNextMove();
        String startDenotation = computerMove.substring(0,2);
        String targetDenotation = computerMove.substring(3,5);
        Piece piece = board.squareByDenotation(startDenotation).getOccupier();

        board.movePiece(startDenotation, targetDenotation);
        if (computerMove.length() == 6){
            String prom = computerMove.substring(5);
            //piece.doPromotion(prom, board.squareByDenotation(targetDenotation));
        }


    }

    private void delayPrint(){
        System.out.print("\u27F3");
        for (int i = 1000000; i > 0; i--){
            String out;
            if (i % 1000 == 0){
                out = "\r ";
            } else {
                out = "\r\u27F3 ";
            }
            System.out.print(out);
        }
        System.out.println("\n");
    }

}
