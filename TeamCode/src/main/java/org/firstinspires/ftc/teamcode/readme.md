## SMHS TeamCode

This is where all of our programming happens. 

Primarily, you wll be using the opmodes folder to interact with the IMU. 

The abstractions for interacting with the IMU is in the hardware folder.
Once this is set up for the current build configuration, there should not
be a need to mess with this further.

Various utilities for programming are in the utilities folder. This includes
a PIDController and Integrator, for PID and Integration respectively.

This is a Kotlin-based project, but you can write in Java if that's more 
comfortable for you.

## Documentation

You can view documentation here: https://smhs-robotics.github.io/SkyStone/
To generate documentation, run `gradlew -b TeamCode/build.gradle dokkaHtml`