/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Pilots.Enemy;
import Pilots.Pilot;
import Pilots.Player;
import Pilots.TypeOfPilot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import main.Game;
import static org.lwjgl.Sys.getTime;

/**
 *
 * @author admin
 */
public class Unit extends GameObject {

    Pilot pilot;
    private int armor;
    private double rateOfShield = 10000;
    private double lastShield = 0;
    private boolean isUsingShield;
    private TypeOfPilot typeOfPilot;
    private TypeOfUnit typeOfShip;
    private Set<Weapon> weapons;
    private boolean isShoting;

    public Unit(double size, Vector position, double speed, double alpha, TypeOfUnit typeOfShip, TypeOfPilot typeOfPilot) {
        super(size, position, speed, alpha, Type.UNIT);
        this.weapons = new HashSet<>();
        this.position = position;
        this.typeOfPilot = typeOfPilot;
        this.typeOfShip = typeOfShip;
        switch (this.typeOfShip) {
            case DEFAULT:
                this.weapons.add(new Weapon(this.size, new Vector(this.position), this.speed,this.alpha, new Vector(), TypeOfWeapon.SHOOTER_BALL));
                this.maxHealth =1000;
                this.armor = 1000;
                this.maxAcceleration = 0.3;
                this.maxSpeed = 2;
                this.speed = 0;
                break;
            case TOWER:
                this.weapons.add(new Weapon(this.size, this.position, this.speed,this.alpha, new Vector(), TypeOfWeapon.SPEED_BALL));
                this.maxSpeed = 0;
                this.maxHealth = 100;
                this.armor = 100;
                this.speedOfRotate = 1;
                break;
        }
        this.health =this.maxHealth;
        switch (typeOfPilot) {
            case PLAYER:
                this.pilot = new Player(this);
                break;
            case BOT:
                this.pilot = new Enemy(this,false);
                break;
        }
    }

    public void move(Game game, ArrayList<GameObject> objects) {
        this.pilot.move(game, objects);
        for (Weapon weapon : this.weapons) {
            weapon.setPosition(this.position, this.getAlpha());
        
        double alpha = this.getAlpha()-weapon.getRelativePosition().getAlpha();
        Vector v = new Vector();
        v.setByAlpha(weapon.getRelativePosition().getLength(), alpha);
        weapon.getPosition().set(Vector.addition(this.position, v));
        }
    }
    
    public int getArmor() {
        return this.armor;
    }

    public void addArmor(int addedArmor) {
        if(this.armor< this.maxHealth) {
            this.armor += addedArmor;
        }
        if(this.armor > this.maxHealth) {
            armor = maxHealth;
        }
    }

    public void takeArmor(int tookArmor) {
        if(this.armor - tookArmor < 0) {
            this.takeHealth(tookArmor + this.armor-tookArmor);
            this.armor = 0;
        } else  
            this.armor -= tookArmor;
    }
    @Override
    public void takeHealth(int tookHealth) {
        if(this.armor >0) {
            this.takeArmor(tookHealth);
        } else {
            super.takeHealth(tookHealth);
        }
    }

    public Set<Weapon> getWeapons() {
        return this.weapons;
    }

    public void shot() {
        for (Weapon weapon : this.weapons) {
            weapon.shot();
        }
    }

    public void createShield() {
        if (getTime() - this.lastShield >= this.rateOfShield) {
            this.lastShield = getTime();
            this.isUsingShield = true;
        }
    }

    public boolean isUsingShield() {
        if (this.isUsingShield) {
            this.isUsingShield = false;
            return true;
        }
        return false;
    }
    
    public TypeOfPilot getTypeOfPilot() {
        return this.typeOfPilot;
    }

    public TypeOfUnit getTypeOfShip() {
        return this.typeOfShip;
    }

    public boolean isShoting() {
        return this.isShoting;
    }

    public void setAlphaWeapon(double alpha) {
        for (Weapon weapon : this.weapons) {
            weapon.rotate(alpha);
        }
    }

    public void aimObject(GameObject obj) {
        for (Weapon weapon : this.weapons) {
            weapon.aimObject(obj);
        }
    }
    public void setAcceleration(int i) {
        if(i == 0){
            if((this.speed>=0 && this.speed - this.maxAcceleration <= 0) || (this.speed<=0 && this.speed + this.maxAcceleration >= 0)) {
                this.speed = 0;
                this.acceleration = 0;
            } else if(this.speed > 0) {
                this.acceleration = -this.maxAcceleration;
            } else if(this.speed < 0) {
                this.acceleration = this.maxAcceleration;
            }
        }
        else if(i > 0) {
            if(this.speed<0) {
                this.acceleration = this.maxAcceleration*2;
            } else {
                this.acceleration = this.maxAcceleration;
            }
        }
        else {
            if(this.speed>0) {
                this.acceleration = -this.maxAcceleration*2;
            } else {
                this.acceleration = -this.maxAcceleration;
            }
        }
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    @Override
    public void addAlpha(double alpha) {
        super.addAlpha(alpha);
        for(Weapon weapon : weapons) {
            weapon.addAlpha(alpha);
        }
    }
}
