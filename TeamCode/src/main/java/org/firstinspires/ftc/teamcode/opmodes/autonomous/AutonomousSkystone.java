package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutonomousSkystone extends LinearOpMode
{
    AutonomousState checkPos = AutonomousState.CHECK_POSITION;


    @Override
    public void runOpMode()
    {
        while(opModeIsActive()){
            switch(checkPos){
                case CHECK_POSITION:
                    checkPosition();
                    break;
                case GET_SKYSTONE_BLUE:
                    break;
            }
        }
    }

    public void checkPosition(){
    }
}
