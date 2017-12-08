import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private boolean win = false;
	private int score = 0;
	private BufferedImage img;
	private BufferedImage platform;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -2;
	private int ballYdir = -3;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g){
		//bg
		URL res = getClass().getResource("./galaxy.png");
		try {
			img = ImageIO.read( res );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.clearRect(0, 0, getWidth(), getHeight() );
		g.drawImage(img, 1, 1, 692, 592, null);
		
		//g.setColor(Color.black);
		//g.fillRect(1, 1, 692, 592);
		//desenhar o mapa
		map.draw((Graphics2D)g);
		//borda
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		//pontuação
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 25);
		//a plataforma que você controla
		
		g.setColor(Color.magenta);
		g.fillRect(playerX, 550, 100, 8);
		//bola
		g.setColor(Color.white);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if (totalBricks == 0){
			play = false;
			win = true;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Você venceu, pontuação: ", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Pressione enter para reiniciar", 230, 350);
		}
		
		if(ballposY > 570){
			play = false;
			win = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Fim de jogo, pontuação: ", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Pressione enter para reiniciar", 230, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play){
			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX,550, 100, 8))){
				ballYdir = -ballYdir;
			}
			
			//obs: o primeiro map se refere ao map dessa classe, o segundo a variavel da classe Mapgenerator
			 for(int i = 0; i<map.map.length; i++){
				for (int j = 0; j<map.map[0].length; j++){
					if(map.map[i][j] > 0){
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)){
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
						}
						
						
						
						
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0){
				ballXdir = -ballXdir;
			}
			if(ballposY < 0){
				ballYdir = -ballYdir;
			}
			if(ballposX > 670){
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerX >= 600){
				playerX = 600;
			}else{
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerX < 10){
				playerX = 10;
			}else{
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				if (win){
					play = true;
					ballposX = 120;
					ballposY = 350;
					ballXdir--;
					ballYdir--;
					playerX = 310;
					score = 0;
					totalBricks = 35;
					map = new MapGenerator(4, 7);
				}else {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				}
				repaint();
			}
		}
	}
	
	
	public void moveRight(){
		play = true;
		playerX += 20;
	}
	
	public void moveLeft(){
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
