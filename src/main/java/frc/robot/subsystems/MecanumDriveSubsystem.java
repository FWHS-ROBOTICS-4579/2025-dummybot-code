// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class MecanumDriveSubsystem extends SubsystemBase {
  private static final int kFrontLeftChannel = 4;
  private static final int kRearLeftChannel = 3;
  private static final int kFrontRightChannel = 2;
  private static final int kRearRightChannel = 1;

  private static final int kControllerChannel = 0;

  private final MecanumDrive m_robotDrive;
  private final XboxController m_XboxController;

  private double m_driveSpeedMultiplier = 0.5;

  private double m_deadzone = 0.2;

  /** Called once at the beginning of the robot program. */
  public MecanumDriveSubsystem() {
    SparkMax frontLeft = new SparkMax(kFrontLeftChannel, MotorType.kBrushless);
    SparkMax rearLeft = new SparkMax(kRearLeftChannel, MotorType.kBrushless);
    SparkMax frontRight = new SparkMax(kFrontRightChannel, MotorType.kBrushless);
    SparkMax rearRight = new SparkMax(kRearRightChannel, MotorType.kBrushless);

    SparkMaxConfig motorConfig = new SparkMaxConfig();

    motorConfig.inverted(true);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    rearRight.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    m_robotDrive = new MecanumDrive(frontLeft::set, rearLeft::set, frontRight::set, rearRight::set);

    m_XboxController = new XboxController(kControllerChannel);

    SendableRegistry.addChild(m_robotDrive, frontLeft);
    SendableRegistry.addChild(m_robotDrive, rearLeft);
    SendableRegistry.addChild(m_robotDrive, frontRight);
    SendableRegistry.addChild(m_robotDrive, rearRight);

    SmartDashboard.putData("MoveSpeed", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("DriveSpeedMultiplier", () -> m_driveSpeedMultiplier, null);
    }
    });
  }

  @Override
  public void periodic() {

    if (m_XboxController.getRightTriggerAxis() > 0.5) {
      m_driveSpeedMultiplier = 0.2;
    } else if (m_XboxController.getLeftTriggerAxis() > 0.5) {
      m_driveSpeedMultiplier = 1;
    } else {
      m_driveSpeedMultiplier = 0.5;
    }
    
    double xSpeed = -m_XboxController.getLeftY();
    double ySpeed = m_XboxController.getLeftX();
    double zSpeed = m_XboxController.getRightX();

    if (Math.abs(xSpeed) < m_deadzone) { xSpeed = 0; }
    if (Math.abs(ySpeed) < m_deadzone) { ySpeed = 0; }
    if (Math.abs(zSpeed) < m_deadzone) { zSpeed = 0; }

    m_robotDrive.driveCartesian(xSpeed * m_driveSpeedMultiplier, ySpeed * m_driveSpeedMultiplier, zSpeed * m_driveSpeedMultiplier);
  }
}
