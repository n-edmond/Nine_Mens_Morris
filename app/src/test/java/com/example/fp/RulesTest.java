package com.example.fp;

import junit.framework.TestCase;

public class RulesTest extends TestCase {

    int black1 = 5;//number of pieces
    int black2 = 0;//no pieces left (used for reserve)
    int black3 = 9;//first move of game (used for reserve)
    int black4 = 3;//fly check
    int loss_check;
    int piece_position = 3;
    int white_turn = Constants.WHITE;
    int black_turn = Constants.BLACK;
    int to1 = 1;//to index 1 of board
    int from1 = 2;//fron index 2 of board
    int board[] = new int [7];//board array. Shortened to 4
    int empty_field_1 = 0;//indicates that this slot on the board is empty


    public void testGetTurnWhite() {
        //this tests that the correct number is being pulled when the getTurn function is called. 2 indicates that it is white's turn, 1 that it is black
        int turn = white_turn;
        int expected_output = 2;
        System.out.println("testGetTurnWhite called. It is white's turn");
        assertEquals(expected_output, turn);
    }

    public void testGetTurnBlack() {
        //this tests that the correct number is being pulled when the getTurn function is called. 2 indicates that it is white's turn, 1 that it is black
        int turn = black_turn;
        int expected_output = 1;
        System.out.println("testGetTurnBlack called. It is black's turn");
        assertEquals(expected_output, turn);
    }

    public void testReserveDecrement() {
        //this tests that the black game pieces in reserve are being decremented. Since white runs under the same logic, this is general test

        //giving predefined variables
        int black_piece = black1;
        int turn = black_turn;
        int to = to1;
        int EMPTY_FIELD =empty_field_1;
        int expected = 4;
        //quickly populating empty board
        board[0] = 0;
        board[1] = 0;
        board[2] = 0;

        //since I only wish to test a specific case here, I have taken only part of the movePieceCheck function and ran it here
        if(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD) {
            black_piece--;//decrement number of black pieces
        }
        System.out.println("testBlackReserveDecrement. Successful decrementing");
        assertEquals(expected, black_piece);//should return true;
    }

    public void testUnsuccessfulReserveDecrement() {
        //this tests that the black game pieces in reserve will not be decremented if there are no more in reserve. Since white runs under the same logic, this is general test

        //giving predefined variables
        int black_piece = black2;
        int turn = black_turn;
        int to = to1;
        int EMPTY_FIELD =empty_field_1;
        int expected = 0;
        //quickly populating nonempty board
        board[0] = 0;
        board[1] = black_turn;
        board[2] = 0;

        //since I only wish to test a specific case here, I have taken only part of the movePieceCheck function and ran it here
        if(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD) {
            black_piece--;//decrement number of black pieces
        }
        System.out.println("testUnsuccessfulBlackReserveDecrement. No pieces in reserve");
        assertEquals(expected, black_piece);//should return true;
    }

    public void testSuccessfulPiecePlacementEmpty(){
        //this tests that the piece will be successfully placed if the board is empty and it is their turn

        //giving predefined variables
        int black_piece = black3;
        int turn = black_turn;
        int to = to1;
        int EMPTY_FIELD =empty_field_1;
        //quickly populating empty board
        board[0] = 0;
        board[1] = 0;
        board[2] = 0;

        //since I only wish to test a specific case here, I have taken only part of the movePieceCheck function and ran it here
        System.out.println("testSuccessfulPiecePlacementEmpty. Successful piece placement- empty board");
        assertTrue(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD);

    }

    public void testSuccessfulPiecePlacementNonempty(){
        //this tests that the piece will be successfully placed if the board is not empty and it is their turn

        //giving predefined variables
        int black_piece = black1;
        int turn = black_turn;
        int to = to1;
        int EMPTY_FIELD =empty_field_1;
        //quickly populating non empty board
        board[0] = black_piece;
        board[1] = empty_field_1;
        board[2] = black_piece;

        //since I only wish to test a specific case here, I have taken only part of the movePieceCheck function and ran it here
        System.out.println("testSuccessfulPiecePlacementEmpty. Successful piece placement-nonempty board");
        assertTrue(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD);//should return true

    }

    public void testUnsuccessfulPiecePlacementTurn(){
        //this function tests that the piece placement has been unsuccessful due to it not being the correct person's turn.

        //giving predefined variables
        int black_piece = black1;
        int turn = white_turn;
        int to = to1;
        int EMPTY_FIELD =empty_field_1;
        //quickly populating non empty board
        board[0] = black_piece;
        board[1] = empty_field_1;
        board[2] = black_piece;

        System.out.println("testUnsuccessfulPiecePlacementTurn. Piece not successfully placed. Wrong turn");
        assertFalse(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD);//should return false

    }

    public void testUnsuccessfulPiecePlacementEmptyReserve(){
        //this function tests that the piece placement has been unsuccessful due to not having any more pieces in reserve

        //giving predefined variables
        int black_piece = black2;
        int turn = white_turn;
        int to = to1;
        int EMPTY_FIELD =empty_field_1;
        //quickly populating non empty board
        board[0] = black_piece;
        board[1] = empty_field_1;
        board[2] = black_piece;

        System.out.println("testUnsuccessfulPiecePlacementEmptyReserve. Piece not successfully placed. No more pieces in reserve");
        assertFalse(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD);//should return false

    }

    public void testAdjacentMoveCheck(){
        //checks that the location the player wishes to move to is adjacent to their current location. Slots selected are empty and valid

        /*this is also a larger function that makes use of switch case. As stated in the Rules.java file in the comments, there are only 25 possible slots one can move to
         * if we consider just the top portion of the board like below
         * 1           2          3
         *     4       5      6
         * We can see that 2 has slots 1, 3, and 5 as adjacent. This logic continues for the entirety of the board and for simplicity, I am only testing one condition
         * */

        //giving predefined variables
        int from = 2;//move from slot 2
        int to = 5;//move to slot 5
        boolean fly = false;
        //quickly populating empty board
        board[0] = empty_field_1;
        board[1] = empty_field_1;
        board[2] = empty_field_1;
        int expected_val = 0;

        //should return true that the new location is adjacent, fly is false, and the slot the piece wishes to move to is empty
        assertTrue((to == 1 || to == 3 || to == 5) && (fly == false) && (expected_val == board[from]) );
        System.out.println("testAdjacentMoveCheck. Adjacent move authorized");
    }

    public void testUnsuccessfulAdjacentMoveCheckOccupied(){
        //checks that the movement cannot be completed if the slot the user wishes to move to is occupied
        //giving predefined variables
        int from = 2;//move from slot 2
        int to = 5;//move to slot 5
        boolean fly = false;
        //quickly populating non empty board
        board[0] = empty_field_1;
        board[1] = empty_field_1;
        board[2] = black_turn;
        int expected_val = 0;

        //should return false since the location the user wishes to move to is not empty
        assertFalse((to == 1 || to == 3 || to == 5) && (fly == false) && (expected_val == board[from]) );
        System.out.println("testUnsuccessfulAdjacentMoveCheckOccupied. Adjacent move not authorized, location blocked");

    }

    public void testUnsuccessfulAdjacentMoveCheckNotAdj(){
        //checks that the movement cannot be completed if the slot the user wishes to move to is not adjacent to their current location

        /*this is also a larger function that makes use of switch case. As stated in the Rules.java file in the comments, there are only 25 possible slots one can move to
         * if we consider just the top portion of the board like below
         * 1           2          3
         *     4       5      6
         * 7
         * We can see that 2 has slots 1, 3, and 5 as adjacent, however 7 is not.
         * */

        //giving predefined variables
        int from = 2;//move from slot 2
        int to = 7;//move to slot 7
        boolean fly = false;
        //quickly populating empty board
        board[0] = empty_field_1;
        board[1] = empty_field_1;
        board[2] = empty_field_1;
        int expected_val = 0;

        assertFalse((to == 1 || to == 3 || to == 5) && (fly == false) && (expected_val == board[from]) );
        System.out.println("testUnsuccessfulAdjacentMoveCheckNotAdj(). Adjacent move not authorized. Slot is not adjacent");


    }

    public void testEnteredFlying(){
        //checks that we can enter the flying phase if we have valid moves and we have at 3 pieces remaining

        //giving predefined variables
        int fly_check = black4;
        int expected_val = 3;
        boolean has_valid_moves = true;

        assertTrue((expected_val == fly_check) && has_valid_moves);//should return true
        System.out.println("testEnteredFlying. Successfully entered flying phase");
    }

    public void testUnsuccessfulFlyCheckWrongPieceCount(){
        //checks that we do not enter the flying phase if we do not have 3 pieces on the board

        //giving predefined variables
        int fly_check = black3;
        int expected_val = 3;
        boolean has_valid_moves = true;

        assertFalse((expected_val == fly_check) && has_valid_moves);//should return true
        System.out.println("testEnteredFlying. Did not enter flying phase. Too many pieces");
    }

    public void testSuccessfulRemoval(){
        //tests that we successfully remove a piece if a mill has been formed

        //giving predefined variables
        boolean formed_mill = true;
        boolean part_of_mill = false;
        boolean only_mills = false;
        //quickly populating non empty board
        board[0] = empty_field_1;
        board[1] = white_turn;
        board[2] = black_turn;
        int color = black_turn;
        int remove_from = board[1];

        assertTrue((remove_from != color) && (formed_mill == true) && (part_of_mill == false) && (only_mills == false));
        System.out.println("testSuccessfulRemoval. Piece may be removed");
    }

    public void testSuccessfulRemovalOnlyMills(){
        //checks that we successfully remove a piece if a mill has been formed and only mills from the opponent remain

        //giving predefined variables
        boolean formed_mill = true;
        boolean part_of_mill = true;
        boolean only_mills = true;
        //quickly populating non empty board
        board[0] = empty_field_1;
        board[1] = white_turn;
        board[2] = black_turn;
        int color = black_turn;
        int remove_from = board[1];

        assertTrue((remove_from != color) && (formed_mill == true) && (part_of_mill == true) && (only_mills == true));
        System.out.println("testSuccessfulRemovalOnlyMills. Only mills remain. Piece may be removed");

    }

    public void testSuccessfulMillFormation(){
        //checks if one of the given positions (which is a parameter) contains 3 in a row.

        //giving predefined variables
        int piecePos = piece_position;
        //quickly populating non empty board. Forming a mill
        board[0] = empty_field_1;
        board[1] = black_turn;
        board[2] = black_turn;
        board[3] = black_turn;

        //position is essentially one of the dots on the bord. Recall that there are only 25 possible dots
        // Since this is a part of a larger conditional statement, I've opted to only test a small portion
        assertTrue(((piecePos == 1 || piecePos == 2 || piecePos == 3) && (board[1] == board[2]
                && board[2] == board[3])));
        System.out.println("testSuccessfulMillFormation. Mill has been successfully created");
    }

    public void testUnsuccessfulMillFormationBlocked(){
        //checks that we do not form a mill when an opponent has blocked our row

        //giving predefined variables
        int piecePos = piece_position;
        //quickly populating non empty board. Forming a mill
        board[0] = empty_field_1;
        board[1] = black_turn;
        board[2] = black_turn;
        board[3] = white_turn;

        //position is essentially one of the dots on the bord. Recall that there are only 25 possible dots
        // Since this is a part of a larger conditional statement, I've opted to only test a small portion
        assertFalse(((piecePos == 1 || piecePos == 2 || piecePos == 3) && (board[1] == board[2]
                && board[2] == board[3])));
        System.out.println("testUnsuccessfulMillFormationBlocked. Mill not created. Piece has been blocked by opponent");

    }

    public void testUnsuccessfulMillFormation(){
        //checks that we do not form a mill with insufficient pieces in a row

        //giving predefined variables
        int piecePos = piece_position;
        //quickly populating non empty board. Forming a mill
        board[0] = black_turn;
        board[1] = black_turn;
        board[2] = black_turn;
        board[3] = empty_field_1;

        //position is essentially one of the dots on the bord. Recall that there are only 25 possible dots
        // Since this is a part of a larger conditional statement, I've opted to only test a small portion
        assertFalse(((piecePos == 1 || piecePos == 2 || piecePos == 3) && (board[1] == board[2]
                && board[2] == board[3])));
        System.out.println("testUnsuccessfulMillFormation. Mill not created. There are not 2 in a row");
    }

    public void testWinCheckNoMoves() {
        //checks if the game is over due to someone not having any moves left
    }

    public void testWinCheckNoPieces() {
        //checks if the game is over due to someone not having enough pieces

        //quickly populating non empty board.
        board[0] = white_turn;
        board[1] = black_turn;
        board[2] = white_turn;
        board[3] = white_turn;

        for(int i : board) {
            //if the index is the specified player val(in other words, has a piece in the board array, increment the counter
            if(i == black_turn) {
                loss_check++;
            }
        }

        //should return true
        assertTrue(loss_check < 3);
        System.out.println("testWinCheckNoPieces. Game has been won due to lack of pieces");
    }

    public void testUnsuccessfulWinCheckTooManyPieces(){
        //checks that the game does not end prematurely due to having more than 3 pieces on the board

        //quickly populating non empty board.
        board[0] = white_turn;
        board[1] = black_turn;
        board[2] = white_turn;
        board[3] = white_turn;
        board[4] = black_turn;
        board[5] = black_turn;
        board[6] = white_turn;

        for(int i : board) {
            //if the index is the specified player val(in other words, has a piece in the board array, increment the counter
            if(i == white_turn) {
                loss_check++;
            }
        }

        //should return false
        assertFalse(loss_check < 3);
        System.out.println("testUnsuccessfulWinCheckTooManyPieces. Game continues. Player still has pieces");
    }

}