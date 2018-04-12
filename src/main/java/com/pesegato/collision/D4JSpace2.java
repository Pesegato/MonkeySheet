package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import org.dyn4j.collision.broadphase.BroadphaseDetector;
import org.dyn4j.collision.broadphase.BroadphasePair;
import org.dyn4j.collision.broadphase.DynamicAABBTree;
import org.dyn4j.collision.manifold.ClippingManifoldSolver;
import org.dyn4j.collision.manifold.ManifoldSolver;
import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.collision.narrowphase.NarrowphaseDetector;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class D4JSpace2 extends BaseAppState {
    //private World world;

    BroadphaseDetector<Body, BodyFixture> bp;
    NarrowphaseDetector np;
    ManifoldSolver ms;

    ArrayList<DebuggableBody> bodies = new ArrayList<>();
    String name = "unnamed space";

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void initialize(Application app) {
        //   world=new World();

// collision detection process:
// Broadphase -> Narrowphase -> Manifold generation
// create detection chain
        bp = new DynamicAABBTree<Body, BodyFixture>();
        np = new Gjk();
        //NarrowphasePostProcessor npp = LinkPostProcessor();  // Only required if you use the Link shape
        ms = new ClippingManifoldSolver();

    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    public void add(DebuggableBody body, MassType massType, long id) {
        for (BodyFixture bf : body.getFixtures())
            bf.setUserData(id);
        body.setMass(massType);
        body.setAutoSleepingEnabled(false);
        bp.add(body);
        bodies.add(body);
    }

    public void remove(DebuggableBody body) {
        bp.remove(body);
        bodies.remove(body);
    }

    float tTPF = 0;

    public void update(float tpf) {
        tTPF += tpf;
        if (tTPF > 1 / 60f) {
            tTPF = 0;
            for (Body b : bodies) {
                bp.update(b);
            }
            //System.out.println("Collisions for "+name);
            // when ready to detect
            List<BroadphasePair<Body, BodyFixture>> pairs = bp.detect();
            for (BroadphasePair<Body, BodyFixture> pair : pairs) {
                Body body1 = pair.getCollidable1();
                Body body2 = pair.getCollidable2();
                BodyFixture fixture1 = pair.getFixture1();
                BodyFixture fixture2 = pair.getFixture2();
                Transform transform1 = body1.getTransform();
                Transform transform2 = body2.getTransform();
                Convex convex2 = fixture2.getShape();
                Convex convex1 = fixture1.getShape();
                Penetration p = new Penetration();
                if (np.detect(convex1, transform1, convex2, transform2, p)) {
                    //System.out.println("Collision " + fixture1.getUserData() + " " + fixture2.getUserData());
                    for (CollisionListener listener : listeners) {
                        listener.listen((Long) fixture1.getUserData(), (Long) fixture2.getUserData());
                    }
                }
            }
        }
    }

    public boolean checkCollisionNP(Body a, Body b) {
        for (BodyFixture bf1 : a.getFixtures()) {
            for (BodyFixture bf2 : b.getFixtures()) {
                if (np.detect(bf1.getShape(), a.getTransform(), bf2.getShape(), b.getTransform())) {
                    return true;
                }
            }
        }
        return false;
    }
        /*

        Alternative solution, but this require the use of World class

    public boolean checkCollisionAll(Body a, Body b){
        return a.isInContact(b);
        }
        */

    /*

    Another alternative
*/
    public boolean checkCollisionAll(Body a, Body b) {
        for (BroadphasePair<Body, BodyFixture> pair : bp.detect()) {
            if ((pair.getCollidable1() == a) && (pair.getCollidable2() == b) ||
                    (pair.getCollidable1() == b) && (pair.getCollidable2() == a)) {
                Body body1 = pair.getCollidable1();
                Body body2 = pair.getCollidable2();
                BodyFixture fixture1 = pair.getFixture1();
                BodyFixture fixture2 = pair.getFixture2();
                Transform transform1 = body1.getTransform();
                Transform transform2 = body2.getTransform();
                Convex convex2 = fixture2.getShape();
                Convex convex1 = fixture1.getShape();
                Penetration p = new Penetration();
                if (np.detect(convex1, transform1, convex2, transform2, p)) {
                    return true;
                }
            }
        }
        return false;
    }

    ArrayList<CollisionListener> listeners = new ArrayList<>();

    public void addListener(CollisionListener cl) {
        listeners.add(cl);
    }

}
