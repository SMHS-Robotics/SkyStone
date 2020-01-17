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
                robot.linSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.linSlide.setTargetPosition(1000);
                robot.linSlide.setPower(1);
                while(robot.linSlide.isBusy() && opModeIsActive()){};
                robot.linSlide.setPower(0);
                robot.linSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            telemetry.addData("Motor Position", robot.rightClaw.getPosition());
            telemetry.update();

            sleep(25);
        }

    }

}
