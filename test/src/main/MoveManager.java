/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects.PassiveObject;
import Objects.Unit;
import Objects.Shell;
import Objects.GameObject;
import Objects.Weapon;

/**
 *
 * @author admin
 */
public class MoveManager {

    public static void move(GameObject object, double delta, Game game) {
        if (delta < 100) {
            object.getPosition().addVector(object.getSpeed(delta));
            object.addAlpha(object.getRotate() * delta);
            object.getPosition().addVector(object.getExternalForce());
        }
        switch (object.getType()) {
            case PASSIVE_OBJECT:
                switch (((PassiveObject) object).getTypeOfPassiveObject()) {
                    case METEORITE:
                        if (object.getX() >= game.WIDTH - object.getSize() || object.getX() <= 0 + object.getSize()) {
                            object.rotate(Math.toRadians(180) - object.getAlpha());
                        }
                        if (object.getY() > game.HEIGHT - object.getSize() || object.getY() < 0 + object.getSize()) {
                            object.rotate(-object.getAlpha());
                        }
                        break;
                }
                break;
            case SHELL:
                ((Shell) object).addLength(object.getSpeedVector().getLength());
                switch (((Shell) object).getTypeOfShell()) {
                    default:
                        if (object.getX() >= game.WIDTH || object.getY() >= game.HEIGHT
                                || object.getX() <= 0 || object.getY() <= 0) {
                            object.remove();
                        }
                        break;
                }
                break;
            case UNIT:
                ((Unit) object).move(game, game.getSpaceObjects());
                if (object.getX() > game.WIDTH - object.getSize()) {
                    object.getPosition().setX(game.WIDTH - object.getSize());
                    ((Unit) object).stop();
                }
                if (object.getX() < object.getSize()) {
                    object.getPosition().setX(object.getSize());
                    ((Unit) object).stop();
                }
                if (object.getY() > game.HEIGHT - object.getSize()) {
                    object.getPosition().setY(game.HEIGHT - object.getSize());
                    ((Unit) object).stop();
                }
                if (object.getY() < object.getSize()) {
                    object.getPosition().setY(object.getSize());
                    ((Unit) object).stop();
                }
                for (Weapon weapon : ((Unit) object).getWeapons()) {
                    weapon.addAlpha(weapon.getRotate() * delta);
                }
                break;
        }
    }

}
