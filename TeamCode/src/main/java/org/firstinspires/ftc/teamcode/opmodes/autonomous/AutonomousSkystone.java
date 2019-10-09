package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;
import org.firstinspires.ftc.teamcode.opmodes.teleop.VuforiaTest;
import org.firstinspires.ftc.teamcode.utilities.PIDController;

@Autonomous(name = "CummyBoyAuto", group = "DummyBot")
public class AutonomousSkystone extends LinearOpMode
{
    final double KP = 2.5; //TODO: Replace placeholder values
    final double KI = 0.1;
    final double KD = 0.2;

    BNO055IMU imu;
    AutonomousState state = AutonomousState.CHECK_POSITION;
    PIDController pid = new PIDController(KP, KI, KD);
    HardwareDummybot robot = new HardwareDummybot();
    VuforiaTest vuforia = new VuforiaTest();

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
            switch(state) {
                case CHECK_POSITION:
                    checkPosition();
                    break;
                case GET_SKYSTONE_RED:
                    getRed();
                    break;
                case GET_SKYSTONE_BLUE:
                    getBlue();
                    break;
            }
        }
    }

    public void checkPosition() {

    }

    public void getRed() {}

    public void getBlue() {}


}
