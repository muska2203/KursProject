/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects.GameObject;
import Objects.Unit;
import Objects.Type;
import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.LWJGLException;
import static org.lwjgl.Sys.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author admin
 */
public class Game {

    private final String NAME = "SpaceWar";
    public final int WIDTH;
    public final int HEIGHT;
    private ArrayList<GameObject> objects;
    private ArrayList<GameObject> addedObjects;
    private Render render;
    private long lastTime;

    public Game(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        objects = new ArrayList<>();
        addedObjects = new ArrayList<>();
        Display.setTitle(null);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException ex) {
            System.exit(0);
        }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        double delta;
        LevelGenerator.generateLevel(this, 4);
        render = new Render();
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            this.deleteNotExisting();
            delta = getDelta();
            for (GameObject object : objects) {
                MoveManager.move(object, delta, this);
                if (object.getType() == Type.UNIT) {
                    Unit player = (Unit) object;
                    ActionManager.checkForShoting(this, player);
                }
            }
            objects.addAll(addedObjects);
            addedObjects.clear();
            CollisionManager.checkForCollision(objects);
            render.printAll(objects,this);
            Display.update();
            Display.sync(45);
        }
        Display.destroy();
        System.exit(0);
    }

    private double getDelta() {
        long currentTime = getTime();
        double delta = currentTime - lastTime;
        lastTime = getTime();
        return delta / 100;
    }

    private void deleteNotExisting() {
        Iterator iteratorObjects = objects.iterator();
        while (iteratorObjects.hasNext()) {
            GameObject object = (GameObject) iteratorObjects.next();
            if (!object.exist()) {
                iteratorObjects.remove();
            }
        }
    }

    public void addSpaceObject(ArrayList<GameObject> list) {
        this.addedObjects.addAll(list);
    }

    public void addSpaceObject(GameObject object) {
        this.addedObjects.add(object);
    }

    public ArrayList<GameObject> getSpaceObjects() {
        return this.objects;
    }
    

    public static void main(String... args) {
        Game game = new Game(1240, 720);
    }
}
