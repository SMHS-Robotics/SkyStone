package org.firstinspires.ftc.teamcode.opmodes.autonomous;


/*
    Represents the current "stage" of the autonomous program
    START: Robot has just been placed. Not moving.
    TO_WALL: Robot is driving directly perpendicular to wall towards wall
    TO_DEPOT: Robot is driving to depot
    DROP_MARKER: Robot is dropping marker in depot
    TO_CRATER: Robot is driving to crater to park
    END: Robot has parked. Not moving
 */
<<<<<<< HEAD
public enum AutonomousState
{
=======
public enum AutonomousState {
>>>>>>> origin/dev-Aditya
    CHECK_POSITION, GET_SKYSTONE_RED, GET_SKYSTONE_BLUE, MOVE_FOUNDATION_RED, MOVE_FOUNDATION_BLUE,
    PARK_RED, PARK_BLUE, END;

    @Override
<<<<<<< HEAD
    public String toString()
    {
        switch (this)
        {
=======
    public String toString() {
        switch (this) {
>>>>>>> origin/dev-Aditya
            case CHECK_POSITION:
                return "CHECK_POSITION";
            case GET_SKYSTONE_RED:
                return "GET_SKYSTONE_RED";
            case GET_SKYSTONE_BLUE:
                return "GET_SKYSTONE_BLUE";
            case MOVE_FOUNDATION_RED:
                return "MOVE_FOUNDATION_RED";
            case MOVE_FOUNDATION_BLUE:
                return "MOVE_FOUNDATION_BLUE";
            case PARK_RED:
                return "PARK_RED";
            case PARK_BLUE:
                return "PARK_BLUE";
            case END:
                return "END";
            default:
                return "Invalid state";
        }
    }
}