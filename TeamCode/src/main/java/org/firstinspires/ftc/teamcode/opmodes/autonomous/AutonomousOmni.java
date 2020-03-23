package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "MoveFoundation", group = "SMHSBots")
public class AutonomousOmni extends AutonomousOpMode {
    private double power = 0.3;

    @Override
    public void runOpMode() {
        super.power = power;
        super.runOpMode();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("AutoOp is working!");
            telemetry.update();
            stop();
        }
    }
}