package com.lewitanski.lisa.assignment2;

/**
 * <b>GameBoard</b>
 * <p>
 * This class manage the card.
 *
 * It manage the card's position, value and if it's shows.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class Card {

    /**
     * @see Card#pos_left
     * The card position at the left.
     *
     *
     * @see Card#pos_top
     *The card position at the top.
     *
     * The card position at the right.
     * @see Card#pos_right
     *
     * The card position at the bottom.
     * @see Card#pos_bottom
     *
     * Allow to manage click event UP and DOWN
     * @see Card#down
     *
     * The variable to know if the card is return.
     * @see Card#show
     *
     * The value of the card.
     * @see Card#value_card
     *
     * The variable to know if two card are already return.
     * @see Card#nb_turn
     *
     * The variable to know if the card is valid or if the player has already found it.
     * @see Card#isValid
     * */

    protected int pos_left;
    protected int pos_top;
    protected int pos_right;
    protected int pos_bottom;
    protected boolean down;
    protected boolean show;
    protected int value_card;
    protected int nb_turn;
    protected boolean isValid;

    /**
     * The constructor of this class initialize the card's position, the validity to true and
     * set the card to not return.
     *
     * @see Card#Card()
     * */
    Card() {
        pos_left = -1;
        pos_top = -1;
        pos_right = -1;
        pos_bottom = -1;
        value_card = -1;
        down = false;
        show = false;
        isValid = true;
    }

    /**
     * The constructor of the class initialize the card's position with the given position put in
     * parameter, the validity to true and set the card to not return.
     *
     * @param left : The left position to the card.
     * @param top : The top position to the card.
     * @param right : The right position to the card.
     * @param bottom : The bottom position to the card.
     * @see Card#Card(int, int, int, int)
     * */
    Card(int left, int top, int right, int bottom) {
        value_card = -1;
        pos_left = left;
        pos_top = top;
        pos_right = right;
        pos_bottom = bottom;
        down = false;
        show = false;
        isValid = true;
    }

    /**
     *
     * This method allows to set the card position with the given position put in parameter to
     * this method.
     *
     * @param left : The left position to the card.
     * @param top : The top position to the card.
     * @param right : The right position to the card.
     * @param bottom : The bottom position to the card.
     * @see Card#setPositions(int, int, int, int)
     */
    public void setPositions(int left, int top, int right, int bottom) {
        pos_left = left;
        pos_top = top;
        pos_right = right;
        pos_bottom = bottom;
    }

    /**
     * Given the position of the click, determine wether the user clicked on the card or not.
     * @param pos_x : The x position of the click.
     * @param pos_y : The y position of the click.
     * @return True if card hit, else false.
     */
    public boolean isCardTriggered(int pos_x, int pos_y) {
        if (pos_x >= pos_left && pos_x <= pos_right &&
                pos_y >= pos_top && pos_y <= pos_bottom)
            return true;
        return false;
    }

    /**
     * This method allows to set the down status.
     * Down will be true if the user clicks on the card else it will be false.
     * @param status
     * @see Card#setDown(boolean)
     */
    public void setDown(boolean status) {
        down = status;
    }

    /**
     * This method allows to get the down status.
     * @see Card#isDown()
     * @return down : It will be true if the user clicks on the card else it will be false.
     */
    public boolean isDown() {
        return down;
    }

    /**
     * This method allows to set the status of the card.
     * If the card is return it will be true, else it will be false.
     * @param status : The status card
     */
    public void showCard(boolean status) {
        show = status;
    }

    /**
     * This method allows to return the status of the card.
     *
     * @see Card#isCardShow()
     * @return If the card is show it return true, else it return false.
     */
    public boolean isCardShow() {
        return show;
    }

    /**
     * This method allows to set the value of the card.
     *
     * @see Card#setValueCard(int)
     * @param value: The card value. (ex: 1)
     */
    public void setValueCard(int value) {
        value_card = value;
    }

    /**
     * This method allows to get the value of the card.
     *
     * @see Card#getValueCard()
     * @return : The card value. (ex: 1)
     */
    public int getValueCard() {
        return value_card;
    }

    /**
     * This method allows to know if 2 card are already showed.
     * If 2 cards are show, the counter nb_turn will be set to 0.
     * @see Card#getNbTurn()
     * @return : If less 2 card it return true, else it return false.
     */
    public boolean getNbTurn() {
        if (nb_turn <= 1)
            return true;
        if (nb_turn == 2)
            nb_turn = 0;
        return false;
    }

    /**
     * This method allows to set the nb_turn + 1 if the player show a card.
     *
     * @see Card#setNbTurn()
     */
    public void setNbTurn() {
        nb_turn += 1;
    }

    /**
     * This method allows to set the validity of the card.
     * It's use to know if the card has be found.
     *
     * @param status : The validity of the card.
     * @see Card#setIsValid(boolean)
     *
     */
    public void setIsValid(boolean status) {
        isValid = status;
    }

    /**
     * This method allows to get the validity of the card.
     * It's use to know if the card has be found.
     *
     * @see Card#getIsValid()
     * @return : The validity of the card.
     *
     */
    public boolean getIsValid() {
        return isValid;
    }
}
