// PID controller courtesy of Peter Tischler, with modifications. http://stemrobotics.cs.pdx.edu/node/7268

package org.firstinspires.ftc.teamcode.utilities

import kotlin.math.*

class PIDController

/**
 * Allocate a PID object with the given constants for P, I, D
 *
 * @param Kp the proportional coefficient
 * @param Ki the integral coefficient
 * @param Kd the derivative coefficient
 */
(Kp: Double, Ki: Double, Kd: Double) {

    /**
     * Get the Proportional coefficient
     *
     * @return proportional coefficient
     */
    var p: Double = 0.toDouble(); private set  // factor for "proportional" control

    /**
     * Get the Integral coefficient
     *
     * @return integral coefficient
     */
    var i: Double = 0.toDouble(); private set  // factor for "integral" control

    /**
     * Get the Differential coefficient
     *
     * @return differential coefficient
     */
    var d: Double = 0.toDouble(); private set // factor for "derivative" control

    private var mInput: Double = 0.toDouble() // sensor input for pid controller
    private var mMaximumOutput = 1.0   // |maximum output|
    private var mMinimumOutput = -1.0  // |minimum output|
    private var mMaximumInput = 0.0    // maximum input - limit setpoint to this
    private var mMinimumInput = 0.0    // minimum input - limit setpoint to this
    private var mContinuous = false   // do the endpoints wrap around? eg. Absolute encoder
    private var mEnabled = false              //is the pid controller enabled
    private var mPrevError = 0.0           // the prior sensor input (used to compute velocity)
    private var mTotalError = 0.0      //the sum of the errors for use in the integral calc
    private var mTolerance = 0.05
    //the percentage error that is considered on target
    /**
     * Returns the current setpoint of the PIDController
     *
     * @return the current setpoint
     */
    /**
     * Set the setpoint for the PIDController
     *
     * @param setpoint the desired setpoint
     */
    var setpoint = 0.0
        set(setpoint) {
            var sign = 1

            if (mMaximumInput > mMinimumInput) {
                if (setpoint < 0) {
                    sign = -1
                }

                field = when {
                    abs(setpoint) > mMaximumInput -> mMaximumInput * sign
                    abs(setpoint) < mMinimumInput -> mMinimumInput * sign
                    else -> setpoint
                }
            } else {
                field = setpoint
            }
        }
    /**
     * Retruns the current difference of the input from the setpoint
     *
     * @return the current error
     */
    @get:Synchronized
    var error = 0.0
        private set

    private var mResult = 0.0

    init {
        p = Kp
        i = Ki
        d = Kd
    }

    /**
     * Read the input, calculate the output accordingly, and write to the output.
     * This should only be called by the PIDTask
     * and is created during initialization.
     */
    private fun calculate() {
        var sign = 1

        // If enabled then proceed into controller calculations
        if (mEnabled) {
            // Calculate the error signal
            error = setpoint - mInput

            // If continuous is set to true allow wrap around
            if (mContinuous) {
                if (abs(error) > (mMaximumInput - mMinimumInput) / 2) {
                    error = if (error > 0) {
                        error - mMaximumInput + mMinimumInput
                    } else {
                        error + mMaximumInput - mMinimumInput
                    }
                }
            }

            // Integrate the errors as long as the upcoming integrator does
            // not exceed the minimum and maximum output thresholds.

            if (abs(mTotalError + error) * i < mMaximumOutput && abs(mTotalError + error) * i > mMinimumOutput) {
                mTotalError += error
            }

            // Perform the primary PID calculation
            mResult = p * error + i * mTotalError + d * (error - mPrevError)

            // Set the current error to the previous error for the next cycle.
            mPrevError = error

            if (mResult < 0) {
                sign = -1    // Record sign of result.
            }

            // Make sure the final result is within bounds. If we constrain the result, we make
            // sure the sign of the constrained result matches the original result sign.
            if (abs(mResult) > mMaximumOutput) {
                mResult = mMaximumOutput * sign
            } else if (abs(mResult) < mMinimumOutput) {
                mResult = mMinimumOutput * sign
            }
        }
    }

    /**
     * Set the PID Controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     *
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     */
    fun setPID(p: Double, i: Double, d: Double) {
        this.p = p
        this.i = i
        this.d = d
    }

    /**
     * Return the current PID result for the last input set with setInput().
     * This is always centered on zero and constrained the the max and min outs
     *
     * @return the latest calculated output
     */
    fun performPID(): Double {
        calculate()
        return mResult
    }

    /**
     * Return the current PID result for the specified input.
     *
     * @param input The input value to be used to calculate the PID result.
     * This is always centered on zero and constrained the the max and min outs
     * @return the latest calculated output
     */
    fun performPID(input: Double): Double {
        setInput(input)
        return performPID()
    }

    /**
     * Set the PID controller to consider the input to be continuous,
     * Rather then using the max and min in as constraints, it considers them to
     * be the same point and automatically calculates the shortest route to
     * the setpoint.
     *
     * @param continuous Set to true turns on continuous, false turns off continuous
     */
    fun setContinuous(continuous: Boolean) {
        mContinuous = continuous
    }

    /**
     * Set the PID controller to consider the input to be continuous,
     * Rather then using the max and min in as constraints, it considers them to
     * be the same point and automatically calculates the shortest route to
     * the setpoint.
     */
    fun setContinuous() {
        this.setContinuous(true)
    }

    /**
     * Sets the maximum and minimum values expected from the input.
     *
     * @param minimumInput the minimum value expected from the input, always positive
     * @param maximumInput the maximum value expected from the input, always positive
     */
    fun setInputRange(minimumInput: Double, maximumInput: Double) {
        mMinimumInput = Math.abs(minimumInput)
        mMaximumInput = Math.abs(maximumInput)
        setpoint = setpoint
    }

    /**
     * Sets the minimum and maximum values to write.
     *
     * @param minimumOutput the minimum value to write to the output, always positive
     * @param maximumOutput the maximum value to write to the output, always positive
     */
    fun setOutputRange(minimumOutput: Double, maximumOutput: Double) {
        mMinimumOutput = Math.abs(minimumOutput)
        mMaximumOutput = Math.abs(maximumOutput)
    }

    /**
     * Set the percentage error which is considered tolerable for use with
     * OnTarget. (Input of 15.0 = 15 percent)
     *
     * @param percent error which is tolerable
     */
    fun setTolerance(percent: Double) {
        mTolerance = percent
    }

    /**
     * Return true if the error is within the percentage of the total input range,
     * determined by setTolerance. This assumes that the maximum and minimum input
     * were set using setInputRange.
     *
     * @return true if the error is less than the tolerance
     */
    fun onTarget(): Boolean {
        return abs(error) < abs(mTolerance / 100 * (mMaximumInput - mMinimumInput))
    }

    /**
     * Begin running the PIDController
     */
    fun enable() {
        mEnabled = true
    }

    /**
     * Stop running the PIDController.
     */
    fun disable() {
        mEnabled = false
    }

    /**
     * Reset the previous error,, the integral term, and disable the controller.
     */
    fun reset() {
        disable()
        mPrevError = 0.0
        mTotalError = 0.0
        mResult = 0.0
    }

    /**
     * Set the input value to be used by the next call to performPID().
     *
     * @param input Input value to the PID calculation.
     */
    fun setInput(input: Double) {
        var sign = 1

        if (mMaximumInput > mMinimumInput) {
            if (input < 0) {
                sign = -1
            }

            mInput = if (abs(input) > mMaximumInput) {
                mMaximumInput * sign
            } else if (abs(input) < mMinimumInput) {
                mMinimumInput * sign
            } else {
                input
            }
        } else {
            mInput = input
        }
    }
}