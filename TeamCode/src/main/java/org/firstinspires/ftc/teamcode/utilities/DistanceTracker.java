package org.firstinspires.ftc.teamcode.utilities;

import java.util.concurrent.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot;

public class DistanceTracker {
    private double deltaV = 0;
    private double distance = 0;
    private boolean isRunning = false;

    public void start(HardwareSkybot bot) {
        isRunning = true;
        CompletableFuture.runAsync(() -> {
            while (isRunning) {
                deltaV += Math.sqrt(Math.pow(bot.imu.getAcceleration().toUnit(DistanceUnit.INCH).xAccel, 2) + Math.pow(bot.imu.getAcceleration().toUnit(DistanceUnit.INCH).zAccel, 2)) * 0.025;
                distance += 0.025 * deltaV;
                try {
                    Thread.sleep(25);
                } catch (InterruptedException ignore) {
                }
            }
        });
    }

    public void stop() {
        isRunning = false;
        deltaV = 0;
        distance = 0;
    }

    public boolean isTracking () {
        return isRunning;
    }

    //Returns in INCHES
    public double getDistance() {
        return distance;
    }
}
