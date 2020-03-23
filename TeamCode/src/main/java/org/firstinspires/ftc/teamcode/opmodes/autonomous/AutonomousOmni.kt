package org.firstinspires.ftc.teamcode.opmodes.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.DcMotor


@Autonomous(name = "Autonomous", group = "SMHSBots")
class AutonomousOmni : AutonomousOpMode() {
    // pidPower and straightPower determine how fast the robot will go by default.
    // pidPower == speed while rotating
    // straightPower == speed while going straight
    override val pidPower = 0.3
    private val straightPower = 0.5

    /*
    This is a parameter for encoder distance recognition. It helps us calculate how far the bot
    has traveled. To calculate, we must find the number of encoder ticks in a revolution of the motor.
    */
    override val countsPerRev = 1120.0

    /*
    This is a parameter for encoder distance recognition. It helps us calculate how far the bot
    has traveled. To determine this, we must find the angular multiplier which the gears apply from
    the motor to the wheel.
    TODO: Determine gearRatio.
     */
    override val gearRatio: Double = 2.0

    // This is the diameter of the wheel. It helps us calculate distance traveled.
    override val wheelDiameter = 4

    override fun runOpMode() {
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