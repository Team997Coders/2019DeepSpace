package frc.robot.vision;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action1;
import com.github.oxo42.stateless4j.delegates.FuncBoolean;
import com.github.oxo42.stateless4j.transitions.Transition;

import edu.wpi.first.networktables.NetworkTable;

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
  private final static String SELECTEDTARGETKEY = "SelectedTarget";
  private final static String RANGEININCHESKEY = "RangeInInches";
  private final static String CAMERAANGLEINDEGREES = "CameraAngleInDegrees";
  private final static String ANGLETOTARGETINDEGREES = "AngleToTargetInDegrees";
  private double tiltRate;
  private double panRate;

  public CameraControlStateMachine(TargetSelector targetSelector, NetworkTable visionNetworkTable) {
    this(targetSelector, visionNetworkTable, new StateMachine<>(State.IdentifyingTargets, GetConfig(targetSelector, visionNetworkTable)));
  }

  public CameraControlStateMachine(TargetSelector targetSelector, NetworkTable visionNetworkTable, StateMachine<State, Trigger> stateMachine) {
    this.stateMachine = stateMachine;
    this.visionNetworkTable = visionNetworkTable;
    this.tiltRate = 0;
    this.panRate = 0;
  }

  /**
   * Produces the configuration of the state machine so that it can be instantiated.
   * 
   * @param targetSelector   A reference to the TargetSelector so that the state machine
   *                         can make correct targeting decisions to drive the heads up display.
   * @return                 The configuration.
   */
  private static StateMachineConfig<State, Trigger> GetConfig(TargetSelector targetSelector, NetworkTable visionNetworkTable) {
    StateMachineConfig<State, Trigger> config = new StateMachineConfig<>();

    // It would be super nice to use the permitIfOtherwiseIgnore function,
    // but alas it is not available in a release version.
    config.configure(State.IdentifyingTargets)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.IdentifyingTargets.toString());
        }
      })
      .permit(Trigger.Slew, State.Slewing)
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
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .permit(Trigger.Slew, State.Slewing)
      .permit(Trigger.LeftThumbstickButton, State.Centering)
      .ignore(Trigger.BButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.TargetLocked)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.TargetLocked.toString());
      }})
      .permit(Trigger.BButton, State.DrivingToTarget)
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .permit(Trigger.LoseLock, State.LockLost)
      .permit(Trigger.Slew, State.Slewing)
      .permit(Trigger.LeftThumbstickButton, State.Centering)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.LockFailed)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(CameraControlStateMachine.STATEKEY).setString(State.LockFailed.toString());
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
      }})
      .permit(Trigger.LoseLock, State.LockLost)
      .permit(Trigger.BButton, State.TargetLocked)
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton)
      .ignore(Trigger.LeftShoulderButton);

    config.configure(State.Calibrating)
      .onEntry(new Action1<Transition<State,Trigger>>() {
        public void doIt(Transition<State, Trigger> transition) {
          visionNetworkTable.getEntry(STATEKEY).setString(State.Calibrating.toString());
      }})
      .permit(Trigger.AButton, State.IdentifyingTargets)
      .ignore(Trigger.BButton)
      .ignore(Trigger.Slew)
      .ignore(Trigger.LeftThumbstickButton)
      .ignore(Trigger.LeftShoulderButton)
      .ignore(Trigger.XButton)
      .ignore(Trigger.YButton);

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

  public void slew(double panRate, double tiltRate) {
    // If we are slewing...
    if (panRate != 0 || tiltRate != 0 ) {
      this.panRate = panRate;
      this.tiltRate = tiltRate;
      stateMachine.fire(Trigger.Slew);  
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
 * @return  A HatchTarget that gives you the data you need to autonomously approach.
 * @throws TargetNotLockedException If there is no locked target.
 */
  public HatchTarget getSelectedTarget() throws TargetNotLockedException {
    if (getState() == State.TargetLocked) {
      HatchTarget hatchTarget = new HatchTarget();
      NetworkTable selectedTargetTable = visionNetworkTable.getSubTable(SELECTEDTARGETKEY);
      hatchTarget.rangeInInches = selectedTargetTable.getEntry(RANGEININCHESKEY).getDouble(0);
      hatchTarget.cameraAngleInDegrees = selectedTargetTable.getEntry(CAMERAANGLEINDEGREES).getDouble(0);
      hatchTarget.angleToTargetInDegrees = selectedTargetTable.getEntry(ANGLETOTARGETINDEGREES).getDouble(0);
      return hatchTarget;
    } else {
      throw new TargetNotLockedException();
    }
  }

  /**
   * The valid states of the state machine.
   */
  public enum State {
    IdentifyingTargets, SlewingToTarget, TargetLocked, LockFailed, LockLost, DrivingToTarget, Slewing, Centering, Calibrating
  }

  /**
   * Triggers that cause state transitions.
   */
  public enum Trigger {
    AButton, BButton, XButton, YButton, LockOn, FailedToLock, LoseLock, IdentifyTargets, Slew, LeftThumbstickButton, LeftShoulderButton
  }
}