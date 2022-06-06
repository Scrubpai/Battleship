public class Ship {
	// Properties
	public String strName;
	public int intSize;
	private int intPositions[][];
	private char chrOrientation = 'H'; // Or V for vertical
	
	// Methods
	public boolean chkHit(int intRow, int intCol) {
		for (int intCount=1; intCount<=intSize; intCount++) {
			if (intPositions[intCount][0] == intRow && intPositions[intCount][1] == intCol) {
				return true;
			}
		}
		
		return false;
	}
	
	// Constructor
	public Ship(String strName, int intSize) {
		this.strName = strName;
		this.intSize = intSize;
		intPositions = new int[intSize+1][2];
	}
}
