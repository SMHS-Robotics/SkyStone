package org.firstinspires.ftc.teamcode.opmodes.autonomous

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot
import org.firstinspires.ftc.teamcode.utilities.*
import kotlin.math.*

abstract class AutonomousOpMode : LinearOpMode() {
    abstract val pidPower: Double

    abstract val countsPerRev: Double
    abstract val wheelDiameter: Int
    abstract val gearRatio: Double

    private var rotation = 0.0

    private var correction = 0.0

    companion object {
        private const val maxErrorRotate = 90.0

        private const val targetSpeedMaxRotate = 1.0
        private const val baseR = targetSpeedMaxRotate / maxErrorRotate

        private const val kdRotate = baseR * 20
        private const val kpRotate = baseR
        private const val kiRotate = baseR / 125

        private const val kdStraight = 0.0
        private const val kpStraight = 0.05
        private const val kiStraight = 0.0
    }

    private var pidRotate: PIDController = PIDController(kpRotate, kiRotate, kdRotate)
    private var pidDrive: PIDController = PIDController(kpStraight, kiStraight, kdStraight)

    @JvmField
    val robot: HardwareSkybot = HardwareSkybot()

    override fun runOpMode() {
        robot.init(hardwareMap)
        pidRotate = PIDController(kpRotate, kiRotate, kdRotate)
        pidDrive = PIDController(kpStraight, kiStraight, kdStraight)
        pidDrive.setpoint = 0.0
        pidDrive.setOutputRange(0.0, pidPower)
        pidDrive.setInputRange(-90.0, 90.0)
        pidDrive.enable()
        robot.resetAngle()
    }

    fun inchToCounts(inches: Double): Double {
        val revs = (inches * gearRatio) / (wheelDiameter * PI)
        return revs * countsPerRev
    }

    fun rotate(deg: Double) {
        var degrees = -deg
        var rpower: Double
        val turnTolerance = 0.1

        robot.resetAngle()
        robot.stopAllMotors()

        if (abs(degrees) > 359) degrees = 359.0.withSign(degrees).toInt().toDouble()

        pidRotate.reset()
        pidRotate.setpoint = degrees
        pidRotate.setInputRange(0.0, degrees + turnTolerance.withSign(degrees))
        pidRotate.setOutputRange(0.0, targetSpeedMaxRotate / 4)
        pidRotate.setTolerance(turnTolerance)
        pidRotate.enable()
        telemetry.addLine("About to Rotate")
        telemetry.update()

        if (degrees < 0) {
            do {
                telemetry.addLine("Rotating Right")
                telemetry.update()
                rpower = pidRotate.performPID(robot.angle) // power will be - on right turn.
                robot.leftDrive!!.power = -rpower
                robot.rightDrive!!.power = rpower
                robot.leftDriveFront!!.power = rpower
                robot.rightDriveFront!!.power = -rpower
                telemetry.addLine("Updating")
                telemetry.addData("Degrees: ", degrees)
                telemetry.update()
            } while (opModeIsActive() && !pidRotate.onTarget())
        } else {   // left turn.
            do {
                telemetry.addLine("Rotating Left")
                rpower = pidRotate.performPID(robot.angle) // power will be + on left turn.
                robot.leftDrive!!.power = -rpower
                robot.rightDrive!!.power = rpower
                robot.leftDriveFront!!.power = rpower
                robot.rightDriveFront!!.power = -rpower
                telemetry.addLine("Updating")
                telemetry.addData("Degrees: ", degrees)
                telemetry.update()
            } while (opModeIsActive() && !pidRotate.onTarget())
        }

        // turn the motors off.
        robot.stopAllMotors()

        rotation = robot.angle

        // wait for rotation to stop.
        sleep(500)

        // reset angle tracking on new heading.
        robot.resetAngle()

        telemetry.addLine("Done")
        telemetry.update()
    }

    fun pidDriveWithEncoders(inches: Double, power: Double) {
        robot.leftDrive!!.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        robot.leftDrive!!.mode = DcMotor.RunMode.RUN_USING_ENCODER

        val counts = inchToCounts(inches)

        robot.leftDrive!!.targetPosition = counts.roundToInt()
        telemetry.addData("Target Count: ", counts)
        telemetry.update()

        val spower = if (counts > 0) power else -power

        robot.runAllMotors(spower)

        while (abs(robot.leftDrive!!.currentPosition) < abs(counts) && opModeIsActive()) {
            correction = pidDrive.performPID(robot.angle)
            robot.leftDrive!!.power = spower - correction
            robot.rightDrive!!.power = spower + correction
            telemetry.addData("Current Count: ", robot.leftDrive!!.currentPosition)
            telemetry.update()
        }

        robot.stopAllMotors()

        robot.leftDrive!!.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    fun driveWithIntegration(inches: Double, power: Double) {
        robot.stopAllMotors()
        val integrator = NaiveAccelerationIntegrator()
        integrator.initialize(robot.imu!!.parameters, null, null)
        var curDistance: Double
        robot.runAllMotors(power)
        do {
            sleep(20)
            integrator.update(robot.acceleration)
            curDistance = sqrt(integrator.position.toUnit(DistanceUnit.INCH).y.pow(2) + integrator.position.toUnit(DistanceUnit.INCH).x.pow(2))
        } while (curDistance < inches)
        robot.stopAllMotors()
    }
}