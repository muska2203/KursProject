/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects.EffectObject;
import Objects.PassiveObject;
import Objects.Shell;
import Objects.GameObject;
import Objects.Type;
import Objects.TypeOfPassiveObject;
import Objects.TypeOfUnit;
import Objects.Unit;
import Objects.Vector;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class CollisionManager {

    public static void checkForCollision(ArrayList<GameObject> objects) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).setCollision(false);
            for (int j = i; j < objects.size(); j++) {
                if (i != j) {
                    double length = Math.pow(Math.pow(objects.get(i).getX() - objects.get(j).getX(), 2) + Math.pow(objects.get(i).getY() - objects.get(j).getY(), 2), 0.5);
                    if (objects.get(i).getSize() + objects.get(j).getSize() > length) {
                        createAction(objects.get(i), objects.get(j));
                        objects.get(i).setCollision(true);
                        objects.get(j).setCollision(true);
                    }
                }
            }
        }
    }

    public static void createAction(GameObject obj1, GameObject obj2) {
        if (obj1.getType() == Type.UNIT && obj2.getType() == Type.PASSIVE_OBJECT) {
            Action((Unit) obj1, (PassiveObject) obj2);
        } else if (obj1.getType() == Type.PASSIVE_OBJECT && obj2.getType() == Type.UNIT) {
            Action((Unit) obj2, (PassiveObject) obj1);
        } else if (obj1.getType() == Type.SHELL && obj2.getType() != Type.EFFECT_OBJECT) {
            Action(obj2, (Shell) obj1);
        } else if (obj2.getType() == Type.SHELL && obj1.getType() != Type.EFFECT_OBJECT) {
            Action(obj1, (Shell) obj2);
        } else if (obj1.getType() == Type.UNIT && obj2.getType() == Type.UNIT) {
            Action((Unit) obj1, (Unit) obj2);
        } else if (obj1.getType() == Type.EFFECT_OBJECT && obj2.getType() == Type.UNIT) {
            Action((EffectObject) obj1, (Unit) obj2);
        } else if (obj2.getType() == Type.EFFECT_OBJECT && obj1.getType() == Type.UNIT) {
            Action((EffectObject) obj2, (Unit) obj1);
        }
    }

    public static void Action(Unit player, PassiveObject object) {
        switch(object.getTypeOfPassiveObject()) {
            case WALL:
                Vector vector = new Vector(player.getX() - object.getX(), player.getY() - object.getY());
                player.stop();
                object.stop();
                Vector vector1 = new Vector();
                vector1.setByAlpha(1, vector.getAlpha());
                if (player.getTypeOfShip() != TypeOfUnit.TOWER) {
                    player.setExternalForce(vector1);
                }
                break;
        }
        if (object.getTypeOfPassiveObject() == TypeOfPassiveObject.METEORITE) {
            object.remove();
            player.takeHealth(10);
            if (player.getHealth() <= 0) {
                player.remove();
            }
        }
    }

    public static void Action(GameObject object, Shell shell) {
        switch (object.getType()) {
            case SHELL:
                if (((Shell) object).getShooter() != shell.getShooter()) {
                    shell.hit();
                    ((Shell) object).hit();
                }
                break;
            case UNIT:
                if (((Unit) object).getTypeOfPilot() != shell.getShooter()) {
                    shell.remove();
                    ((Unit)object).takeHealth(shell.getDamage());
                    if (object.getHealth() <= 0) {
                        object.remove();
                    }
                }
                break;
            case PASSIVE_OBJECT:
                switch(((PassiveObject)object).getTypeOfPassiveObject()) {
                    case WALL:
                        shell.remove();
                        break;
                }
                break;
            default:
                shell.hit();
                object.takeHealth(shell.getDamage());
                if (object.getHealth() <= 0) {
                    object.remove();
                }
                break;
        }
    }

    public static void Action(Unit obj1, Unit obj2) {
        Vector vector = new Vector(obj1.getX() - obj2.getX(), obj1.getY() - obj2.getY());
        obj1.stop();
        obj2.stop();
        Vector vector1 = new Vector();
        vector1.setByAlpha(1, vector.getAlpha());
        Vector vector2 = new Vector();
        vector2.setByAlpha(1, vector.getAlpha() + Math.toRadians(180));
        if (obj1.getTypeOfShip() != TypeOfUnit.TOWER) {
            obj1.setExternalForce(vector1);
        }
        if (obj2.getTypeOfShip() != TypeOfUnit.TOWER) {
            obj2.setExternalForce(vector2);
        }
    }

    public static void Action(EffectObject obj1, Unit obj2) {
        switch (obj1.getTypeOfEffectObject()) {
            case REPAIR_KIT:
                if(obj2.getHealth()<obj2.getMaxHealth()) {
                    obj2.addHealth(20);
                    obj1.remove();
                }
                break;
        }
    }

    public static double getAlpha(GameObject obj1, GameObject obj2) {
        double alpha;
        if (obj1.getX() - obj2.getX() <= 0 && obj1.getY() - obj2.getY() <= 0) {
            alpha = -Math.atan((Math.abs(obj1.getX() - obj2.getX())) / (Math.abs(obj1.getY() - obj2.getY())));
        } else if (obj1.getX() - obj2.getX() < 0 && obj1.getY() - obj2.getY() > 0) {
            alpha = Math.toRadians(180) + Math.atan((Math.abs(obj1.getX() - obj2.getX())) / (Math.abs(obj1.getY() - obj2.getY())));
        } else if (obj1.getX() - obj2.getX() > 0 && obj1.getY() - obj2.getY() < 0) {
            alpha = Math.atan((Math.abs(obj1.getX() - obj2.getX())) / (Math.abs(obj1.getY() - obj2.getY())));
        } else {
            alpha = Math.toRadians(90) + Math.atan((Math.abs(obj1.getY() - obj2.getY())) / Math.abs(obj1.getX() - obj2.getX()));
        }
        return alpha;
    }
}
