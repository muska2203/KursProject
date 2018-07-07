/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects.EffectObject;
import Objects.PassiveObject;
import Objects.GameObject;
import Objects.TypeOfEffectObject;
import Objects.Unit;
import Objects.TypeOfPassiveObject;
import Objects.TypeOfUnit;
import Objects.Vector;
import Pilots.TypeOfPilot;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author admin
 */
public class LevelGenerator {

    public static void generateLevel(Game game, int level) {
        ArrayList<GameObject> list = new ArrayList<>();
        switch (level) {
            case 1:
                for (int i = 0; i < 1; i++) {
                    list.add(new Unit(100, new Vector(new Random().nextDouble() * game.WIDTH, new Random().nextDouble() * game.HEIGHT), 0, 0, TypeOfUnit.DEFAULT, TypeOfPilot.PLAYER));
                }
                for (int i = 0; i < 3; i++) {
                    list.add(new Unit(20, new Vector(new Random().nextDouble() * game.WIDTH, new Random().nextDouble() * game.HEIGHT), 0, 0, TypeOfUnit.DEFAULT, TypeOfPilot.BOT));
                }
                for (int i = 0; i < 0; i++) {
                    list.add(new Unit(20, new Vector(new Random().nextDouble() * game.WIDTH, new Random().nextDouble() * game.HEIGHT), 0, 0, TypeOfUnit.TOWER, TypeOfPilot.BOT));
                }
                for (int i = 0; i < 0; i++) {
                    list.add(new PassiveObject(20, new Vector(Math.random() * (game.WIDTH - 40) + 20, Math.random() * (game.HEIGHT - 40) + 20), 10, 3, TypeOfPassiveObject.METEORITE));
                }
                game.addSpaceObject(list);
                break;
            case 2:
                list.add(new Unit(50, new Vector(game.WIDTH / 10, game.HEIGHT / 2), 0, 0, TypeOfUnit.TOWER, TypeOfPilot.PLAYER));

                for (int enemy = 0; enemy < 3; enemy++) {
                    double x = new Random().nextDouble() * game.WIDTH / 2 + game.WIDTH / 2;
                    double y = new Random().nextDouble() * game.HEIGHT;
                    list.add(new Unit(40, new Vector(x, y), -1, 0, TypeOfUnit.DEFAULT, TypeOfPilot.BOT));
                }
                game.addSpaceObject(list);
                break;
            case 3:
                list.add(new Unit(50, new Vector(game.WIDTH / 10, game.HEIGHT / 2), 0, 0, TypeOfUnit.DEFAULT, TypeOfPilot.PLAYER));
                game.addSpaceObject(list);
                break;
            case 4:
                list.add(new Unit(15, new Vector(40, 40), 0, 0, TypeOfUnit.DEFAULT, TypeOfPilot.PLAYER));
                for(int i = 0; i < 10; i++) {
                    list.add(new Unit(30, new Vector(300+90*i, 300+(Math.random()*200)-100), 0, 0, TypeOfUnit.TOWER, TypeOfPilot.BOT));
                }
                for (int i = 0; i < 10; i++) {
                    list.add(new EffectObject(20, new Vector(300 + 60 * i, 100), 0, 0, TypeOfEffectObject.REPAIR_KIT));
                }
                for (int i = 0; i < 25; i++) {
                    list.add(new PassiveObject(10, new Vector(200, 0 + 20 * i), 0, 0, TypeOfPassiveObject.WALL));
                }
                game.addSpaceObject(list);
                break;
        }

    }

}
