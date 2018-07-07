/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.util.ArrayList;
import static org.lwjgl.Sys.getTime;

/**
 *
 * @author admin
 */
public class Weapon extends GameObject {

    private TypeOfWeapon typeOfWeapon;
    private TypeOfShell typeOfShell;
    private double rateOfFire;
    private double lastFire = 0;
    private double sizeShell;
    private double speedShot = 0;
    private boolean isShoting;
    private Vector relativePosition;

    public Weapon(double size, Vector position, double speed, double alpha, Vector relativePosition, TypeOfWeapon typeOfWeapon) {
        super(size * 2, position, speed, alpha, Type.WEAPON);
        this.relativePosition = relativePosition;
        this.typeOfWeapon = typeOfWeapon;
        switch (this.typeOfWeapon) {
            case SPEED_BALL:
                this.speedOfRotate = 0.3;
                this.typeOfShell = TypeOfShell.HEAVY_SHELL;
                this.rateOfFire = 800;
                this.sizeShell = this.size / 20;
                this.speedShot = 5;
                break;
            default:
                this.speedOfRotate = 0.2;
                this.typeOfShell = TypeOfShell.HEAVY_SHELL;
                this.rateOfFire = 780;
                this.sizeShell = this.size / 20;
                this.speedShot = 30;
                break;
        }
    }

    public void shot() {
        if (getTime() - this.lastFire >= this.rateOfFire) {
            this.lastFire = getTime();
            this.isShoting = true;
        }
    }

    public boolean isShoting() {
        return this.isShoting;
    }

    public ArrayList<GameObject> takeShell(Unit unit) {
        ArrayList<GameObject> list = new ArrayList<>();
        Vector position;
        this.isShoting = false;
        switch (typeOfShell) {
            default:
                position = new Vector();
                position.setByAlpha(this.getSize(), this.alpha);
                position.addVector(new Vector(this.getPosition().getX(), this.getPosition().getY()));
                list.add(new Shell(this.sizeShell, position, speedShot, alpha, this.typeOfShell, unit.getTypeOfPilot()));
                break;
        }
        return list;
    }

    public TypeOfShell getTypeOfShell() {
        return this.typeOfShell;
    }
    public TypeOfWeapon getTypeOfWeapon() {
        return this.typeOfWeapon;
    }

    public double getSpeedShot() {
        return this.speedShot;
    }

    public Vector getRelativePosition() {
        return this.relativePosition;
    }

    public void setPosition(Vector vector, double alpha) {
        double betta = alpha - this.relativePosition.getAlpha();
        Vector v = new Vector();
        v.setByAlpha(this.relativePosition.getLength(), betta);
        this.position = Vector.addition(vector, v);
    }

    @Override
    public void rotate(double alpha) {
        double k = 0;
        if (this.alpha >= Math.toRadians(180) && alpha <= Math.toRadians(180)) {
            k = Math.toRadians(360);
        }
        if (Math.abs(this.alpha - alpha) < this.speedOfRotate * 0.5) {
            this.alpha = alpha;
            this.rotate = 0;
        } else if (this.alpha < alpha + k && alpha + k < this.alpha + Math.toRadians(180)) {
            this.rotate = this.speedOfRotate;
        } else {
            this.rotate = -this.speedOfRotate;
        }
    }

    public void aimObject(GameObject object) {
        //Входные данные цели
        double x1 = object.getPosition().getX();
        double y1 = object.getPosition().getY();
        Vector v1 = object.getSpeedVector();
        //Входные данные стрелка
        double x2 = this.getPosition().getX();
        double y2 = this.getPosition().getY();
        double v2 = this.getSpeedShot();
        //Вектор
        Vector vector = new Vector(x1 - x2, y1 - y2);
        double alpha = object.getSpeedVector().getAlpha();
        double gamma = Math.acos(Math.abs(x1 - x2) / vector.getLength());
        double alpha1 = 0;
        if (x1 >= x2 && y1 >= y2) {
            alpha1 = alpha - Math.toRadians(180) - gamma;
        } else if (x1 >= x2 && y1 <= y2) {
            alpha1 = alpha - Math.toRadians(180) + gamma;
        } else if (x1 < x2 && y1 > y2) {
            alpha1 = -alpha - gamma;
        } else {
            alpha1 = alpha - gamma;
        }
        double h = v1.getLength() * Math.sin(alpha1);
        double phi = Math.asin(h / v2);
        double betta = 0;
        if (x1 >= x2 && y1 >= y2) {
            betta = gamma - phi;
        } else if (x1 >= x2 && y1 <= y2) {
            betta = -gamma - phi;
        } else if (x1 < x2 && y1 > y2) {
            betta = Math.toRadians(180) - gamma + phi;
        } else {
            betta = Math.toRadians(180) + gamma - phi;
        }
        if (Double.isNaN(betta)) {
            this.rotate(vector.getAlpha());//this.//return vector.getAlpha();
        }
        this.rotate(betta);//return betta;  
    }
}
