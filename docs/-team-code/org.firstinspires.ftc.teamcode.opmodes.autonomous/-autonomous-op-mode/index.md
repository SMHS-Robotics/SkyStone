---
title: AutonomousOpMode -
---
//[TeamCode](../../index.md)/[org.firstinspires.ftc.teamcode.opmodes.autonomous](../index.md)/[AutonomousOpMode](index.md)



# AutonomousOpMode  
 [androidJvm] abstract class [AutonomousOpMode](index.md) : [LinearOpMode]()   


## Types  
  
|  Name|  Summary| 
|---|---|
| [Companion](-companion/index.md)| [androidJvm]  <br>Content  <br>object [Companion](-companion/index.md)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [driveWithIntegration](drive-with-integration.md)| [androidJvm]  <br>Content  <br>fun [driveWithIntegration](drive-with-integration.md)(inches: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), power: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [androidJvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [getRuntime]()| [androidJvm]  <br>Content  <br>open override fun [getRuntime]()(): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)  <br><br><br>
| [handleLoop]()| [androidJvm]  <br>Content  <br>open override fun [handleLoop]()()  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [androidJvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [idle]()| [androidJvm]  <br>Content  <br>override fun [idle]()()  <br><br><br>
| [inchToCounts](inch-to-counts.md)| [androidJvm]  <br>Content  <br>fun [inchToCounts](inch-to-counts.md)(inches: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)  <br><br><br>
| [init]()| [androidJvm]  <br>Content  <br>override fun [init]()()  <br><br><br>
| [init_loop]()| [androidJvm]  <br>Content  <br>override fun [init_loop]()()  <br><br><br>
| [internalPostInitLoop]()| [androidJvm]  <br>Content  <br>open override fun [internalPostInitLoop]()()  <br><br><br>
| [internalPostLoop]()| [androidJvm]  <br>Content  <br>open override fun [internalPostLoop]()()  <br><br><br>
| [internalPreInit]()| [androidJvm]  <br>Content  <br>open override fun [internalPreInit]()()  <br><br><br>
| [internalUpdateTelemetryNow]()| [androidJvm]  <br>Content  <br>override fun [internalUpdateTelemetryNow]()(p0: [TelemetryMessage]())  <br><br><br>
| [isStarted]()| [androidJvm]  <br>Content  <br>override fun [isStarted]()(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [isStopRequested]()| [androidJvm]  <br>Content  <br>override fun [isStopRequested]()(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [loop]()| [androidJvm]  <br>Content  <br>override fun [loop]()()  <br><br><br>
| [opModeIsActive]()| [androidJvm]  <br>Content  <br>override fun [opModeIsActive]()(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [pidDriveWithEncoders](pid-drive-with-encoders.md)| [androidJvm]  <br>Content  <br>fun [pidDriveWithEncoders](pid-drive-with-encoders.md)(inches: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), power: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [requestOpModeStop]()| [androidJvm]  <br>Content  <br>override fun [requestOpModeStop]()()  <br><br><br>
| [resetStartTime]()| [androidJvm]  <br>Content  <br>open override fun [resetStartTime]()()  <br><br><br>
| [rotate](rotate.md)| [androidJvm]  <br>Content  <br>fun [rotate](rotate.md)(deg: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [runOpMode](run-op-mode.md)| [androidJvm]  <br>Content  <br>open override fun [runOpMode](run-op-mode.md)()  <br><br><br>
| [sleep]()| [androidJvm]  <br>Content  <br>override fun [sleep]()(p0: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))  <br><br><br>
| [start]()| [androidJvm]  <br>Content  <br>override fun [start]()()  <br><br><br>
| [stop]()| [androidJvm]  <br>Content  <br>override fun [stop]()()  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [androidJvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| [updateTelemetry]()| [androidJvm]  <br>Content  <br>open override fun [updateTelemetry]()(p0: [Telemetry]())  <br><br><br>
| [waitForNextHardwareCycle]()| [androidJvm]  <br>Content  <br>~~open~~ ~~override~~ ~~fun~~ [~~waitForNextHardwareCycle~~]()~~(~~~~)~~  <br><br><br>
| [waitForStart]()| [androidJvm]  <br>Content  <br>open override fun [waitForStart]()()  <br><br><br>
| [waitOneFullHardwareCycle]()| [androidJvm]  <br>Content  <br>~~open~~ ~~override~~ ~~fun~~ [~~waitOneFullHardwareCycle~~]()~~(~~~~)~~  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [countsPerRev](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/countsPerRev/#/PointingToDeclaration/)|  [androidJvm] abstract val [countsPerRev](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/countsPerRev/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [executorService](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/executorService/#/PointingToDeclaration/)|  [androidJvm] override val [executorService](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/executorService/#/PointingToDeclaration/): [ExecutorService](https://developer.android.com/reference/java/util/concurrent/ExecutorService.html)   <br>
| [gamepad1](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/gamepad1/#/PointingToDeclaration/)|  [androidJvm] override val [gamepad1](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/gamepad1/#/PointingToDeclaration/): [Gamepad]()   <br>
| [gamepad2](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/gamepad2/#/PointingToDeclaration/)|  [androidJvm] override val [gamepad2](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/gamepad2/#/PointingToDeclaration/): [Gamepad]()   <br>
| [gearRatio](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/gearRatio/#/PointingToDeclaration/)|  [androidJvm] abstract val [gearRatio](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/gearRatio/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [hardwareMap](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/hardwareMap/#/PointingToDeclaration/)|  [androidJvm] override val [hardwareMap](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/hardwareMap/#/PointingToDeclaration/): [HardwareMap]()   <br>
| [helper](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/helper/#/PointingToDeclaration/)|  [androidJvm] override val [helper](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/helper/#/PointingToDeclaration/): [LinearOpMode.LinearOpModeHelper]()   <br>
| [internalOpModeServices](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/internalOpModeServices/#/PointingToDeclaration/)|  [androidJvm] override val [internalOpModeServices](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/internalOpModeServices/#/PointingToDeclaration/): [OpModeServices]()   <br>
| [isStarted](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/isStarted/#/PointingToDeclaration/)|  [androidJvm] override val [isStarted](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/isStarted/#/PointingToDeclaration/): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)   <br>
| [msStuckDetectInit](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectInit/#/PointingToDeclaration/)|  [androidJvm] override val [msStuckDetectInit](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectInit/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>
| [msStuckDetectInitLoop](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectInitLoop/#/PointingToDeclaration/)|  [androidJvm] override val [msStuckDetectInitLoop](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectInitLoop/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>
| [msStuckDetectLoop](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectLoop/#/PointingToDeclaration/)|  [androidJvm] override val [msStuckDetectLoop](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectLoop/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>
| [msStuckDetectStart](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectStart/#/PointingToDeclaration/)|  [androidJvm] override val [msStuckDetectStart](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectStart/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>
| [msStuckDetectStop](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectStop/#/PointingToDeclaration/)|  [androidJvm] override val [msStuckDetectStop](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/msStuckDetectStop/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>
| [pidPower](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/pidPower/#/PointingToDeclaration/)|  [androidJvm] abstract val [pidPower](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/pidPower/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [robot](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/robot/#/PointingToDeclaration/)|  [androidJvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-field/index.html)()  <br>  <br>val [robot](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/robot/#/PointingToDeclaration/): [HardwareSkybot](../../org.firstinspires.ftc.teamcode.hardware/-hardware-skybot/index.md)   <br>
| [startTime](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/startTime/#/PointingToDeclaration/)|  [androidJvm] override val [startTime](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/startTime/#/PointingToDeclaration/): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)   <br>
| [stopRequested](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/stopRequested/#/PointingToDeclaration/)|  [androidJvm] override val [stopRequested](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/stopRequested/#/PointingToDeclaration/): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)   <br>
| [telemetry](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/telemetry/#/PointingToDeclaration/)|  [androidJvm] override val [telemetry](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/telemetry/#/PointingToDeclaration/): [Telemetry]()   <br>
| [time](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/time/#/PointingToDeclaration/)|  [androidJvm] override val [time](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/time/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [wheelDiameter](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/wheelDiameter/#/PointingToDeclaration/)|  [androidJvm] abstract val [wheelDiameter](index.md#org.firstinspires.ftc.teamcode.opmodes.autonomous/AutonomousOpMode/wheelDiameter/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>


## Inheritors  
  
|  Name| 
|---|
| [AutonomousOmni](../-autonomous-omni/index.md)

