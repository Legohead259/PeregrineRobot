package subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class ShiftingGearbox {
	Solenoid soleHighL = new Solenoid(0, 1111); //TODO: Configure
	Solenoid soleHighR = new Solenoid(0, 1111); //TODO: Configure
	Solenoid soleLowL = new Solenoid(0, 1111); //TODO: Configure
	Solenoid soleLowR = new Solenoid(0, 1111); //TODO: Configure
	boolean low = true;
	
	public void shift() {
		low = !low;
		soleHighL.set(!low);
		soleHighR.set(!low);
		soleLowL.set(low);
		soleLowR.set(low);
	}
}
