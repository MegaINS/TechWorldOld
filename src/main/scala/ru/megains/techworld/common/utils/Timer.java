package ru.megains.techworld.common.utils;


public class Timer {

    private double lastLoopTime;
    private int targetTick;
    private int tick;
    private double ellapsedTime = 0f;


    public Timer(int tick) {
        this.targetTick = tick;
    }

    public void update() {

        tick = (int) Math.floor((getTime() - lastLoopTime) * targetTick);
        lastLoopTime += (1f / targetTick) * tick;
        ellapsedTime = getTime();
        if (tick > 20) {
            tick = 20;
        }

    }

    public int getTick() {
        return tick;
    }

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getEllapsedTime() {
        double time = getTime();
        float ellapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return ellapsedTime;
    }

    public double getLastLoopTime() {
        return ellapsedTime;
    }
}