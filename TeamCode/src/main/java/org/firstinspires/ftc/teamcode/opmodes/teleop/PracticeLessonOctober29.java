package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

public class PracticeLessonOctober29 extends LinearOpMode {
    HardwareDummybot robot = new HardwareDummybot();
    double left;
    double right;
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()){
            left = gamepad1.left_stick_y;
            right = gamepad2.right_stick_y;
            robot.leftDrive.setPower(Range.clip(left,-1,1));
            robot.rightDrive.setPower(Range.clip(right,-1,1));
        }
    }
}
