package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.HardwarePushbot;

@TeleOp(name = "Kamikaze.TeleOp.SMHS", group = "MateoBotics")

<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/teleop/Kamikaze.java
public class Kamikaze extends LinearOpMode {
=======
public class KamikazeTele extends LinearOpMode {
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/teleop/KamikazeTele.java
    HardwarePushbot bot = new HardwarePushbot();

    // Telemetric Operation: Manual, remote-controlled operations including movement
    // and manipulation of blocks.
    @Override
    public void runOpMode() {
        double leftMotor;
        double rightMotor;
        boolean lifted = false;

        // Initialize our robot with the hardware map
        bot.init(hardwareMap);

        // Wait for robot to start...
        waitForStart();

        // Repeat while our robot's TeleOp mode is active
        while (opModeIsActive()) {
            // Find values of left and right joysticks and find relevant power
            leftMotor = Range.clip(-gamepad1.left_stick_y, -1.0, 1.0);
            rightMotor = Range.clip(-gamepad1.right_stick_y, -1.0, 1.0);

            // Find values of needed bumper based on lifted bool
            if (lifted) {
                if (gamepad1.left_bumper) {
                    bot.linSlide.setPower(1);
                } else {
                    bot.linSlide.setPower(0);
                }

            } else {
                if (gamepad1.right_bumper) {
                    bot.linSlide.setPower(-1);
                    lifted = true;
                } else {
                    bot.linSlide.setPower(0);
                }
            }

            // Find values of needed bumper based on lifted bool
            if (lifted) {
                if (gamepad1.left_bumper) {
                    bot.linSlide.setPower(1);
                }
                else {
                    bot.linSlide.setPower(0);
                }

            }
            else {
                if (gamepad1.right_bumper) {
                    bot.linSlide.setPower(-1);
                    lifted = true;
                }
                else {
                    bot.linSlide.setPower(0);
                }
            }

            // Set power of left and right motors
            bot.leftDrive.setPower(leftMotor);
            bot.rightDrive.setPower(rightMotor);

            // Set telemetry data for debugging purposes
            telemetry.addData("Left power:", leftMotor);
            telemetry.addData("Right power:", rightMotor);
            telemetry.addData("Left motor position:", bot.leftDrive.getCurrentPosition());
            telemetry.addData("Right motor position:", bot.rightDrive.getCurrentPosition());
            telemetry.update();

            // Sleep for 25 ms to keep robot from overheating...
            sleep(25);
        }
    }
}
