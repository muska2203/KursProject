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
public class EffectObject extends GameObject {

    private TypeOfEffectObject typeOfEffectObject;
    public EffectObject(double size, Vector position, double speed, double alpha,TypeOfEffectObject type) {
        super(size, position, speed, alpha, Type.EFFECT_OBJECT);
        this.typeOfEffectObject = type;
        switch(type) {
            default:
                break;
        }
    }
    public TypeOfEffectObject getTypeOfEffectObject() {
        return this.typeOfEffectObject;
    }
}
