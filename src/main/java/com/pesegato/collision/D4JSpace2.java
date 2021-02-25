package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import org.dyn4j.collision.CollisionItem;
import org.dyn4j.collision.CollisionPair;
import org.dyn4j.collision.broadphase.*;
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


/**
 * TODO Evaluate the new 4.1 API https://github.com/dyn4j/dyn4j/issues/144
 */

public class D4JSpace2 extends BaseAppState {
    //private World world;

    int initialBodyCapacity=1024;

    final BroadphaseFilter<CollisionItem<Body, BodyFixture>> broadphaseFilter = new CollisionItemBroadphaseFilter<Body, BodyFixture>();
    final AABBProducer<CollisionItem<Body, BodyFixture>> aabbProducer = new CollisionItemAABBProducer<Body, BodyFixture>();
    final AABBExpansionMethod<CollisionItem<Body, BodyFixture>> expansionMethod = new StaticValueAABBExpansionMethod<CollisionItem<Body, BodyFixture>>(0.2);
    final BroadphaseDetector<CollisionItem<Body, BodyFixture>> broadphase = new DynamicAABBTree<CollisionItem<Body, BodyFixture>>(
            broadphaseFilter,
            aabbProducer,
            expansionMethod,
            initialBodyCapacity);
    final CollisionItemBroadphaseDetector<Body, BodyFixture> broadphaseDetector = new CollisionItemBroadphaseDetectorAdapter<Body, BodyFixture>(broadphase);
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
        np = new Gjk();
        //NarrowphasePostProcessor npp = LinkPostProcessor();  // Only required if you use the Link shape
        ms = new ClippingManifoldSolver();
        broadphaseDetector.setUpdateTrackingEnabled(true);
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
        body.setAtRestDetectionEnabled(false);
        broadphaseDetector.add(body);
        bodies.add(body);
    }

    public void remove(DebuggableBody body) {
        broadphaseDetector.remove(body);
        bodies.remove(body);
    }

    float tTPF = 0;

    public void update(float tpf) {
        tTPF += tpf;
        if (tTPF > 1 / 60f) {
            tTPF = 0;
            broadphase.update();
            //System.out.println("Collisions for "+name);
            // when ready to detect
            List<CollisionPair<CollisionItem<Body, BodyFixture>>> pairs = broadphaseDetector.detect();
            for (CollisionPair<CollisionItem<Body, BodyFixture>> pair : pairs) {
                CollisionItem<Body, BodyFixture> first = pair.getFirst();
                CollisionItem<Body, BodyFixture> second = pair.getSecond();
                BodyFixture fixture1 = first.getFixture();
                BodyFixture fixture2 = second.getFixture();
                Transform transform1 = first.getBody().getTransform();
                Transform transform2 = second.getBody().getTransform();
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
        List<CollisionPair<CollisionItem<Body, BodyFixture>>> pairs = broadphaseDetector.detect();
        for (CollisionPair<CollisionItem<Body, BodyFixture>> pair : pairs) {
            if ((pair.getFirst().getBody()==a)&&(pair.getSecond().getBody()==b)||
                    (pair.getFirst().getBody()==b)&&(pair.getSecond().getBody()==a)) {
                CollisionItem<Body, BodyFixture> first = pair.getFirst();
                CollisionItem<Body, BodyFixture> second = pair.getSecond();
                BodyFixture fixture1 = first.getFixture();
                BodyFixture fixture2 = second.getFixture();
                Transform transform1 = first.getBody().getTransform();
                Transform transform2 = second.getBody().getTransform();
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
