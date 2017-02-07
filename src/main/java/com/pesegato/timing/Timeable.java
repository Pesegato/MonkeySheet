package com.pesegato.timing;

public interface Timeable {
    /**
     *
     * Implement this interface to have configurable timing.
     *
     * Time factor:
     * 0: time stops
     * 1: time runs normally
     * &lt;1: time is faster
     * &gt;1: time is slower
     *
     * @return time factor
     */
    float getClockspeed();
}
