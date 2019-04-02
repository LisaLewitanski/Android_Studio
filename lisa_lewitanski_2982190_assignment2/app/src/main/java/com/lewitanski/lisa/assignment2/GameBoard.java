package com.lewitanski.lisa.assignment2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

/**
 * <b>GameBoard</b>
 * <p>
 * This class manage the game.
 *
 * It displays the game and manages all events.
 * Indeed, it displays all card, swipe the selected card, if two same cards are shown,
 * it removes the card and modify the player's score.
 *
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */

class GameBoard extends View {
    /**
     * The Drawable background is the background's image.
     * @see GameBoard#backGround
     *
     * The Drawable basicCard is the card's image when it isn't return.
     * @see GameBoard#basicCard
     *
     * The int size is the maximum size of the mobile window.
     * @see GameBoard#size
     *
     * The int INTER_SIZE is the size beetween each cards.
     * @see GameBoard#INTER_SIZE
     *
     * The Rect imagebounds is the board game.
     * @see GameBoard#imageBounds
     *
     * The arrayList of Card cards is the list of each cards in the board. It contains the card's position,
     * if it is show, validity and the value.
     * @see GameBoard#cards
     *
     * The arrayList of integer scores is the list of score of each players.
     * @see GameBoard#scores
     *
     * The arrayList of Drawable list_card_value is the list of each drawable cards. It contains 2 cards
     * of 8 chocoboo.
     * @see GameBoard#list_card_value
     *
     * The int numberCardShowed is used to know how many cards are returned in the game.
     * @see GameBoard#numberCardShowed
     *
     * The boolean player_one is used to know if it is the turn of this player.
     * @see GameBoard#player_one
     *
     * The boolean player_two is used to know if it is the turn of this player.
     * @see GameBoard#player_two
     *
     * The int score_player_one is used to set the score of the player one.
     * @see GameBoard#score_player_one
     *
     * The int score_player_two is used to set the score of the player two.
     * @see GameBoard#score_player_two
     *
     * The boolean en_game is to known if the game is finished
     * @see GameBoard#end_game
     *
     * @see GameBoard#freeze
     *
     * @see GameBoard#nbCardReturn
     *
     */
    private Drawable backGround;
    private Drawable basicCard;
    private int size = 0;
    private static final int INTER_SIZE = 2;
    private Rect imageBounds;
    private ArrayList<Card> cards = null;
    private ArrayList<Integer> scores = null;
    private ArrayList<Drawable> list_card_value;
    private int numberCardShowed = 0;
    private boolean player_one;
    private boolean player_two;
    private int score_player_one;
    private int score_player_two;
    private boolean freeze = false;
    public boolean end_game = false;
    private int nbCardReturn = 0;
    /**
     * The overriden method allows to create an instance to the canvas.
     * @see GameBoard#dispatchDraw(Canvas)
     * @param canvas : The canvas

     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * The constructor to the class that takes in references to a context.
     *
     * The constructor will initialize the background, the basic card, the list of all cards values
     * the player's turn and score.
     * At the end, it will create a new game calling the newGame method.
     *
     * @param context  the context
     * @see GameBoard#GameBoard(Context)
     * */
    public GameBoard(Context context) {
        this(context, null);
        backGround = context.getResources().getDrawable(R.drawable.background);
        basicCard = context.getResources().getDrawable(R.drawable.basic_card);
        list_card_value = new ArrayList<Drawable>(8);
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo1));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo2));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo3));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo4));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo5));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo6));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo7));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo8));
        setWillNotDraw(false);
        newGame();
    }

    /**
     * The constructor to the class that takes in references to a context.
     *
     * The constructor will initialize the background, the basic card, the list of all cards values
     * the player's turn and score.
     * At the end, it will create a new game calling the newGame method.
     *
     * @param context  the context
     * @see GameBoard#GameBoard(Context, AttributeSet)
     * */
    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        backGround = context.getResources().getDrawable(R.drawable.background);
        basicCard = context.getResources().getDrawable(R.drawable.basic_card);
        list_card_value = new ArrayList<Drawable>(8);
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo1));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo2));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo3));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo4));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo5));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo6));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo7));
        list_card_value.add(context.getResources().getDrawable(R.drawable.chocobo8));
        setWillNotDraw(false);
        newGame();
    }

    /**
     * Overridden method that will draw the background with the 16 cards on it.
     *
     * @see GameBoard#onDraw(Canvas)
     * @param canvas : The canvas
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {
        imageBounds = canvas.getClipBounds();
        backGround.setBounds(imageBounds);
        backGround.draw(canvas);
        draw_cards(canvas);
    }

    /**
     * This method will creates an array of size 16. A cell corresponds to a card.
     * It will initialize each cells with a random value between 0 to 8. Each values is set two times
     * in the array.
     *
     *
     * @see GameBoard#create_game_tab()
     * @return tab_game : The array containing each value's card.
     *
     */
    protected int[] create_game_tab(){
        int[] tab_game = new int[16];
        Random rand = new Random();
        int tmp_card;
        int maximum;

        // Array's initialization
        for (int count = 0; count < 16; count += 1) {
            tab_game[count] = 0;
        }
        // Fill each array's cell with a random value
        for (int count = 0; count < 16; count += 1) {
            tmp_card = rand.nextInt(8) + 1;
            maximum = 0;
            for (int size = 0; size < tab_game.length; size += 1) {
                if (tmp_card == tab_game[size]) {
                    maximum += 1;
                    if (maximum == 2) {
                        tmp_card = -1;
                        break;
                    }
                }
            }
            // Verify if the value is not already set 2 times
            if (tmp_card != -1)
                tab_game[count] = tmp_card;
            else
                count -= 1;
        }
        return (tab_game);
    }

    /**
     * This method  will creates a new game.
     * Indeed, it will initialize an arrayList card containing 16 cards.
     * Then, it will creates an array  tab_game containing each cards values with the
     * create_game_tab method.
     * To finish, It will set each values card in the cards class with the tab_game values.
     *
     * @see GameBoard#newGame()
     *
     */
    public void newGame() {
        player_one = true;
        player_two = false;
        score_player_one = 0;
        score_player_two = 0;
        nbCardReturn = 0;

        end_game = false;
        cards = new ArrayList<Card>(16);

        for (int idx = 0; idx < 16; idx += 1) {
                cards.add(new Card());
        }
        int tab_game[] = create_game_tab();
        for (int count = 0; count < 16; count += 1)
            cards.get(count).setValueCard(tab_game[count]);
    }

    /**
     * This method that will creates a new game.
     * Indeed, it will initialize an arrayList card containing 16 cards.
     * Then, it will create an array  tab_game containing each cards values with the
     * create_game_tab method.
     * To finish, It will set each values card in the cards class with the tab_game values.
     *
     * @see GameBoard#draw_cards(Canvas)
     *
     */
    protected void draw_cards(Canvas canvas){
        if (size > 0) {
            // Initilization of each dimension
            int img_height = basicCard.getIntrinsicHeight();
            int img_width = basicCard.getIntrinsicWidth();
            int height = (size / 5) - (size * INTER_SIZE / 100);
            int width = (img_width * height) / img_height;
            int count = 0;
            Drawable tmpCard;

            // Loop to draw the card in the y axis
            for (int idxHeight = (size / 5); idxHeight <= (size / 5) * 4; idxHeight += (size / 5)) {
                // Loop to draw the card in the x axis
                for (int idxWidth = (size / 5); idxWidth <= (size / 5) * 4; idxWidth += (size / 5)) {
                    // Verify if the card is valid
                    if (cards.get(count).getIsValid()) {
                        // Set the card's position
                        cards.get(count).setPositions(idxWidth - (width / 2), idxHeight - (height / 2),
                                idxWidth + (width / 2), idxHeight + (height / 2));

                        if (!cards.get(count).isCardShow()) {
                            // If the card isn't show, draws the basic card at the given position
                            basicCard.setBounds(idxWidth - (width / 2), idxHeight - (height / 2),
                                    idxWidth + (width / 2), idxHeight + (height / 2));
                            basicCard.draw(canvas);
                        } else {
                            // If the card is show, draws the chocoboo card corresponding at the value card at the given position
                            tmpCard = list_card_value.get(cards.get(count).getValueCard() - 1);
                            tmpCard.setBounds(idxWidth - (width / 2), idxHeight - (height / 2),
                                    idxWidth + (width / 2), idxHeight + (height / 2));
                            tmpCard.draw(canvas);
                        }
                    }
                    count += 1;
                }
            }
        }
    }

    /**
     * This method that will add the new score of each players in a scores ArrayList.
     *
     * @see GameBoard#getScore()
     * @return scores: Scores is an arrayList containing the score of each players.
     *
     */
    public ArrayList<Integer> getScore() {
        scores = new ArrayList<Integer>(2);
        scores.add(score_player_one);
        scores.add(score_player_two);
        return scores;
    }

    /**
     * This method allows to known the turn.
     * If it's the turn of the player one, the method will return 1
     * If it's the turn of the player two, the method will return 2.
     *
     * @return turn: The player's turn (1 or 2)
     */
    public int getTurn() {
        int turn;
        if (player_one)
            turn = 1;
        else
            turn = 2;
        return turn;
    }

    /**
     * This method will update the text on the content main activity with the new value.
     * (The player's score and turn)
     *
     * @see GameBoard#updateDisplay()
     *
     */
    public void updateDisplay() {
        MainActivity md = (MainActivity) this.getContext();
        md.onDisplayChanged();
    }

    /**
     * This method shows if the two card's return are the same.
     * If they are the same, it updates the player's score and turn and it removes the selected
     * cards.
     * @see GameBoard#lookForPair()
     */
    private void lookForPair() {
        final Card[] save_card_showed = new Card[2];
        int count = 0;
        // save the card which shows
        for (Card card : cards) {
            if (count < 2 && card.isCardShow()) {
                save_card_showed[count] = card;
                count += 1;
            }
        }
        // If the player return 2 same cards
        if (count == 2 && save_card_showed[0].getValueCard() == save_card_showed[1].getValueCard()) {
            // Update the player's score
            if (player_one) {
                score_player_one += 1;
            }
            else {
                score_player_two += 1;
            }
            // Update the player's turn
            player_one = !player_one;
            player_two = !player_two;
            nbCardReturn += 2;
            invalidate();
            freeze = true;
            // If two cards are the same, we wait 1 second to help the player to shows the card
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    save_card_showed[0].setIsValid(false);
                    save_card_showed[1].setIsValid(false);
                    numberCardShowed = 0;
                    hideCard();
                    invalidate();
                    freeze = false;
                }
            }, 1000);
        }
        // To know if it's the end of the game
        if (nbCardReturn == 16)
            end_game = true;
    }

    /**
     * This method allows to set the card status to false.
     * @see GameBoard#hideCard()
     */
    private void hideCard() {
        for (Card card : cards) {
            card.showCard(false);
        }
    }

    /**
     *
     * This method allows to trigger the click event.
     * It also allows to manage the the cards display and the players turn.
     *
     * @see GameBoard#onTouchEvent(MotionEvent)
     * @param event: The event
     * @return: True
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (freeze)
            return true;
        int eventAction = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();

        // Check if two cards are showed
        if (numberCardShowed == 2) {
            numberCardShowed = 0;
            // call the hideCard method to not show all card in the board
            hideCard();
            updateDisplay();
            invalidate();
            return true;
        }
        basicCard.getBounds();
        for (Card card : cards) {
            if (card.isCardTriggered(x, y)) {
                // If the player press on a card
                if (eventAction == MotionEvent.ACTION_DOWN)
                    card.setDown(true);
                // If the user release a card after an ACTION_DOWN event
                if (eventAction == MotionEvent.ACTION_UP && card.isDown() && !card.isCardShow() &&
                        numberCardShowed < 2) {
                    numberCardShowed += 1;
                    card.setDown(false);
                    card.showCard(true);
                    invalidate();
                    // Check if the two return cards are the same
                    if (numberCardShowed == 2) {
                        lookForPair();
                        // set the turn of each players
                        player_one = !player_one;
                        player_two = !player_two;
                    }
                }
                break;
            }
        }
        updateDisplay();
        return true;
    }

    /**
     * The overriden onMeasure method allows to adapt display when screen size changes.
     * @param widthMeasureSpec : Width size
     * @param heightMeasureSpec : Height size
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            size = height;
        } else {
            size = width;
        }
        setMeasuredDimension(size, size);
    }
}
