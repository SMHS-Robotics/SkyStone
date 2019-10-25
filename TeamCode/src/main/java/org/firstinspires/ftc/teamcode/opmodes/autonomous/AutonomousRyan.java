package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

import java.util.ArrayList;
import java.util.List;

@TeleOp()
public class AutonomousRyan extends AutonomousOpMode
{

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

    @Override
    public void runOpMode()
    {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(
                cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("Skystone");
        VuforiaTrackable redTarget = stonesAndChips.get(0);
        redTarget.setName("RedTarget");  // Stones

        VuforiaTrackable blueTarget = stonesAndChips.get(1);
        blueTarget.setName("BlueTarget");  // Chips

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(stonesAndChips);

        while (opModeIsActive())
        {
            switch (checkPos)
            {
                case CHECK_POSITION:
                    break;
                case GET_SKYSTONE_RED:
                    getSkystoneRed();
                    break;
                case GET_SKYSTONE_BLUE:
                    break;
                case MOVE_FOUNDATION_RED:
                    break;
                case MOVE_FOUNDATION_BLUE:
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
        }
    }

    public void getSkystoneRed(){
        rotate(90, 1);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1000); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(-90, 1);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1000); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        robot.leftClaw.setPosition(1);
        robot.rightClaw.setPosition(0);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(200); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        robot.leftClaw.setPosition(0);
        robot.rightClaw.setPosition(1);

        robot.leftDrive.setPower(-1);
        robot.rightDrive.setPower(-1);
        sleep(200); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(90, 1);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1500); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(90, 1);

        //robot.backHook?.setPosition(1); TODO: find actual variable for this etc.

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(800); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        //robot.backHook?.setPosition(0); TODO: find actual variable for this etc.

        rotate(180, 1);

        robot.leftClaw.setPosition(1);
        robot.rightClaw.setPosition(0);

        rotate(-90, 1);
    }
}
