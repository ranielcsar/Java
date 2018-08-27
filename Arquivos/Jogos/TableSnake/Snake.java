package cobrinha;

import java.util.ArrayList;
import java.util.Iterator;


public class Snake implements Iterable<Elemento> {
    
    private ArrayList<Elemento> snakeList;
    private int size = 2;
    private Elemento tail;
    
    Snake() { this(Propriedades.initX, Propriedades.initY); }
    
    Snake(int x, int y)
    {
        snakeList = new ArrayList<>();
        snakeList.add(new Elemento(Elemento.Tipo.COBRA, x, y));
    }
    
    int getSize() { return size; }
    
    boolean moveLeft() { return mover(Direction.ESQUERDA); }
    boolean moveRight() { return mover(Direction.DIREITA); }
    boolean moveUp() { return mover(Direction.CIMA); }
    boolean moveDown() { return mover(Direction.BAIXO); }
    
    
    private synchronized boolean mover(Direction direcao)
    {        
        int coordX = 0;
        int coordY = 0;
        
        if (direcao == Direction.ESQUERDA)
        {
            coordX = -1;
        } else if (direcao == Direction.DIREITA) {
            coordX = 1;
        } else if (direcao == Direction.CIMA) {
            coordY = -1;
        } else if (direcao == Direction.BAIXO) {
            coordY = 1;
        }
        
        Elemento posicaoKbca = getHead();
        
        int oldX = posicaoKbca.getX();
        int oldY = posicaoKbca.getY();
        
        Elemento cabeca = new Elemento(Elemento.Tipo.COBRA, oldX + coordX, oldY + coordY);
        
        if (contains(cabeca)) return false;
        
        snakeList.add(0, cabeca);
        removerCauda();
        
        return true;
    }
    
    private synchronized void removerCauda()
    {        
        if (snakeList.size() > size)
        {
            snakeList.remove(snakeList.size() - 1);
        }
    }
    
    Elemento getTail() { return tail; }
    
    Elemento getHead() { return snakeList.get(0); }
    
    void crescer() { size++; }
    
    void crescer(int x) { size += x; }
    
    public synchronized Iterator<Elemento> iterator()
    {        
        return snakeList.iterator();
    }
    
    
    public boolean contains(Elemento elemento)
    {        
        for (Elemento element : this)
        {
            if (element.igual(elemento)) { return true; }
        }
        
        return false;
    }
    
    public String toString()
    {        
        StringBuilder string = new StringBuilder();
        
        for (Elemento element : this)
        {
            string.append(element);
            string.append("\n");
        }
        
        return new String(string);
    }
}
