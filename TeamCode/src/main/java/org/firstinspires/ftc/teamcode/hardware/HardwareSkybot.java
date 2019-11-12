package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class HardwareSkybot
{
    public static final double MID_SERVO = 0.5;
    public static final String TAG = "PUSHBOT";

    /* Public OpMode members. */
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor linSlide = null;
    public Servo leftClaw = null;
    public Servo rightClaw = null;
    public BNO055IMU imu = null;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    Orientation lastAngles = new Orientation();

    //Parameters for REV Expansion Hub
    double globalAngle = 0;
    Acceleration accel;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareSkybot() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        linSlide = hwMap.get(DcMotor.class, "linear_slide");// TODO: Reinstate it when linslide is on

        // Set to FORWARD if using AndyMark motors
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        // Set to REVERSE if using AndyMark motors
        linSlide.setDirection(DcMotor.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile
                = "IMUTestCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Set all motors to zero power
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        linSlide.setPower(0.0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Tick Per Revolution: 1120
        linSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Robot
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Define and initialize ALL installed servos.
        leftClaw = hwMap.get(Servo.class, "left_claw");
        rightClaw = hwMap.get(Servo.class, "right_claw");
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);

        //Set Up Angle
        resetAngle();
    }

    public double getAngle() {
        Orientation angles = imu
                .getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        globalAngle += deltaAngle;
        lastAngles = angles;

        return globalAngle;
    }

    public void resetAngle() {
        lastAngles = imu
                .getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }

}
