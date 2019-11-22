package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;
import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot;

@TeleOp(name = "CummyBoy", group = "Dummybot")
public class WannaSmashBro extends LinearOpMode
{
    public static final double CLAW_SPEED = 0.05;
    HardwareSkybot robot = new HardwareSkybot();

    @Override
    public void runOpMode()
    {
        double left;
        double right;


        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive())
        {
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            left = Range.clip(drive + turn, -1.0, 1.0) * 0.3;
            right = Range.clip(drive - turn, -1.0, 1.0  ) * 0.32;

            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(right);

            if (gamepad2.right_bumper) {
                robot.rightClaw.setPosition(1.0);
                robot.leftClaw.setPosition(0.0);
            }
            else if (gamepad2.left_bumper) {
                robot.rightClaw.setPosition(0.0);
                robot.leftClaw.setPosition(1.0);
            }
            else if (gamepad2.right_trigger > 0) {
                robot.rightClaw.setPosition(Range.clip(robot.rightClaw.getPosition()
                        + (gamepad2.right_trigger * CLAW_SPEED), 0, 1));
                robot.leftClaw.setPosition(Range.clip(robot.leftClaw.getPosition()
                        - (gamepad2.right_trigger * CLAW_SPEED), 0, 1));
            }
            else if (gamepad2.left_trigger > 0) {
                robot.rightClaw.setPosition(Range.clip(robot.rightClaw.getPosition()
                        - (gamepad2.left_trigger * CLAW_SPEED), 0, 1));
                robot.leftClaw.setPosition(Range.clip(robot.leftClaw.getPosition()
                        + (gamepad2.left_trigger * CLAW_SPEED), 0, 1));
            }

            if (gamepad2.dpad_down) {
                robot.linSlide.setPower(-1);
            } else if (gamepad2.dpad_up) {
                robot.linSlide.setPower(1);
            } else {
                robot.linSlide.setPower(0);
            }

            if(gamepad2.a){
                robot.hook.setPosition(0.5);
            }else if(gamepad2.b){
                robot.hook.setPosition(0);
            }

            telemetry.addData("left", left);
            telemetry.addData("right", right);
            telemetry.addData("Right Servo Position", robot.rightClaw.getPosition());
            telemetry.addData("Left Servo Position", robot.leftClaw.getPosition());
            telemetry.update();
            sleep(25);
        }

    }

}
