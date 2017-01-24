package com.pesegato.collision;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.pesegato.collision.hitbox.HBRect;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.collision.broadphase.BroadphaseDetector;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;


public class Dyn4JShapeControl extends IDyn4JControl {
    protected Body body;
    BodyFixture fixture;
    //private World world;
    BroadphaseDetector broadphase;
    HBRect hbRect;

    public Dyn4JShapeControl(Convex shape,
                      MassType massType,
                             HBRect hbRect
    ) {
        this.hbRect=hbRect;
        body = new Body();
        fixture = new BodyFixture(shape);
        body.addFixture(fixture);
        body.setMass(massType);
        body.setAutoSleepingEnabled(false);
        fixture.setUserData(hbRect.id);
    }

    public void setFilter(CategoryFilter cf){
        fixture.setFilter(cf);
    }

    public Dyn4JShapeControl(Body body,
                             MassType massType,
                             long id
    ) {
        this.body = body;
        for (BodyFixture bf:body.getFixtures())
            bf.setUserData(id);
        body.setMass(massType);
        body.setAutoSleepingEnabled(true);
    }

    Dyn4JShapeControl(Convex shape,
                           MassType massType,
                           Double weight, //in kg/m
                           Double friction, // low = more slippery
                           Double restitution// more = more bouncy
    ) {
        body = new Body();
        fixture = new BodyFixture(shape);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        fixture.setDensity(weight);
        body.addFixture(fixture);
        body.setMass(massType);
        body.setAutoSleepingEnabled(true);
    }
    public void addToWorld(BroadphaseDetector broadphase) {
        this.broadphase = broadphase;
        broadphase.add(body);
    }

    public void removeFromWorld() {
        this.broadphase.remove(body);
    }
    /*
    @Override
    void addToWorld(World world) {
        this.world = world;
        world.addBody(body);
    }

    @Override
    public void removeFromWorld() {
        if (world==null)
            return;
        boolean removed=this.world.removeBody(body);
        System.out.println("removed "+removed);

        BroadphaseDetector bp = world.getBroadphaseDetector();
        boolean stillThere = bp.contains(body);
        for (BodyFixture fixture : body.getFixtures()) {
            stillThere |= bp.contains(body, fixture);
        }
        if (stillThere) {
            // I would need to see more code around the way the body is being removed
            System.out.println("still there");
        }

        this.world=null;
    }
*/
    // more = more bouncy
    void setRestitution(Double restitution) {
        fixture.setRestitution(restitution);
    }
    // more = in kg/m
    void setDensity(Double kg) {
        fixture.setDensity(kg);
    }

    // low = more slippery
    void setFriction(Double friction) {
        fixture.setFriction(friction);
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
        body.translate(new Double(spatial.getLocalTranslation().x), new Double(spatial.getLocalTranslation().y));

        //TODO: set initial rotation of the dyn4j-Body

    }

    @Override
    protected void controlUpdate(float tpf) {
        //Dyn4JAppState handles everything
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public Body getBody(){
        return body;

    }

    private Double lastAngle=-1d;
    private Transform lastTransform = new Transform();

    private final static Float negligibleAngleRotation = 0.001f;
    void updatePhysics(BroadphaseDetector bp, float tpf){
        if (bp.contains(body))
            bp.update(body);
    }
    void updateDraw(float tpf) {
        Vector2 vector2 = body.getTransform().getTranslation();
        this.spatial.setLocalTranslation(
                new Float(vector2.x),
                new Float(vector2.y), 0f);


        Transform transform = body.getTransform();
        if (transform.getTranslation().x == lastTransform.getTranslation().x &&
                transform.getTranslation().y == lastTransform.getTranslation().y) {
            this.spatial.setLocalTranslation(
                    new Vector3f(
                            new Float(transform.getTranslation().x),
                            new Float(transform.getTranslation().y),
                            0f));
            lastTransform=transform;
        }
        Double angle = body.getTransform().getRotation();
        if (angle != lastAngle) {
            Quaternion roll = new Quaternion();
            roll.fromAngleAxis( new Float(angle) , Vector3f.UNIT_Z);
            this.spatial.setLocalRotation(roll);
            lastAngle = angle;
        }
    }
}
