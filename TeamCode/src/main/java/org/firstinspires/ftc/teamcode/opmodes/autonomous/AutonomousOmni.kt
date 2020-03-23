package org.firstinspires.ftc.teamcode.opmodes.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.DcMotor


@Autonomous(name = "MoveFoundation", group = "SMHSBots")
class AutonomousOmni : AutonomousOpMode() {
    // pidPower and straightPower determine how fast the robot will go by default.
    private val pidPower = 0.3
    private val straightPower = 0.5

    /* This is the parameter for encoder distance recognition. It helps us calculate how far the bot
     has traveled. To calculate, we must find the number of encoder ticks in a revolution of the wheel.
     TODO: Determine countsFactor through experimentation. */
    private val countsFactor: Double = 28.0

    // Diameter is the diameter of the wheel. It helps us calculate distance traveled.
    private val diameter = 4

    override fun runOpMode() {
        super.power = pidPower
        super.wheelDiameter = diameter
        super.countsPerRev = countsFactor
        super.runOpMode()

        waitForStart()

        while (opModeIsActive()) {
            telemetry.addLine("AutoOp is working!")
            telemetry.update()
            rotate(90.0)
            pidDriveWithEncoders(inchToCounts(24.0), straightPower)
            stop()
        }
    }
}