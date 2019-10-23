package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.HardwareDummybot;

@TeleOp()
public class AutonomousRyan extends AutonomousOpMode
{
    AutonomousState checkPos = AutonomousState.CHECK_POSITION;

    @Override
    public void runOpMode()
    {

        while (opModeIsActive())
        {
            switch (checkPos)
            {
                case CHECK_POSITION:
                    break;
                case GET_SKYSTONE_RED:
                    getSkystoneRed();
                    break;
                case GET_SKYSTONE_BLUE:
                    break;
                case MOVE_FOUNDATION_RED:
                    break;
                case MOVE_FOUNDATION_BLUE:
                    break;
                case PARK_RED:
                    break;
                case PARK_BLUE:
                    break;
                case END:
                    break;
                default:
                    break;
            }
        }
    }

    public void getSkystoneRed(){
        rotate(90, 1);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1000); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(-90, 1);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1000); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        robot.leftClaw.setPosition(1);
        robot.rightClaw.setPosition(0);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(200); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        robot.leftClaw.setPosition(0);
        robot.rightClaw.setPosition(1);

        robot.leftDrive.setPower(-1);
        robot.rightDrive.setPower(-1);
        sleep(200); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(90, 1);

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1500); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        rotate(90, 1);

        //robot.backHook?.setPosition(1); TODO: find actual variable for this etc.

        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(800); //TODO: Adjust time to drive correct distance.
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        //robot.backHook?.setPosition(0); TODO: find actual variable for this etc.

        rotate(180, 1);

        robot.leftClaw.setPosition(1);
        robot.rightClaw.setPosition(0);

        rotate(-90, 1);
    }
}
