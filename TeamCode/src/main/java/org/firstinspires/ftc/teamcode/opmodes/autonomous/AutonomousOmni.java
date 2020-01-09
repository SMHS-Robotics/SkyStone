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

@Autonomous(name = "AutoSkystone", group = "SMHSBots")
public class AutonomousOmni extends AutonomousOpMode {

    public static final String TAG = "Vuforia Navigation Sample";

    private static final String VUFORIA_KEY =
            "Af4Nrm3/////AAABmYrLO8rBw0UsgjLZZOnsZXyKCoqLXTsA+qwyN1l/pSSzkUxTt7m1CLhYYAEc7rHn" +
                    "0djvGBwSwbeujxh/oIfCA0SLCLgsSzi49untTRKe5VYYGINH7L/7YTahO4w6xKY4WRZgBOab" +
                    "Nx+w/8vGbV4gNmaKpLjpC9WD5GLeGKAOgf32oBGB6AOrKprvXX3++9A+is+G2u3Cv74ldLZp" +
                    "g7J8Sg55TBuo5N9LxvcE7zOAKvsQO+q+FWsxjm+Axfac57jd/YbgnkVidKsEiXLXRShe+xgW" +
                    "1Ma3uP9H5Xiz1HY8RbtWZtgwozIZSRJUB+8km2LqZsI/bUTQ4ysXNRUC/KrxHVThhdcllY40" +
                    "J8A260JkRcUj";

    private static double power = 0.85, rotation, globalAngle = 0, correction;
    private static final double maxErrorRotate = 90, targetSpeedMaxRotate = 1;
    private static final double baseR = targetSpeedMaxRotate / maxErrorRotate;
    private static final double KDrotate = baseR * 20;
    private static final double KProtate = baseR;
    private static final double KIrotate = baseR / 125;

    private PIDController pidRotate, pidDrive;
    private Orientation lastAngles = new Orientation();

    private AutonomousState checkPos = AutonomousState.GET_SKYSTONE_RED;

    private OpenGLMatrix lastLocation = null;

    private VuforiaLocalizer vuforia;

    private List<VuforiaTrackable> allTrackables = new ArrayList<>();

    public void getSkystoneRed() {

    }

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(
                cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("Skystone");
        VuforiaTrackable skystone = stonesAndChips.get(0);
        skystone.setName("Skystone");

        VuforiaTrackable blueSkybridgeRear = stonesAndChips.get(1);
        blueSkybridgeRear.setName("BlueSkybridgeRear");
        VuforiaTrackable blueSkybridgeFront = stonesAndChips.get(4);
        blueSkybridgeFront.setName("BlueSkybridgeFront");
        VuforiaTrackable redSkybridgeRear = stonesAndChips.get(2);
        redSkybridgeRear.setName("RedSkybridgeRear");
        VuforiaTrackable redSkybridgeFront = stonesAndChips.get(3);
        redSkybridgeFront.setName("RedSkybridgeFront");
        VuforiaTrackable blueAllianceFront = stonesAndChips.get(9);
        blueAllianceFront.setName("BlueAllianceWallFront");
        VuforiaTrackable blueAllianceRear = stonesAndChips.get(10);
        blueAllianceRear.setName("BlueAllianceWallRear");
        VuforiaTrackable redAllianceFront = stonesAndChips.get(6);
        redAllianceFront.setName("RedAllianceWallFront");
        VuforiaTrackable redAllianceRear = stonesAndChips.get(5);
        redAllianceRear.setName("RedAllianceWallRear");
        VuforiaTrackable frontWallRed = stonesAndChips.get(7);
        frontWallRed.setName("FrontWallRedAlliance");
        VuforiaTrackable frontWallBlue = stonesAndChips.get(8);
        frontWallBlue.setName("FrontWallBlueAlliance");
        VuforiaTrackable rearWallRed = stonesAndChips.get(12);
        rearWallRed.setName("RearWallRedAlliance");
        VuforiaTrackable rearWallBlue = stonesAndChips.get(11);
        rearWallBlue.setName("RearWallBlueAlliance");

        allTrackables.addAll(stonesAndChips);

        float mmPerInch = 25.4f;
        float mmBotWidth = 18 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth = (12 * 12 - 2) *
                mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels

        OpenGLMatrix redTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        //  redTarget.setLocation(redTargetLocationOnField);
        RobotLog.ii(TAG, "Red Target=%s", format(redTargetLocationOnField));

        /*
         * To place the Stones Target on the Blue Audience wall:
         * - First we rotate it 90 around the field's X axis to flip it upright
         * - Finally, we translate it along the Y axis towards the blue audience wall.
         */
        OpenGLMatrix blueTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        //    blueTarget.setLocation(blueTargetLocationOnField);
        RobotLog.ii(TAG, "Blue Target=%s", format(blueTargetLocationOnField));

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));

    /*    ((VuforiaTrackableDefaultListener) redTarget.getListener())
                .setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener) blueTarget.getListener())
                .setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); */

        waitForStart();

        /** Start tracking the data sets we care about. */
        stonesAndChips.activate();

        telemetry.addData(">", "It is Almost Active");
        telemetry.update();

        while (opModeIsActive()) {
            telemetry.addData(">", "It is Almost Active");
            telemetry.update();
            for (VuforiaTrackable trackable : allTrackables) {
                /*
                 * getUpdatedRobotLocation() will return null if no new information is available since
                 * the last time that call was made, or if the trackable is not currently visible.
                 * getRobotLocation() will return null if the trackable is not currently visible.
                 */
                telemetry.addData(trackable.getName(),
                        ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ?
                                "Visible" : "Not Visible");    //

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable
                        .getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }
            /**
             * Provide feedback as to where the robot was last located (if we know).
             */
            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
                telemetry.addData("Pos", format(lastLocation));
            } else {
                telemetry.addData("Pos", "Unknown");
            }

            switch (checkPos) {
                case CHECK_POSITION:
                    checkPosition();
                    break;
                case GET_SKYSTONE_RED:
                    getSkystoneRed();
                    break;
                case GET_SKYSTONE_BLUE:
                    getSkystoneBlue();
                    break;
                case MOVE_FOUNDATION_RED:
                    moveFoundationRed();
                    break;
                case MOVE_FOUNDATION_BLUE:
                    moveFoundationBlue();
                    break;
                case PARK_RED:
                    break;
                case PARK_BLUE:
                    break;
                case END:
                    break;
                default:
                    break;
            }

            telemetry.update();
        }
    }

    public void getSkystoneBlue() {
        rotate(90);
        stop();
    }

    public void moveFoundationRed() {
        rotate(90);
    }

    public void moveFoundationBlue() {
        rotate(90);
    }

    public void checkPosition() {
        for (VuforiaTrackable trackable : allTrackables) {
            boolean temp = ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible();  //

            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable
                    .getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastLocation = robotLocationTransform;
            }
            switch (trackable.getName()) {
                case "RedAllianceWallFront":
                    checkPos = AutonomousState.GET_SKYSTONE_RED;
                case "BlueAllianceWallFront":
                    checkPos = AutonomousState.GET_SKYSTONE_BLUE;
                case "RedAllianceWallRear":
                    checkPos = AutonomousState.MOVE_FOUNDATION_RED;
                case "BlueAllianceWallRear":
                    checkPos = AutonomousState.MOVE_FOUNDATION_BLUE;
            }
        }
    }

    private String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
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

        while(robot.leftDrive.isBusy() && opModeIsActive()){ }

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.leftDriveFront.setPower(0);
        robot.rightDriveFront.setPower(0);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void pidDriveWithEncoders (int counts) {
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