package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.HardwarePushbot;
import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot;
import org.firstinspires.ftc.teamcode.utilities.PIDController;

import java.util.Locale;

public abstract class AutonomousOpMode extends LinearOpMode {
    private final double aConstantProp = 0.000163;
    private final double bConstantProp = 0.009567;
    private final double cConstantProp = 0.126718;

    private final int MAX_SLIDE = 200;

    HardwareSkybot robot = new HardwareSkybot();
    private AutonomousState state;
    private Orientation angles;

    protected void rotate(double degrees, double power) {
        final double TURN_TOLERANCE = 2;

        robot.resetAngle();

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        //rotate within TURN_TOLERANCE degrees of error before stopping, and adjust as necessary
        while (Math.abs(robot.getAngle() - degrees) > TURN_TOLERANCE && opModeIsActive()) {
            robot.leftDrive.setPower(quadPropCorrectLeft(degrees, robot.getAngle()));
            robot.leftDrive.setPower(quadPropCorrectRight(degrees, robot.getAngle()));
            sleep(10);
            telemetry.update();
        }

        //stop the robot
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        robot.resetAngle();
    }

    //robot drives until proximity sensor is hitting an object
    protected void driveUntilImpact(double power) {
        power = Range.clip(power, -1, 1);
        robot.leftDrive.setPower(power);
        robot.rightDrive.setPower(power);

        /*while (Double.isNaN(robot.distanceSensor.getDistance(DistanceUnit.CM))) {
            telemetry.update();
            sleep(25);
        }*/

        //telemetry.addLine().addData("RYAN!", " I'VE HIT LINE 204");

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
    }

    protected String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    protected String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    protected void composeTelemetry() {
        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(() ->
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,
                    AngleUnit.DEGREES);
        });

        telemetry.addLine().addData("status", () -> robot.imu.getSystemStatus().toShortString())
                .addData("calib", () -> robot.imu.getCalibrationStatus().toString());

        //heading is firstAngle
        telemetry.addLine()
                .addData("heading", () -> formatAngle(angles.angleUnit, angles.firstAngle))
                .addData("roll", () -> formatAngle(angles.angleUnit, angles.secondAngle))
                .addData("pitch", () -> formatAngle(angles.angleUnit, angles.thirdAngle));

        /*telemetry.addLine().addData("dist", () -> String
                .format(Locale.US, "%.02f", robot.distanceSensor.getDistance(DistanceUnit.CM))); */

        telemetry.addLine().addData("state", () -> state.toString());
    }

    private double quadPropCorrectRight(double target, double current) {
        double delta = target - current;
        double gyroMod = delta / 45.0;
        if (Math.abs(gyroMod) > 1.0) {
            gyroMod = Math.signum(gyroMod);
            return -gyroMod;
        } //set gyromod to 1 or -1 if the error is more than 45 degrees
        else if (delta > 0) {
            return -(aConstantProp * Math.pow(delta, 2) + bConstantProp * delta + cConstantProp);
        } else {
            return (aConstantProp * Math.pow(delta, 2) + bConstantProp * delta + cConstantProp);
        } //otherwise use our quadratic model
    }

    private double quadPropCorrectLeft(double target, double current) {
        double delta = target - current;
        double gyroMod = delta / 45.0;
        if (Math.abs(gyroMod) > 1.0) {
            gyroMod = Math.signum(gyroMod);
            return gyroMod;
        } //set gyromod to 1 or -1 if the error is more than 45 degrees
        else if (delta > 0) {
            return (aConstantProp * Math.pow(delta, 2) + bConstantProp * delta + cConstantProp);
        } else {
            return -(aConstantProp * Math.pow(delta, 2) + bConstantProp * delta + cConstantProp);
        } //otherwise use our quadratic model
    }

    public void linSlideMove(float height, float power){
        robot.linSlide.setTargetPosition((int)(MAX_SLIDE*height));

        robot.linSlide.setPower(power);

        while (opModeIsActive() && robot.linSlide.isBusy())
        {
            telemetry.addData("encoder-fwd", robot.linSlide.getCurrentPosition() + "  busy=" + robot.linSlide.isBusy());
            telemetry.update();
            idle();
        }
    }
}