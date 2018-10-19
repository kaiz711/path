package map;

import java.util.LinkedList;

/****************************************
 * 	  1	     -> 시작할때 X를 기준으로 1은 UP(↑)
 * 2  x  3   -> 2는 L(←) 3은 R(→)
 *    4      -> 4는 DOWN↓(↓)
 * 여기서 정면은 3이라고 생각
 * 1은 왼쪽, 2는 반대방향, 3은 정면, 4는 오른쪽이 됨
 ***************************************/
public class searchRoot {
	
	static int matrix[][] = null;
    static LinkedList<Dot> path = new LinkedList<Dot>();
    static LinkedList<String> pathStr = new LinkedList<String>();

	public static void main(String[] args) {
		connecter();
		/* 장애물 위치 정보 & 현재 좌표 받아오는거 알려줘 맞춰서 수정해야함
		 * 장애물 있을때 글로 안가고 새로운 경로 찾는거는 이렇게 하면 되는데
		 * 쟁애물 위치 어케 전송된느지 모름 -> 알려주셈
		 * 경로 이탈도 웹에서 판단해서 이탈하면 현재 위치 전송해서 재탐색 들어가면 되는데
		 */
		/*if(flag==1){ 
			obstacle(new Dot(2,4));
		}else*/
		newMapRoad();
	}
	
	//DB연동
	static void connecter() {
		databaseConnect dbConnecter = new databaseConnect();
		matrix = dbConnecter.connect();
	}
	//장애물 있어서 못간다고 판단되는 자표 찍어서 0처리후 현재위치에서부터 재탐색 & 안내생성
	static void obstacle(Dot dot) {
		matrix[dot.x][dot.y]=0;
		bfs(new Dot(1,1),new Dot(5,6));
		getPathStr();
	}
	//처음 시작할 때 경로탐색 & 안내 생성
	static void newMapRoad() {
		bfs(new Dot(1,1),new Dot(5,6));
		getPathStr();
	}
	//경로탐색 (만약 가중치가 들어가면 다익스트라써야함, 지금은 가중치가 없기때문에 bfs를 쓰는게 맞음)
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
	//방향에 따른 안내 생성 맨위 주석 참고
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