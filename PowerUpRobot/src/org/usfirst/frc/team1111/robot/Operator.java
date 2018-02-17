package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Joystick;
import subsystems.Shooter;
import variables.ControllerMap;

public class Operator {
	Joystick joy = new Joystick(ControllerMap.OPERATOR_PORT);
	Shooter shooter;
	
	public Operator(Shooter s) {
		shooter = s;
	}
	
	
	//=====OPERATOR METHODS=====
	
	
	/**
	 * Standard wrapper method for the operator
	 */
	public void operate() {
		boolean boxPos = shooter.getBoxPos();
		
		//Intake logic
		if (joy.getRawButton(ControllerMap.INTAKE) && !boxPos) { //INTAKES box
			shooter.intake(shooter.INTAKE_POWER);
		}
		else if (joy.getRawButton(ControllerMap.OUTTAKE)) { //OUTAKES box
			shooter.intake(-1);
		}
		else { //STOPS intake
			shooter.intake(0);
		}
		
		//SINGLE button shooter logic
		if (joy.getRawButton(ControllerMap.SWITCH_SHOOT) && boxPos) { //Shoots for SWITCH as long as it is held down
			shooter.shoot(shooter.SWITCH_SPEED);
		}
		else if (joy.getRawButton(ControllerMap.SCALE_SHOOT) && boxPos) { //Shoots for SCALE as long as it is held down
			shooter.shoot(shooter.SCALE_SPEED);
		}
		else { //STOPS shooter
			shooter.setShooters(0);
		}
		
		//DUAL button shooter logic
//		if (joy.getRawButton(ControllerMap.SWITCH_SHOOT) && boxPos) { //Spins up for SWITCH
//			shooter.spinUp(shooter.SWITCH_SPEED);
//		}
//		else if (joy.getRawButton(ControllerMap.SCALE_SHOOT) && boxPos) { //Spins up for SCALE
//			shooter.spinUp(shooter.SCALE_SPEED);
//		}
//		else { //STOPS shooter
//			shooter.setShooters(0);
//		}
//		if (joy.getRawButton(ControllerMap.SHOOT) && shooter.spunUp) { //Pushes box into shooter when motors are spun up
//			shooter.intake(shooter.INTAKE_POWER);
//		}
		
		//Piston logic
		if (joy.getPOV() == ControllerMap.UP_DPAD) { //RAISE shooter to 60 degrees
			shooter.changeAngle(1);
		}
		else if (joy.getPOV() == ControllerMap.DOWN_DPAD) { //LOWER shooter to 30 degrees
			shooter.changeAngle(0);
		}
	}
}
