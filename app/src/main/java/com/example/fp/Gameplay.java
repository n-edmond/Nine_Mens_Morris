package com.example.fp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;



public class Gameplay extends AppCompatActivity {
    private final String TAG = "Main screen";

    private final String WHITE_INDEXES = "WHITE_INDEXES";
    private final String BLACK_INDEXES = "BLACK_INDEXES";
    private final String WHITE_INDEXES_SIZE = "WHITE_INDEXES_SIZE";
    private final String BLACK_INDEXES_SIZE = "BLACK_INDEXES_SIZE";
    private final String REMOVE_CHECKER = "REMOVE_CHECKER";
    private final String WIN_CHECK = "WIN_CHECK";
    private final String NEW_GAME = "NEW_GAME";

    Rules rules = new Rules();

    private TextView playerTurn;
    private ArrayList<ImageView> white_pieces;
    private ArrayList<ImageView> black_pieces;
    private ArrayList<FrameLayout> hitBoxLoc;
    private ImageView selectedPiece;
    private FrameLayout areaToMoveTo;
    private HashMap<ImageView, Integer> posOfPiece;

    private boolean selectPieceCheck = false;
    private boolean removePiece = false;
    private boolean may_remove_from_mill = false;
    private boolean isWin = false;
    private boolean newGame = true;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    private ArrayList<String> whiteIndexes = new ArrayList<String>();
    private ArrayList<String> blackIndexes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting Board Activity Creation");
        setContentView(R.layout.activity_gameboard);//sets the layout of the page

        pref = this.getSharedPreferences("com.example.fp", Context.MODE_PRIVATE);
        edit = pref.edit();

        selectedPiece = null;
        areaToMoveTo = null;
        posOfPiece = new HashMap<>();//provides coords for piece positon
        playerTurn = (TextView) findViewById(R.id.TurnText);

        //This will save all the white game pieces into an array by finding the checker peice by the id given in the xml linked to this
        white_pieces = new ArrayList<ImageView>();
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker1));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker2));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker3));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker4));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker5));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker6));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker7));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker8));
        white_pieces.add((ImageView) findViewById(R.id.whiteChecker9));

        //This will save all the black game pieces into an array by finding the checker peice by the id given in the xml linked to this
        black_pieces = new ArrayList<ImageView>();
        black_pieces.add((ImageView) findViewById(R.id.blackChecker1));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker2));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker3));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker4));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker5));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker6));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker7));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker8));
        black_pieces.add((ImageView) findViewById(R.id.blackChecker9));

        //Saves the locations of the hitboxes into a list
        hitBoxLoc = new ArrayList<FrameLayout>();
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area1));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area2));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area3));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area4));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area5));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area6));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area7));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area8));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area9));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area10));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area11));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area12));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area13));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area14));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area15));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area16));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area17));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area18));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area19));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area20));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area21));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area22));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area23));
        hitBoxLoc.add((FrameLayout) findViewById(R.id.area24));



        //Add a onClickListener to the white piece. Essentially checks if a user has "clicked" something on the screen
        for (ImageView v : white_pieces) {
            posOfPiece.put(v, 0);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (rules.getTurn() == Constants.WHITE && !isWin) {
                        selectChecker(v);
                    }
                }
            });
        }

        //Add a onClickListener to the black piece. Essentially checks if a user has "clicked" something on the screen
        for (ImageView v : black_pieces) {
            posOfPiece.put(v, 0);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (rules.getTurn() == Constants.BLACK && !isWin) {
                        selectChecker(v);
                    }
                }
            });
        }

        //Add a onClickListener to the where the hitboxes have been placed. Essentially checks if a user has "clicked" something on the screen
        for (FrameLayout v : hitBoxLoc) {
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Checks if the user has selected a piece and will try movement
                    if (selectPieceCheck) {
                        //Log.i is used to post information that is helpful into the Logcat
                        Log.i(TAG, "Area has been clicked");
                        int currentTurn = rules.getTurn();
                        areaToMoveTo = (FrameLayout) v;

                        //Determines where the piece will be moving to
                        int to = Integer.parseInt((String) areaToMoveTo.getContentDescription()); //new loc
                        int from = posOfPiece.get(selectedPiece);//old loc
                        //Attempts to move the piece
                        if (rules.placePieceCheck(from, to)) { // This line will change turn
                            Log.i(TAG, "Valid move");
                            //Update the UI to show movement
                            removeHighlights();
                            movePiece(currentTurn);
                            //gets coordinates for piece location
                            posOfPiece.put((ImageView) selectedPiece, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                            //Checks if player has achieved a mill
                            removePiece = rules.millCheck(to);
                            //sets the selected piece for the user
                            selectedPiece.setAlpha(1.0f);

                            //Stops the piece from being selected
                            selectPieceCheck = false;
                            selectedPiece = null;
                            posOfPiece.put((ImageView) selectedPiece, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                            //Checks for mill
                            removePiece = rules.millCheck(to);

                            //Changes the text for each player
                            //if a piece can be removed and the current turn is black(player 2), notify player to remove piece
                            if (removePiece) {
                                if (currentTurn == Constants.BLACK) {
                                    playerTurn.setText("Remove a white piece");
                                } else {
                                    playerTurn.setText("Remove a black piece");
                                }
                            }
                            else {
                                //if pieces cannot be removed, just indicate whose turn it is
                                if (currentTurn == Constants.BLACK) {
                                    playerTurn.setText("Player 1, Go!");
                                } else {
                                    playerTurn.setText("Player 2, Go!");
                                }
                            }
                            //Checks to see if anyone has won the game
                            isWin = rules.winCheck(rules.getTurn());
                            if(isWin) {
                                if (rules.getTurn() == Constants.BLACK) {
                                    playerTurn.setText("White wins!");
                                } else {
                                    playerTurn.setText("Black wins!");
                                }

                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    /*
    On pause is a built in function with android studios. It is called as part of the activity lifecycle when the user no
    longer actively interacts with the activity, but it is still visible on screen.
     */
    public void onPause() {
        super.onPause();
        Log.i(TAG, "********pausing game**********");
        rules.savePref(edit);
        edit.putInt(WHITE_INDEXES_SIZE, whiteIndexes.size());
        edit.putInt(BLACK_INDEXES_SIZE, blackIndexes.size());

        for(int i = 0; i < whiteIndexes.size(); i++) {
            edit.putString(WHITE_INDEXES+i, whiteIndexes.get(i));
        }
        for(int i = 0; i < blackIndexes.size(); i++) {
            edit.putString(BLACK_INDEXES+i, blackIndexes.get(i));
        }
        edit.putBoolean(WIN_CHECK, isWin);
        edit.putBoolean(REMOVE_CHECKER, removePiece);
        edit.commit();
    }

    @Override
    /*
    built in function called after the pause. Allows activity to start interacting with the user. This is an
    indicator that the activity became active and ready to receive input.
     */
    public void onResume() {
        super.onResume();
        Log.i(TAG, "********resuming game**********");
        newGame = pref.getBoolean(NEW_GAME, false);
        edit.putBoolean(NEW_GAME, false);
        if(!newGame) {
            rules.restorePref(pref);
            int whiteSize = pref.getInt(WHITE_INDEXES_SIZE, 0);
            int blackSize = pref.getInt(BLACK_INDEXES_SIZE, 0);
            for(int i = 0; i < whiteSize; i++) {
                whiteIndexes.add(pref.getString(WHITE_INDEXES+i, ""));
            }
            for(int i = 0; i < blackSize; i++) {
                blackIndexes.add(pref.getString(BLACK_INDEXES+i, ""));
            }

            isWin = pref.getBoolean(WIN_CHECK, false);
            removePiece = pref.getBoolean(REMOVE_CHECKER, false);
            restore();
        }
    }

    private void restore() {
        int white = 0;
        int black = 0;
        for(int i = 1; i < 25; i ++) {
            int color = rules.tileColor(i);
            int index = 0;
            ViewGroup parent = null;
            if(color == Constants.WHITE) {
                index = Integer.parseInt(whiteIndexes.get(white));
                white++;
                parent = ((ViewGroup)findViewById(R.id.whiteCheckerArea));
            } else if(color == Constants.BLACK) {
                index = Integer.parseInt(blackIndexes.get(black));
                black++;
                parent = ((ViewGroup)findViewById(R.id.blackCheckerArea));
            }
            if(parent != null) {
                ImageView checker = setPlaceHolder(index, parent);
                ((ViewGroup) findViewById(R.id.board)).addView(checker);
                int areaId = getResources().getIdentifier("area" + i, "id", this.getPackageName());
                checker.setLayoutParams(findViewById(areaId).getLayoutParams());
                posOfPiece.put(checker, i);
            }
            if (removePiece) {
                if (rules.getTurn() == Constants.WHITE) {
                    playerTurn.setText("Remove White");
                } else {
                    playerTurn.setText("Remove Black");
                }
            } else {
                if (rules.getTurn() == Constants.WHITE) {
                    playerTurn.setText("Player 1, Go!");
                } else {
                    playerTurn.setText("Player 2, Go!");
                }
            }
            if(isWin) {
                if (rules.getTurn() == Constants.BLACK) {
                    playerTurn.setText("Player 1, you win!!");
                } else {
                    playerTurn.setText("Player 2, you win!!!");
                }
            }
        }
        while(white < whiteIndexes.size()) {
            setPlaceHolder(Integer.parseInt(whiteIndexes.get(white)), ((ViewGroup)findViewById(R.id.whiteCheckerArea)));
            white++;
        }
        while(black < blackIndexes.size()) {
            setPlaceHolder(Integer.parseInt(blackIndexes.get(black)), ((ViewGroup)findViewById(R.id.blackCheckerArea)));
            black++;
        }
    }

    private ImageView setPlaceHolder(int index, ViewGroup parent) {
        ImageView checker = (ImageView)parent.getChildAt(index);
        parent.removeViewAt(index);
        FrameLayout placeholder = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_placeholder, parent, false);
        parent.addView(placeholder, index);
        return checker;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    /**
     * Used to start a new game once the selection has been made
     * @param item is the menu to retrieve
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Standard menu creating
        switch (item.getItemId()) {
            //Start a new game
            case R.id.newgame:
                //Start a new game
                finish();
                startActivity(getIntent());
                edit.putBoolean(NEW_GAME, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Move the game piece
     * @param turn Constant.WHITE or Constant.BLACK according to whose turn it is
     */
    private void movePiece(int turn) {

        ImageView animChecker = null;
        //Get the position of the checker that will move and the area it will move to
        final int[] locationChecker = {0, 0};
        final int[] locationArea = {0, 0};
        //retrieves x, y coords from screen
        selectedPiece.getLocationOnScreen(locationChecker);
        //retrieves x, y coords for where the player wants to move
        areaToMoveTo.getLocationOnScreen(locationArea);
        Log.i(TAG, "move from x: " + locationChecker[0] + " y: " + locationChecker[1]);
        Log.i(TAG, "move to x: " + locationArea[0] + " y: " + locationArea[1]);

        ViewGroup parent = ((ViewGroup)selectedPiece.getParent());
        final int index = parent.indexOfChild(selectedPiece);

        //Creates the movement animation. This will essentially create a see through game piece
        //the actual game piece will move
        if(turn == Constants.WHITE) {
            whiteIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.animation_white, parent, false);
        } else {
            blackIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.animation_black, parent, false);
        }


        //Checks if pieces still remain to be selected from during initial game set up.
        // We want to remove pieces form the sidelines that have already been used
        if (parent != findViewById(R.id.board)) {
            //Remove the real piece, create the phantom piece
            parent.removeView(selectedPiece);
            parent.addView(animChecker, index);
            //Move the real one to the side board
            ((ViewGroup) findViewById(R.id.board)).addView(selectedPiece);

        } else {
            //Make position a phantom piece
            parent.addView(animChecker);
            animChecker.setLayoutParams(selectedPiece.getLayoutParams());
        }

        //Makes the phantom piece invisible, then moves the selected piece
        selectedPiece.setLayoutParams(areaToMoveTo.getLayoutParams());
        selectedPiece.setVisibility(View.INVISIBLE);

        //final copies to be used in the animation thread
        final ImageView tmpAnimChecker = animChecker;
        final ImageView tmpSelectedChecker = selectedPiece;

        //This will prepare the animation of the checker piece
        TranslateAnimation tAnimation = new TranslateAnimation(0, locationArea[0] - locationChecker[0], 0, locationArea[1] - locationChecker[1]);
        tAnimation.setFillEnabled(true);
        tAnimation.setFillAfter(true);
        tAnimation.setDuration(1500);

        tAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup parent = ((ViewGroup)tmpAnimChecker.getParent());
                //Fix the side board so its children stays in position
                if (tmpAnimChecker.getParent() != findViewById(R.id.board)) {
                    //Add a placeholder frame layout to stop the other checkers from jmping towards the middle.
                    FrameLayout placeholder = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_placeholder, parent, false);
                    parent.addView(placeholder, index);
                }

                //Remove the ghost and make the real checker visible again.
                parent.removeView(tmpAnimChecker);
                tmpSelectedChecker.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        tmpAnimChecker.startAnimation(tAnimation);
    }

    /**
     * Allows the user to select a piece to remove or move. Just piece selection in general
     * @param v The checker which was clicked on.
     */
    private void selectChecker(View v) {
        //Checks if the selected piece is used for removing
        if (removePiece) {
            //Checks if the piece to be removed is valid (black piece turn)
            if(rules.getTurn() == Constants.BLACK && rules.remove(posOfPiece.get(v), Constants.BLACK)) {

                //Removes highlighted section and moves the selected piece
                removeHighlights();
                black_pieces.remove(v);
                removePiece = false;
                ViewGroup parent = ((ViewGroup)v.getParent());
                parent.removeView(v);
                playerTurn.setText("Player 2, Go!");

                //Checks if anyone won
                isWin = rules.winCheck(Constants.BLACK);
                if (isWin) {
                    playerTurn.setText("Player 1, you win!");
                }
            }
            //Checks if the piece to be removed is valid (white piece turn)
            else if(rules.getTurn() == Constants.WHITE && rules.remove(posOfPiece.get(v), Constants.WHITE)) {
                //Unmark all options and remove the selected checker
                removeHighlights();
                white_pieces.remove(v);
                removePiece = false;
                ViewGroup parent = ((ViewGroup)v.getParent());
                parent.removeView(v);
                playerTurn.setText("Player 1, Go!");

                //Checks to see if anyone won
                isWin = rules.winCheck(Constants.WHITE);
                if (isWin) {
                    playerTurn.setText("Player 2, you win!");
                }
            }

        }

        //Checks if piece is only for board movement
        else if (!(posOfPiece.get(v) != 0 && posOfPiece.containsValue(0)) || (posOfPiece.get(v) == 0)) {
            //If the user has already selected a piece and they click again, unselect it
            if (selectedPiece != null) {
                selectedPiece.setAlpha(1.0f);
            }
            //If its the selected checker which is clicked, no checker is selected.
            if(selectedPiece == v) {
                selectPieceCheck = false;
                selectedPiece = null;
                removeHighlights();
                return;
            }
            //User selects the piece and valid moves will be highlighted
            highlightMoves(posOfPiece.get(v));
            selectPieceCheck = true;
            selectedPiece = (ImageView) v;
            selectedPiece.setAlpha(0.5f);
        }
    }

    /**
     * Mark all available moves that can be done.
     * @param from The position of the checker which wants to move
     */
    private void highlightMoves(int from) {
        //iterates through the 24 possible spots
        for(int i = 0; i < 24; i++) {
            //if the location is valid, highlighted it
            if(rules.validLocationCheck(from, i+1)) {
                hitBoxLoc.get(i).setBackgroundResource(R.drawable.valid_move);
            }
        }
    }

    /**
     * Removes the highlighted areas
     */
    private void removeHighlights() {
        for(FrameLayout f : hitBoxLoc) {
            f.setBackgroundResource(0);
        }
    }
}
