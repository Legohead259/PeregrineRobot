package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Joystick;
import subsystems.DriveTrain;
import variables.ControllerMap;

public class Driver {
	Joystick joy = new Joystick(ControllerMap.DRIVER_PORT);
	DriveTrain driveTrain = new DriveTrain();
	
	public Driver(DriveTrain dt) {
		driveTrain = dt;
	}
	
	public void drive() {
		driveTrain.drive(joy.getRawAxis(ControllerMap.LEFT_STICK), joy.getRawAxis(ControllerMap.RIGHT_STICK));
		
		if (joy.getRawButton(ControllerMap.SHIFT_GEAR)) {
			driveTrain.shiftGear();
		}
	}
}
