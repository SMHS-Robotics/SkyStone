package org.firstinspires.ftc.teamcode.utilities

import com.qualcomm.hardware.bosch.BNO055IMU

import org.firstinspires.ftc.robotcore.external.navigation.NavUtil.meanIntegrate
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil.plus

import com.qualcomm.robotcore.util.RobotLog

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration
import org.firstinspires.ftc.robotcore.external.navigation.Position
import org.firstinspires.ftc.robotcore.external.navigation.Velocity

/**
 * [NaiveAccelerationIntegrator] provides a very naive implementation of
 * an acceleration integration algorithm. It just does the basic physics.
 * One you would actually want to use in a robot would, for example, likely
 * filter noise out the acceleration data or more sophisticated processing.
 */
class NaiveAccelerationIntegrator :  BNO055IMU.AccelerationIntegrator {
    internal var parameters: BNO055IMU.Parameters? = null
    internal var position: Position
    internal var velocity: Velocity
    internal var acceleration: Acceleration? = null

    override fun getPosition(): Position {
        return this.position
    }

    override fun getVelocity(): Velocity {
        return this.velocity
    }

    override fun getAcceleration(): Acceleration? {
        return this.acceleration
    }

    init {
        this.parameters = null
        this.position = Position()
        this.velocity = Velocity()
        this.acceleration = null
    }

    //------------------------------------------------------------------------------------------
    // Operations
    //------------------------------------------------------------------------------------------

    override fun initialize(parameters: BNO055IMU.Parameters, initialPosition: Position?, initialVelocity: Velocity?) {
        this.parameters = parameters
        this.position = initialPosition ?: this.position
        this.velocity = initialVelocity ?: this.velocity
        this.acceleration = null
    }

    override fun update(linearAcceleration: Acceleration) {
        // We should always be given a timestamp here
        if (linearAcceleration.acquisitionTime != 0L) {
            // We can only integrate if we have a previous acceleration to baseline from
            if (acceleration != null) {
                val accelPrev = acceleration
                val velocityPrev = velocity

                acceleration = linearAcceleration

                if (accelPrev!!.acquisitionTime != 0L) {
                    val deltaVelocity = meanIntegrate(acceleration!!, accelPrev)
                    velocity = plus(velocity, deltaVelocity)
                }

                if (velocityPrev.acquisitionTime != 0L) {
                    val deltaPosition = meanIntegrate(velocity, velocityPrev)
                    position = plus(position, deltaPosition)
                }

                if (parameters != null && parameters!!.loggingEnabled) {
                    RobotLog.vv(parameters!!.loggingTag, "dt=%.3fs accel=%s vel=%s pos=%s", (acceleration!!.acquisitionTime - accelPrev.acquisitionTime) * 1e-9, acceleration, velocity, position)
                }
            } else
                acceleration = linearAcceleration
        }
    }
}
