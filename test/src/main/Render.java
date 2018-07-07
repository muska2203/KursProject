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
import Objects.TypeOfPassiveObject;
import Objects.TypeOfShell;
import Objects.Unit;
import Objects.Type;
import Objects.TypeOfEffectObject;
import Objects.TypeOfUnit;
import Objects.TypeOfWeapon;
import Objects.Weapon;
import Pilots.TypeOfPilot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author admin
 */
class Render {

    HashMap<TypeOfPassiveObject, Texture> passiveObjectMap;
    HashMap<TypeOfUnit, Texture> unitMap;
    HashMap<TypeOfWeapon, Texture> weaponMap;
    HashMap<TypeOfShell, Texture> shellMap;
    HashMap<TypeOfEffectObject, Texture> effectObjectMap;
    Texture grass;
    Texture maxHealth;
    Texture health;
    Texture shield;
    Texture armor;
    Unit player = null;

    public Render() {
        passiveObjectMap = new HashMap<>();
        shellMap = new HashMap<>();
        unitMap = new HashMap<>();
        weaponMap = new HashMap<>();
        effectObjectMap = new HashMap<>();
        try {
            passiveObjectMap.put(TypeOfPassiveObject.METEORITE, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/PassiveObject/" + TypeOfPassiveObject.METEORITE + ".png"))));
            passiveObjectMap.put(TypeOfPassiveObject.WALL, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/PassiveObject/" + TypeOfPassiveObject.WALL + ".png"))));
            shellMap.put(TypeOfShell.SLOW_BALL, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Shell/" + TypeOfShell.BALL + ".png"))));
            shellMap.put(TypeOfShell.BALL, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Shell/" + TypeOfShell.BALL + ".png"))));
            shellMap.put(TypeOfShell.HEAVY_SHELL, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Shell/" + TypeOfShell.HEAVY_SHELL + ".png"))));
            shellMap.put(TypeOfShell.CIRCLE, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Shell/" + TypeOfShell.BALL + ".png"))));
            unitMap.put(TypeOfUnit.DEFAULT, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Unit/" + TypeOfUnit.DEFAULT + ".png"))));
            unitMap.put(TypeOfUnit.TOWER, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Unit/" + TypeOfUnit.TOWER + ".png"))));
            shield = TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/shield.png")));
            grass = TextureLoader.getTexture("JPG", new FileInputStream(new File("resources/images/GRASS.jpg")));
            maxHealth = TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/maxHealth.png")));
            health = TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/health.png")));
            armor = TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/armor.png")));
            effectObjectMap.put(TypeOfEffectObject.REPAIR_KIT, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/EffectObject/" + TypeOfEffectObject.REPAIR_KIT + ".png"))));
        } catch (IOException ex) {
            System.out.println("Ошибка текстур ");
        }
        try {
            weaponMap.put(TypeOfWeapon.SHOOTER_BALL, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Weapon/" + TypeOfWeapon.SHOOTER_BALL + ".png"))));
            weaponMap.put(TypeOfWeapon.SPEED_BALL, TextureLoader.getTexture("PNG", new FileInputStream(new File("resources/images/Weapon/" + TypeOfWeapon.SHOOTER_BALL + ".png"))));
        } catch (IOException ex) {
            System.out.println("Ошибка - текстур оружия");
        }
    }

    public void printAll(ArrayList<GameObject> objects, Game game) {
        grass.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2d(0, 0);
        glTexCoord2f(1, 0);
        glVertex2d(game.WIDTH, 0);
        glTexCoord2f(1, 1);
        glVertex2d(game.WIDTH, game.HEIGHT);
        glTexCoord2f(0, 1);
        glVertex2d(0, game.HEIGHT);
        glEnd();
        for (GameObject object : objects) {
            switch (object.getType()) {
                case PASSIVE_OBJECT:
                    init((PassiveObject) object);
                    break;
                case SHELL:
                    init((Shell) object);
                    break;
                case UNIT:
                    init((Unit) object);
                    break;
                case WEAPON:
                    init((Weapon) object);
                    break;
                case EFFECT_OBJECT:
                    init((EffectObject) object);
                    break;
                default:
                    break;
            }
            glEnable(GL_ALPHA_TEST);
            glAlphaFunc(GL_GREATER, 0.9f);
            glPushMatrix();
            glTranslated(object.getX(), object.getY(), 0);
            glRotated(Math.toDegrees(object.getAlpha()), 0, 0, 1);
            glTranslated(-object.getX(), -object.getY(), 0);
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize());
            glTexCoord2f(1, 0);
            glVertex2d(object.getX() + object.getSize(), object.getY() - object.getSize());
            glTexCoord2f(1, 1);
            glVertex2d(object.getX() + object.getSize(), object.getY() + object.getSize());
            glTexCoord2f(0, 1);
            glVertex2d(object.getX() - object.getSize(), object.getY() + object.getSize());
            glEnd();
            glPopMatrix();
            glDisable(GL_ALPHA_TEST);

        }
        for (GameObject object : objects) {
            if (object.getType() == Type.UNIT) {
                if (((Unit) object).getTypeOfPilot() == TypeOfPilot.PLAYER) {
                    this.player = (Unit) object;
                }
                this.print(((Unit) object).getWeapons());
            }
        }
        for (GameObject object : objects) {
            if (object.getType() == Type.UNIT) {
                this.maxHealth.bind();
                glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize());
                glTexCoord2f(1, 0);
                glVertex2d(object.getX() + object.getSize(), object.getY() - object.getSize());
                glTexCoord2f(1, 1);
                glVertex2d(object.getX() + object.getSize(), object.getY() - object.getSize() / 1.3);
                glTexCoord2f(0, 1);
                glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize() / 1.3);
                glEnd();
                this.health.bind();
                glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize());
                glTexCoord2f(1, 0);
                glVertex2d(object.getX() - object.getSize() + object.getSize() * 2 * object.getHealth() / object.getMaxHealth(), object.getY() - object.getSize());
                glTexCoord2f(1, 1);
                glVertex2d(object.getX() - object.getSize() + object.getSize() * 2 * object.getHealth() / object.getMaxHealth(), object.getY() - object.getSize() / 1.3);
                glTexCoord2f(0, 1);
                glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize() / 1.3);
                glEnd();
                this.armor.bind();
                glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize());
                glTexCoord2f(1, 0);
                glVertex2d(object.getX() - object.getSize() + object.getSize() * 2 * ((Unit)object).getArmor() / ((Unit)object).getMaxHealth(), object.getY() - object.getSize());
                glTexCoord2f(1, 1);
                glVertex2d(object.getX() - object.getSize() + object.getSize() * 2 * ((Unit)object).getArmor() / ((Unit)object).getMaxHealth(), object.getY() - object.getSize() / 1.3);
                glTexCoord2f(0, 1);
                glVertex2d(object.getX() - object.getSize(), object.getY() - object.getSize() / 1.3);
                glEnd();
            }
        }
        if (player != null) {
            printInfo(player);
        }
    }

    public void print(Set<Weapon> weapons) {
        for (Weapon weapon : weapons) {
            this.init(weapon);
            glEnable(GL_ALPHA_TEST);
            glAlphaFunc(GL_GREATER, 0.9f);
            GL11.glPushMatrix();
            GL11.glTranslated(weapon.getX(), weapon.getY(), 0);
            GL11.glRotated(Math.toDegrees(weapon.getAlpha()), 0, 0, 1);
            glTranslated(-weapon.getX(), -weapon.getY(), 0);
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2d(weapon.getX() - weapon.getSize(), weapon.getY() - weapon.getSize());
            glTexCoord2f(1, 0);
            glVertex2d(weapon.getX() + weapon.getSize(), weapon.getY() - weapon.getSize());
            glTexCoord2f(1, 1);
            glVertex2d(weapon.getX() + weapon.getSize(), weapon.getY() + weapon.getSize());
            glTexCoord2f(0, 1);
            glVertex2d(weapon.getX() - weapon.getSize(), weapon.getY() + weapon.getSize());
            glEnd();
            GL11.glPopMatrix();
            glDisable(GL_ALPHA_TEST);
        }
    }

    public void printInfo(Unit player) {
    }

    public void init(PassiveObject enemy) {
        this.passiveObjectMap.get(enemy.getTypeOfPassiveObject()).bind();
    }

    public void init(Unit ship) {
        this.unitMap.get(ship.getTypeOfShip()).bind();
    }

    public void init(Shell shell) {
        this.shellMap.get(shell.getTypeOfShell()).bind();
    }

    public void init(Weapon weapon) {
        this.weaponMap.get(weapon.getTypeOfWeapon()).bind();
    }

    public void init(EffectObject effect) {
        this.effectObjectMap.get(effect.getTypeOfEffectObject()).bind();
    }
}
