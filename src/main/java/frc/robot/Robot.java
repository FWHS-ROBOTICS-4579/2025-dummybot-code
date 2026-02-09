// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  private static PWMSparkMax m_frontLeft = new PWMSparkMax(1);
  private static PWMSparkMax m_backLeft = new PWMSparkMax(2);
  private static PWMSparkMax m_frontRight = new PWMSparkMax(3);
  private static PWMSparkMax m_backRight  = new PWMSparkMax(4);
  private static MecanumDrive m_robotDrive = new MecanumDrive(m_frontLeft, m_backLeft, m_frontRight, m_backRight);
 

  private XboxController m_XboxController = new XboxController(0);

  public Robot() {
    m_robotContainer = new RobotContainer();

    m_frontRight.setInverted(true);
    m_backRight.setInverted(true);

    SendableRegistry.addChild(m_frontLeft, m_robotDrive);
    SendableRegistry.addChild(m_backLeft, m_robotDrive);
    SendableRegistry.addChild(m_frontRight, m_robotDrive);
    SendableRegistry.addChild(m_backRight, m_robotDrive);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {
    m_robotDrive.driveCartesian(0.1, 0.0, 0.0);
    m_robotDrive.feed();
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    // m_robotDrive.setSafetyEnabled(false);
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    m_robotDrive.driveCartesian(0.1, 0.0, 0.0);
    // m_robotDrive.driveCartesian(-m_XboxController.getLeftY(), m_XboxController.getLeftX(), m_XboxController.getRightX());
    // MecanumDriveSubsystem.drive(-m_XboxController.getLeftY(), m_XboxController.getLeftX(), m_XboxController.getRightX());
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
