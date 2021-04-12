package com.example.fp;


import android.content.SharedPreferences;
import android.util.Log;


/*
 *The board for nine men's morris does not allow movements in all locations despite the board being 7x7
 Many locations do not actually have a slot that a player is allowed to insert a piece. If you look at
 the board, you can note that the playable pieces are all laid out in an asterisk format like below
 *
 * x           x          x
 *     x       x      x
 *         x   x   x
 * x   x   x       x  x   x
 *         x   x   x
 *     x       x       x
 * x           x           x
 *
 *
 * Note how there are only 24 possible spots you can move in. By noting this, we can limit the player
 * from choosing invalid locations and set our board to only contain space for 24 possible locations
 * this would prevent the user from selecting locations that are not valid to the board
 */

public class Rules {
    private final String tag = "Rules";
    private final String board_s = "Board";
    private final String turn_s = "Turn";
    private final String white_piece_s = "White_Piece";
    private final String black_piece_s = "Black_Piece";

    private final int[] board;
    private int turn;
    //Markers not on the playing field
    private int black_piece;
    private int white_piece;
    private int empty_space = 0;

    private final int EMPTY_FIELD = 0;

    public Rules() {
        board = new int[25]; //the number of viable spots on the board
        black_piece = 9; //how many black game pieces a player will have
        white_piece = 9; //how many white pieces a player will have
        turn = Constants.WHITE; //Randomize who will begin.
    }

    /**
     * Checks if player can place piece. Used for using up reserve pieces
     * @param from The position to move from.
     * @param to The position to move to.
     * @return True if the move was successful, else false is returned.
     */
    public boolean placePieceCheck(int from, int to) {
        Log.i(tag, "Trying to move piece: from " + from + " to " + to);
        Log.i(tag, "White pieces remaining in reserve: " + white_piece);
        Log.i(tag, "Black pieces remaining in reserve: " + black_piece);

        //Checks if the there are more than 0 game pieces, it is black piece turns, and the clicked location is empty
        if(black_piece > 0 && turn == Constants.BLACK && board[to] == EMPTY_FIELD) {
            board[to] = Constants.BLACK;//sets the board array at the desired location, to be equal to int val representing black piece
            black_piece--;//decrement number of black pieces in reserve
            turn = Constants.WHITE;//switch to white piece turn
            return true;//return the move is a valid one and black may move their piece
        }
        //Checks if there are game pieces, white player turn, and location is empty
        if(white_piece > 0 && turn == Constants.WHITE  && board[to] == EMPTY_FIELD) {
            board[to] = Constants.WHITE;//sets the board array at the index to, to be equal to white piece
            white_piece--;//decrement number of white pieces
            turn = Constants.BLACK;//switches to black piece
            return true;//return the move is a valid one
        }

        //If the player should not be moving (not their turn) return false. Prevents from both moving
        if(board[from] != turn) {
            return false;
        }

        //If the move isnt valid, return false
        if(!validLocationCheck(from, to)) {

            return false;
        }

        //movements are done here
        // This will move the game piece to the new position
        board[to] = board[from];
        board[from] = EMPTY_FIELD;

        //After the piece has been moved, change whose turn it is
        if(turn == Constants.WHITE) {
            turn = Constants.BLACK;
        } else {
            turn = Constants.WHITE;
        }

        return true;
    }

    /**
     * Checks if a game piece is allowed to move to a specified spot.
     * @param from The area the checker is at.
     * @param to The area the checker wants to go.
     * @return True if it's a valid move, else false is returned.
     */
    public boolean validLocationCheck(int from, int to) {
        //Checks that the location of the piece is moving to an empty tile
        if(board[to] != EMPTY_FIELD)  {
            Log.i(tag, "Location is currently occupied");
            return false;
        }
        if(from == 0) {
            return true;
        }
        //Checks if flying is occurring. All moves are valid if this is the case.
        if(flyingCheck(board[from])) {
            return true;
        }

        //There are only 25 possible spots to move a game piece, yet once a game piece has been placed it can only move to adjacent locations
        switch (to) {
            //returns if any adjacent spots for the given point are free. For example, in slot 1
            // (first free spot upper left), the adjacent locations are directly under it (spot 10) or directly to the right of it (spot 2)
            case 1:
                return (from == 10 || from == 2);
            case 2:
                return (from == 1 || from == 3 || from == 5 );
            case 3:
                return (from == 2 || from == 15);
            case 4:
                return (from == 5 || from == 11);
            case 5:
                return (from == 2 || from == 4 || from == 8 || from == 6);
            case 6:
                return (from == 5 || from == 14);
            case 7:
                return (from == 8 || from == 12);
            case 8:
                return (from == 5 || from == 7 || from == 9);
            case 9:
                return (from == 8 || from == 13);
            case 10:
                return (from == 1 || from == 11 || from == 22);
            case 11:
                return (from == 4 || from == 10 || from == 12 || from == 19);
            case 12:
                return (from == 7 || from == 11 || from == 16);
            case 13:
                return (from == 9 || from == 14 || from == 18);
            case 14:
                return (from == 6 || from == 13 || from == 15 || from == 21);
            case 15:
                return (from == 3 || from == 14 || from == 24);
            case 16:
                return (from == 12 || from == 17);
            case 17:
                return (from == 16 || from == 18 || from == 20);
            case 18:
                return (from == 13 || from == 17);
            case 19:
                return (from == 11 || from == 20);
            case 20:
                return (from == 17 || from == 19 || from == 21 || from == 23);
            case 21:
                return (from == 14 || from == 20);
            case 22:
                return (from == 10 || from == 23);
            case 23:
                return (from == 20 || from == 22 || from == 24);
            case 24:
                return (from == 15 || from == 23);
        }
        Log.i(tag, "Location is not adjacent");
        return false;//if no conditions are met return false
    }

    /**
     * Checks if the player can remove a piece.
     * @param piecePos The position of the current piece.
     * @return True if the checker is part of a line, else return false.
     */
    public boolean millCheck(int piecePos) {

        //Checks if game piece is part of a mill
        if(board[piecePos] == EMPTY_FIELD) {
            return false;
        }

        //List of all possible mills
        if((piecePos == 1 || piecePos == 2 || piecePos == 3) && (board[1] == board[2]
                && board[2] == board[3])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos== 4 || piecePos == 5 || piecePos == 6) && (board[4] == board[5]
                && board[5] == board[6])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 7 || piecePos == 8 || piecePos == 9) && (board[7] == board[8]
                && board[8] == board[9])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 10 || piecePos == 11 || piecePos == 12) && (board[10] == board[11]
                && board[11] == board[12])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 13 || piecePos == 14 || piecePos == 15) && (board[13] == board[14]
                && board[14] == board[15])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 16 || piecePos == 17 || piecePos == 18) && (board[16] == board[17]
                && board[17] == board[18])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 19 || piecePos == 20 || piecePos == 21) && (board[19] == board[20]
                && board[20] == board[21])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 22 || piecePos == 23 || piecePos == 24) && (board[22] == board[23]
                && board[23] == board[24])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 1 || piecePos == 10 || piecePos == 22) && (board[1] == board[10]
                && board[10] == board[22])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 4 || piecePos == 11 || piecePos == 19) && (board[4] == board[11]
                && board[11] == board[19])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 7 || piecePos == 12 || piecePos == 16) && (board[7] == board[12]
                && board[12] == board[16])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 2 || piecePos == 5 || piecePos == 8) && (board[2] == board[5]
                && board[5] == board[8])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 17 || piecePos == 20 || piecePos == 23) && (board[17] == board[20]
                && board[20] == board[23])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if(((piecePos == 9) || (piecePos == 13) || (piecePos == 18)) && ((board[9] == board[13])
                && (board[13] == board[18]))) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 6 || piecePos == 14 || piecePos == 21) && (board[6] == board[14]
                && board[14] == board[21])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        if((piecePos == 3 || piecePos == 15 || piecePos == 24) && (board[3] == board[15]
                && board[15] == board[24])) {
            empty_space++;
            Log.i(tag, "Number of free pieces - " +empty_space);
            return true;
        }
        //return false if no conditions are met
        empty_space = 0;
        Log.i(tag, "Number of free pieces - " +empty_space);
        return false;
    }

    /**
     * Remove a marker from the position if it matches the color
     * @param rfrom The checker to be removed.
     * @param color The color the checker should be if the remove is valid.
     * @return True if the removal was successful, else false is returned.
     */
    public boolean remove(int rfrom, int color) {
        //if the board at the specified index contains a certain color, set that tile to empty, then return it has been removed
        //boolean mill_remove_check = isolatedPieceCheck(rfrom);

        if (empty_space != 0){
            if (board[rfrom] == color) {
                Log.i(tag, "Trying to move piece from slot" + rfrom);
                Log.i(tag, "Color equals  " + color + " - opposite color");

                board[rfrom] = EMPTY_FIELD;//sets the tile to an empty one
                return true;
        }

        } else
            Log.i(tag, "Color equals  " + color + " not opposite color");
            return false;
    }

    /**
     * Checks if a player has won the game
     * @param player The player which may have lost.
     * @return True if player has lost, else false is returned.
     */
    public boolean winCheck(int player) {
        //All pieces must be placed on the board first. It is not possible to win if all pieces have not been placed.
        if(white_piece > 0 || black_piece > 0) {
            return false;
        }
        //If no valid moves are left, this player has lost
        if(!hasValidMoves(player)) {
            return true;
        }
        //Checks if the player has < 3 pieces left. Game is over if so
        int count = 0;//counter to count number of pieces on board
        for(int i : board) {
            //if the index is the specified player val(in other words, has a piece in the board array, increment the counter
            if(i == player) {
                count++;
            }
        }
        return (count < 3);//returns  bool if the player has < 3
    }

    /**
     * Checks if a player has a valid move
     * @param player The player whose turn it currently is
     * @return True if player has a valid move, else false is returned.
     */
    private boolean hasValidMoves(int player) {
        //essentially iterates through entire board (since only 25 spots are viable) and checks if a player can move
        for(int i = 0; i < 24; i++) {
            if(board[i+1] == player) {
                Log.i(tag, "Found the player " + player);
                for(int j = 0; j < 24; j++) {
                    //if the validLocationCheck function returns true, then they player has valid moves and should also return true
                    if(validLocationCheck(i+1, j+1)) {
                        Log.i(tag, "yes, valid moves");
                        return true;
                    }
                }
            }
        }
        //if no conditions are met, no valid moves return false
        Log.i(tag, "no valid moves");
        return false;
    }

    /**
     *Checks to see if flying is allowed yet
     * @param player The color which may be in the flying phase.
     * @return True if it has exactly 3 pieces left, else return false.
     */
    private boolean flyingCheck(int player) {
        int count = 0;
        for(int i : board) {
            if(i == player) {
                count++;
            }
        }
        //flying is allowed when only 3 or fewer pieces are left on the board. If the counter reaches 3, flying authorized
        return (count == 3);
    }

    /**
     * Purpose is to return the game piece that is placed
     * @param loc The location to be checked.
     * @return The color the checker in a loc is.
     */
    public int tileColor(int loc) {
        return board[loc];
    }

    /**
     *Getter. Returns player's turn
     * @return The player whose turn it is.
     */
    public int getTurn() {
        return turn;
    }

    public void savePref(SharedPreferences.Editor instance) {
        for(int i = 0; i < board.length; i++) {
            instance.putInt(board_s+i, board[i]);
        }

        instance.putInt(turn_s, turn);
        instance.putInt(white_piece_s, white_piece);
        instance.putInt(black_piece_s, black_piece);
        instance.commit();
    }

    public void restorePref(SharedPreferences instance) {
        for(int i = 0; i < board.length; i++) {
            board[i] = instance.getInt(board_s+i, EMPTY_FIELD);
            Log.i(tag, "Board position " + i + " filled with piece number " + board[i]);
        }


        turn = instance.getInt(turn_s, Constants.WHITE);
        white_piece= instance.getInt(white_piece_s, 9);
        black_piece = instance.getInt(black_piece_s, 9);
        Log.i(tag, white_piece + "");
        Log.i(tag, black_piece + "");
    }
}