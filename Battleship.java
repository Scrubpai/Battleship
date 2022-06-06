public class Battleship {
	// Properties
	private int intP1Health = 17; // 5 + 4 + 3 + 3 + 2
	private int intP2Health = 17; // 5 + 4 + 3 + 3 + 2
	private char chrBattleShips[][][] = new char[3][11][11];
	private char chrMiniMaps[][][] = new char[3][11][11];
	public int intTurn = 1; // P1
	public int intP1Wins = 0;
	public int intP2Wins = 0;
	
	// Methods
	public void resetBoard() {
		for (int intPlayer=1; intPlayer<=2; intPlayer++) {
			for (int intRow=1; intRow<=10; intRow++) {
				for (int intCol=1; intCol<=10; intCol++) {
					chrBattleShips[intPlayer][intRow][intCol] = 'O'; // Nothing
					chrMiniMaps[intPlayer][intRow][intCol] = 'E'; // Empty
				}
			}
		}
	}
	
	// Constructor
	
}
