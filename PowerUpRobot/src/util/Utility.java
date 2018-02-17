package util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import variables.Motors;

public class Utility {
	public static void resetEncoder(TalonSRX motor) {
		motor.setSelectedSensorPosition(0, 0, Motors.TIMEOUT);
	}
	
	public static void configurePID(double p, double i, double d, MiniPID pid) {
		pid.setP(p); pid.setI(i); pid.setD(d);
		pid.setOutputLimits(-1, 1);
	}
	
	public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
	
	public static boolean inRange(double x, double bound, double target) {
		return x < target+bound && x > target-bound;
	}
}
