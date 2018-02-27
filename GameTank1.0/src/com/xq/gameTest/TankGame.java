package com.xq.gameTest;

import javax.swing.JFrame;

import com.xq.JPanel.TankPanel;

public class TankGame extends JFrame{

	public TankGame() {
		// TODO Auto-generated constructor stub
		this.setTitle("坦克大战");
		this.setSize(1000, 800);
		TankPanel tankPanel = new TankPanel();
		this.addKeyListener(tankPanel);
		Thread tankPanelThread = new Thread(tankPanel);
		tankPanelThread.start();
		this.add(tankPanel);
		this.setLocationRelativeTo(null);  //窗口出现在屏幕中间
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new TankGame();
	}
}
