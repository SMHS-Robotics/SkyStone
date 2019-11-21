package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.utilities.PIDController;
import org.firstinspires.ftc.teamcode.utilities.DistanceTracker;


@Autonomous(name = "AutoCummyBoi", group = "SMHSBots")
public class AutonomousRedFront extends AutonomousOpMode
{

    private static double power = 0.25, rotation, globalAngle = 0, correction;
    private static final double maxErrorRotate = 90, targetSpeedMaxRotate = 0.45;
    private static final double baseR = targetSpeedMaxRotate / maxErrorRotate;
    private static final double KDrotate = baseR * 20;
    private static final double KProtate = baseR;
    private static final double KIrotate = baseR / 125;

    private static final double maxErrorStraight = 3, targetSpeedMaxStraight = 0.5;
    private static final double baseS = targetSpeedMaxStraight / maxErrorStraight;
    private static final double KDstraight = baseS * 2;
    private static final double KPstraight = baseS / 2;
    private static final double KIstraight = baseS / 100;

    private PIDController pidRotate, pidStraight;
    private Orientation lastAngles = new Orientation();

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);
        pidRotate = new PIDController(KProtate, KIrotate, KDrotate);
        pidStraight = new PIDController(KPstraight, KIstraight, KDstraight);
        resetAngle();


        float mmPerInch = 25.4f;
        float mmBotWidth = 18 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth = (12 * 12 - 2) *
                mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels

        pidStraight.setSetpoint(0);
        pidStraight.setOutputRange(0, 0.05);
        pidStraight.setInputRange(-1, 1);
        pidStraight.enable();

        telemetry.addData(">", "It is Almost Active");
        telemetry.update();

        while (opModeIsActive())
        {
            telemetry.addLine("We did it boys");
            rotate(-90);

            robot.leftDrive.setPower(0.3);
            robot.rightDrive.setPower(0.3);
            sleep(1000); //TODO: Adjust time to drive correct distance.
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            rotate(90);

            robot.leftClaw.setPosition(1);
            robot.rightClaw.setPosition(0);

            //Drives up to the block
            robot.leftDrive.setPower(0.3);
            robot.rightDrive.setPower(0.3);
            sleep(1000); //TODO: Adjust time to drive correct distance.
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            robot.leftClaw.setPosition(0);
            robot.rightClaw.setPosition(1);

            //Drives back after picking up the block
            robot.leftDrive.setPower(-0.3);
            robot.rightDrive.setPower(-0.3);
            sleep(200); //TODO: Adjust time to drive correct distance.
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            rotate(90);

            robot.leftDrive.setPower(1);
            robot.rightDrive.setPower(1);
            sleep(1500); //TODO: Adjust time to drive correct distance.
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            robot.leftClaw.setPosition(1);
            robot.rightClaw.setPosition(0);

            robot.leftDrive.setPower(-0.3);
            robot.rightDrive.setPower(-0.3);
            sleep(800); //TODO: Adjust time to drive correct distance.
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);
        }
    }

    private void rotate(double degrees) {
        degrees = -degrees;
        final double TURN_TOLERANCE = 0.15;

        robot.resetAngle();

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

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
                sleep(100);
            }

            do {
                power = pidRotate.performPID(getAngle()); // power will be - on right turn.
                robot.leftDrive.setPower(-power);
                robot.rightDrive.setPower(power);
            } while (opModeIsActive() && !pidRotate.onTarget());
        } else    // left turn.
            do {
                power = pidRotate.performPID(getAngle()); // power will be + on left turn.
                robot.leftDrive.setPower(-power);
                robot.rightDrive.setPower(power);
            } while (opModeIsActive() && !pidRotate.onTarget());

        // turn the motors off.
        robot.rightDrive.setPower(0);
        robot.leftDrive.setPower(0);

        rotation = getAngle();

        // wait for rotation to stop.
        sleep(500);

        // reset angle tracking on new heading.
        resetAngle();

        telemetry.addLine("Done");
        telemetry.update();
    }

    private void straight(double power)
    {
        resetAngle();
        correction = pidStraight.performPID(getAngle());

        robot.leftDrive.setPower(power - correction);

        robot.rightDrive.setPower(power + correction);
    }

    private void straight(double millisecs, double power)
    {
        for (int i = 0; i < millisecs; i += 10)
        {
            straight(power);
            sleep(10);
        }
        stop();
    }

    private double getAngle()
    {
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
}
