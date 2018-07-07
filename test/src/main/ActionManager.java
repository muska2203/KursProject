/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects.Unit;
import Objects.Vector;
import Objects.Weapon;

/**
 *
 * @author admin
 */
public class ActionManager {

    public static void checkForShoting(Game game, Unit ship) {
        for(Weapon weapon : ship.getWeapons()) {
            if (weapon.isShoting()) {
                game.addSpaceObject(weapon.takeShell(ship));
            }
        }
    }
}
