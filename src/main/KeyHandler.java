package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean dropPressed, drinkPressed, escPressed, upPressed, downPressed, leftPressed, rightPressed, attackPressed, speakPressed, nextDialoguePressed, pickPressed;
    public boolean navigateRightPressed, navigateLeftPressed;
    public boolean meniuPressed;
    public boolean meniuHandled;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_P) {
            pickPressed = true;

        }


        if (code == KeyEvent.VK_M) {
            meniuPressed = true;

        }


        if (code == KeyEvent.VK_O) {
            dropPressed = true;

        }


        if (code == KeyEvent.VK_I) {
            drinkPressed = true;

        }


        if (code == KeyEvent.VK_K) {
            navigateLeftPressed = true;

        }
        if (code == KeyEvent.VK_L) {
            navigateRightPressed = true;

        }


        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = true;
        }


        if (code == KeyEvent.VK_SPACE) {
            nextDialoguePressed = true;
        }


        if (code == KeyEvent.VK_ENTER) {
            attackPressed = true;
        }


        if (code == KeyEvent.VK_R) {
            speakPressed = true;

        }


        if (code == KeyEvent.VK_W) {

            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {

            downPressed = true;
        }


        if (code == KeyEvent.VK_A) {

            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {

            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }


        if (code == KeyEvent.VK_P) {
            pickPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            attackPressed = false;
        }
        if (code == KeyEvent.VK_R) {
            speakPressed = false;
        }


        if (code == KeyEvent.VK_M) {
            meniuPressed = false;
            meniuHandled = false;
        }


        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }
    }
}


