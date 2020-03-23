package org.firstinspires.ftc.teamcode.opmodes.autonomous

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.Range

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Orientation
import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot
import org.firstinspires.ftc.teamcode.utilities.PIDController
import java.util.Locale
import kotlin.math.*

abstract class AutonomousOpKotlin : LinearOpMode() {
    private val power = 0.3
    private var rotation = 0.0
    private var globalAngle = 0.0

    private var correction = 0.0
    private val maxErrorRotate = 90.0

    private val targetSpeedMaxRotate = 1.0
    private val baseR = targetSpeedMaxRotate / maxErrorRotate

    private val kdRotate = baseR * 20
    private val kpRotate = baseR
    private val kiRotate = baseR / 125

    private val kdStraight = 0.0
    private val kpStraight = 0.05
    private val kiStraight = 0.0

    private var pidRotate: PIDController? = null
    private var pidDrive: PIDController? = null

    private var robot = HardwareSkybot()

    private var lastAngles = Orientation()

    override fun runOpMode() {
        robot.init(hardwareMap)
        pidRotate = PIDController(kpRotate, kiRotate, kdRotate)
        pidDrive = PIDController(.05, 0.0, 0.0)
        pidDrive?.setpoint = 0.0
        pidDrive?.setOutputRange(0.0, power)
        pidDrive?.setInputRange(-90.0, 90.0)
        pidDrive?.enable()
        resetAngle()
    }

    private fun getAngle(): Double {
        val angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
        var deltaAngle = (angles.firstAngle - lastAngles.firstAngle).toDouble()

        if (deltaAngle < -180)
            deltaAngle += 360.0
        else if (deltaAngle > 180)
            deltaAngle -= 360.0

        globalAngle += deltaAngle
        lastAngles = angles
        return globalAngle
    }

    private fun resetAngle() {
        lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
        globalAngle = 0.0
    }

    private fun formatAngle(angleUnit: AngleUnit, angle: Double): String {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle))
    }

    private fun formatDegrees(degrees: Double): String {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees))
    }

    private fun rotate(degrees: Double) {
        var degrees = degrees
        var rpower = power
        degrees = -degrees
        val turnTolerance = 0.1

        robot.resetAngle()
        robot.leftDrive.power = 0.0
        robot.rightDrive.power = 0.0
        robot.leftDriveFront.power = 0.0
        robot.rightDriveFront.power = 0.0

        if (abs(degrees) > 359) degrees = 359.0.withSign(degrees).toInt().toDouble()

        pidRotate!!.reset()
        pidRotate!!.setpoint = degrees
        pidRotate!!.setInputRange(0.0, degrees + 0.1)
        pidRotate!!.setOutputRange(0.0, targetSpeedMaxRotate / 4)
        pidRotate!!.setTolerance(turnTolerance)
        pidRotate!!.enable()
        telemetry.addLine("About to Rotate")
        telemetry.update()

        if (degrees < 0) {
            while (opModeIsActive() && getAngle() == 0.0) {
                robot.leftDrive.power = -rpower
                robot.rightDrive.power = rpower
                robot.leftDriveFront.power = rpower
                robot.rightDriveFront.power = -rpower
                telemetry.addLine("About to rotate right")
                telemetry.update()
                sleep(100)
            }
            do {
                telemetry.addLine("Rotating Right")
                telemetry.update()
                rpower = pidRotate!!.performPID(getAngle()) // power will be - on right turn.
                robot.leftDrive.power = -rpower
                robot.rightDrive.power = rpower
                robot.leftDriveFront.power = rpower
                robot.rightDriveFront.power = -rpower
            } while (opModeIsActive() && !pidRotate.onTarget())
        } else {   // left turn.
            do {
                rpower = pidRotate!!.performPID(getAngle()) // power will be + on left turn.
                robot.leftDrive.power = -power
                robot.rightDrive.power = power
                robot.leftDriveFront.power = power
                robot.rightDriveFront.power = -power
                telemetry.addLine("Updating")
                telemetry.addData("Degrees: ", degrees)
                telemetry.update()
            } while (opModeIsActive() && !pidRotate.onTarget())
        }

        // turn the motors off.
        robot.rightDrive.power = 0.0
        robot.leftDrive.power = 0.0
        robot.leftDriveFront.power = 0.0
        robot.rightDriveFront.power = 0.0

        rotation = getAngle()

        // wait for rotation to stop.
        sleep(500)

        // reset angle tracking on new heading.
        resetAngle()

        telemetry.addLine("Done")
        telemetry.update()
    }

    fun pidDriveWithEncoders(counts: Int, power: Double) {
        robot.leftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        robot.leftDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER

        robot.leftDrive.targetPosition = counts
        telemetry.addData("Target Count: ", counts)
        telemetry.update()

        val spower = if (counts > 0) power else -power

        robot.leftDrive.power = spower
        robot.rightDrive.power = spower
        robot.leftDriveFront.power = spower
        robot.rightDriveFront.power = spower

        while (abs(robot.leftDrive.currentPosition) < abs(counts) && opModeIsActive()) {
            correction = pidDrive!!.performPID(getAngle())
            robot.leftDrive.power = spower - correction
            robot.rightDrive.power = spower + correction
            telemetry.addData("Current Count: ", robot.leftDrive.currentPosition)
            telemetry.update()
        }

        robot.leftDrive.power = 0.0
        robot.rightDrive.power = 0.0
        robot.rightDriveFront.power = 0.0
        robot.leftDriveFront.power = 0.0


        robot.leftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
}