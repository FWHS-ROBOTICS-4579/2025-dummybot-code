package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;

public class CameraServerSubsystem {
    public CameraServerSubsystem() {
        CameraServer.startAutomaticCapture();
    }
}
