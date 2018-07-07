/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilots;

import Objects.GameObject;
import Objects.Unit;
import Objects.Vector;
import java.util.ArrayList;
import main.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 * @author admin
 */
public class Player extends Pilot {

    private final int keyLeft = Keyboard.KEY_A;
    private final int keyRight = Keyboard.KEY_D;
    private final int keyUp = Keyboard.KEY_W;
    private final int keyDown = Keyboard.KEY_S;
    private final int keyShield = Keyboard.KEY_SPACE;
    private final int keyShot = 0;

    public Player(Unit unit) {
        super(unit);
    }

    @Override
    public void move(Game game,ArrayList<GameObject> objects) {
        if (Keyboard.isKeyDown(keyLeft)) {
            if(this.unit.getSpeed()<0) {
                unit.rotate(unit.getAlpha() + Math.toRadians(21.3));
            } else {
                unit.rotate(unit.getAlpha() - Math.toRadians(21.3));
            }
        }
        if (Keyboard.isKeyDown(keyRight)) {
            if(this.unit.getSpeed()<0) {
                unit.rotate(unit.getAlpha() - Math.toRadians(21.3));
            } else {
                unit.rotate(unit.getAlpha() + Math.toRadians(21.3));
            }
        }
        if (!Keyboard.isKeyDown(keyLeft)&&!Keyboard.isKeyDown(keyRight)) {
            unit.rotate(unit.getAlpha());
        }
        if (Keyboard.isKeyDown(keyUp)) {
            unit.setAcceleration(1);
        }
        if (Keyboard.isKeyDown(keyDown)) {
            unit.setAcceleration(-1);
        }
        if (Keyboard.isKeyDown(keyShield)) {
            unit.createShield();
        }
        if (!Keyboard.isKeyDown(keyDown)&&!Keyboard.isKeyDown(keyUp)) {
            unit.setAcceleration(0);
        }
        Vector vector = new Vector(Mouse.getX()-unit.getX(), game.HEIGHT-Mouse.getY()-unit.getY());
        unit.setAlphaWeapon(vector.getAlpha());
        if (Mouse.isButtonDown(keyShot)) {
            unit.shot();
        }
    }

}
