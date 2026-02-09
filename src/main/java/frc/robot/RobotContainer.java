// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CameraServerSubsystem;
import frc.robot.subsystems.MecanumDriveSubsystem;

public class RobotContainer {

  private final MecanumDriveSubsystem m_DriveSubsystem = new MecanumDriveSubsystem();
  private final CameraServerSubsystem m_CameraServerSubsystem = new CameraServerSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
