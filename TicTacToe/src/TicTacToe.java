import java.util.*;

class TicTacToe {
	public enum Role { Human , Comp };
	private final int GridNuber;
	private final char Comp_Char;
	private final char Human_Char;
	private final char Blank_Char;
	
	private final int CompWin;
	private final int Draw;
	private final int HumanWin;
	private final int Deep;
	
	char Board[];
	
	Scanner read;
	
	public TicTacToe() {
		GridNuber = 9;
		Comp_Char = 'X';
		Human_Char = 'O';
		Blank_Char = ' ';
		
		CompWin = 100;
		Draw = 0;
		HumanWin = -100;
		Deep = 2;
		
//		CompWin = 2;
//		Draw = 1;
//		HumanWin = 0;
//		Deep = 1;
		
		Board = new char[9];
		init();
		
		read = new Scanner(System.in);
	}
	
	public void init(){
		for (int i = 0; i < GridNuber; i++)
			Board[i] = ' ';
	}
	
	boolean isEmpty(int pos) {
		return Board[pos] == ' ';
	}
	
	public boolean isFull() {
		for (int i = 0; i < GridNuber; i++) {
			if (Board[i] == ' ')
				return false;
		}
		return true;
	}
	
	public void humanPlace(int pos) {
		Board[pos] = 'O';
	}
	
	public void compPlace(int pos) {
		Board[pos] = 'X';
	}
	
	public void unPlace(int pos) {
		Board[pos] = ' ';
	}
	
	public void print() {
		int cnt = 0;
		for (int i = 0; i <= 2; i ++) {
			for (int j = 0; j <= 2; j ++) {
				System.out.print(Board[cnt++]);
			}
			System.out.println();
		}
	}
	
	public boolean canWin(char c) {
		//check every row
		for (int i = 0; i <= 6; i+=3) {
			if (Board[i] == c && Board[i] == Board[i + 1] && Board[i] == Board[i + 2])
				return true;
		}
		//check every col
		for (int i = 0; i < 3; i++) {
			if (Board[i] == c && Board[i] == Board[i + 3] && Board[i] == Board[i + 6])
				return true;
		}
		//check every diagonals
		if (Board[0] == c && Board[4] == c && Board[8] == c)
			return true;
		if (Board[2] == c && Board[4] == c && Board[6] == c)
			return true;
		return false;
	}
	
	
	int bestMove = -1;
	public boolean immediateComWin() {
		for (int i = 0; i < GridNuber; i++) {
			if (isEmpty(i)) {
				Board[i] =Comp_Char;
				boolean Win = canWin(Comp_Char); 
				Board[i] = Blank_Char;         //backtraceing
				if (Win) {
					bestMove = i;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean immediateHumanWin() {
		for (int i = 0; i < GridNuber; i++) {
			if (isEmpty(i)) {
				Board[i] = Human_Char;
				boolean Win = canWin(Human_Char);
				Board[i] = Blank_Char;   //backtraceing
				if (Win) {
					bestMove = i;
					return true;
				}
			}
		}
		return false;
	}
	
	class A{
		private int value;
		public A(int v){
			value = v;
		}
		public void set(int v){
			value = v;
		}
		public int get(){
			return value;
		}
	}
	
	public int compfind() {
		if( ! immediateComWin() ) {
			bestMove = getBestMove();
		}
		Board[bestMove] = 'X';
		return bestMove;
	}
	
	public int getBestMove() {
		A best = new A(-1);
		A value = new A(0);
		findCompMove(best,value,HumanWin, CompWin, 1);
		return best.get();
	}
	
	public int getvalue() {
		int res = 0;

		//Comp_row
		for(int i=0 ; i<9 ; i+=3) {
			int j;
			for(j=0 ; j<3 ; j++) {
				if(Board[i+j]!=Comp_Char && Board[i+j]!=Blank_Char)  break;
			}
			if(j==3) res++;
		}
		//Comp_col
		for(int i=0 ; i<3 ; i++) {
			int j;
			for(j=0 ; j<3 ; j++) {
				if(Board[j*3+i]!=Comp_Char && Board[j*3+i]!=Blank_Char)  break;
			}
			if(j==3) res++;
		}
		//Comp_diag
		int k;
		for(k=0 ; k<3 ; k++) {
			if(Board[k*3+k]!=Comp_Char && Board[k*3+k]!=Blank_Char)  break;
		}
		if(k==3 )  res++;
		for(k=0 ; k<3 ; k++) {
			if(Board[k*3+(2-k)]!=Comp_Char && Board[k*3+(2-k)]!=Blank_Char)  break;
		}
		if(k==3 )  res++;
		
		//Human_row
		for(int i=0 ; i<9 ; i+=3) {
			int j;
			for(j=0 ; j<3 ; j++) {
				if(Board[i+j]!=Human_Char && Board[i+j]!=Blank_Char)  break;
			}
			if(j==3) res--;
		}
		//Human_col
		for(int i=0 ; i<3 ; i++) {
			int j;
			for(j=0 ; j<3 ; j++) {
				if(Board[j*3+i]!=Human_Char && Board[j*3+i]!=Blank_Char)  break;
			}
			if(j==3) res--;
		}
		//Human_diag
		for(k=0 ; k<3 ; k++) {
			if(Board[k*3+k]!=Human_Char && Board[k*3+k]!=Blank_Char)  break;
		}
		if(k==3 )  res--;
		for(k=0 ; k<3 ; k++) {
			if(Board[k*3+(2-k)]!=Human_Char && Board[k*3+(2-k)]!=Blank_Char)  break;
		}
		if(k==3 )  res--;
		
		return res;
	}
	
	public void findCompMove(A best, A value, int alpha, int beta, int deep) {
		if (isFull())
			value.set(getvalue()); 
		else if(deep > Deep) 
			value.set(getvalue());
		else if(immediateComWin())	{
			best.set(bestMove);
			value.set(CompWin);
		}
		else if (immediateHumanWin()) {
			best.set(bestMove);
			value.set(HumanWin);
		}
		else {
			value.set(alpha); 
			for (int i = 0; i < GridNuber && value.get() < beta; i++) {
				if (isEmpty(i)) {
					compPlace(i);
					A tmp = new A(-1);  // Tmp is useless
					A response = new A(-1);
				    findHumanMove(tmp, response, value.get(), beta, deep+1);
					if (response.get() > value.get()) {
						value.set(getvalue());
						best.set(i);
					}
					unPlace(i);
				}
			}
		}
	}
	
	public void findHumanMove(A best,A value, int alpha, int beta, int deep) {
		if (isFull())
			value.set(getvalue());
		else if(deep > Deep) 
			value.set(getvalue());
		else if (immediateHumanWin()){
			best.set(bestMove);
			value.set(HumanWin);
		}
		else if (immediateComWin()){
			best.set(bestMove);
			value.set(CompWin);
		}
		else {
			value.set(beta);
			for (int i = 0; i < GridNuber && value.get() > alpha; i++) {
				if (isEmpty(i)) {
					humanPlace(i);
					
					A tmp = new A(-1);			// Tmp is useless
					A response = new A(-1);  
					findCompMove(tmp, response, alpha, value.get(), deep+1);
					if (response.get() < value.get()) {
						value.set(getvalue());
						best.set(i);
					}
					unPlace(i);
				}
			}
		}
	}
	
//	public void findCompMove(A bestMove, A value, int alpha, int beta) {
//		if (isFull())
//			value.set(Draw); 
//		else if (immediateComWin())
//			value.set(CompWin);
//		else {
//			value.set(alpha); 
//			for (int i = 0; i < GridNuber && value.get() < beta; i++) {
//				if (isEmpty(i)) {
//					compPlace(i);
//					A tmp = new A(-1);  // Tmp is useless
//					A response = new A(-1);
//					findHumanMove(tmp, response, value.get(), beta);
//					unPlace(i);
//					if (response.get() > value.get()) {
//						value.set(response.get());
//						bestMove.set(i);
//					}
//				}
//			}
//		}
//	}
//	
//	public void findHumanMove(A bestMove,A value, int alpha, int beta) {
//		if (isFull())
//			value.set(Draw);
//		else if (immediateHumanWin()){
//			value.set(HumanWin);
//		}
//		else {
//			value.set(beta);
//			for (int i = 0; i < GridNuber && value.get() > alpha; i++) {
//				if (isEmpty(i)) {
//					humanPlace(i);
//					A tmp = new A(-1);			// Tmp is useless
//					A response = new A(-1);  
//					findCompMove(tmp, response, alpha, value.get());
//					unPlace(i);
//					if (response.get() < value.get()) {
//						value.set(response.get());
//						bestMove.set(i);
//					}
//				}
//			}
//		}
//	}
	
	public int gameIsOver() {
		if (canWin(Comp_Char)) {            // 电脑胜利返回2
			System.out.println( "You lose!" );
			return 2;
		}
		else if (canWin(Human_Char)) {		// 人类胜利返回0
			System.out.println( "Congratulations! You defeat the computer." );
			return 0;
		}
		else if (isFull()) {                // 平局返回1
			System.out.println( "Draw!" );
			return 1;
		}
		else {                                      // 未结束返回-1
			return -1;
		}
	}
	
};