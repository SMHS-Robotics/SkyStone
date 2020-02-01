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

    public double upPosLeft, upPosRight;

    @Override
    public void runOpMode()
    {
        double leftF;
        double rightF;
        double leftB;
        double rightB;
        double rampPow = 0;

        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive())
        {


            double drive = -gamepad1.left_stick_y;
            double shift = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            leftF = Range.clip(drive - shift - turn, -1.0, 1.0) * 0.4;
            rightF = Range.clip(drive + shift + turn, -1.0, 1.0  ) * 0.4;
            leftB = Range.clip(drive - shift + turn, -1.0, 1.0  ) * 0.4;
            rightB = Range.clip(drive + shift - turn, -1.0, 1.0) * 0.4;

            if(Math.abs(leftF) + Math.abs(leftB) + Math.abs(rightB) + Math.abs(rightF) > 1){
                rampPow = Range.clip(rampPow + 0.1, 0, 1);
            }else{
                rampPow = Range.clip(rampPow - 0.1, 0, 1);
            }

            robot.leftDrive.setPower(leftB);
            robot.leftDriveFront.setPower(leftF);
            robot.rightDrive.setPower(rightB);
            robot.rightDriveFront.setPower(rightF);

            /*if(gamepad1.dpad_left){
                robot.leftDrive.setPower(1);
                robot.rightDrive.setPower(-1);
                robot.leftDriveFront.setPower(-1);
                robot.rightDriveFront.setPower(1);
            } else if(gamepad1.dpad_right){
                robot.leftDrive.setPower(-1);
                robot.rightDrive.setPower(1);
                robot.leftDriveFront.setPower(1);
                robot.rightDriveFront.setPower(-1);
            } else {
                robot.leftDrive.setPower(0);
                robot.rightDrive.setPower(0);
                robot.leftDriveFront.setPower(0);
                robot.rightDriveFront.setPower(0);
            }*/

            //opens
            if (gamepad2.right_bumper) {
                //robot.rightClaw.setPosition(0.6);
                robot.leftClaw.setPosition(0.4);
                telemetry.addLine("it opened?");
            }
            //closes
            else if (gamepad2.left_bumper) {
                //robot.rightClaw.setPosition(0.2);
                robot.leftClaw.setPosition(0.8);
                telemetry.addLine("it closed?");
            }
            else if (gamepad2.right_trigger > 0) {
                //robot.rightClaw.setPosition(Range.clip(robot.rightClaw.getPosition()
                        //+ (gamepad2.right_trigger * CLAW_SPEED), 0, 1));
                robot.leftClaw.setPosition(Range.clip(robot.leftClaw.getPosition()
                        - (gamepad2.right_trigger * CLAW_SPEED), 0, 1));
            }
            else if (gamepad2.left_trigger > 0) {
                //robot.rightClaw.setPosition(Range.clip(robot.rightClaw.getPosition()
                       // - (gamepad2.left_trigger * CLAW_SPEED), 0, 1));
                robot.leftClaw.setPosition(Range.clip(robot.leftClaw.getPosition()
                        + (gamepad2.left_trigger * CLAW_SPEED), 0, 1));
            }

            /*if (gamepad2.dpad_down) {
                robot.linSlide.setPower(-1);
            } else if (gamepad2.dpad_up) {
                robot.linSlide.setPower(1);
            } else {
                robot.linSlide.setPower(0);
            }*/

            if(gamepad1.a){
                robot.leftHook.setPosition(0);
                robot.rightHook.setPosition(1);
                telemetry.addLine("you call is very important to us.");
            }else if(gamepad1.b){
                robot.leftHook.setPosition(0.5);
                robot.rightHook.setPosition(0.5);
                telemetry.addLine("is this Patrick?");
            }

            telemetry.addData("Claw Pos:", robot.leftClaw.getPosition());
            telemetry.addData("Hook Pos:", robot.leftHook.getPosition());
            telemetry.addData("R Hook Pos", robot.rightHook.getPosition());
            telemetry.update();

            sleep(25);
        }

    }

}
