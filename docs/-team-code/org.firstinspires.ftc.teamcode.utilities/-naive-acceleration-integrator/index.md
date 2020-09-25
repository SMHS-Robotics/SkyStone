---
title: NaiveAccelerationIntegrator -
---
//[TeamCode](../../index.md)/[org.firstinspires.ftc.teamcode.utilities](../index.md)/[NaiveAccelerationIntegrator](index.md)



# NaiveAccelerationIntegrator  
 [androidJvm] [NaiveAccelerationIntegrator](index.md) provides a very naive implementation of an acceleration integration algorithm. It just does the basic physics. One you would actually want to use in a robot would, for example, likely filter noise out the acceleration data or more sophisticated processing.  
  
class [NaiveAccelerationIntegrator](index.md) : [BNO055IMU.AccelerationIntegrator]()   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [androidJvm] fun [<init>](-init-.md)()   <br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [androidJvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [getAcceleration](get-acceleration.md)| [androidJvm]  <br>Content  <br>open override fun [getAcceleration](get-acceleration.md)(): [Acceleration]()?  <br><br><br>
| [getPosition](get-position.md)| [androidJvm]  <br>Content  <br>open override fun [getPosition](get-position.md)(): [Position]()  <br><br><br>
| [getVelocity](get-velocity.md)| [androidJvm]  <br>Content  <br>open override fun [getVelocity](get-velocity.md)(): [Velocity]()  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [androidJvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [initialize](initialize.md)| [androidJvm]  <br>Content  <br>open override fun [initialize](initialize.md)(parameters: [BNO055IMU.Parameters](), initialPosition: [Position]()?, initialVelocity: [Velocity]()?)  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [androidJvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| [update](update.md)| [androidJvm]  <br>Content  <br>open override fun [update](update.md)(linearAcceleration: [Acceleration]())  <br><br><br>

