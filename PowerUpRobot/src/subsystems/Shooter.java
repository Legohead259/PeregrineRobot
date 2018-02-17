package subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import variables.Motors;
import util.Utility;

public class Shooter {
	DigitalInput photosensor = new DigitalInput(0);
	double scalePower = .7; //TODO: Verify
	double switchPower = .6; //TODO: Verify
	double scaleSpeed = 1111; //TODO: Verify
	double switchSpeed = 1111; //TODO: Verify
	boolean up = false;
	
	public Shooter() {
		Motors.shooterBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0,Motors.TIMEOUT);
		Motors.shooterBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Motors.TIMEOUT);
	}
	
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
	 * Shoots the box at the given motor speed
	 * @param speed: speed of the shooter motors
	 */
	public void shoot(double speed) {
//		SmartDashboard.putBoolean("Shooter Spun Up:", checkSpinUp());
		
		speed *= 1; //Use this to set soft motor speed limits
		Motors.shooterFrontLeft.set(ControlMode.PercentOutput, speed);
		Motors.shooterFrontRight.set(ControlMode.PercentOutput, -speed);
		Motors.shooterBackLeft.set(ControlMode.PercentOutput, speed);
		Motors.shooterBackRight.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Experimental function that checks if the shooter motors have been spun up to their max speed
	 * @return if the shooter is spun-up to full speed
	 */
	private boolean checkSpinUp() {
		double deadzone = 1111; //TODO: Configure
		double leftVelocity = Motors.shooterBackLeft.getSelectedSensorVelocity(0);
		double rightVelocity = Motors.shooterBackRight.getSelectedSensorVelocity(0);
		SmartDashboard.putNumber("Left Motor Velocity:", leftVelocity); //Debug
		SmartDashboard.putNumber("Right Motor Velocity:", rightVelocity); //Debug
		
		if (up && Utility.inRange(leftVelocity, deadzone, scaleSpeed) && Utility.inRange(rightVelocity, deadzone, scaleSpeed)) { //Scale spun-up
			return true;
		}
		else if (!up && Utility.inRange(leftVelocity, deadzone, switchSpeed) && Utility.inRange(rightVelocity, deadzone, switchSpeed)) { //Switch spun-up
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Experimental function that checks moves the piston for the shooting mechanism up/down
	 * @param dir: Direction for the piston to move
	 */
	public void movePiston(String dir) {
		if (dir.equals("d")) {
			//Move piston down
		}
		else if (dir.equals("u")) {
			//Move piston up
		}
	}
}
