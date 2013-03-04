package pack;

public class Internet extends Multiplayer{
	private Connection f;
	
	public int limit(long l) {
		return 10;
	}
	public int test() {
		return limit(0);
	}

}
