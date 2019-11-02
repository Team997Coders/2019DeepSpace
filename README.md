# 2019DeepSpace
Team 997's repository for the 2019 FRC game, Deep Space. Spoiler Alert, it's a dumpster fire
<h1>Subsystems</h1>
<h3>Drivetrain</h3>
  Our Drivetrain was a 6 TalonSRX drivetrain. We added motion profile capabilites but never properly implemented them.
<h3>Elevator</h3>
  The Elevator had 2 SparkMaxs and a CTRE Mag relative encoder to read position. We used to relative encoder because
  at the time we were skeptical about the internal encoders on the Neos. We used PID control to lock and move the elevator.
<h3>Arm</h3>
  Our Arm consisted of a SparkMax and an Absolute encoder. The absolute encoder was used to give us arm angle without a
  specified starting position.
<h3>Hatch Manipulator</h3>
  Our hatch manipulator was controlled by a Single Solenoid piston.
<h3>Cargo Intake</h3>
  The Cargo Intake was controlled by a VictorSPX to intake and release cargo.
<h3>Landing Gear</h3>
  We had one Single Solenoid that controlled the front landing gear and another Single Solenoid to control the back.
