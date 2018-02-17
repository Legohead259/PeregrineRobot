package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import subsystems.DriveTrain;
import subsystems.Shooter;

public class Robot extends IterativeRobot {
	//Subsystem Instantiation
	DriveTrain driveTrain = new DriveTrain();
	Shooter shooter = new Shooter();
	
	//Drive Team Instantiation
	Driver driver = new Driver(driveTrain);
	Operator operator = new Operator(shooter);
	
	@Override
	public void robotInit() {

	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopPeriodic() {
		driver.drive();
		operator.operate();
	}
}
