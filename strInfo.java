package map;

public enum strInfo {
	STRAIGHT("����"), RIGHT("��ȸ��"), LEFT("��ȸ��"), END("�������α�"),
	RIGHTSIGHT("��������"), LEFTSIGHT("��������"), FRONT("��������"), OPPOSITE("�ݴ��"),
	UP("��"), DOWN("��"), L("��"), R("��");
	
	private String str;
	strInfo(String str){
		this.str=str;
	}
	public String getStr() {
		return str;
	}
}
