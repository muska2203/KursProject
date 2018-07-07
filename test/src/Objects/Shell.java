/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Pilots.TypeOfPilot;

/**
 *
 * @author admin
 */
public class Shell extends GameObject {

    private final TypeOfShell typeOfShell;
    private final int damage;
    private final TypeOfPilot shooter;
    private final double maxLength;
    private double length = 0;

    public Shell(double size, Vector position, double speed, double alpha, TypeOfShell typeOfShell,TypeOfPilot shooter) {
        super(size, position, speed, alpha, Type.SHELL);
        this.speed = speed;
        this.typeOfShell = typeOfShell;
        this.shooter = shooter;
        switch(this.typeOfShell) {
            case BALL:
                this.damage = 1;
                this.maxLength = 300;
                this.maxSpeed = 30;
                break;
            default: 
                this.damage = 20;
                this.maxLength = 300;
                this.maxSpeed = 15;
        }
    }

    public TypeOfShell getTypeOfShell() {
        return this.typeOfShell;
    }

    public int getDamage() {
        return this.damage;
    }

    public void hit() {
        switch (this.typeOfShell) {
            case HEAVY_SHELL:
                break;
            default:
                this.remove();
                break;
        }
    }

    public TypeOfPilot getShooter() {
        return this.shooter;
    }

    public void addLength(double length) {
        this.length += length;
        if (this.length >= this.maxLength) {
            this.remove();
        }
    }

}
