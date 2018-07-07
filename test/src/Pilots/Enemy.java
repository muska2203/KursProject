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

/**
 *
 * @author admin
 */
public class Enemy extends Pilot {
    
    boolean enemyWasFounded = false;
    boolean ally;
    
    public Enemy(Unit spaceShip, boolean ally) {
        super(spaceShip);
        this.ally = ally;
    }
    
    @Override
    public void move(Game game, ArrayList<GameObject> objects) {
        GameObject nearest = null;
        switch (unit.getTypeOfShip()) {
            case DEFAULT:
                unit.setAcceleration(0);
                for (GameObject object : objects) {
                    if (!(unit.getX() == object.getX() && unit.getY() == object.getY())) {
                        switch (object.getType()) {
                            case UNIT:
                                if (((Unit) object).getTypeOfPilot() == TypeOfPilot.PLAYER) {
                                    this.enemyWasFounded = true;
                                    //if (Math.sqrt(Math.pow(spaceShip.getX() - object.getX(), 2) + Math.pow(spaceShip.getY() - object.getY(), 2)) < 1000) {
                                    if (nearest == null) {
                                        nearest = object;
                                    } else if (object.getDistance(this.unit) < nearest.getDistance(this.unit)) {
                                        nearest = object;
                                    }
                                //}
                                    
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (nearest != null) {
                    double length = nearest.getPosition().getLength(this.unit.getPosition());
                    if (length < 1000
                            && length > 100) {
                        Vector vector = new Vector(nearest.getX() - unit.getX(), nearest.getY() - unit.getY());
                        unit.rotate(vector.getAlpha());
                    } else if (length < 50) {
                        Vector vector = new Vector(nearest.getX() - unit.getX(), nearest.getY() - unit.getY());
                    } else {
                        unit.setSpeed(0);
                    }
                    //if (Math.sqrt(Math.pow(spaceShip.getX() - nearest.getX(), 2) + Math.pow(spaceShip.getY() - nearest.getY(), 2)) < 300) {
                    Vector vector = new Vector(nearest.getX() - unit.getX(), nearest.getY() - unit.getY());
                    unit.aimObject(nearest);
                    unit.shot();
                //}
                    
                } else {
                    this.enemyWasFounded = false;
                }
                if (!this.enemyWasFounded) {
                    unit.setSpeed(0);
                }
                break;
            case TOWER:
                for (GameObject object : objects) {
                    if (!(unit.getX() == object.getX() && unit.getY() == object.getY())) {
                        switch (object.getType()) {
                            case UNIT:
                                if (((Unit) object).getTypeOfPilot() == TypeOfPilot.PLAYER) {
                                    if (Math.sqrt(Math.pow(unit.getX() - object.getX(), 2) + Math.pow(unit.getY() - object.getY(), 2)) < 350) {
                                        if (nearest == null) {
                                            nearest = object;
                                        } else if (object.getDistance(this.unit) < nearest.getDistance(this.unit)) {
                                            nearest = object;
                                        }
                                    } else {
                                        this.unit.setAlphaWeapon(0);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (nearest != null) {
                    this.unit.aimObject(nearest);
                    this.unit.shot();
                }
                break;
        }
        
    }
    
}
