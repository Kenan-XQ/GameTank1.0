package com.xq.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.xml.ws.handler.HandlerResolver;

import com.xq.po.Bomb;
import com.xq.po.EnemyTank;
import com.xq.po.Hero;
import com.xq.po.Shot;
import com.xq.po.Tank;

public class TankPanel extends JPanel implements KeyListener, Runnable{

	//定义常量
	private final static int DIRECT_UP = 0;
	private final static int DIRECT_DOWN = 1;
	private final static int DIRECT_LEFT = 2;
	private final static int DIRECT_RIGHT = 3;
	private final static int TYPE_HERO = 0;
	private final static int TYPE_ENEMY = 1;
	
	//定义敌方坦克集合
	private List<EnemyTank> enemyTanks;
	
	//初始化英雄坦克
	Hero hero = null;
	
	//英雄坦克被击中的次数
	private Integer hitCount = 0;
	//击落坦克的数量
	private Integer hitEnemyCount = 0;
	
	//定义爆炸图片
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image1 = toolkit.getImage("image/bomb1.png");
	Image image2 = toolkit.getImage("image/bomb2.png");
	Image image3 = toolkit.getImage("image/bomb3.png");
	
	//定义炸弹集合
	List<Bomb> bombsList;
	
	public TankPanel() {
		// TODO Auto-generated constructor stub
		hero = new Hero(200, 200, TYPE_HERO, DIRECT_RIGHT);
		enemyTanks = new ArrayList<EnemyTank>();
		//初始化
		for (int i=0; i<10; i++) {
			EnemyTank eTank = new EnemyTank(400+30*i, 200, TYPE_ENEMY, DIRECT_DOWN);
			enemyTanks.add(eTank);
			new Thread(eTank).start();
		}
		bombsList = new ArrayList<Bomb>();
		bombsList.add(new Bomb(1000, 800));
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.fillRect(0, 0, 1000, 800);
		g.setColor(Color.white);
		g.setFont(new Font(null, 0, 20));
		g.drawString("英雄坦克被击中次数：" + hitCount, 700, 50);
		g.drawString("击败敌方的坦克次数：" + hitEnemyCount, 700, 100);
		//画英雄坦克
		drawTank(hero, g);
		//画敌方坦克
		for (int i=0; i<enemyTanks.size(); i++) {
			if (enemyTanks.get(i).isLive() == true) {
				drawTank(enemyTanks.get(i), g);
				//如果这个坦克的子弹集合存在
				if (enemyTanks.get(i).getEnemyShotsList().size() > 0) {
					//判断子弹是否失效 
					for (int j=0; j<enemyTanks.get(i).getEnemyShotsList().size(); j++) {
						Shot mShot = enemyTanks.get(i).getEnemyShotsList().get(j);
						if (mShot.isLive() == true) {
							g.setColor(Color.GREEN);
							g.fill3DRect(mShot.getX(), mShot.getY(), 3, 3, false);
						} else {
							enemyTanks.get(i).getEnemyShotsList().remove(mShot);
						}
					}
				}
			} else {
				enemyTanks.remove(i);
			}
		}

		//画英雄坦克的子弹
		g.setColor(Color.RED);
		for (int i=0; i<hero.getShotsList().size(); i++) {
			g.draw3DRect(hero.getShotsList().get(i).getX(), hero.getShotsList().get(i).getY(), 3, 3, false);
			
			if (hero.getShotsList().get(i).isLive() == false) {
				hero.getShotsList().remove(i);
			}
		}
		
		//画出爆炸效果
		for (int i=0; i<bombsList.size(); i++) {
			//System.out.println("size = " + bombsList.size());
			Bomb bomb = bombsList.get(i);
			//System.out.println("isLive = " + bomb.isLive());
			while (bomb.isLive() == true) {
				if (bomb.getLife() == 3) {
					g.drawImage(image1, bomb.getX(), bomb.getY(), 50, 50, this);
					bomb.lifeDown();
				} else if (bomb.getLife() == 2) {
					g.drawImage(image2, bomb.getX(), bomb.getY(), 50, 50, this);
					bomb.lifeDown();
				} else if (bomb.getLife() == 1) {
					g.drawImage(image3, bomb.getX(), bomb.getY(), 50, 50, this);
					bomb.lifeDown();
				} else {
					bomb.setLive(false);
					bombsList.remove(bomb);	
				}
			}
		}
	}
	
	/**
	 * 画出坦克
	 * @param tank
	 * @param graphics
	 * @param direct
	 */
	public void drawTank(Tank tank, Graphics graphics) {
		//判断坦克类型
		switch (tank.getType()) {
		case 0:
			//表示是英雄坦克
			graphics.setColor(Color.RED);
			break;
		default:
			//表示敌方坦克
			graphics.setColor(Color.GREEN);
			break;
		}
		//判断坦克方向
		switch (tank.getDirect()) {
		case DIRECT_UP:
			//向上
			graphics.fill3DRect(tank.getX(), tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+15, tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 10, 20, false);
			graphics.fillOval(tank.getX()+4, tank.getY()+10, 10, 10);
			graphics.drawLine(tank.getX()+9, tank.getY()+15, tank.getX()+9, tank.getY());
			break;
		case DIRECT_DOWN:
			//向下
			graphics.fill3DRect(tank.getX(), tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+15, tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 10, 20, false);
			graphics.fillOval(tank.getX()+4, tank.getY()+10, 10, 10);
			graphics.drawLine(tank.getX()+9, tank.getY()+15, tank.getX()+9, tank.getY()+30);
			break;
		case DIRECT_LEFT:
			//向左
			graphics.fill3DRect(tank.getX(), tank.getY(), 30, 5, false);
			graphics.fill3DRect(tank.getX(), tank.getY()+15, 30, 5, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 20, 10, false);
			graphics.fillOval(tank.getX()+9, tank.getY()+4, 10, 10);
			graphics.drawLine(tank.getX()+5, tank.getY()+9, tank.getX()-4, tank.getY()+9);
			break;
		case DIRECT_RIGHT:
			//向右
			graphics.fill3DRect(tank.getX(), tank.getY(), 30, 5, false);
			graphics.fill3DRect(tank.getX(), tank.getY()+15, 30, 5, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 20, 10, false);
			graphics.fillOval(tank.getX()+9, tank.getY()+4, 10, 10);
			graphics.drawLine(tank.getX()+15, tank.getY()+9, tank.getX()+30, tank.getY()+9);
			break;
		}
	}
	
	
	/**
	 * 对敌方坦克进行射击
	 */
	public void hitEnemy() {
		//System.out.println("size = " + hero.getShotsList().size());
		for (int i=0; i<hero.getShotsList().size(); i++) {
			Shot myShot = hero.getShotsList().get(i);
			//判断子弹是否有效
			if (myShot.isLive() == true) {
				for (int j=0; j<enemyTanks.size(); j++) {
					//判断敌方坦克是否存在
					if (enemyTanks.get(j).isLive() == true) {
						if ((myShot.getX() > enemyTanks.get(j).getX() &&
								myShot.getX() < enemyTanks.get(j).getX() + 20) &&
								(myShot.getY() > enemyTanks.get(j).getY() &&
								myShot.getY() < enemyTanks.get(j).getY() + 30)) {
							System.out.println("打中了！");
							hero.getShotsList().get(i).setLive(false);
							enemyTanks.get(j).setLive(false);
							hitEnemyCount ++;
							bombsList.add(new Bomb(enemyTanks.get(j).getX(), enemyTanks.get(j).getY()));
						}
					}	
				}
			}
		}
	}
	
	/**
	 * 射击英雄坦克(判断是否被打中了)
	 */
	public void hitHero() {
		for (int i=0; i<enemyTanks.size(); i++) {
			EnemyTank eTank = enemyTanks.get(i);
			if (eTank.isLive() == true) {
				for (int j=0; j<eTank.getEnemyShotsList().size(); j++) {
					Shot eShot = eTank.getEnemyShotsList().get(j);
					if (eShot.isLive() == true) {
						if ((eShot.getX() > hero.getX() && eShot.getX() < hero.getX() + 20)
							&& (eShot.getY() > hero.getY() && eShot.getY() < hero.getY() + 30)) {
							System.out.println("被打中了");
							hitCount ++;
							eShot.setLive(false);
							bombsList.add(new Bomb(hero.getX(), hero.getY()));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("t");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			//按向上键
			hero.setDirect(DIRECT_UP);
			hero.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			//按向下键
			hero.setDirect(DIRECT_DOWN);
			hero.moveDown();
			break;
		case KeyEvent.VK_LEFT:
			//按向左键
			hero.setDirect(DIRECT_LEFT);
			hero.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			//按向右键
			hero.setDirect(DIRECT_RIGHT);
			hero.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			//空格键进行射击(场上最多5发子弹在飞行中)
			if (hero.getShotsList().size() < 5) {
				hero.shotEnemy();
			}
			break;
		default:
			break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("r");
	}

	private volatile boolean exit = true;
	/**
	 * 每隔100ms进行刷新
	 */
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
			hitEnemy();
			hitHero();
			repaint();
		}	
	}
}
