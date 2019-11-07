package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;
import org.firstinspires.ftc.teamcode.hardware.HardwarePushbot;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "uwu", group = "yes")
public class AutonomousRyan extends AutonomousOpMode
{

    public static final String TAG = "Vuforia Navigation Sample";

    private static final String VUFORIA_KEY =
            "Af4Nrm3/////AAABmYrLO8rBw0UsgjLZZOnsZXyKCoqLXTsA+qwyN1l/pSSzkUxTt7m1CLhYYAEc7rHn" +
                    "0djvGBwSwbeujxh/oIfCA0SLCLgsSzi49untTRKe5VYYGINH7L/7YTahO4w6xKY4WRZgBOab" +
                    "Nx+w/8vGbV4gNmaKpLjpC9WD5GLeGKAOgf32oBGB6AOrKprvXX3++9A+is+G2u3Cv74ldLZp" +
                    "g7J8Sg55TBuo5N9LxvcE7zOAKvsQO+q+FWsxjm+Axfac57jd/YbgnkVidKsEiXLXRShe+xgW" +
                    "1Ma3uP9H5Xiz1HY8RbtWZtgwozIZSRJUB+8km2LqZsI/bUTQ4ysXNRUC/KrxHVThhdcllY40" +
                    "J8A260JkRcUj";



    AutonomousState checkPos = AutonomousState.CHECK_POSITION;

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);

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

        VuforiaTrackable redAllianceRear = stonesAndChips.get(5); //TODO
        redAllianceRear.setName("RedAllianceWallRear");

        VuforiaTrackable frontWallRed = stonesAndChips.get(7); //TODO
        frontWallRed.setName("FrontWallRedAlliance");

        VuforiaTrackable frontWallBlue = stonesAndChips.get(8); //TODO
        frontWallBlue.setName("FrontWallBlueAlliance");

        VuforiaTrackable rearWallRed = stonesAndChips.get(12); //TODO
        rearWallRed.setName("RearWallRedAlliance");

        VuforiaTrackable rearWallBlue = stonesAndChips.get(11); //TODO
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

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        stonesAndChips.activate();

        while (opModeIsActive())
        {
            for (VuforiaTrackable trackable : allTrackables) {
                /**
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

            switch (checkPos)
            {
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

    public void getSkystoneRed(){
        telemetry.addLine("We did it boys");
        rotate(90);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1000); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(-90);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1000); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        //robot.leftClaw.setPosition(1);
        //robot.rightClaw.setPosition(0);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(200); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        robot.leftDrive.setPower(-1);
        robot.rightDrive.setPower(-1);
        sleep(200); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(90);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1500); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(90);

        //robot.backHook?.setPosition(1); TODO: find actual variable for this etc.

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(800); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        //robot.backHook?.setPosition(0); TODO: find actual variable for this etc.

        rotate(180);

        //robot.leftClaw.setPosition(1);
        //robot.rightClaw.setPosition(0);

        rotate(-90);
    }

    public void getSkystoneBlue(){
        rotate(90);
    }

    public void moveFoundationRed(){
        rotate(90);
    }

    public void moveFoundationBlue(){
        rotate(90);
    }

   public void checkPosition(){
       for (VuforiaTrackable trackable : allTrackables) {
           boolean temp = ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible();  //

           OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable
                   .getListener()).getUpdatedRobotLocation();
           if (robotLocationTransform != null) {
               lastLocation = robotLocationTransform;
           }
           switch (trackable.getName()){
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

   public void testTurn(){
        rotate(90);
   }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}
