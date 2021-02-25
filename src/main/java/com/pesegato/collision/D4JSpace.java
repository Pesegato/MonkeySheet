package com.pesegato.collision;

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
import org.dyn4j.geometry.Transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
public class D4JSpace {
    //private World world;
    private Set<Spatial> spatials = new HashSet<Spatial>();

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

    String name="unnamed space";

    public void setName(String name){
        this.name=name;
    }

    public D4JSpace(){
     //   world=new World();

// collision detection process:
// Broadphase -> Narrowphase -> Manifold generation
// create detection chain
        np = new Gjk();
        //NarrowphasePostProcessor npp = LinkPostProcessor();  // Only required if you use the Link shape
        ms = new ClippingManifoldSolver();

        broadphaseDetector.setUpdateTrackingEnabled(true);
    }

    public void add(Spatial spatial) {
        if (spatial.getControl(IDyn4JControl.class) == null) throw new IllegalArgumentException("Cannot handle a node which isnt a ${Dyn4JShapeControl.getClass().getSimpleName()}");
        synchronized(spatials) {
            spatials.add(spatial);
            IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
            ctl.addToWorld(broadphaseDetector);
        }
    }

    public void remove(Spatial spatial) {
        if (spatial == null || spatial.getControl(IDyn4JControl.class) == null) return;
        synchronized (spatials) {
            spatials.remove(spatial);
        }
        IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
        for (int i = spatial.getNumControls() - 1; i > -1; i--) {
            //getNumControls changes for each cycle, because BGeometryBodyControl removes itself when disabled
            ((AbstractControl) spatial.getControl(i)).setEnabled(false);
        }
        ctl.removeFromWorld();
    }
    public void updateDraw(float tpf) {
        synchronized(spatials) {
            for (Spatial spatial: spatials){
                IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
                if (ctl == null) { spatials.remove(spatial); return; } //evict nodes which have their Dyn4JShapeControl removed
                ctl.updateDraw(tpf);
            }
        }
    }

    float tTPF=0;

    public void updatePhysics(float tpf) {
        //world.update(tpf, Integer.MAX_VALUE);
        tTPF+=tpf;
        if (tTPF>1/60f) {
            synchronized(spatials) {
                //System.out.println("*** numero di oggetti nel D4JSpace "+spatials.size());
                for (Spatial spatial: spatials){
                    IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
                    if (ctl == null) { spatials.remove(spatial); return; } //evict nodes which have their Dyn4JShapeControl removed
                    ctl.updatePhysics(broadphaseDetector, tpf);
                }
            }
            tTPF=0;
            //System.out.println("Collisions for "+name);
            // when ready to detect

            List<CollisionPair<CollisionItem<Body, BodyFixture>>> pairs = broadphaseDetector.detect();
            for (CollisionPair<CollisionItem<Body, BodyFixture>> pair : pairs) {
                // handle the pairs by using pair.getFirst().getBody() / pair.getSecond().getFixture() / etc.
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
                    for (CollisionListener listener:listeners){
                        listener.listen((Long)fixture1.getUserData(), (Long)fixture2.getUserData());
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
    public boolean checkCollisionAll(Body a, Body b){
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

    ArrayList<CollisionListener> listeners=new ArrayList<>();

    public void addListener(CollisionListener cl){
        listeners.add(cl);
    }

}
