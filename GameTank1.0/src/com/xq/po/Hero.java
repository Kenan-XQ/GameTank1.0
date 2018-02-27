package com.xq.po;

import java.util.ArrayList;
import java.util.List;

/**
 * 英雄坦克
 * @author user
 *
 */
public class Hero extends Tank{

	private final static int DIRECT_UP = 0;
	private final static int DIRECT_DOWN = 1;
	private final static int DIRECT_LEFT = 2;
	private final static int DIRECT_RIGHT = 3;
	
	//英雄坦克炮筒（最多存在3发子弹）
	List<Shot> shotsList = new ArrayList<Shot>();
	
	public Hero() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hero(int x, int y, int type, int direct) {
		super(x, y, type, direct);
		// TODO Auto-generated constructor stub
	}

	public Hero(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public List<Shot> getShotsList() {
		return shotsList;
	}

	public void setShotsList(List<Shot> shotsList) {
		this.shotsList = shotsList;
	}

	/**
	 * 射击方法
	 */
	public void shotEnemy() {
		Shot shot = null;
		switch (this.getDirect()) {
		case DIRECT_UP:
			shot = new Shot(this.getX()+8, this.getY()-4, DIRECT_UP);
			shotsList.add(shot);
			break;
		case DIRECT_DOWN:
			shot = new Shot(this.getX()+9, this.getY()+32, DIRECT_DOWN);
			shotsList.add(shot);
			break;
		case DIRECT_LEFT:
			shot = new Shot(this.getX()-8, this.getY()+8, DIRECT_LEFT);
			shotsList.add(shot);
			break;
		case DIRECT_RIGHT:
			shot = new Shot(this.getX()+32, this.getY()+9, DIRECT_RIGHT);
			shotsList.add(shot);
			break;
		default:
			break;
		}
		Thread shotThread = new Thread(shot);
		shotThread.start();
	}
	
}
