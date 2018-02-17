package subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import util.MiniPID;
import variables.Motors;
import util.Utility;

public class DriveTrain {
	//===NAVX PID VARIABLES===
	static double pN = 0.0175, iN = 0.0000001, dN = 0.1125; //TODO: Calibrate
	static AHRS navx = new AHRS(SPI.Port.kMXP);
	static MiniPID pidNavx = new MiniPID(pN, iN, dN);
	
	//===ENCODER PID VARIABLES===
	static double pT = .00001, iT = 0, dT = 0; //TODO: Calibrate
	static MiniPID pidDist = new MiniPID(pT, iT, dT);
	static final int TPRE = 2048; //Ticks/Encoder Revolution - Multiply by #GEAR_RATIO to get Ticks/Wheel Revolution
	static final int GEAR_RATIO = 8; //Encoder shaft revolutions/wheel shaft revolutions
	static final int TPRW = TPRE * GEAR_RATIO; //Ticks/Wheel Revolution
	static final int WHEEL_CIRCUMFERENCE = (int) (4 * Math.PI); //Inches
	static final int DPT = TPRW / WHEEL_CIRCUMFERENCE; //Ticks/inch
	
	static double speed;
	
	public static void driveTrainInit() {
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
	
	public static void drive(double speed) {
		speed *= 1; //Use this to set a soft limit
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, -speed);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, speed);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -speed);
		Motors.driveBackRight.set(ControlMode.PercentOutput, speed);
	}
	
	public static void drive(double left, double right) {
		left *= 1; right *= -1; //Use this to set a soft limit
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, left);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, right);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, left);
		Motors.driveBackRight.set(ControlMode.PercentOutput, right);
	}
	
	public static void driveDistance(double dist) {
		int desTicks = (int) (DPT * dist);
		pidDist.setSetpoint(desTicks);
		int ticks = Motors.driveBackRight.getSelectedSensorPosition(0);
		speed = pidDist.getOutput(ticks);
		drive(speed);
	}
	
	public static void turnTo(double desAngle) {
		double curAngle = navx.getAngle();		
		pidNavx.setSetpoint(desAngle);
		speed = pidNavx.getOutput(curAngle);		
		drive(speed, -speed);
	}
}
