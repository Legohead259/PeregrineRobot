package subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import util.MiniPID;
import variables.Motors;
import util.Utility;

public class DriveTrain {
	//NavX PID Variables
	double pN = 0.0175, iN = 0.0000001, dN = 0.1125; //TODO: Calibrate
	AHRS navx = new AHRS(SPI.Port.kMXP);
	MiniPID pidNavx = new MiniPID(pN, iN, dN);
	
	//Encoder PID Variables
	double pT = .00001, iT = 0, dT = 0; //TODO: Calibrate
	MiniPID pidDist = new MiniPID(pT, iT, dT);
	final int TPRE = 4096; //Ticks/Encoder Revolution - Multiply by #GEAR_RATIO to get Ticks/Wheel Revolution
	final int GEAR_RATIO = 8; //Encoder shaft revolutions/wheel shaft revolutions
	final int TPRW = TPRE * GEAR_RATIO; //Ticks/Wheel Revolution
	final int WHEEL_CIRCUMFERENCE = (int) (4 * Math.PI); //Inches
	final int DPT = TPRW / WHEEL_CIRCUMFERENCE; //Ticks/inch
	
	//Gear Shifter Variables
	DoubleSolenoid shifter = new DoubleSolenoid(0, 1);
	boolean low = false;
	
	//Standard Variables
	double speed;
	
	
	public DriveTrain() {
		//Configure encoder PID
		Motors.driveBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Motors.TIMEOUT);
		Motors.driveBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Motors.TIMEOUT);
		Utility.resetEncoder(Motors.driveBackLeft); 
		Utility.resetEncoder(Motors.driveBackRight);
		Utility.configurePID(pT, iT, dT, pidDist);
				
		//Configure NavX PID
		navx.reset();
		Utility.configurePID(pN, iN, dN, pidNavx);
	}
	
	
	//=====STANDARD METHODS=====
	
	
	/**
	 * Drives at a given speed
	 * Used as a wrapper for easier PID-control
	 * @param speed: speed for the motors.
	 */
	public void drive(double speed) {
		speed *= 1; //Use this to set a soft limit
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, speed);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, speed);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -speed);
		Motors.driveBackRight.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Drives at a given speed
	 * Standard tank drive method
	 * @param left: speed for the left motors
	 * @param right: speed for the right motors
	 */
	public void drive(double left, double right) {
		left *= 1; right *= -1; //Use this to set a soft limit
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, left);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, right);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -left);
		Motors.driveBackRight.set(ControlMode.PercentOutput, -right);
	}
	
	public void shiftGear() {
		low = !low;
		
		if (low) { //Shift to LOW gear
			shifter.set(Value.kForward);
		}
		else if (!low) { //Shift to HIGH gear
			shifter.set(Value.kReverse);
		}
		
		SmartDashboard.putBoolean("Low Gear:", low); //NOT A DEBUG
	}
	
	
	//=====PID METHODS=====
	
	
	public void driveDistance(double dist) {
		int desTicks = (int) (DPT * dist);
		pidDist.setSetpoint(desTicks);
		int ticks = Motors.driveBackRight.getSelectedSensorPosition(0);
		speed = pidDist.getOutput(ticks);
		drive(speed);
	}
	
	public void turnTo(double desAngle) {
		double curAngle = navx.getAngle();		
		pidNavx.setSetpoint(desAngle);
		speed = pidNavx.getOutput(curAngle);		
		drive(speed, -speed);
	}
}
