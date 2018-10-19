package map;

import java.util.LinkedList;

/****************************************
 * 	  1	     -> �����Ҷ� X�� �������� 1�� UP(��)
 * 2  x  3   -> 2�� L(��) 3�� R(��)
 *    4      -> 4�� DOWN��(��)
 * ���⼭ ������ 3�̶�� ����
 * 1�� ����, 2�� �ݴ����, 3�� ����, 4�� �������� ��
 ***************************************/
public class searchRoot {
	
	static int matrix[][] = null;
    static LinkedList<Dot> path = new LinkedList<Dot>();
    static LinkedList<String> pathStr = new LinkedList<String>();

	public static void main(String[] args) {
		connecter();
		/* ��ֹ� ��ġ ���� & ���� ��ǥ �޾ƿ��°� �˷��� ���缭 �����ؾ���
		 * ��ֹ� ������ �۷� �Ȱ��� ���ο� ��� ã�°Ŵ� �̷��� �ϸ� �Ǵµ�
		 * ��ֹ� ��ġ ���� ���۵ȴ��� �� -> �˷��ּ�
		 * ��� ��Ż�� ������ �Ǵ��ؼ� ��Ż�ϸ� ���� ��ġ �����ؼ� ��Ž�� ���� �Ǵµ�
		 */
		/*if(flag==1){ 
			obstacle(new Dot(2,4));
		}else*/
		newMapRoad();
	}
	
	//DB����
	static void connecter() {
		databaseConnect dbConnecter = new databaseConnect();
		matrix = dbConnecter.connect();
	}
	//��ֹ� �־ �����ٰ� �ǴܵǴ� ��ǥ �� 0ó���� ������ġ�������� ��Ž�� & �ȳ�����
	static void obstacle(Dot dot) {
		matrix[dot.x][dot.y]=0;
		bfs(new Dot(1,1),new Dot(5,6));
		getPathStr();
	}
	//ó�� ������ �� ���Ž�� & �ȳ� ����
	static void newMapRoad() {
		bfs(new Dot(1,1),new Dot(5,6));
		getPathStr();
	}
	//���Ž�� (���� ����ġ�� ���� ���ͽ�Ʈ������, ������ ����ġ�� ���⶧���� bfs�� ���°� ����)
	static void bfs(Dot start, Dot end) {

        int[][] visited = matrix;
        LinkedList<Dot> queue = new LinkedList<Dot>();
        queue.add(start);
        
        while(!queue.isEmpty()) {
            Dot dot = (Dot) queue.pollFirst();
            path.addAll(dot.path);
            visited[dot.x][dot.y] = 0;

            if(dot.x == end.x && dot.y == end.y) break;
            
            if(dot.x-1 > 0 && visited[dot.x-1][dot.y] == 1) 
                queue.add(new Dot(dot.x-1, dot.y, path));
            if(dot.x+1 < matrix.length && visited[dot.x+1][dot.y] == 1)
                queue.add(new Dot(dot.x+1, dot.y, path));
            if(dot.y-1 > 0 && visited[dot.x][dot.y-1] == 1) 
            	queue.add(new Dot(dot.x, dot.y-1, path));
            if(dot.y+1 < matrix[1].length && visited[dot.x][dot.y+1] == 1) 
                queue.add(new Dot(dot.x, dot.y+1, path));
            
            path.clear();
        }
        path.addFirst(start);
        queue.clear();			
	}
	//���⿡ ���� �ȳ� ���� ���� �ּ� ����
	static void getPathStr() {
		String nowDirection = "";
		
		if(path.get(0).x + 1 == path.get(1).x) {
			pathStr.add(strInfo.RIGHTSIGHT.getStr());
			nowDirection = strInfo.DOWN.getStr();
		}
		else if(path.get(0).y + 1 == path.get(1).y) {
			pathStr.add(strInfo.FRONT.getStr());
			nowDirection = strInfo.R.getStr();
		}
		else if(path.get(0).x - 1 == path.get(1).x) {
			pathStr.add(strInfo.LEFTSIGHT.getStr());
			nowDirection = strInfo.UP.getStr();
		}
		else {
			pathStr.add(strInfo.OPPOSITE.getStr());
			nowDirection = strInfo.L.getStr();
		}

		for(int i=1 ; i<path.size();i++) {
			if(nowDirection.equals(strInfo.DOWN.getStr())) {
				if(path.get(i-1).y + 1 == path.get(i).y) {
					pathStr.add(strInfo.LEFT.getStr());
					nowDirection = strInfo.R.getStr();
				}
				else if(path.get(i-1).y - 1 == path.get(i).y) {
					pathStr.add(strInfo.RIGHT.getStr());
					nowDirection = strInfo.L.getStr();					
				}
				else pathStr.add(strInfo.STRAIGHT.getStr());
			}
			else if(nowDirection.equals(strInfo.R.getStr())) {
				if(path.get(i-1).x + 1 == path.get(i).x) {
					pathStr.add(strInfo.RIGHT.getStr());
					nowDirection = strInfo.DOWN.getStr();
				}
				else if(path.get(i-1).x - 1 == path.get(i).x) {
					pathStr.add(strInfo.LEFT.getStr());
					nowDirection = strInfo.UP.getStr();					
				}
				else pathStr.add(strInfo.STRAIGHT.getStr());
			}
			else if(nowDirection.equals(strInfo.UP.getStr())) {
				if(path.get(i-1).y + 1 == path.get(i).y) {
					pathStr.add(strInfo.RIGHT.getStr());
					nowDirection = strInfo.R.getStr();
				}
				else if(path.get(i-1).y - 1 == path.get(i).y) {
					pathStr.add(strInfo.LEFT.getStr());
					nowDirection = strInfo.L.getStr();					
				}
				else pathStr.add(strInfo.STRAIGHT.getStr());
			}
			else {
				if(path.get(i-1).x + 1 == path.get(i).x) {
					pathStr.add(strInfo.LEFT.getStr());
					nowDirection = strInfo.DOWN.getStr();
				}
				else if(path.get(i-1).x - 1 == path.get(i).x) {
					pathStr.add(strInfo.RIGHT.getStr());
					nowDirection = strInfo.UP.getStr();					
				}
				else pathStr.add(strInfo.STRAIGHT.getStr());
			}
		}
		pathStr.add(strInfo.END.getStr());
	}
}