public class Battleship {
	// Properties
	public int intP1Health = 17; // 5 + 4 + 3 + 3 + 2
	public int intP2Health = 17; // 5 + 4 + 3 + 3 + 2
	public Ship BattleShips[][] = new Ship[3][6];
	public char chrBattleShips[][][] = new char[3][11][11]; // S for ship, . for empty
	public char chrMiniMaps[][][] = new char[3][11][11]; // M for miss, H for hit
	public int intHealths[] = new int[6];
	public String strNames[] = new String[6];
	public int intTurn = 1; // P1
	public int intP1Wins = 0;
	public int intP2Wins = 0;
	
	// Methods
	public void resetBoard() {
		for (int intPlayer=1; intPlayer<=2; intPlayer++) {
			for (int intCount=1; intCount<=5; intCount++) {
				BattleShips[intPlayer][intCount] = null;
			}
			
			for (int intRow=1; intRow<=10; intRow++) {
				for (int intCol=1; intCol<=10; intCol++) {
					chrMiniMaps[intPlayer][intRow][intCol] = '.'; // Reset
					chrBattleShips[intPlayer][intRow][intCol] = '.'; // Reset
				}
			}
		}
	}
	
	public void nextTurn() {
		intTurn = (intTurn % 2) + 1;
		// 1 --> 2
		// 2 --> 1
	}
	
	public boolean placeShip(int intPlayer, int intCount, int intRow, int intCol, char chrOrientation) {
		// Returns true if the ship has been placed successfully
		if (chrOrientation == 'H') {
			for (int intC=intCol; intC<intCol+intHealths[intCount]; intC++) {
				if (intC > 10 || chrBattleShips[intPlayer][intRow][intC] == 'S') { // Out of bounds or already ship there
					return false;
				}
			}
			for (int intC=intCol; intC<intCol+intHealths[intCount]; intC++) {
				chrBattleShips[intPlayer][intRow][intC] = 'S';
			}
			
			BattleShips[intPlayer][intCount] = new Ship(strNames[intCount], intHealths[intCount], intRow, intCol, chrOrientation);
		} else if (chrOrientation == 'V') {
			for (int intR=intRow; intR<intRow+intHealths[intCount]; intR++) {
				if (intR > 10 || chrBattleShips[intPlayer][intR][intCol] == 'S') {
					return false;
				}
			}
			for (int intR=intRow; intR<intRow+intHealths[intCount]; intR++) {
				chrBattleShips[intPlayer][intR][intCol] = 'S';
			}
			
			BattleShips[intPlayer][intCount] = new Ship(strNames[intCount], intHealths[intCount], intRow, intCol, chrOrientation);
		}
		
		return true;
	}
	
	public int fire(int intTurn, int intRow, int intCol) {
		// Returns -1 if miss
		// Returns 0 if hit
		// Returns 1 if sunk ship 1 
		// Returns 2 if sunk ship 2
		// Returns 3 if sunk ship 3
		// Returns 4 if sunk ship 4
		// Returns 5 if sunk ship 5
		int intOpponent = (intTurn % 2) + 1;
		if (chrBattleShips[intOpponent][intRow][intCol] == '.') {
			chrMiniMaps[intTurn][intRow][intCol] = 'M';
			return -1;
		}
		
		if (intOpponent == 1) {
			intP1Health--;
		} else {
			intP2Health--;
		}
		chrBattleShips[intOpponent][intRow][intCol] = '.';
		chrMiniMaps[intTurn][intRow][intCol] = 'H';
		
		for (int intCount=1; intCount<=5; intCount++) {
			if (BattleShips[intOpponent][intCount].checkHit(intRow, intCol)) {
				if (BattleShips[intOpponent][intCount].intHealth == 0) {
					return intCount;
				} else {
					return 0;
				}
			}
		}
		
		return -1;
	}
	
	// Constructor
	public Battleship() {
		strNames[1] = "Destroyer";
		intHealths[1] = 2;
		
		strNames[2] = "Cruiser";
		intHealths[2] = 3;
		
		strNames[3] = "Submarine";
		intHealths[3] = 3;
		
		strNames[4] = "Battleship";
		intHealths[4] = 4;
		
		strNames[5] = "Carrier";
		intHealths[5] = 5;
		
		for (int intPlayer=1; intPlayer<=2; intPlayer++) {
			for (int intRow=1; intRow<=10; intRow++) {
				for (int intCol=1; intCol<=10; intCol++) {
					chrMiniMaps[intPlayer][intRow][intCol] = '.'; // Reset
					chrBattleShips[intPlayer][intRow][intCol] = '.'; // Reset
				}
			}
		}
	}
}
