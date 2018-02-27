package com.xq.po;

import java.util.ArrayList;
import java.util.List;

public class EnemyTank extends Tank implements Runnable{

	private final static int DIRECT_UP = 0;
	private final static int DIRECT_DOWN = 1;
	private final static int DIRECT_LEFT = 2;
	private final static int DIRECT_RIGHT = 3;
	//控制随机数次数
	private int random_count = 0;
	private int random_integer = 0;
	//子弹的集合 
	List<Shot> enemyShotsList = new ArrayList<Shot>();
	
	public EnemyTank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnemyTank(int x, int y, int type, int direct) {
		super(x, y, type, direct);
		// TODO Auto-generated constructor stub
	}

	public EnemyTank(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public List<Shot> getEnemyShotsList() {
		return enemyShotsList;
	}

	public void setEnemyShotsList(List<Shot> enemyShotsList) {
		this.enemyShotsList = enemyShotsList;
	}

	private volatile boolean exit = true;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (exit) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (random_count % 3 == 0) {
				int random_x = (int) (Math.random()*4);
				random_integer = random_x;
			}
			if (random_count % 10 == 0) {
				Shot mShot = null;
				switch (this.getDirect()) {
				case DIRECT_UP:
					mShot = new Shot(this.getX()+8, this.getY()-4, DIRECT_UP);
					enemyShotsList.add(mShot);
					break;
				case DIRECT_DOWN:
					mShot = new Shot(this.getX()+9, this.getY()+32, DIRECT_DOWN);
					enemyShotsList.add(mShot);
					break;
				case DIRECT_LEFT:
					mShot = new Shot(this.getX()-8, this.getY()+8, DIRECT_LEFT);
					enemyShotsList.add(mShot);
					break;
				case DIRECT_RIGHT:
					mShot = new Shot(this.getX()+32, this.getY()+9, DIRECT_RIGHT);
					enemyShotsList.add(mShot);
					break;

				default:
					break;
				}
				Thread enemyShotThread = new Thread(mShot);
				enemyShotThread.start();
			}
			random_count ++;
			
			switch (random_integer) {
			case 0:
				this.setDirect(DIRECT_UP);
				if (this.getY() > 0)
					this.setY(this.getY() - 10);
				break;
			case 1:
				this.setDirect(DIRECT_DOWN);
				if (this.getY() < 700)
					this.setY(this.getY() + 10);
				break;
			case 2:
				this.setDirect(DIRECT_LEFT);
				if (this.getX() > 0)
					this.setX(this.getX()-10);
				break;
			case 3:
				this.setDirect(DIRECT_RIGHT);
				if (this.getX() < 900)
					this.setX(this.getX()+10);
				break;

			default:
				break;
			}
			
			if (this.isLive() == false) {
				exit = false;
				break;
			}
		}
	}

	
}
