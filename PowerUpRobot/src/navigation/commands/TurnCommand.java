package navigation.commands;

import subsystems.DriveTrain;

public class TurnCommand extends Command {
	double angle;
	
	public TurnCommand(double a) {
		angle = a;
	}
	
	public void execute() {
		DriveTrain.turnTo(angle);
	}
}
