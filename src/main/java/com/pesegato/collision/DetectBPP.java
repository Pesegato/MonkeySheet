package com.pesegato.collision;
import org.dyn4j.collision.broadphase.BroadphaseDetector;
import org.dyn4j.collision.broadphase.BroadphaseFilter;
import org.dyn4j.collision.broadphase.DefaultBroadphaseFilter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

/**
 * Represents a {@link BroadphaseFilter} for the {@link BroadphaseDetector#detect(BroadphaseFilter)} method.
 * <p>
 * This filter extends the {@link DefaultBroadphaseFilter} class and adds filtering for the additional information
 * in the {@link Body} class.
 * <p>
 * Extend this class to add additional filtering capabilities to the broad-phase.
 * @author William Bittle
 * @version 3.2.0
 * @since 3.2.0
 */
public class DetectBPP extends DefaultBroadphaseFilter<Body, BodyFixture> implements BroadphaseFilter<Body, BodyFixture> {
    /* (non-Javadoc)
     * @see org.dyn4j.collision.broadphase.BroadphaseFilter#isAllowed(org.dyn4j.collision.Collidable, org.dyn4j.collision.Fixture, org.dyn4j.collision.Collidable, org.dyn4j.collision.Fixture)
     */
    @Override
    public boolean isAllowed(Body body1, BodyFixture fixture1, Body body2, BodyFixture fixture2) {
        if (body1.isConnected(body2, false)) return false;

        return super.isAllowed(body1, fixture1, body2, fixture2);
    }
}
