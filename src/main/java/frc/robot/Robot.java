 /*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/                                                                                                                                 


package frc.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 2;
  private static final int kRearLeftChannel = 3;
  private static final int kFrontRightChannel = 1;
  private static final int kRearRightChannel = 0;
  private static final int kJoystickChannel = 0;
  

  private Spark m_frontLeft;
  private Spark m_frontRight;
  //private Spark m_rearLeft;
  private Victor m_rearLeft;
  private Spark m_rearRight;
  private MecanumDrive m_robotDrive;
  private Joystick m_stick;
  private JoystickButton toggleButton, led1, led2, led3;
  private double ledStyle = 0;
  private DoubleSolenoid launchersolenoid;
  private DigitalInput getPressureSwitchValue;
  private Relay compressorSwitch;
  private Spark m_led_driver;



  private static double linearDeadband(double raw, double deadband)
 {
 
     if (Math.abs(raw)<deadband) return 0;
 
    return Math.signum(raw)*(Math.abs(raw)-deadband)/(1-deadband);
 }

  @Override
  public void robotInit() {


    
    m_frontLeft = new Spark(0);	
    m_rearLeft = new Victor(4);
    m_frontRight = new Spark(2);	
    m_rearRight = new Spark(3);
    m_led_driver = new Spark(5);
    m_robotDrive = new MecanumDrive(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight);
    m_stick = new Joystick(kJoystickChannel);
    toggleButton = new JoystickButton(m_stick, 1);
    led1 = new JoystickButton(m_stick, 2);
    led2 = new JoystickButton(m_stick, 3);
    led3 = new JoystickButton(m_stick, 4);
    launchersolenoid = new DoubleSolenoid(1,0);
    getPressureSwitchValue = new DigitalInput(2);
    compressorSwitch = new Relay(1);
    

    // Invert the left side motors.
    // You may need to change or remove this to match your robot.
    m_frontLeft.setInverted(false);
    m_rearLeft.setInverted(true);
  }

  
  //Override
  

  public void teleopPeriodic() {
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(m_stick.getZ(), m_stick.getY(), m_stick.getX(), 0.0);


    //launchersolenoid.set(DoubleSolenoid.Value.kOff);
    //launchersolenoid.set(DoubleSolenoid.Value.kForward);
    //launchersolenoid.set(DoubleSolenoid.Value.kReverse);

    if (toggleButton.get()) {
      launchersolenoid.set(DoubleSolenoid.Value.kForward);
    } else {
      launchersolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    ledFunction();
  }

  /** This is the function that checks buttons and sets the LEDs accordingly */
  private void ledFunction() {
    if (led1.get() && ledStyle != .5/* This is some value */) ledStyle = .5;
    else if (led1.get()) ledStyle = 0;
    if (led2.get() && ledStyle != .6/* This is some value */) ledStyle = .6;
    else if (led2.get()) ledStyle = 0;
    if (led3.get() && ledStyle != .7/* This is some value */) ledStyle = .7;
    else if (led3.get()) ledStyle = 0;

    m_led_driver.set(ledStyle);
  }
}

