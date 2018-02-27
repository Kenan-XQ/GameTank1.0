package com.xq.po;

public class Bomb {

	private int life = 3;
	private boolean isLive = true;
	private int x;
	private int y;
	
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
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
	
	public Bomb(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.isLive = true;
	}
	public void lifeDown() {
		if (life > 0) {
			this.life --;
		}
		if (life == 0) {
			this.isLive = false;
		}
	}
	
}
