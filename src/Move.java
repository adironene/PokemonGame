import java.awt.*;

public class Move {
	private String name;
	private String type;
	private int power;
	private int pp;
	private int accuracy;
	private Image image;
	int x = 100;
	int y = 409;
static final double[][] moveChart = new double[][] { 
			{1.0,1.0,1.0,1.0,1.0,1.0,1.0},
			{1.0,0.5,0.5,1.0,2.0,1.0,2.0}, 
			{1.0,2.0,0.5,1.0,0.5,1.0,1.0}, 
			{1.0,1.0,2.0,0.5,0.5,1.0,1.0},
			{1.0,0.5,2.0,1.0,0.5,0.5,0.5}, 
			{1.0,1.0,1.0,1.0,1.0,2.0,0.5},
			{1.0,0.5,1.0,1.0,2.0,0.5,1.0}
	};

public Move(String mov, String typ, int hpdamage, int hitmuch, int powerpoint) {
		name = mov;
		type = typ;
		power = hpdamage;
		accuracy = hitmuch;
		pp = powerpoint;

	}
	// guys idk if how to incorporate pp

	// checks if the move worked based on accuracy

	public boolean ifHit() {
		double acc = (double) accuracy / 100;
		double randomCheck = Math.random();
		if (pp < 1)
			return false;
		// do you guys mean else if?
		if (acc > randomCheck)
			return true;
		return false;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	// returns the power a move has on other pokemon, maybe move to another class?
	public int totalPower(pokemon other) {
		int oType = other.typeToNum();
		int thisType = this.typeToNum();

		double mult = moveChart[thisType][oType];
		double total = (double) this.power * mult;
		return (int) total;

	}

	public void decreasePP() {
		pp--;
	}

	public int typeToNum() {
		int typeNum=-1;
		String type = this.type.toLowerCase();
		if (type.equals("normal")) {
			typeNum = 0;
		
		}
		if (type.equals("fire")) {
			typeNum = 1;
	}
		if (type.equals("water")) {
			typeNum = 2;
		}
		if (type.equals("electric")) {
			typeNum = 3;

		}
		if (type.equals("grass")) {
			typeNum = 4;
			//emily
		}
		if (type.equals("poison")) {
			typeNum = 5;
			//emily
		} 
		if (type.equals("bug")) {
			typeNum = 6;
			//phoebe
		} 
		return typeNum;

	}

	public String getStringImg() {
		if (this.typeToNum() == 1)
			return "MovesFlameThrower/fireThrow1.png";
		return null;
	}

	public void animate(Graphics g) {
		// if(this.typeToNum() ==1) {

		// }
	}

}
// type order: normal, fire, water, electric, grass, poison, bug
