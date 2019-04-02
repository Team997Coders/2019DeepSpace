package frc.robot.commands;

public class TestComChain extends CommandChain {

  public TestComChain() {
    addSeq(new PrintMessage("First Command"));
    addSeq(new PrintMessage("Two Command"));
    addSeq(new PrintMessage("Three Command"));
  }

}