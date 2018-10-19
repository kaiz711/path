package map;

public enum strInfo {
	STRAIGHT("직진"), RIGHT("우회전"), LEFT("좌회전"), END("목적지부근"),
	RIGHTSIGHT("우측으로"), LEFTSIGHT("좌측으로"), FRONT("정면으로"), OPPOSITE("반대로"),
	UP("↑"), DOWN("↓"), L("←"), R("→");
	
	private String str;
	strInfo(String str){
		this.str=str;
	}
	public String getStr() {
		return str;
	}
}
