/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilots;

import Objects.GameObject;
import Objects.Unit;
import java.util.ArrayList;
import main.Game;

/**
 *
 * @author admin
 */
public abstract class Pilot {

    protected Unit unit;

    public Pilot(Unit unit) {
        this.unit = unit;
    }

    public abstract void move(Game game,ArrayList<GameObject> objects);
}
