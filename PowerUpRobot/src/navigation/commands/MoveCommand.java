package navigation.commands;

import subsystems.DriveTrain;

public class MoveCommand extends Command {
	public double distance;
	DriveTrain driveTrain;
	
	public MoveCommand(double dist, DriveTrain dt) {
		this.distance = dist;
		driveTrain = dt;
	}
	
	@Override
	public void execute() {
		driveTrain.driveDistance(distance);
	}
}
