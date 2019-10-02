package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

@TeleOp(name = "CummyBoy", group = "Dummybot")

public class WannaSmashBro extends LinearOpMode
{
    public static final double CLAW_SPEED = 0.05;
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
            linear = Range.clip(-gamepad2.left_stick_y, -1.0, 1.0);

            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(right);
            robot.linear_drive.setPower(linear);

            if (gamepad2.right_bumper) {
                robot.rightClaw.setPosition(1.0);
                robot.leftClaw.setPosition(1.0);
            }
            else if (gamepad2.left_bumper) {
                robot.rightClaw.setPosition(0.0);
                robot.leftClaw.setPosition(0.0);
            }

            telemetry.addData("left", left);
            telemetry.addData("right", right);
            telemetry.addData("linear tower", linear);
            telemetry.addData("Current Position", robot.linear_drive.getCurrentPosition());
            telemetry.update();
            sleep(25);
        }

    }

}
