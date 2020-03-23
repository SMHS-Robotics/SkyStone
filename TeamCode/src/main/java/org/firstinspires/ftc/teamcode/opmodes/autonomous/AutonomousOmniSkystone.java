package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.utilities.PIDController;

@Autonomous(name = "GetSkystoneRed", group = "SMHSBots")
public class AutonomousOmniSkystone extends AutonomousOpMode {

    private static final double power = 0.3;
    private static double rotation, globalAngle = 0, correction;
    private static final double maxErrorRotate = 90, targetSpeedMaxRotate = 1;
    private static final double baseR = targetSpeedMaxRotate / maxErrorRotate;
    private static final double KDrotate = baseR * 21;
    private static final double KProtate = baseR;
    private static final double KIrotate = baseR / 125;

    private PIDController pidRotate, pidDrive;
    private Orientation lastAngles = new Orientation();

    private static final int ticksPerInch = 25;

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

        while (opModeIsActive()) {
            telemetry.addLine("Here");
            telemetry.update();
            getSkystoneRed();
            stop();
        }
    }


    public void moveFoundationRed() {
        //go halfway to foundation

        //go rest of way to foundation
        pidDriveWithEncoders(-28 * ticksPerInch, power);

        pidDriveWithEncoders(-10 * ticksPerInch, power/2);

        //clamp
        robot.leftHook.setPosition(0);
        robot.rightHook.setPosition(1);

        sleep(500);

        //drag back to area
        pidDriveWithEncoders(40 * ticksPerInch, power);

        //unclamp
        robot.leftHook.setPosition(1);
        robot.rightHook.setPosition(0);
    }

    public void moveFoundationBlue() {
        //go halfway to foundation
        pidDriveWithEncoders(-28 * ticksPerInch, power);

        pidDriveWithEncoders(-10 * ticksPerInch, power/2);

        //clamp
        robot.leftHook.setPosition(0);
        robot.rightHook.setPosition(1);

        sleep(500);

        //drag back to area
        pidDriveWithEncoders(40 * ticksPerInch, power);

        //unclamp
        robot.leftHook.setPosition(1);
        robot.rightHook.setPosition(0);
    }

    public void getSkystoneRed() {

        //drive to block
        pidDriveWithEncoders(32 * ticksPerInch, power);
        pidDriveWithEncoders(10 * ticksPerInch, power/2);

        sleep(100);

        //clamp
        robot.leftClaw.setPosition(0.8);

        sleep(500);
        //head back to wall
        pidDriveWithEncoders(-10 * ticksPerInch, power);

        //rotate and go under team bridge
        rotate(90);
        pidDriveWithEncoders(58 * ticksPerInch, power);

        robot.leftClaw.setPosition(0.4);
        pidDriveWithEncoders(-50 * ticksPerInch, power);

        rotate(-90);
        pidDriveWithEncoders(16 * ticksPerInch, power/2);
        robot.leftClaw.setPosition(0.8);
        pidDriveWithEncoders(-16 * ticksPerInch, power);
        rotate(90);
        pidDriveWithEncoders(40 * ticksPerInch, power);
        robot.leftClaw.setPosition(0.4);
        pidDriveWithEncoders(-24 * ticksPerInch, power);
    }

    public void getSkystoneBlue() {
        //drive to block
        pidDriveWithEncoders(32 * ticksPerInch, power);
        pidDriveWithEncoders(10 * ticksPerInch, power/2);

        sleep(100);

        //clamp
        robot.leftClaw.setPosition(0.8);

        sleep(500);
        //head back to wall
        pidDriveWithEncoders(-25 * ticksPerInch, power);

        //rotate and go under team bridge
        rotate(-90);
        pidDriveWithEncoders(68 * ticksPerInch, power);

        robot.leftClaw.setPosition(0.4);
        pidDriveWithEncoders(-40 * ticksPerInch, power);

        rotate(90);
        pidDriveWithEncoders(20 * ticksPerInch, power);
        pidDriveWithEncoders(10 * ticksPerInch, power/2);
        robot.leftClaw.setPosition(0.8);
        pidDriveWithEncoders(-30 * ticksPerInch, power);
        rotate(-90);
        pidDriveWithEncoders(48 * ticksPerInch, power);
        robot.leftClaw.setPosition(0.4);
        pidDriveWithEncoders(-18 * ticksPerInch, power);
    }

    private void rotate(double degrees) {
        double rpower = power;
        degrees = -degrees;
        final double TURN_TOLERANCE = 0.5;

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
        telemetry.addLine("About to Rotate");
        telemetry.update();

        if (degrees < 0) {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {
                robot.leftDrive.setPower(-rpower);
                robot.rightDrive.setPower(rpower);
                robot.leftDriveFront.setPower(rpower);
                robot.rightDriveFront.setPower(-rpower);
                telemetry.addLine("About to rotate right");
                telemetry.update();
                sleep(100);
            }

            do {
                telemetry.addLine("Rotating Right");
                telemetry.update();
                rpower = pidRotate.performPID(getAngle()); // power will be - on right turn.
                robot.leftDrive.setPower(-rpower);
                robot.rightDrive.setPower(rpower);
                robot.leftDriveFront.setPower(rpower);
                robot.rightDriveFront.setPower(-rpower);
            } while (opModeIsActive() && !pidRotate.onTarget());
        } else
        {   // left turn.
            do
            {
                rpower = pidRotate.performPID(getAngle()); // power will be + on left turn.
                robot.leftDrive.setPower(-rpower);
                robot.rightDrive.setPower(rpower);
                robot.leftDriveFront.setPower(rpower);
                robot.rightDriveFront.setPower(-rpower);
                telemetry.addLine("Updating");
                telemetry.addData("Degrees: ", degrees);
                telemetry.update();
            }
            while (opModeIsActive() && !pidRotate.onTarget());
        }

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

        telemetry.addData("Target Count: ", counts);
        telemetry.addData("Current Count: ", robot.leftDrive.getCurrentPosition());
        telemetry.update();

        robot.leftDrive.setPower(power);
        robot.rightDrive.setPower(power);
        robot.leftDriveFront.setPower(power);
        robot.rightDriveFront.setPower(power);

        while(robot.leftDrive.getCurrentPosition()<counts && opModeIsActive()) {
            telemetry.addData("Current Count: ", robot.leftDrive.getCurrentPosition());
            telemetry.update();
        }

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void leftWithEncoders (int counts, double power) {
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftDrive.setPower(power);
        robot.rightDrive.setPower(-power);
        robot.leftDriveFront.setPower(power);
        robot.rightDriveFront.setPower(-power);

        while(robot.leftDrive.getCurrentPosition()<counts && opModeIsActive()) {
            telemetry.addData("Current Count: ", robot.leftDrive.getCurrentPosition());
            telemetry.update();
        }

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
        robot.leftDriveFront.setPower(-power);
        robot.rightDriveFront.setPower(power);

        while(robot.leftDrive.getCurrentPosition()<counts && opModeIsActive()) {
            telemetry.addData("Current Count: ", robot.leftDrive.getCurrentPosition());
            telemetry.update();
        }

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
        telemetry.addData("Target Count: ", counts);
        telemetry.update();

        double spower = counts > 0 ? power : -power;

        robot.leftDrive.setPower(spower);
        robot.rightDrive.setPower(spower);
        robot.leftDriveFront.setPower(spower);
        robot.rightDriveFront.setPower(spower);

        while(Math.abs(robot.leftDrive.getCurrentPosition()) < Math.abs(counts) && opModeIsActive()){
            correction = pidDrive.performPID(getAngle());
            robot.leftDrive.setPower(spower-correction);
            robot.rightDrive.setPower(spower+correction);
            telemetry.addData("Current Count: ", robot.leftDrive.getCurrentPosition());
            telemetry.update();
        }

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.rightDriveFront.setPower(0);
        robot.leftDriveFront.setPower(0);


        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}