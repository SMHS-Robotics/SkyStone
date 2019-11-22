package org.firstinspires.ftc.teamcode.utilities;

import java.util.concurrent.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot;

public class DistanceTracker {
    private double Vcurrent = 0;
    private double distance = 0;
    private boolean isRunning = false;
    private boolean killSig = false;

    public DistanceTracker () {}

    public void start(HardwareSkybot bot) {
        isRunning = true;
        CompletableFuture.runAsync(() -> {
            while (!killSig) {
                Vcurrent += Math.sqrt(Math.pow(bot.imu.getAcceleration().toUnit(DistanceUnit.INCH).xAccel, 2) + Math.pow(bot.imu.getAcceleration().toUnit(DistanceUnit.INCH).zAccel, 2)) * 0.01;
                distance += 0.01 * Vcurrent;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignore) {
                }
            }
        });
        isRunning = false;
        killSig = false;
    }

    public void stop() {
        killSig = true;
        Vcurrent = 0;
        distance = 0;
    }

    public boolean isTracking () {
        return isRunning;
    }

    //Returns in INCHES
    public double getDistance() {
        return Math.abs(distance);
    }
}
