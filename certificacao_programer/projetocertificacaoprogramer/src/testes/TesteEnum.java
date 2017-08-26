package testes;

public class TesteEnum {

	public enum Suits{
		CLUBS(20),
		NOTRUMP(40){
			public int getValue(int bid){
				return((bid-1)*30);
			}
		},
		SPADES(30);
		
		private Suits(int points) {
			this.points = points;
		}
		
		private int points;

		public int getPoints() {
			return points;
		}
		
	}
	
	public static void main(String[] args) {
		int x = Suits.SPADES.points;
	}
	
}
