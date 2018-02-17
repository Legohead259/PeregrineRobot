package subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import variables.Motors;
import util.MiniPID;
import util.Utility;

public class Shooter {
	DigitalInput photosensor = new DigitalInput(0);
	double scalePower = .42; //TODO: Verify
	double switchPower = .25; //TODO: Verify
	double scaleSpeed = 32000; //TODO: Verify
	double switchSpeed = 1111; //TODO: Verify
	boolean up = false;
	boolean spunUp = false;
	DoubleSolenoid piston = new DoubleSolenoid(2, 3);
	double p = 0.000175, i = 0.00001, d = 0.0;
	MiniPID pid = new MiniPID(p, i, d);
	
	public Shooter() {
		Motors.shooterBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0,Motors.TIMEOUT);
		Motors.shooterBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Motors.TIMEOUT);
		Utility.configurePID(p, i, d, pid);
	}
	
	
	//=====OPERATOR METHODS=====
	
	
	/**
	 * Intakes the box at given speed
	 * @param speed: speed of the intake motors
	 */
	public void intake(double speed) {
		speed *= 1; //Use this to set soft motor speed limits
		Motors.intakeLeft.set(ControlMode.PercentOutput, speed);
		Motors.intakeRight.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Shoots the box at a given target
	 * @param target: the speed for shooting the box at
	 */
	public void shoot(double target) {
		double curSpeed;
		try {
			curSpeed = Motors.shooterBackLeft.getSelectedSensorVelocity(0);
		}
		catch (Exception exception) {
			curSpeed = Motors.shooterBackRight.getSelectedSensorVelocity(0);
			System.out.println(exception);
		}
		
		pid.setSetpoint(target);
		double speed = pid.getOutput(curSpeed);
		spinUp(speed);
		checkSpinUp(target, curSpeed);
	}
	
	/**
	 * Changes the angle of the shooter
	 * Used for SINGLE button toggle
	 */
	public void changeAngle() {
		up = !up;
		
		if (up) { //Angles piston to 60 degrees
			piston.set(Value.kReverse);
		}
		else if (!up) { //Angles piston to 30 degrees
			piston.set(Value.kForward);
		}
	}
	
	/**
	 * Changes angle of piston but based on state.
	 * Used for DUAL button changes
	 * @param state: the state of the shooter (0 for 30, 1 for 60)
	 */
	public void changeAngle(int state) {
		if (state == 0) { //30 degrees
			piston.set(Value.kForward);
		}
		else if (state == 1) { //60 degrees
			piston.set(Value.kReverse);
		}
	}
	
	
	//=====UTILITY METHODS=====
	
	
	/**
	 * Spins the motors up to a certain speed
	 * @param speed: speed of the shooter motors
	 */
	 private void spinUp(double speed) {
		speed *= 1; //Use this to set soft motor speed limits
		Motors.shooterFrontLeft.set(ControlMode.PercentOutput, speed);
		Motors.shooterFrontRight.set(ControlMode.PercentOutput, -speed);
		Motors.shooterBackLeft.set(ControlMode.PercentOutput, speed);
		Motors.shooterBackRight.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Checks if the shooter motors have been spun up to their max speed
	 */
	private void checkSpinUp(double target, double curSpeed) {
		double deadzone = 1000;
		
		SmartDashboard.putNumber("Left Motor Velocity:", Motors.shooterBackLeft.getSelectedSensorVelocity(0)); //Debug
		SmartDashboard.putNumber("Right Motor Velocity:", Motors.shooterBackRight.getSelectedSensorVelocity(0)); //Debug
		
		if (up && Utility.inRange(curSpeed, deadzone, target)) { //Scale spun-up
			spunUp = true;
		}
		else {
			spunUp = false;
		}
	}
}
