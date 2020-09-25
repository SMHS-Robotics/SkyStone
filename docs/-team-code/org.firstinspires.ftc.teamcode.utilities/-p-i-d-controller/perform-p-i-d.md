---
title: performPID -
---
//[TeamCode](../../index.md)/[org.firstinspires.ftc.teamcode.utilities](../index.md)/[PIDController](index.md)/[performPID](perform-p-i-d.md)



# performPID  
[androidJvm]  
Brief description  
Return the current PID result for the last input set with setInput(). This is always centered on zero and constrained the the max and min outs  
  


#### Return  
the latest calculated output  
  
  
Content  
fun [performPID](perform-p-i-d.md)(): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)  


[androidJvm]  
Brief description  
Return the current PID result for the specified input.  
  


#### Return  
the latest calculated output  
  


## Parameters  
  
androidJvm  
  
|  Name|  Summary| 
|---|---|
| input| The input value to be used to calculate the PID result. This is always centered on zero and constrained the the max and min outs
  
  
Content  
fun [performPID](perform-p-i-d.md)(input: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)  



