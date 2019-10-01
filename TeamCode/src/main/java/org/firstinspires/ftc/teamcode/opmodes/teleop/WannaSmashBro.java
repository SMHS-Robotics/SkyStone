package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

@TeleOp(name = "CummyBoy", group = "Dummybot")

public class WannaSmashBro extends LinearOpMode
{
    HardwareDummybot robot = new HardwareDummybot();

    @Override
    public void runOpMode()
    {
        double left;
        double right;
        double linear;

        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive())
        {
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            left = Range.clip(drive + turn, -1.0, 1.0);
            right = Range.clip(drive - turn, -1.0, 1.0);
            linear = Range.clip(-gamepad2.left_stick_y, -1, 1);

            telemetry.addData("left", left);
            telemetry.addData("right", right);
            telemetry.addData("linear tower", linear);
            telemetry.addData("Current Position", robot.linear_drive.getCurrentPosition());
            telemetry.update();

            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(right);
            robot.linear_drive.setPower(linear);
            sleep(10);
        }

    }

}
