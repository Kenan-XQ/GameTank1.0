package com.xq.po;

public class Tank {
	
	private int x = 0;
	private int y = 0;
	private int type = 0;
	private int direct = 0;
	private int speed = 10;  //初始速度为10
	private boolean isLive = true;  //判断是否存活
	
	public Tank() {
		// TODO Auto-generated constructor stub
	}
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Tank(int x, int y, int type, int direct) {
		this.x = x;
		this.y = y;
		this.type = type;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	//移动方法
	public void moveUp() {
		this.y -= speed;
	}
	public void moveDown() {
		this.y += speed;
	}
	public void moveLeft() {
		this.x -= speed;
	}
	public void moveRight() {
		this.x += speed;
	}
}




