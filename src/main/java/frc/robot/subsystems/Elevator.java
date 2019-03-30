/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import frc.robot.commands.LockElevator;
import frc.robot.data.ElevatorData;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.CANifier;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANSparkMax.IdleMode;
/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  private CANSparkMax master, follower;

  private CANEncoder encoder;

  private double rampAccel = 0.5; // Use this value to see if the elevator is actually being deccelerated

  private CANPIDController pidController;
  private CANDigitalInput limitSwitchTop;
  private CANDigitalInput limitSwitchBottom;
  private CANifier canifier;
  //public int index = 0;
  //public double[]  heightList;
  public boolean gamePieceType; 
  //This is to switch between balls and hatches for elevator heights.
  //// Balls = true Hatches = false
  public boolean isZeroed;

  public Elevator() {
    master = new CANSparkMax(RobotMap.Ports.masterElevatorMotor, MotorType.kBrushless);
    follower = new CANSparkMax(RobotMap.Ports.followerElevatorMotor, MotorType.kBrushless);
    
    master.restoreFactoryDefaults();
    follower.restoreFactoryDefaults();

    encoder = master.getEncoder();
    encoder.setPosition(0);
    encoder.setPositionConversionFactor(42);

    // This line must be this way now that the canifiers are shared recources
    canifier = Robot.elevatorCanifier;
    limitSwitchTop = new CANDigitalInput(master, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyOpen);
    limitSwitchTop.enableLimitSwitch(true);
    
    limitSwitchBottom= new CANDigitalInput(master, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);
    limitSwitchBottom.enableLimitSwitch(true);

    //master.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);

    master.setIdleMode(IdleMode.kBrake);
    follower.setIdleMode(IdleMode.kBrake);

    master.setInverted(true);
    follower.setInverted(true);

    follower.follow(master, true); // reverse the follower in the follow command

    //master.setOpenLoopRampRate(0.25);
    //follower.setOpenLoopRampRate(0.25); // Not sure if this is need for the follower motor but just in case

    pidController = master.getPIDController();
    pidController.setOutputRange(-0.3, 0.5);
    pidController.setP(RobotMap.Values.elevatorPidP);
    pidController.setI(RobotMap.Values.elevatorPidI);
    pidController.setD(RobotMap.Values.elevatorPidD);
    pidController.setFF(RobotMap.Values.elevatorPidF);
    
    pidController.setReference(0.0/*total - current*/, ControlType.kPosition);

    SetPosition(GetPosition());
    isZeroed = limitSwitchBottom.get();

    SmartDashboard.putNumber("Elevator/Elevator Pid P", RobotMap.Values.elevatorPidP);
    SmartDashboard.putNumber("Elevator/Elevator Pid I", RobotMap.Values.elevatorPidI);
    SmartDashboard.putNumber("Elevator/Elevator Pid D", RobotMap.Values.elevatorPidD);
    SmartDashboard.putNumber("Elevator/Elevator Pid F", RobotMap.Values.elevatorPidF);
  }

  public void SetPosition(double height) {
    //System.out.println("Set elevator to go to height " + height); 
    pidController.setReference(height - GetPosition(), ControlType.kPosition);
  }

  public void resetElevatorEncoder() {
    canifier.setQuadraturePosition(0, 10);
  }

  public int GetPosition() {
    return canifier.getQuadraturePosition();
  }

  public double getInternalEncoderPos() {
    return encoder.getPosition();
  }

  public boolean GetBottomLimitSwitch(){
    return limitSwitchBottom.get();
  }

  public double getMasterTemp() {
    return master.getMotorTemperature();
  }

  public double getFollowerTemp() {
    return follower.getMotorTemperature();
  }

  public void Stop(){
    master.set(0);
  }

  public void SetPower(double volts){
    master.set(volts);
  }

  /**
   * This function processes the power given and limits it based on position and current
   * speed to determine an appropriate speed to go at for a smooth, nice elevator.
   * It probably doesn't work... PLEASE ADJUST THE ACCELERATION INSTEAD OF DELETING / NOT USING THIS
   * 
   * @param pow The desired power which you shall not receive
   * 
   * Timothy: Our cpu usage is hella high.
   * Hunter: Let me run this large processing function to determine the speed we should go at.
   * Timothy: but the RIO is gonna die...
   * Hunter: ... i commented it....
   * Timothy: Bad Hunter.
   * Hunter: It's fine I'll just disable the logger.
   * [Tests robot]
   * Hunter and Timothy: ....
   * Hunter: It's doing some weird stuff...
   * Timothy: If only we had the logger... >:C
   */
  public void setDeccelPower(double pow) {
    double last = master.get(); // The last set power to the motor
    double hek = pow;
    boolean didMod = false; // Did I need to alter the power

    double deltaTime = Robot.getDeltaTime();

    if (Math.abs(pow) > Math.abs(last) + (rampAccel * deltaTime)) { // Did is the motor going to over accelerate?
      hek = last + ((last / Math.abs(last)) * (rampAccel * deltaTime)); // Limit how much it changes
      didMod = true; // Record it
    }

    if (didMod) { // Did you mod it?
      if (hek < 0) { // Is the new values moving up
        if ((GetPosition() < RobotMap.Values.bottomElevatorAccelPosLimit) && (hek < RobotMap.Values.bottomElevatorLimitVelocity)) { // Is it approching the bottom of the elevator and is going rather fast?
          hek = RobotMap.Values.bottomElevatorLimitVelocity; // Limit the velocity even more
        }
      } else if ((GetPosition() > RobotMap.Values.topElevatorAccelPosLimit) && (hek > RobotMap.Values.topElevatorLimitVelocity)) { // Is it approching the top of the elevator and is going rather fast?
        hek = RobotMap.Values.topElevatorLimitVelocity; // Limit the velocity even more
      }
    }

    SetPower(hek); // Apply new velocity
  }

  public double CtreToSparkEncoder(double ctre) {
    return ((ctre / 1024) / 2.5) * 42;
  }

  public void ZeroElevator(){

    if (limitSwitchBottom.get()){

      resetElevatorEncoder();
      isZeroed = true;
    } 
  }

  /*public void incrementIndex() {
    index++;
    if (index > heightList.length - 1) {
      index = heightList.length - 1;
    }
  }

  public void decrementIndex() {
    index--;
    if(index < 0) {
      index = 0;
    }
  }
  public double getHeightFromArray() {
    return heightList[index];
  }*/
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LockElevator());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public ElevatorData getElevatorData() {
    ElevatorData e = new ElevatorData();
    e.output = master.getAppliedOutput();
    e.current = master.getOutputCurrent();
    e.ticks = GetPosition();
    e.velocity = canifier.getQuadratureVelocity();
    e.bottom = GetBottomLimitSwitch();
    e.top = limitSwitchTop.get();

    return e;
  }

  public void updatePID() {
    pidController.setP(SmartDashboard.getNumber("Elevator Pid P", RobotMap.Values.elevatorPidP));
    pidController.setI(SmartDashboard.getNumber("Elevator Pid I", RobotMap.Values.elevatorPidI));
    pidController.setD(SmartDashboard.getNumber("Elevator Pid D", RobotMap.Values.elevatorPidD));
    pidController.setFF(SmartDashboard.getNumber("Elevator Pid F", RobotMap.Values.elevatorPidF));
  }

  public void updateSmartDashboard() {
    //SmartDashboard.putNumber("Elevator/Elevator volts", master.get());
    SmartDashboard.putNumber("Elevator/Elevator Height: ", GetPosition());
    SmartDashboard.putBoolean("Elevator/Bottom Limit Switch", limitSwitchBottom.get());
    SmartDashboard.putBoolean("Elevator/Top Limit Switch", limitSwitchTop.get());
    //SmartDashboard.putNumber("Elevator/Elevator", master.getOutputCurrent());
    //SmartDashboard.putNumber("Elevator/Elevator Internal Encoder", getInternalEncoderPos());
    //SmartDashboard.putNumber("Elevator/Elevator Master Temp", getMasterTemp());
    //SmartDashboard.putNumber("Elevator/Elevator Follower Temp", getFollowerTemp());
    
  }
}
