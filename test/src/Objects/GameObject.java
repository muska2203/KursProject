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
public class GameObject{

    protected Vector position;
    protected double acceleration = 0;
    protected double maxAcceleration = 1;
    protected double speed;
    protected double speedOfRotate = 0.1;
    protected double maxSpeed;
    protected Vector externalForce = new Vector();
    protected double size;
    protected int maxHealth;
    protected int health;
    protected Type type;
    protected boolean exist = true;
    protected boolean isRemoved = false;
    protected double alpha = 0;
    protected double rotate = 0;
    protected boolean inCollision = false;

    public GameObject(double size, Vector position, double speed,double alpha, Type type) {
        this.size = size;
        this.position = position;
        this.speed = speed;
        this.type = type;
        this.alpha = alpha;
    }
    public GameObject(GameObject gameObject) {
        this.size = gameObject.size;
        this.position = gameObject.position;
        this.speed = gameObject.speed;
        this.type = gameObject.type;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public double getSize() {
        return this.size;
    }

    public Type getType() {
        return this.type;
    }

    public void addHealth(int addedHealth) {
        this.health += addedHealth;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public void takeHealth(int tookHealth) {
        this.health -= tookHealth;
    }

    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return this.maxHealth;
    }

    public Vector getPosition() {
        return position;
    }

    public double getAcceleration(){
        return this.acceleration;
    }
    
    public double getSpeed(){
        return this.speed;//new Vector(this.speed*Math.cos(alpha),this.speed*Math.sin(alpha));
    }
    public Vector getSpeedVector(){
        return new Vector(this.speed*Math.cos(alpha),this.speed*Math.sin(alpha));
    }
    
    public Vector getSpeed(double delta){
        this.speed += this.acceleration * delta;
        if(this.speed>this.maxSpeed) {
            this.speed = maxSpeed;
        } else if(this.speed<-maxSpeed) {
            this.speed = -maxSpeed;
        }
        return new Vector(this.speed*Math.cos(alpha),this.speed*Math.sin(alpha));
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void remove() {
        this.exist = false;
    }

    public boolean exist() {
        return this.exist;
    }

    public double getDistance(GameObject obj) {
        return Math.sqrt(Math.pow(obj.getX() - this.getX(), 2) + Math.pow(obj.getY() - this.getY(), 2));
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    public double getAlpha() {
        return this.alpha;
    }
    public void addAlpha(double alpha) {
        this.alpha+= alpha;
        if(this.alpha>Math.toRadians(360))
            this.alpha-=Math.toRadians(360);
        if(this.alpha<0)
            this.alpha+=Math.toRadians(360);
    }
    public double getRotate() {
        return this.rotate;
    }
    public void rotate(double alpha) {
        if(alpha < 0) {
            alpha += Math.toRadians(360);
        }
        else if(alpha > Math.toRadians(360))
            alpha -=Math.toRadians(360);
        double k = 0;
        if(this.alpha >= Math.toRadians(180) && alpha <= Math.toRadians(180)) {
            k = Math.toRadians(360);
        }
        if(Math.abs(this.alpha -alpha) < this.speedOfRotate*2) {
            this.alpha = alpha;
            this.rotate = 0;
        }
        else if(this.alpha < alpha+k && alpha+k < this.alpha + Math.toRadians(180))
            this.rotate = this.speedOfRotate;
        else
            this.rotate = - this.speedOfRotate;
    }
    public void setExternalForce(Vector vector) {
        this.externalForce = vector;
    }
    public Vector getExternalForce() {
        Vector v = new Vector(this.externalForce);
        this.externalForce.set(0, 0);
        return v;
    }
    public void setCollision(boolean inCollision) {
        this.inCollision = inCollision;
    }
    public boolean getCollsision() {
        return this.inCollision;
    }
    public void stop() {
        this.acceleration = 0;
        this.speed = 0;
    }
}
