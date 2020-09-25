---
title: PIDController -
---
//[TeamCode](../../index.md)/[org.firstinspires.ftc.teamcode.utilities](../index.md)/[PIDController](index.md)



# PIDController  
 [androidJvm] Allocate a PID object with the given constants for P, I, D  
  
class [PIDController](index.md)(**Kp**: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html),**Ki**: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html),**Kd**: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))   


## Parameters  
  
androidJvm  
  
|  Name|  Summary| 
|---|---|
| Kd| the derivative coefficient
| Ki| the integral coefficient
| Kp| the proportional coefficient
  


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [androidJvm] the proportional coefficientfun [<init>](-init-.md)(Kp: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), Ki: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), Kd: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))   <br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [disable](disable.md)| [androidJvm]  <br>Brief description  <br>Stop running the PIDController.  <br>Content  <br>fun [disable](disable.md)()  <br><br><br>
| [enable](enable.md)| [androidJvm]  <br>Brief description  <br>Begin running the PIDController  <br>Content  <br>fun [enable](enable.md)()  <br><br><br>
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [androidJvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [androidJvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [onTarget](on-target.md)| [androidJvm]  <br>Brief description  <br>Return true if the error is within the percentage of the total input range, determined by setTolerance. This assumes that the maximum and minimum input were set using setInputRange.  <br>Content  <br>fun [onTarget](on-target.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [performPID](perform-p-i-d.md)| [androidJvm]  <br>Brief description  <br>Return the current PID result for the last input set with setInput(). This is always centered on zero and constrained the the max and min outs  <br>Content  <br>fun [performPID](perform-p-i-d.md)(): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)  <br><br><br>[androidJvm]  <br>Brief description  <br>Return the current PID result for the specified input.  <br>Content  <br>fun [performPID](perform-p-i-d.md)(input: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)  <br><br><br>
| [reset](reset.md)| [androidJvm]  <br>Brief description  <br>Reset the previous error,, the integral term, and disable the controller.  <br>Content  <br>fun [reset](reset.md)()  <br><br><br>
| [setContinuous](set-continuous.md)| [androidJvm]  <br>Brief description  <br>Set the PID controller to consider the input to be continuous, Rather then using the max and min in as constraints, it considers them to be the same point and automatically calculates the shortest route to the setpoint.  <br>Content  <br>fun [setContinuous](set-continuous.md)(continuous: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))  <br>fun [setContinuous](set-continuous.md)()  <br><br><br>
| [setInput](set-input.md)| [androidJvm]  <br>Brief description  <br>Set the input value to be used by the next call to performPID().  <br>Content  <br>fun [setInput](set-input.md)(input: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [setInputRange](set-input-range.md)| [androidJvm]  <br>Brief description  <br>Sets the maximum and minimum values expected from the input.  <br>Content  <br>fun [setInputRange](set-input-range.md)(minimumInput: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), maximumInput: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [setOutputRange](set-output-range.md)| [androidJvm]  <br>Brief description  <br>Sets the minimum and maximum values to write.  <br>Content  <br>fun [setOutputRange](set-output-range.md)(minimumOutput: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), maximumOutput: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [setPID](set-p-i-d.md)| [androidJvm]  <br>Brief description  <br>Set the PID Controller gain parameters. Set the proportional, integral, and differential coefficients.  <br>Content  <br>fun [setPID](set-p-i-d.md)(p: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), i: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), d: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [setTolerance](set-tolerance.md)| [androidJvm]  <br>Brief description  <br>Set the percentage error which is considered tolerable for use with OnTarget. (Input of 15.0 = 15 percent)  <br>Content  <br>fun [setTolerance](set-tolerance.md)(percent: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [androidJvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [d](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/d/#/PointingToDeclaration/)|  [androidJvm] Get the Differential coefficientvar [d](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/d/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [error](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/error/#/PointingToDeclaration/)|  [androidJvm] Retruns the current difference of the input from the setpointvar [error](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/error/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [i](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/i/#/PointingToDeclaration/)|  [androidJvm] Get the Integral coefficientvar [i](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/i/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [p](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/p/#/PointingToDeclaration/)|  [androidJvm] Get the Proportional coefficientvar [p](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/p/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>
| [setpoint](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/setpoint/#/PointingToDeclaration/)|  [androidJvm] Set the setpoint for the PIDControllervar [setpoint](index.md#org.firstinspires.ftc.teamcode.utilities/PIDController/setpoint/#/PointingToDeclaration/): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)   <br>

