package controller;

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

@SuppressWarnings("serial")
public class Gameplay extends JPanel implements KeyListener, ActionListener{
	//verificação se o jogo começou e se o jogador ganhou a ultima rodada
	private boolean play = false;
	private boolean win = false;
	
	private int hScore = 0;
	private int score = 0;
	
	private BufferedImage img;
	
	private int totalBricks = 21;
	
	//temporalizador
	private Timer timer;
	private int delay = 5;
	
	//posicão da plataforma
	private int playerX = 310;
	
	//posição e direção da bola
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -2;
	private int ballYdir = -3;
	
	//Gerador dos tijolos
	private MapGenerator map;
	
	public Gameplay() {
		//deixa o jogo pronto para ser iniciado, mover para a direita ou a esquerda que irá iniciar o jogo(KeyListener)
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	//desenha os elementos
	public void paint(Graphics g){
		//fundo
		URL res = getClass().getResource("./galaxy.png");
		try {
			img = ImageIO.read( res );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.clearRect(0, 0, getWidth(), getHeight() );
		g.drawImage(img, 1, 1, 692, 592, null);
		
		//desenhar o mapa dos tijolos, chamando o draw da MapGenerator
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
		
		//pontuação maxima
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("high score: "+hScore, 50, 25);
		
		//a plataforma que você controla
		g.setColor(Color.magenta);
		g.fillRect(playerX, 550, 100, 8);
		
		//bola
		g.setColor(Color.white);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//condicional para a vitória, ter destruido todos os tijolos, pode reiniciar o jogo mais dificil
		if (totalBricks == 0){
			play = false;
			win = true;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Você venceu! pontuação: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Pressione enter para reiniciar", 230, 350);
			g.dispose();
		}
		
		//condicional de derrota, ter deixado a bola ir para fora. Reinicia o jogo na dificuldade inicial.
		if(ballposY > 570){
			play = false;
			win = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Fim de jogo, pontuação: " + score, 190, 300);
			
			if (score > hScore){
				g.setColor(Color.RED);
				g.setFont(new Font("serif", Font.BOLD, 30));
				g.drawString("***NOVO HIGH SCORE***", 190, 250);
			}
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Pressione enter para reiniciar", 230, 350);
			g.dispose();
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
			
			//colisão da bola e dos tijolos, além da "destruição" dos tijolos
			//obs: o primeiro map se refere ao map desta classe, o segundo a variavel da classe Mapgenerator
			 for(int i = 0; i<map.map.length; i++){
				for (int j = 0; j<map.map[0].length; j++){
					if(map.map[i][j] > 0){
						
						Rectangle rect = new Rectangle(j * map.brickWidth + 80, i * map.brickHeight + 50,
								map.brickWidth, map.brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						//quando um tijolo é acertado, o valor dele muda para zero, assim sendo "destruido"
						if(ballRect.intersects(brickRect)){
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							//faz a bola "quicar" ao tocar um tijolo
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
						}
						
						
						
						
					}
				}
			}
			
			 //faz a bola quicar ao chegar em uma das bordas
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
		//recebe as teclas que o usuário digirar
		
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
		
		//reinicia o jogo apenas se play for false
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				
				if (win){
					play = true;
					ballposX = 120;
					ballposY = 350;
					ballXdir=-3;
					ballYdir=-4;
					System.out.println(ballXdir);
					playerX = 310;
					//score = 0;
					totalBricks = 21;
					map = new MapGenerator(3, 7);
				}else {
					if (score > hScore)
							hScore = score;
					play = true;
					ballposX = 120;
					ballposY = 350;
					ballXdir = -2;
					ballYdir = -3;
					playerX = 310;
					score = 0;
					totalBricks = 21;
					map = new MapGenerator(3, 7);
					}
					repaint();
			}
		}
	}
	
	//moveRight e moveLeft movimentam o jogador, se o jogo ainda não estiver rodando, irá rodar
	//você pode segurar as setas  também caso precise andar de um extremo para outro
	public void moveRight(){
		if (!play)
			play = true;
		playerX += 10;
	}
	
	public void moveLeft(){
		if (!play)
			play = true;
		playerX -= 10;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
