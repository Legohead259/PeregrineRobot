package variables;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Motors {
	//DRIVE TRAIN motors
	public static TalonSRX driveFrontLeft = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX driveFrontRight = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX driveBackLeft = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX driveBackRight = new TalonSRX(1111); //TODO: Configure
	
	//SHOOTER motors
	public static TalonSRX shooterFrontLeft = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX shooterFrontRight = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX shooterBackLeft = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX shooterBackRight = new TalonSRX(1111); //TODO: Configure
	
	//INTAKE motors
	public static TalonSRX intakeLeft = new TalonSRX(1111); //TODO: Configure
	public static TalonSRX intakeRight = new TalonSRX(1111); //TODO: Configure
	
	//Default speeds
	public static final double SWITCH_SPEED = .5;
	public static final double SCALE_SPEED = .7;
	public static final double INTAKE_SPEED = .5;
	
	//Miscellaneous values
	public static final int TIMEOUT = 200; //ms
}
