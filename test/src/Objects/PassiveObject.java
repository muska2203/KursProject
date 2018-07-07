/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author admin
 */
public class PassiveObject extends GameObject {

    protected final TypeOfPassiveObject typeOfPassiveObject;

    public PassiveObject(double size, Vector position, double speed, double alpha, TypeOfPassiveObject typeOfPassiveObject) {
        super(size, position, speed, alpha, Type.PASSIVE_OBJECT);
        
        this.typeOfPassiveObject = typeOfPassiveObject;
        switch(typeOfPassiveObject) {
            case WALL:
                this.maxSpeed = 0;
                this.speed = 0;
                this.acceleration = 0;
        }
    }

    public TypeOfPassiveObject getTypeOfPassiveObject() {
        return this.typeOfPassiveObject;
    }
}
