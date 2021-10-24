import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;


public class BoardView extends JFrame{
	
	JMenuBar menubar;
	JMenu menu;
	JMenuItem item1,item2;
	TicTacToe game;
	JButton jb[];
	
	public BoardView() {
		super("井字棋");
		
		menubar = new JMenuBar();
		menu = new JMenu("开始");
		item1 = new JMenuItem("电脑先手");
		item1.addActionListener(new Listener1());
		item2 = new JMenuItem("用户先手");
		item2.addActionListener(new Listener2());
		menu.add(item1);
		menu.add(item2);
		menubar.add(menu);
		setJMenuBar(menubar);
		
		game = new TicTacToe();
		jb = new JButton[9];
		Listener lis[] = new Listener[9];
		
		setLayout(new GridLayout(3,3));
		
		int cnt = 0;
		jb[cnt] = new JButton("0");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("1");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("2");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("3");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("4");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("5");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("6");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("7");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
		jb[cnt] = new JButton("8");
		lis[cnt] = new Listener();
		jb[cnt].addActionListener(lis[cnt]);
		add(jb[cnt++]);
		
//		for(int i=0 ; i<9 ; i++) {
//			jb[i] = new JButton();
//  	    jb[i].addActionListener(l);
//			add(jb[i]);
//		}
		
		setSize(300,300);
		//窗体大小不能改变
		setResizable(false);
		//居中显示
		setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
 
	}
	
	class Listener1 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ImageIcon icon1 = new ImageIcon("1.jpg");
			for(int i=0 ; i<9 ; i++) {
				jb[i].setIcon(icon1);
			}
			game.init();
			
			int bestMove = game.compfind();
			ImageIcon icon = new ImageIcon("X.jpg");
			jb[bestMove].setIcon(icon);
//			System.out.println(game.getvalue());
//			game.print();
		}
	}
	class Listener2 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ImageIcon icon1 = new ImageIcon("1.jpg");
			for(int i=0 ; i<9 ; i++) {
				jb[i].setIcon(icon1);
			}
			game.init();
		}
	}
	
	class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			String str = e.getActionCommand();
			int pos = -1;
			try {

			    pos = Integer.parseInt(str);

			} catch (NumberFormatException h) {
			    h.printStackTrace();
			}
			if (game.isEmpty(pos)) {
				game.humanPlace(pos);			
				JButton jbt = (JButton)e.getSource();
				ImageIcon icon = new ImageIcon("O.jpg");
		        jbt.setIcon(icon);
//		        System.out.println(game.getvalue());
		        int flag = -1;
		        flag = game.gameIsOver();
				if (flag == 0) 
					JOptionPane.showMessageDialog(null,"Congratulations! You defeat the computer.");
				else if(flag == 1) 
					JOptionPane.showMessageDialog(null,"Draw!");
				
				if(flag == -1) {
					int bestMove = game.compfind();
					flag = game.gameIsOver();
					icon = new ImageIcon("X.jpg");
					jb[bestMove].setIcon(icon);
//					System.out.println(game.getvalue());
//					game.print();
					if (flag == 2) 
						JOptionPane.showMessageDialog(null,"You lose!");
					else if(flag == 1)
						JOptionPane.showMessageDialog(null,"Draw!");
				}
			}
			else
				System.out.println( "error,please input again:" );
			
		}
	}

}
