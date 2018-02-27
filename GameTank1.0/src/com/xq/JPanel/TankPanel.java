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

	//���峣��
	private final static int DIRECT_UP = 0;
	private final static int DIRECT_DOWN = 1;
	private final static int DIRECT_LEFT = 2;
	private final static int DIRECT_RIGHT = 3;
	private final static int TYPE_HERO = 0;
	private final static int TYPE_ENEMY = 1;
	
	//����з�̹�˼���
	private List<EnemyTank> enemyTanks;
	
	//��ʼ��Ӣ��̹��
	Hero hero = null;
	
	//Ӣ��̹�˱����еĴ���
	private Integer hitCount = 0;
	//����̹�˵�����
	private Integer hitEnemyCount = 0;
	
	//���屬ըͼƬ
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image1 = toolkit.getImage("image/bomb1.png");
	Image image2 = toolkit.getImage("image/bomb2.png");
	Image image3 = toolkit.getImage("image/bomb3.png");
	
	//����ը������
	List<Bomb> bombsList;
	
	public TankPanel() {
		// TODO Auto-generated constructor stub
		hero = new Hero(200, 200, TYPE_HERO, DIRECT_RIGHT);
		enemyTanks = new ArrayList<EnemyTank>();
		//��ʼ��
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
		g.drawString("Ӣ��̹�˱����д�����" + hitCount, 700, 50);
		g.drawString("���ܵз���̹�˴�����" + hitEnemyCount, 700, 100);
		//��Ӣ��̹��
		drawTank(hero, g);
		//���з�̹��
		for (int i=0; i<enemyTanks.size(); i++) {
			if (enemyTanks.get(i).isLive() == true) {
				drawTank(enemyTanks.get(i), g);
				//������̹�˵��ӵ����ϴ���
				if (enemyTanks.get(i).getEnemyShotsList().size() > 0) {
					//�ж��ӵ��Ƿ�ʧЧ 
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

		//��Ӣ��̹�˵��ӵ�
		g.setColor(Color.RED);
		for (int i=0; i<hero.getShotsList().size(); i++) {
			g.draw3DRect(hero.getShotsList().get(i).getX(), hero.getShotsList().get(i).getY(), 3, 3, false);
			
			if (hero.getShotsList().get(i).isLive() == false) {
				hero.getShotsList().remove(i);
			}
		}
		
		//������ըЧ��
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
	 * ����̹��
	 * @param tank
	 * @param graphics
	 * @param direct
	 */
	public void drawTank(Tank tank, Graphics graphics) {
		//�ж�̹������
		switch (tank.getType()) {
		case 0:
			//��ʾ��Ӣ��̹��
			graphics.setColor(Color.RED);
			break;
		default:
			//��ʾ�з�̹��
			graphics.setColor(Color.GREEN);
			break;
		}
		//�ж�̹�˷���
		switch (tank.getDirect()) {
		case DIRECT_UP:
			//����
			graphics.fill3DRect(tank.getX(), tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+15, tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 10, 20, false);
			graphics.fillOval(tank.getX()+4, tank.getY()+10, 10, 10);
			graphics.drawLine(tank.getX()+9, tank.getY()+15, tank.getX()+9, tank.getY());
			break;
		case DIRECT_DOWN:
			//����
			graphics.fill3DRect(tank.getX(), tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+15, tank.getY(), 5, 30, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 10, 20, false);
			graphics.fillOval(tank.getX()+4, tank.getY()+10, 10, 10);
			graphics.drawLine(tank.getX()+9, tank.getY()+15, tank.getX()+9, tank.getY()+30);
			break;
		case DIRECT_LEFT:
			//����
			graphics.fill3DRect(tank.getX(), tank.getY(), 30, 5, false);
			graphics.fill3DRect(tank.getX(), tank.getY()+15, 30, 5, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 20, 10, false);
			graphics.fillOval(tank.getX()+9, tank.getY()+4, 10, 10);
			graphics.drawLine(tank.getX()+5, tank.getY()+9, tank.getX()-4, tank.getY()+9);
			break;
		case DIRECT_RIGHT:
			//����
			graphics.fill3DRect(tank.getX(), tank.getY(), 30, 5, false);
			graphics.fill3DRect(tank.getX(), tank.getY()+15, 30, 5, false);
			graphics.fill3DRect(tank.getX()+5, tank.getY()+5, 20, 10, false);
			graphics.fillOval(tank.getX()+9, tank.getY()+4, 10, 10);
			graphics.drawLine(tank.getX()+15, tank.getY()+9, tank.getX()+30, tank.getY()+9);
			break;
		}
	}
	
	
	/**
	 * �Եз�̹�˽������
	 */
	public void hitEnemy() {
		//System.out.println("size = " + hero.getShotsList().size());
		for (int i=0; i<hero.getShotsList().size(); i++) {
			Shot myShot = hero.getShotsList().get(i);
			//�ж��ӵ��Ƿ���Ч
			if (myShot.isLive() == true) {
				for (int j=0; j<enemyTanks.size(); j++) {
					//�жϵз�̹���Ƿ����
					if (enemyTanks.get(j).isLive() == true) {
						if ((myShot.getX() > enemyTanks.get(j).getX() &&
								myShot.getX() < enemyTanks.get(j).getX() + 20) &&
								(myShot.getY() > enemyTanks.get(j).getY() &&
								myShot.getY() < enemyTanks.get(j).getY() + 30)) {
							System.out.println("�����ˣ�");
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
	 * ���Ӣ��̹��(�ж��Ƿ񱻴�����)
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
							System.out.println("��������");
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
			//�����ϼ�
			hero.setDirect(DIRECT_UP);
			hero.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			//�����¼�
			hero.setDirect(DIRECT_DOWN);
			hero.moveDown();
			break;
		case KeyEvent.VK_LEFT:
			//�������
			hero.setDirect(DIRECT_LEFT);
			hero.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			//�����Ҽ�
			hero.setDirect(DIRECT_RIGHT);
			hero.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			//�ո���������(�������5���ӵ��ڷ�����)
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
	 * ÿ��100ms����ˢ��
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
