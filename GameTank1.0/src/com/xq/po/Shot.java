package com.xq.po;

/**
 * 子弹类
 * @author user
 *
 */
public class Shot implements Runnable{

	private int x;
	private int y;
	private int direct;
	private int speed = 10;
	private boolean isLive = true;
	
	private final static int DIRECT_SHOT_UP = 0;
	private final static int DIRECT_SHOT_DOWN = 1;
	private final static int DIRECT_SHOT_LEFT = 2;
	private final static int DIRECT_SHOT_RIGHT = 3;
	
	public Shot() {
		
	}
	
	public Shot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	

	public Shot(int x, int y, int direct) {
		super();
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
	private volatile boolean exit = true;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (exit) {
			//System.out.println("子弹x = " + x + "，y = " + y);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//判断子弹方向
			switch (direct) {
			case DIRECT_SHOT_UP:
				//向上
				this.y -= speed;
				break;
			case DIRECT_SHOT_DOWN:
				//向下
				this.y += speed;
				break;
			case DIRECT_SHOT_LEFT:
				//向左
				this.x -= speed;
				break;
			case DIRECT_SHOT_RIGHT:
				//向右
				this.x += speed;
				break;
			default:
				break;
			}
			
			if (this.x < 0 || this.x > 1000 || this.y < 0 || this.y > 800 || this.isLive == false) {
				isLive = false;
				exit = false;
				break;
			}
		}
		
	}
}
