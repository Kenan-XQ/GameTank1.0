package com.xq.gameTest;

import javax.swing.JFrame;

import com.xq.JPanel.TankPanel;

public class TankGame extends JFrame{

	public TankGame() {
		// TODO Auto-generated constructor stub
		this.setTitle("̹�˴�ս");
		this.setSize(1000, 800);
		TankPanel tankPanel = new TankPanel();
		this.addKeyListener(tankPanel);
		Thread tankPanelThread = new Thread(tankPanel);
		tankPanelThread.start();
		this.add(tankPanel);
		this.setLocationRelativeTo(null);  //���ڳ�������Ļ�м�
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new TankGame();
	}
}
