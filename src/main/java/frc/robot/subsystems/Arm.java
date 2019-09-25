package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.PWMChannel;
import com.ctre.phoenix.CANifier.GeneralPin;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Robot;
import frc.robot.data.ArmData;
import frc.robot.data.RobotState.ScoringDirectionStates;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import frc.robot.misc.MiniPID;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {

  public double setpoint = 0;

  public CANPIDController pidController;

  private CANSparkMax sparkMax;
  private CANEncoder internalEncoder;
  private CANifier dataBus;
  private CANDigitalInput frontLimitSwitch, backLimitSwitch;

  private double armFrontLimit = RobotMap.Values.armFrontParallel;

  // Read Encoder Vars
  private final double MAX = 1022;
  private final double LIMIT = 500;
  private double prevRead = -1;
  private int revs = 0;
  private int flipModifier = 1;
  // private double fConstant = 0.002;
  public boolean armState;

  public MiniPID miniBoi;

  public Arm() {

    miniBoi = new MiniPID(0.0006, 0.0, 0.001, 0);
    miniBoi.setOutputLimits(-0.4, 0.4);

    sparkMax = new CANSparkMax(RobotMap.Ports.armSpark, MotorType.kBrushless);
    
    sparkMax.restoreFactoryDefaults();

    sparkMax.setInverted(true);

    internalEncoder = sparkMax.getEncoder();
    double conversionFactor = RobotMap.Values.internalFlipTickCount / (RobotMap.Values.armBackParallel - RobotMap.Values.armFrontParallel);
    internalEncoder.setPositionConversionFactor(conversionFactor);

    //sparkMax.setOpenLoopRampRate(0);

    //sparkMax.setIdleMode(IdleMode.kBrake);
    
    pidController = sparkMax.getPIDController();
    pidController.setP(RobotMap.Values.armPidP);
    pidController.setI(RobotMap.Values.armPidI);
    pidController.setD(RobotMap.Values.armPidD);
    pidController.setFF(0);
    pidController.setOutputRange(-0.8, 0.8);

    pidController.setIAccum(0);

    pidController.setReference(0.0/*total - current*/, ControlType.kPosition);
    
    frontLimitSwitch = new CANDigitalInput(sparkMax, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);
    frontLimitSwitch.enableLimitSwitch(false);
    backLimitSwitch = new CANDigitalInput(sparkMax, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyOpen);
    backLimitSwitch.enableLimitSwitch(false);

    dataBus = Robot.armCanifier;

    pidController.setReference(0.0, ControlType.kDutyCycle);

    /*SmartDashboard.putNumber("Arm/Arm Pid P", RobotMap.Values.armPidP);
    SmartDashboard.putNumber("Arm/Arm Pid I", RobotMap.Values.armPidI);
    SmartDashboard.putNumber("Arm/Arm Pid D", RobotMap.Values.armPidD);
    SmartDashboard.putNumber("Arm/Arm Pid F", RobotMap.Values.armMaxPidF);*/
  }

  public void setPower(double speed) {
    //sparkMax.set(speed);
    pidController.setReference(speed, ControlType.kDutyCycle);
  }

  public void setArmFrontLimit(double value) {
    armFrontLimit = value;
  }

  public double getArmFrontLimit() {
    return armFrontLimit;
  }

  public ScoringDirectionStates getArmSide(){
    if(readEncoder() < RobotMap.Values.armVertical){
      return ScoringDirectionStates.Front;
    }
    else{
      return ScoringDirectionStates.Back;
    }

  }

  public void resetPID() {
    pidController.setIAccum(0);
  }

  // This function only works if the inital read of the arm is horizontal
  public void updateF(double setpoint){

    double angleMod = Math.PI / Math.abs(RobotMap.Values.armFrontParallel - RobotMap.Values.armBackParallel);
    double angle = (readEncoder() - RobotMap.Values.armFrontParallel) * angleMod;

    pidController.setFF((-Math.cos(angle) * RobotMap.Values.armMaxPidF) / setpoint);
  }

  public double getCurrent() {
    return sparkMax.getOutputCurrent();
  }

  public void SetPostion(double setpoint){

    updateF(setpoint);
    pidController.setReference(setpoint, ControlType.kPosition);

    /*
    releaseBrake();
    //System.out.println("Setting arm position to " + setpoint);
    //internalEncoder.setPosition(readEncoder());
    //pidController.setReference(setpoint, ControlType.kPosition);
    //pidController.setReference(setpoint - readEncoder(), ControlType.kPosition);
    this.setpoint = setpoint;
    double actual = readEncoder();
    double a = miniBoi.getOutput(actual, setpoint);
    //SmartDashboard.putNumber("Arm/Error", setpoint - actual);
    //SmartDashboard.putNumber("Arm/PID Output", a + UpdateF());
    //SmartDashboard.putNumber("Arm/MiniPID Output", a);
    sparkMax.set(a + UpdateF());
    //UpdateF();
    */
  }

  public double readEncoder() {
    double newVal = getRawEncoder();
    
    /*if (test) {
      newVal = testInput; // Read test input
    } else {
      newVal = getRawEncoder(); // Read encoder data
    }*/

    if (prevRead == -1) { 
      prevRead = newVal;
    }

    if (Math.abs(prevRead - newVal) > LIMIT) {
      if (newVal > prevRead) {
        revs -= flipModifier;
      } else {
        revs += flipModifier;
      }
    }
    prevRead = newVal;
    return (double)((revs * MAX) + newVal);
  }

  public double getRawEncoder() {
    double[] a = new double[2];
    dataBus.getPWMInput(PWMChannel.PWMChannel0, a);
    SmartDashboard.putNumber("Duty Cycle", a[1]);
    return a[0];
  }

  public void engageBrake() {
    //discBrake.set(false);
    SmartDashboard.putBoolean("Brake", true);
  }

  public void releaseBrake() {
    //discBrake.set(true);
    SmartDashboard.putBoolean("Brake", false);
  }

  public void SetIdleBrakeMode() {
    sparkMax.setIdleMode(IdleMode.kBrake);
  }

  public void SetIdleCoastMode() {
    sparkMax.setIdleMode(IdleMode.kCoast);
  }

  public void stop() {
    // need to disable the PID and stop the motor
    engageBrake();
    pidController.setReference(0.0, ControlType.kDutyCycle);
  }

  public boolean getForwardLimitSwitch() {
    return !dataBus.getGeneralInput(GeneralPin.LIMR);
  }

  public double getMotorTemp() {
    return sparkMax.getMotorTemperature();
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new LockArm());
  }

  /*public double getAngle() {
    (RobotMap.ElevatorHeights.armBackParallel - RobotMap.ElevatorHeights.armFrontParallel) 
  }*/

  public ArmData getArmData() {
    ArmData a = new ArmData();
    a.output = sparkMax.getAppliedOutput();
    a.current = sparkMax.getOutputCurrent();
    a.ticks = readEncoder();
    a.velocity = 0;
    a.front = frontLimitSwitch.get();
    a.back = backLimitSwitch.get();

    return a;
  }

  public void updatePID() {
    //pidController.setP(SmartDashboard.getNumber("Arm Pid P", RobotMap.Values.armPidP));
    //pidController.setI(SmartDashboard.getNumber("Arm Pid I", RobotMap.Values.armPidI));
    //pidController.setD(SmartDashboard.getNumber("Arm Pid D", RobotMap.Values.armPidD));
    //fConstant = SmartDashboard.getNumber("Arm Pid F", RobotMap.Values.armMaxPidF);
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Arm/Arm Absolute Raw", getRawEncoder());
    SmartDashboard.putNumber("Arm/Absolute Parsed", readEncoder());
    SmartDashboard.putNumber("Arm Front Limit", getArmFrontLimit());
    SmartDashboard.putNumber("Arm Angle", ((readEncoder() - RobotMap.Values.armFrontParallel) * RobotMap.Values.ticksToRadiansArm));
    //SmartDashboard.putBoolean("Arm/Disc Brake state: ", discBrake.get());
    //SmartDashboard.putBoolean("Arm/Arm forward limit switch", getForwardLimitSwitch());

    SmartDashboard.putNumber("Arm/Arm F", pidController.getFF());
    //SmartDashboard.putBoolean("Arm/Arm Front Limit Switch", frontLimitSwitch.get());
    //SmartDashboard.putBoolean("Arm/Arm Back Limit Switch", backLimitSwitch.get());
    SmartDashboard.putNumber("Arm/Arm voltage", sparkMax.getAppliedOutput());
    SmartDashboard.putNumber("Arm/Arm Internal Read", internalEncoder.getPosition());
    //SmartDashboard.putNumber("Arm/Arm Current", getCurrent());
    //SmartDashboard.putNumber("Arm/Arm Motor Temp", getMotorTemp());
  }


//Started copying over Hunter's PID code from SetArmPosition cuz PID ain't
//supposed to go in commands.
  public boolean pidError(double setpoint, double tolerance) {
    return (readEncoder() > setpoint - tolerance) && (readEncoder() < setpoint + tolerance);
  }

}