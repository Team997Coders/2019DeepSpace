package frc.robot.vision;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action1;
import com.github.oxo42.stateless4j.delegates.FuncBoolean;
import com.github.oxo42.stateless4j.transitions.Transition;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.AutoDriveToTarget;

/**
 * Implements a state machine to enforce proper sequencing
 * and execution of camera control as the user interacts
 * with vision gamepad and the heads up display.
 */
public class CameraControlStateMachine {
  private final StateMachine<State, Trigger> stateMachine;
  private final NetworkTable visionNetworkTable;
  private final static String STATEKEY = "State";
  private final static String TRIGGERKEY = "Trigger";
  private final static String FIREKEY = "Fire";
  private double tiltRate;
  private double panRate;

  /**
   * Convenience constructor wired to Robot
   */
  public CameraControlStateMachine() {
    this(new TargetSelector(Robot.visionNetworkTable), 
      Robot.visionNetworkTable, 
      new AutoDriveToTarget()
    );
  }

  /**
   * Constructor that uses the default state machine configuration.
   * 
   * @param targetSelector      A target selector which determines which buttons are pressable for selecting targets at any given time.
   * @param visionNetworkTable  The network table for communicating state interapp.
   * @param autoDriveToTarget   Command to start when driver indicates time to auto drive to target.
   */
  public CameraControlStateMachine(TargetSelector targetSelector, 
      NetworkTable visionNetworkTable, 
      Command autoDriveToTarget) {
    this(targetSelector, 
      visionNetworkTable, 
      autoDriveToTarget, 
      new StateMachine<>(State.IdentifyingTargets, 
        GetConfig(targetSelector, visionNetworkTable, autoDriveToTarget)
      )
    );
  }

  /**
   * Construct a CameraControlStateMachine passing in the configured state machine for camera control interaction.
   * 
   * @param targetSelector      A target selector which determines which buttons are pressable for selecting targets at any given time.
   * @param visionNetworkTable  The network table for communicating state interapp.
   * @param autoDriveToTarget   Command to start when driver indicates time to auto drive to target.
   * @param stateMachine        A state machine which can be obtained by calling the GetConfig static.
   */
  public CameraControlStateMachine(TargetSelector targetSelector, 
      NetworkTable visionNetworkTable, 
      Command autoDriveToTarget, 
      StateMachine<State, Trigger> stateMachine) {
    this.stateMachine = stateMachine;
    this.visionNetworkTable = visionNetworkTable;
    this.tiltRate = 0;
    this.panRate = 0;
    // Set up a listener to fire state changes as sent by the heads up display running on the Pi
    visionNetworkTable.addEntryListener(FIREKEY, (table, key, entry, value, flags) -> {
        CameraControlStateMachine.Trigger trigger = Enum.valueOf(CameraControlStateMachine.Trigger.class, value.getString());
        if (trigger == CameraControlStateMachine.Trigger.FailedToLock) {
          this.failedToLock();
        } else if (trigger == CameraControlStateMachine.Trigger.LoseLock) {
          this.loseLock();
        } else if (trigger == CameraControlStateMachine.Trigger.IdentifyTargets) {
          this.identifyTargets();
        }
      }, 
      EntryListenerFlags.kNew | EntryListenerFlags.kUpdate
    );
  }

  /**
   * Produces a configuration of the state machine so that it can be instantiated.
   * 
   * @param targetSelector      A reference to the TargetSelector so that the state machine
   *                            can make correct target selectiion decisions when user selects targets.
   * @param visionNetworkTable  The network table to write current state so remote HUD knows what state we
   *                            are in.
   * @return                    The configuration to feed the CameraControlStateMachine constructor.
   * @see                       https://github.com/oxo42/stateless4j
   */
  private static StateMachineConfig<State, Trigger> GetConfig(TargetSelector targetSelector, 
      NetworkTable visionNetworkTable, 
      Command autoDriveToTarget) {
    StateMachineConfig<State, Trigger> config = new StateMachineConfig<>();

    // It would be super nice to use the permitIfOtherwiseIgnore function,
    // but alas it is not available in a release version.
    config.configure(State.IdentifyingTargets)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.IdentifyingTargets.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
        }
      })
      .permit(Trigger.Slew, State.Slewing)
      .permitReentry(Trigger.IdentifyTargets)
      .permit(Trigger.LeftThumbstickButton, State.Centering)
      .permit(Trigger.LeftShoulderButton, State.Calibrating)
      .permitIf(Trigger.AButton, State.SlewingToTarget, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return targetSelector.isTriggerValid(Trigger.AButton);
        }
      })
      .permitIf(Trigger.BButton, State.SlewingToTarget, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return targetSelector.isTriggerValid(Trigger.BButton);
        }
      })
      .permitIf(Trigger.XButton, State.SlewingToTarget, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return targetSelector.isTriggerValid(Trigger.XButton);
        }
      })
      .permitIf(Trigger.YButton, State.SlewingToTarget, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return targetSelector.isTriggerValid(Trigger.YButton);
        }
      })
      .ignoreIf(Trigger.AButton, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return !targetSelector.isTriggerValid(Trigger.AButton);
        }
      })
      .ignoreIf(Trigger.BButton, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return !targetSelector.isTriggerValid(Trigger.BButton);
        }
      })
      .ignoreIf(Trigger.XButton, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return !targetSelector.isTriggerValid(Trigger.XButton);
        }
      })
      .ignoreIf(Trigger.YButton, new FuncBoolean() {
        @Override
        public boolean call() {
          targetSelector.getValidTriggers();
          return !targetSelector.isTriggerValid(Trigger.YButton);
        }
      });
    
    config.configure(State.Slewing)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.Slewing.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
        }
      })
      .permitReentry(Trigger.Slew)
      .permit(Trigger.LeftThumbstickButton, State.Centering)
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .ignore(Trigger.LeftShoulderButton)
      .ignore(Trigger.AButton)
      .ignore(Trigger.BButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton);

    config.configure(State.Centering)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.Centering.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
        }
      })
      .permitReentry(Trigger.LeftThumbstickButton)
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .ignore(Trigger.LeftShoulderButton)
      .ignore(Trigger.AButton)
      .ignore(Trigger.Slew)
      .ignore(Trigger.BButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton);

    config.configure(State.SlewingToTarget)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.SlewingToTarget.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString(transition.getTrigger().toString());
      }})
      .permit(Trigger.LockOn, State.TargetLocked)
      .permit(Trigger.FailedToLock, State.LockFailed)
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.BButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.TargetLocked)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.TargetLocked.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
      }})
      .permit(Trigger.BButton, State.DrivingToTarget)
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .permit(Trigger.LoseLock, State.LockLost)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.LockFailed)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.LockFailed.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
      }})
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.AButton)
      .ignore(Trigger.BButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.LockLost)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.LockLost.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
      }})
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.AButton)
      .ignore(Trigger.BButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.DrivingToTarget)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.DrivingToTarget.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
          autoDriveToTarget.start();
      }})
      .permit(Trigger.LoseLock, State.LockLost)
      .permit(Trigger.BButton, State.TargetLocked)
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.Calibrating)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.Calibrating.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).setString("");
      }})
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .permit(Trigger.IdentifyTargets, State.IdentifyingTargets)
      .ignore(Trigger.BButton)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.LeftShoulderButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton);

    config.configure(State.AutoLocking)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition){
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.Calibrating.toString());
          visionNetworkTable.getEntry(CameraControlStateMachine.TRIGGERKEY).getString("");          
        }
      })
      .permit(Trigger.Slew, State.AutoLocking)
      .ignore(Trigger.BButton)
      .ignore(Trigger.AButton)
      .ignore(Trigger.IdentifyTargets)
      .ignore(Trigger.LoseLock)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);
    return config;
  }
  

  public void aButtonPressed() {
    stateMachine.fire(Trigger.AButton);
  }

  public void bButtonPressed() {
    stateMachine.fire(Trigger.BButton);
  }

  public void xButtonPressed() {
    stateMachine.fire(Trigger.XButton);
  }

  public void yButtonPressed() {
    stateMachine.fire(Trigger.YButton);
  }

  /**
   * By firing this slew trigger, the state machine will figure
   * out whether slewing can be done under operator control and
   * then make the rates available to a consumer.
   * 
   * @param panRate   A number between -1..1 which specifies rate of maximum pan.
   * @param tiltRate  A number between -1..1 which specifies rate of maximum tilt.
   */
  public void slew(double panRate, double tiltRate) {
    // If we are slewing...
    if (panRate != 0 || tiltRate != 0 ) {
      if (stateMachine.canFire(Trigger.Slew)) {
        this.panRate = panRate;
        this.tiltRate = tiltRate;
        stateMachine.fire(Trigger.Slew);
      }
    } else if (panRate == 0 && tiltRate == 0 && stateMachine.getState() == State.IdentifyingTargets) {
      // do nothing as there is nothing to slew given that we are already identifying targets
    } else if (panRate == 0 && tiltRate == 0 && stateMachine.getState() == State.Slewing) {
      // Assume we went from slewing to now not
      this.panRate = 0;
      this.tiltRate = 0;
      stateMachine.fire(Trigger.IdentifyTargets);  
    }
  }

  public double getTiltRate() {
    return tiltRate;
  }

  public double getPanRate() {
    return panRate;
  }

  public void leftThumbstickButtonPressed() {
    stateMachine.fire(Trigger.LeftThumbstickButton);
  }

  public void identifyTargets() {
    stateMachine.fire(Trigger.IdentifyTargets);
  }

  public State getState() {
    return stateMachine.getState();
  }

  public void failedToLock() {
    stateMachine.fire(Trigger.FailedToLock);
  }

  public void lockOn() {
    stateMachine.fire(Trigger.LockOn);
  }

  public void loseLock() {
    stateMachine.fire(Trigger.LoseLock);
  }

  public void leftShoulderButtonPressed() {
    stateMachine.fire(Trigger.LeftShoulderButton);
  }

  public void rightShoulderButtonPressed() {
    // TODO
    throw new RuntimeException("Implement me please!");
  }

  /**
   * Get the selected, locked on target, so that we can approach it.
   * 
   * @return  A SelectedTarget that gives you the data you need to autonomously approach.
   * @throws TargetNotLockedException If there is no locked target.
   */
  public SelectedTarget getSelectedTarget() throws TargetNotLockedException {
    if (getState() == State.TargetLocked || getState() == State.DrivingToTarget) {
      SelectedTarget selectedTarget = new SelectedTarget(visionNetworkTable);
      return selectedTarget;
    } else {
      throw new TargetNotLockedException();
    }
  }

  /**
   * The valid states of the state machine.
   */
  public enum State {
    IdentifyingTargets, SlewingToTarget, TargetLocked, LockFailed, LockLost, DrivingToTarget, Slewing, Centering, Calibrating, AutoLocking
  }

  /**
   * Triggers that cause state transitions.
   */
  public enum Trigger {
    AButton, BButton, XButton, YButton, LockOn, FailedToLock, LoseLock, IdentifyTargets, Slew, LeftThumbstickButton, LeftShoulderButton
  }
}