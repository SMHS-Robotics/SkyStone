package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.utilities.PIDController;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "MoveFoundation", group = "SMHSBots")
public class AutonomousOmni extends AutonomousOpMode {

    private static double power = 0.85, rotation, globalAngle = 0, correction;
    private static final double maxErrorRotate = 90, targetSpeedMaxRotate = 1;
    private static final double baseR = targetSpeedMaxRotate / maxErrorRotate;
    private static final double KDrotate = baseR * 20;
    private static final double KProtate = baseR;
    private static final double KIrotate = baseR / 125;

    private PIDController pidRotate, pidDrive;
    private Orientation lastAngles = new Orientation();

    private static final int ticksPerInch = 1000;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        pidRotate = new PIDController(KProtate, KIrotate, KDrotate);
        pidDrive = new PIDController(.05, 0, 0);
        pidDrive.setSetpoint(0);
        pidDrive.setOutputRange(0, power);
        pidDrive.setInputRange(-90, 90);
        pidDrive.enable();
        resetAngle();

        waitForStart();

        telemetry.addData(">", "It is Almost Active");
        telemetry.update();

        while (opModeIsActive()) {
            telemetry.addData(">", "It is Almost Active");
            telemetry.update();

            //moveFoundationRed();
            rotate(90);

            stop();
        }
    }


    public void moveFoundationRed() {
        //go halfway to foundation
        pidDriveWithEncoders(19 * ticksPerInch, power);

        //turn 180 degrees
        rotate(90);
        rotate(90);

        //go rest of way to foundation
        pidDriveWithEncoders(-19 * ticksPerInch, -power);

        //clamp
        robot.leftHook.setPosition(0.5);
        robot.rightHook.setPosition(0.5);

        //drag back to area
        pidDriveWithEncoders(40 * ticksPerInch, power);

        //unclamp
        robot.leftHook.setPosition(0);
        robot.rightHook.setPosition(0);

        //park
        rightWithEncoders(16 * ticksPerInch, power);
    }

    public void moveFoundationBlue() {
        //go halfway to foundation
        pidDriveWithEncoders(19 * ticksPerInch, power);

        //turn 180 degrees
        rotate(90);
        rotate(90);

        //go rest of way to foundation
        pidDriveWithEncoders(-19 * ticksPerInch, -power);

        //clamp
        robot.leftHook.setPosition(0.5);
        robot.rightHook.setPosition(0.5);

        //drag back to area
        pidDriveWithEncoders(40 * ticksPerInch, power);

        //unclamp
        robot.leftHook.setPosition(0);
        robot.rightHook.setPosition(0);

        //park
        leftWithEncoders(16 * ticksPerInch, power);
    }

    public void getSkystoneRed() {
        //unclamp
        //TODO: set claw position

        //drive to block
        pidDriveWithEncoders(38 * ticksPerInch, power);

        //clamp
        //TODO: set claw position

        //head back to wall
        pidDriveWithEncoders(-38 * ticksPerInch, -power);
        pidDriveWithEncoders(5 * ticksPerInch, power);

        //rotate and go under team bridge
        rotate(-90);
        pidDriveWithEncoders(72 * ticksPerInch, power);

        //unclamp and park under bridge
        //TODO: set claw position
        pidDriveWithEncoders(-36 * ticksPerInch, -power);
    }

    public void getSkystoneBlue() {
        //unclamp
        //TODO: set claw position

        //drive to block
        pidDriveWithEncoders(38 * ticksPerInch, power);

        //clamp
        //TODO: set claw position

        //head back to wall
        pidDriveWithEncoders(-38 * ticksPerInch, -power);
        pidDriveWithEncoders(5 * ticksPerInch, power);

        //rotate and go under team bridge
        rotate(90);
        pidDriveWithEncoders(72 * ticksPerInch, power);

        //unclamp and park under bridge
        //TODO: set claw position
        pidDriveWithEncoders(-36 * ticksPerInch, -power);
    }

    private void rotate(double degrees) {
        degrees = -degrees;
        final double TURN_TOLERANCE = 0.15;

        robot.resetAngle();

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);
        if (Math.abs(degrees) > 359) degrees = (int) Math.copySign(359, degrees);

        pidRotate.reset();
        pidRotate.setSetpoint(degrees);
        pidRotate.setInputRange(0, degrees + 0.1);
        pidRotate.setOutputRange(0, targetSpeedMaxRotate/4);
        pidRotate.setTolerance(TURN_TOLERANCE);
        pidRotate.enable();
        telemetry.addLine("Reached");
        telemetry.update();

        if (degrees < 0) {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {
                robot.leftDrive.setPower(power);
                robot.rightDrive.setPower(-power);
                robot.leftDriveFront.setPower(power);
                robot.rightDriveFront.setPower(-power);
                sleep(100);
            }

            do {
                power = pidRotate.performPID(getAngle()); // power will be - on right turn.
                robot.leftDrive.setPower(-power);
                robot.rightDrive.setPower(power);
                robot.leftDriveFront.setPower(-power);
                robot.rightDriveFront.setPower(power);
            } while (opModeIsActive() && !pidRotate.onTarget());
        } else    // left turn.
            do {
                power = pidRotate.performPID(getAngle()); // power will be + on left turn.
                robot.leftDrive.setPower(-power);
                robot.rightDrive.setPower(power);
                robot.leftDriveFront.setPower(-power);
                robot.rightDriveFront.setPower(power);
            } while (opModeIsActive() && !pidRotate.onTarget());

        // turn the motors off.
        robot.rightDrive.setPower(0);
        robot.leftDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        rotation = getAngle();

        // wait for rotation to stop.
        sleep(500);

        // reset angle tracking on new heading.
        resetAngle();

        telemetry.addLine("Done");
        telemetry.update();
    }

    private double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    public void resetAngle() {
        lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    public void straightWithEncoders (int counts){
        //resets the encoder and sets the leftDrive to run with encoders
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftDrive.setTargetPosition(counts);

        robot.leftDrive.setPower(power);
        robot.rightDrive.setPower(power);
        robot.leftDriveFront.setPower(power);
        robot.rightDriveFront.setPower(power);

        while(robot.leftDrive.isBusy() && opModeIsActive()) {}

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void leftWithEncoders (int counts, double power) {
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftDrive.setTargetPosition(counts);

        robot.leftDrive.setPower(power);
        robot.rightDrive.setPower(-power);
        robot.leftDriveFront.setPower(-power);
        robot.rightDriveFront.setPower(power);

        while(robot.leftDrive.isBusy() && opModeIsActive()){}

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rightWithEncoders (int counts, double power) {
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftDrive.setTargetPosition(-counts);

        robot.leftDrive.setPower(-power);
        robot.rightDrive.setPower(power);
        robot.leftDriveFront.setPower(power);
        robot.rightDriveFront.setPower(-power);

        while(robot.leftDrive.isBusy() && opModeIsActive()){}

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void pidDriveWithEncoders (int counts, double power) {
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftDrive.setTargetPosition(counts);

        robot.leftDrive.setPower(power);
        robot.rightDrive.setPower(power);
        robot.leftDriveFront.setPower(power);
        robot.rightDriveFront.setPower(power);

        while(robot.leftDrive.isBusy() && opModeIsActive()){
            correction = pidDrive.performPID(getAngle());
            robot.leftDrive.setPower(power-correction);
            robot.rightDrive.setPower(power+correction);
        }

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}