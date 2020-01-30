package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot;

@TeleOp(name = "BigCummyBoy", group = "Dummybot")
public class Test extends LinearOpMode
{
    public static final double CLAW_SPEED = 0.05;
    HardwareSkybot robot = new HardwareSkybot();

    public double upPosLeft, upPosRight;

    @Override
    public void runOpMode()
    {
        double left;
        double right;


        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive())
        {

            if(gamepad1.a){
            }
            telemetry.update();

            sleep(25);
        }

    }

}
