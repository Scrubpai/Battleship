public class Battleship {
	// Properties
	private int intP1Health = 17; // 5 + 4 + 3 + 3 + 2
	private int intP2Health = 17; // 5 + 4 + 3 + 3 + 2
	private Ship BattleShips[][] = new Ship[3][6];
	private char chrMiniMaps[][][] = new char[3][11][11];
	public int intTurn = 1; // P1
	public int intP1Wins = 0;
	public int intP2Wins = 0;
	
	// Methods
	public void resetBoard() {
		for (int intPlayer=1; intPlayer<=2; intPlayer++) {
			BattleShips[intPlayer][1] = new Ship("Destroyer", 2);
			BattleShips[intPlayer][2] = new Ship("Cruiser", 3);
			BattleShips[intPlayer][3] = new Ship("Submarine", 3);
			BattleShips[intPlayer][4] = new Ship("Battleship", 4);
			BattleShips[intPlayer][5] = new Ship("Carrier", 5);
			
			for (int intRow=1; intRow<=10; intRow++) {
				for (int intCol=1; intCol<=10; intCol++) {
					chrMiniMaps[intPlayer][intRow][intCol] = 'E'; // Empty
				}
			}
		}
	}
	
	// Constructor
	public Battleship() {
		for (int intCount=1; intCount<=2; intCount++) {
			BattleShips[intCount][1] = new Ship("Destroyer", 2);
			BattleShips[intCount][2] = new Ship("Cruiser", 3);
			BattleShips[intCount][3] = new Ship("Submarine", 3);
			BattleShips[intCount][4] = new Ship("Battleship", 4);
			BattleShips[intCount][5] = new Ship("Carrier", 5);
		}
	}
}
