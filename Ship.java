public class Ship {
	// Properties
	public String strName;
	public int intHealth;
	public int intPositions[][];
	public char chrOrientation; // Or V for vertical
	public boolean blnAlive = true;
	
	// Methods
	public boolean checkHit(int intRow, int intCol) {
		for (int intCount=1; intCount<=intHealth; intCount++) {
			if (intPositions[intCount][0] == intRow && intPositions[intCount][1] == intCol) {
				intHealth--;
				if (intHealth == 0) {
					blnAlive = false;
				}
				return true;
			}
		}
		
		return false;
	}
	
	// Constructor
	public Ship(String strName, int intHealth, int intRow, int intCol, char chrOrientation) {
		this.strName = strName;
		this.intHealth = intHealth;
		intPositions = new int[intHealth+1][2];
		
		if (chrOrientation == 'H') { // Horizontal
			for (int intCount=1; intCount<=intHealth; intCount++) {
				intPositions[intCount][0] = intRow;
				intPositions[intCount][1] = intCol+intCount-1;
			}
		} else { // Vertical
			for (int intCount=1; intCount<=intHealth; intCount++) {
				intPositions[intCount][0] = intRow+intCount-1;
				intPositions[intCount][1] = intCol;
			}
		}
	}
	
	public Ship(String strName, int intHealth) {
		this.strName = strName;
		this.intHealth = intHealth;
		intPositions = new int[intHealth+1][2];
	}
}
