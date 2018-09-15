/*
# Jogo feito por:
#
# Raniel César (ranoob)
#
# Pode usar o código a vontade, mas não
# tire os créditos. :D
#
#
*/

package tableSnake;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tela extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private final Engine motor;
    private Timer timer;
    
    private Tela() 
    {
        motor = createEngine();
        setPropriedades();
    }
    
    private Engine createEngine() 
    {        
        Container conteudo = getContentPane();
        Game jogo = new Game();
        Engine motor = new Engine(jogo);
        
        int canvasLargura = Propriedades.PIXELS * Propriedades.COL;
        int canvasAltura = Propriedades.PIXELS * Propriedades.ROW;
        
        motor.setPreferredSize(new Dimension(canvasLargura, canvasAltura));
        
        addKeyListener(new Teclas());
        
        conteudo.add(motor);
        
        return motor;
    }
    
    private void setPropriedades() 
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("TableSnake");
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null); // centralizar tela
    }
    
    public void startGame(Engine motor) 
    {
        Thread processo = new Thread(motor);
        
        processo.start();
    }
    
    
    
    private class Engine extends JPanel implements Runnable {

        private static final long serialVersionUID = 1L;
        
        private final Game jogo;      
        private Status status = Status.NAO_COMECOU;
        
        private Engine(Game game)
        {
           this.jogo = game;
        }        
        
        @Override
        protected void paintComponent(Graphics lapis)
        {            
            super.paintComponent(lapis);
            
            if (System.getProperty("os.name").equals("Linux"))
            {
                 Toolkit.getDefaultToolkit().sync();
            }
            
            setBackground(Propriedades.BG);
            jogo.pintar(lapis);
        }
        
        @Override
        public void run() 
        {            
            long lastTime = System.nanoTime();
            double tempoPassado = 0.0;
            double FPS = 20.0;
            
            // Game loop
            while (true)
            {
                long now = System.nanoTime();
                
                tempoPassado += ((now - lastTime) /  1_000_000_000.0) * FPS;
                
                if (tempoPassado >= 1)
                {
                    jogo.atualizar();                    
                    tempoPassado--;
                }
                
                sleep();
                
                repaint();
            }
        }       
    }
    
    public void sleep() 
    {        
        try
        {
            Thread.sleep(95); // muda a velocidade da cobrinha. quanto maior o número, mais devagar
        } catch (InterruptedException exception) { }
    }       
    
    
    public class Teclas extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent evento)
        {            
            if (motor.status == Status.NAO_COMECOU)
            {
                startGame(motor);
                motor.status = Status.RODANDO;
            }
            
            switch (evento.getKeyCode())
            {
            	case KeyEvent.VK_LEFT:
            	case KeyEvent.VK_A:
            		motor.jogo.directionLeft();
            		break;
            		
            	case KeyEvent.VK_RIGHT:
            	case KeyEvent.VK_D:
            		motor.jogo.directionRight();
            		break;
            		
            	case KeyEvent.VK_UP:
            	case KeyEvent.VK_W:
            		motor.jogo.directionUp();
            		break;
            		
            	case KeyEvent.VK_DOWN:
            	case KeyEvent.VK_S:
            		motor.jogo.directionDown();
            		break;
            	default:
            		break;
            }
        }
    }
    
    public static void main(String[] args)
    {
       Tela tela = new Tela();
    }
}