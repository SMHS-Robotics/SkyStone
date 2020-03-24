package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.hardware.bosch.NaiveAccelerationIntegrator
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.*

/* Constructor */
class HardwareSkybot {

    /* Public OpMode members. */
    var leftDrive: DcMotor? = null
    var leftDriveFront: DcMotor? = null
    var rightDrive: DcMotor? = null
    var rightDriveFront: DcMotor? = null
    var leftClaw: Servo? = null
    var rightHook: Servo? = null
    var leftHook: Servo? = null

    var imu: BNO055IMU? = null

    /* local OpMode members. */
    internal var hwMap: HardwareMap? = null
    internal var lastAngles = Orientation()

    //Parameters for REV Expansion Hub
    internal var globalAngle = 0.0
    internal val acceleration: Acceleration
        get() = imu!!.acceleration
    private val period = ElapsedTime()

    val angle: Double
        get() {
            val angles = imu!!
                    .getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
            var deltaAngle = (angles.firstAngle - lastAngles.firstAngle).toDouble()

            if (deltaAngle < -180) {
                deltaAngle += 360.0
            } else if (deltaAngle > 180) {
                deltaAngle -= 360.0
            }

            globalAngle += deltaAngle
            lastAngles = angles

            return globalAngle
        }

    /* Initialize standard Hardware interfaces */
    fun init(ahwMap: HardwareMap) {
        // Save reference to Hardware map
        hwMap = ahwMap

        // Define and Initialize Motors
        leftDrive = hwMap?.get(DcMotor::class.java, "left_drive")
        rightDrive = hwMap?.get(DcMotor::class.java, "right_drive")
        leftDriveFront = hwMap?.get(DcMotor::class.java, "left_drive_front")
        rightDriveFront = hwMap?.get(DcMotor::class.java, "right_drive_front")

        leftDrive?.direction = DcMotorSimple.Direction.REVERSE
        rightDrive?.direction = DcMotorSimple.Direction.FORWARD
        leftDriveFront?.direction = DcMotorSimple.Direction.FORWARD
        rightDriveFront?.direction = DcMotorSimple.Direction.REVERSE

        val parameters = BNO055IMU.Parameters()
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.calibrationDataFile = "IMUTestCalibration.json" // see the calibration sample opmode
        parameters.loggingEnabled = true
        parameters.loggingTag = "IMU"
        parameters.accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()

        imu = hwMap?.get(BNO055IMU::class.java, "imu")
        imu?.initialize(parameters)

        // Set all motors to zero power
        leftDrive?.power = 0.0
        rightDrive?.power = 0.0
        leftDriveFront?.power = 0.0
        rightDriveFront?.power = 0.0

        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive?.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightDrive?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        leftDrive?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        rightDrive?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        //Tick Per Revolution: 1120

        //Robot
        leftDrive?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightDrive?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        leftDriveFront?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightDriveFront?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE


        // Define and initialize ALL installed servos.
        leftClaw = hwMap?.get(Servo::class.java, "left_claw")
        rightHook = hwMap?.get(Servo::class.java, "right_hook")
        leftHook = hwMap?.get(Servo::class.java, "left_hook")
        leftClaw?.position = 0.0
        leftHook?.position = 1.0
        rightHook?.position = 0.0

        //Set Up Angle
        resetAngle()
    }

    fun resetAngle() {
        lastAngles = imu!!.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
        globalAngle = 0.0
    }

    companion object {
        val MID_SERVO = 0.5
        val TAG = "PUSHBOT"
    }

    fun runAllMotors (power: Double) {
        leftDrive!!.power = power
        rightDrive!!.power = power
        rightDriveFront!!.power = power
        leftDriveFront!!.power = power
    }

    fun stopAllMotors() = runAllMotors(0.0)

    fun runMotors (leftFront: Double, rightFront: Double, leftBack: Double, rightBack: Double) {
        leftDrive!!.power = leftBack
        rightDrive!!.power = rightBack
        rightDriveFront!!.power = leftFront
        leftDriveFront!!.power = rightFront
    }
}
