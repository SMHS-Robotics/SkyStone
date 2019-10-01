package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

@TeleOp(name = "CommyBoy", group = "Dummybot")
public class NotGonnaSmashBro extends LinearOpMode
{
    double left;
    double right;

    HardwareDummybot robot = new HardwareDummybot();
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()){
            left = Range.clip( gamepad1.left_stick_y, -1, 1);

            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(Range.clip( gamepad1.right_stick_y, -1, 1));
            sleep(25);
        }
    }
}
