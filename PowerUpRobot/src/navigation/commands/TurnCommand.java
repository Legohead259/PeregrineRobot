package navigation.commands;

import subsystems.DriveTrain;

public class TurnCommand extends Command {
	double angle;
	DriveTrain driveTrain;
	
	public TurnCommand(double a, DriveTrain dt) {
		angle = a;
		driveTrain = dt;
	}
	
	public void execute() {
		driveTrain.turnTo(angle);
	}
}
