package proj.model.vo;

public class Player {
	// - name:String
	// - hp:int = 100
	//
	// + Player()
	// + Player(name:String)
	// + Player(name:String, hp:int)
	//
	// + getter/setter()
	// + toString():String
	//		name님
	private String name;
	private int hp = 100;
	
	public Player() {}
	public Player(String name) {
		this.name = name;
	}
	public Player(String name, int hp) {
		this.name = name;
		this.hp = hp;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public String getName() {
		return name;
	}
	public int getHp() {
		return hp;
	}
	
	public String toString() {
		return name + "님";
	}
	
}
