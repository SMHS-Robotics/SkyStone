package org.firstinspires.ftc.teamcode.utilities;

import java.util.concurrent.*;
import java.time.*;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot;

public class DistanceTracker {
    private double Vcurrent = 0;
    private double distance = 0;
    public DistanceTracker () {}

    public void start(HardwareSkybot bot, double target) {
        bot.leftDrive.setPower(1);
        bot.rightDrive.setPower(1);
        double currentTime;
        double prevTime= System.nanoTime();
        double deltaTime;
            while (Math.abs(target - distance) < 0.1) {
                currentTime = System.nanoTime();
                deltaTime = currentTime - prevTime;
                Vcurrent += Math.pow(bot.imu.getAcceleration().zAccel, 2) * deltaTime/1000000000;
                distance += Vcurrent * deltaTime/1000000000;
                prevTime = currentTime;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignore) {}
            }
        bot.leftDrive.setPower(0);
        bot.rightDrive.setPower(0);
    }
}
