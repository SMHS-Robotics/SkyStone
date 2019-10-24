package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import java.util.*;

import android.util.SparseArray;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


@Autonomous(name = "Blitzkrieg.Auto.SMHS", group = "MateoBotics")
public class BlitzkriegAuto extends AutonomousOpMode {

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final String VUFORIA_KEY =
            "Af4Nrm3/////AAABmYrLO8rBw0UsgjLZZOnsZXyKCoqLXTsA+qwyN1l/pSSzkUxTt7m1CLhYYAEc7rHn" +
                    "0djvGBwSwbeujxh/oIfCA0SLCLgsSzi49untTRKe5VYYGINH7L/7YTahO4w6xKY4WRZgBOab" +
                    "Nx+w/8vGbV4gNmaKpLjpC9WD5GLeGKAOgf32oBGB6AOrKprvXX3++9A+is+G2u3Cv74ldLZp" +
                    "g7J8Sg55TBuo5N9LxvcE7zOAKvsQO+q+FWsxjm+Axfac57jd/YbgnkVidKsEiXLXRShe+xgW" +
                    "1Ma3uP9H5Xiz1HY8RbtWZtgwozIZSRJUB+8km2LqZsI/bUTQ4ysXNRUC/KrxHVThhdcllY40" +
                    "J8A260JkRcUj";

    BNO055IMU imu;
    AutonomousState state = AutonomousState.CHECK_POSITION;
    HardwareDummybot robot = new HardwareDummybot();

    //0 == Adjacent to Red Skystones
    //1 == Adjacent to Red Foundation
    //2 == Adjacent to Blue Skystones
    //3 == Adjacent to Blue Foundation
    int position = 0;

    private VuforiaLocalizer vuforia = null;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        waitForStart();

        while (opModeIsActive()) {
            switch (state) {
                case MOVE_FOUNDATION_RED:
                    redFoundation();
                    break;
                case MOVE_FOUNDATION_BLUE:
                    blueFoundation();
                    break;
                case GET_SKYSTONE_RED:
                    getRed();
                    break;
                case GET_SKYSTONE_BLUE:
                    getBlue();
                    break;
                default:
                    checkPosition();
                    break;
            }
        }
    }

    private void checkPosition() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("camera" +
                "MonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        params.vuforiaLicenseKey = VUFORIA_KEY;
        params.cameraDirection = CAMERA_CHOICE;

        vuforia = ClassFactory.getInstance().createVuforia(params);

        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
        List<VuforiaTrackable> trackables = new ArrayList<VuforiaTrackable>();
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        trackables.add(red1);
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        trackables.add(red2);
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        trackables.add(blue1);
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        trackables.add(blue2);

        Map<VuforiaTrackable, Integer> targetToInt = new HashMap<VuforiaTrackable, Integer>();
        targetToInt.put(red1, 0);
        targetToInt.put(red2, 1);
        targetToInt.put(blue1, 2);
        targetToInt.put(blue2, 3);
        boolean cond = true;

        OpenGLMatrix robotFromCamera = OpenGLMatrix.rotation(EXTRINSIC, YZX, DEGREES, -90, 0, 90);
        for (VuforiaTrackable trackable : trackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, params.cameraDirection);
        }
        targetsSkyStone.activate();
        while (cond) {
            for (VuforiaTrackable trackable : trackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    position = targetToInt.get(trackable);
                    cond = false;
                }
            }
        }
        SparseArray<AutonomousState> positionToState = new SparseArray<>();
        positionToState.put(0, AutonomousState.MOVE_FOUNDATION_RED);
        positionToState.put(1, AutonomousState.GET_SKYSTONE_RED);
        positionToState.put(2, AutonomousState.MOVE_FOUNDATION_BLUE);
        positionToState.put(3, AutonomousState.GET_SKYSTONE_BLUE);

        state = positionToState.get(position);
    }

    private void getRed() {

    }

    private void getBlue() {

    }

    private void redFoundation() {

    }

    private void blueFoundation() {

    }

}