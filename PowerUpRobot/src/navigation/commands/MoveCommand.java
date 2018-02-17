package navigation.commands;

import subsystems.DriveTrain;

public class MoveCommand extends Command {
	public double distance;
	
	public MoveCommand(double dist) {
		this.distance = dist;
	}
	
	@Override
	public void execute() {
		DriveTrain.driveDistance(distance);
	}
}
