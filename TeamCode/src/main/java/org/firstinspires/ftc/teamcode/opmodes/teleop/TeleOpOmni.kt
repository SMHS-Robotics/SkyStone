package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.Range

import org.firstinspires.ftc.teamcode.hardware.HardwareSkybot
import kotlin.math.abs

@TeleOp(name = "TeleOp", group = "SMHSBots")
class TeleOpOmni : LinearOpMode() {
    private val robot = HardwareSkybot()

    override fun runOpMode() {
        var leftF: Double
        var rightF: Double
        var leftB: Double
        var rightB: Double
        var rampPow = 0.0

        robot.init(hardwareMap)
        waitForStart()

        while (opModeIsActive()) {
            val drive = (-gamepad1.left_stick_y).toDouble()
            val shift = gamepad1.left_stick_x.toDouble()
            val turn = gamepad1.right_stick_x.toDouble()
            leftF = Range.clip(drive - shift - turn, -1.0, 1.0) * 0.7
            rightF = Range.clip(drive + shift + turn, -1.0, 1.0) * 0.7
            leftB = Range.clip(drive - shift + turn, -1.0, 1.0) * 0.7
            rightB = Range.clip(drive + shift - turn, -1.0, 1.0) * 0.7

            rampPow = when (abs(leftF) + abs(leftB) + abs(rightB) + abs(rightF) > 1) {
                true -> Range.clip(rampPow + 0.1, 0.0, 1.0)
                false -> Range.clip(rampPow - 0.1, 0.0, 1.0)
            }

            if (gamepad1.left_stick_button) {
                //Faster: range of -0.7 to 0.7
                robot.leftDrive!!.power = leftB
                robot.leftDriveFront!!.power = leftF
                robot.rightDrive!!.power = rightB
                robot.rightDriveFront!!.power = rightF
            } else {
                //Slower: range of -0.35 to 0.35
                robot.leftDrive!!.power = leftB / 2
                robot.leftDriveFront!!.power = leftF / 2
                robot.rightDrive!!.power = rightB / 2
                robot.rightDriveFront!!.power = rightF / 2
            }

            if (gamepad1.a) {
                robot.leftHook!!.position = 0.0
                robot.rightHook!!.position = 1.0
                telemetry.addLine("your call is very important to us!")
            } else if (gamepad1.b) {
                robot.leftHook!!.position = 0.5
                robot.rightHook!!.position = 0.5
                telemetry.addLine("is this Patrick!")
            }

            when {
                gamepad1.right_bumper -> {
                    robot.rightHook!!.position = 1.0
                    robot.leftHook!!.position = 0.0
                    telemetry.addLine("it opened!")
                }
                gamepad1.left_bumper -> {
                    robot.rightHook!!.position = 0.5
                    robot.leftHook!!.position = 0.5
                    telemetry.addLine("it closed!")
                }
                gamepad1.right_trigger > 0 -> {
                    robot.rightHook!!.position = Range.clip(robot.rightHook!!.position + gamepad1.right_trigger * CLAW_SPEED, 0.0, 1.0)
                    robot.leftHook!!.position = Range.clip(robot.leftHook!!.position - gamepad1.right_trigger * CLAW_SPEED, 0.0, 1.0)
                }
                gamepad1.left_trigger > 0 -> {
                    robot.rightHook!!.position = Range.clip(robot.rightHook!!.position - gamepad2.left_trigger * CLAW_SPEED, 0.0, 1.0)
                    robot.leftHook!!.position = Range.clip(robot.leftHook!!.position + gamepad2.left_trigger * CLAW_SPEED, 0.0, 1.0)
                }
            } //closes

            telemetry.addData("Claw Pos:", robot.leftClaw!!.position)
            telemetry.addData("Hook Pos:", robot.leftHook!!.position)
            telemetry.addData("R Hook Pos", robot.rightHook!!.position)
            telemetry.update()

            sleep(25)
        }

    }

    companion object {
        const val CLAW_SPEED = 0.05
    }

}
