package client.gui;

import client.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private final int width = 3;
    private final int fieldSize = 100;
    private final int fieldStrokeSize = 1;
    private final Color cFieldStroke = new Color(64, 64, 64);
    
    private BufferedImage imgCross;
    private BufferedImage imgZero;
    private BufferedImage imgSea;
    private BufferedImage imgSplash;
    private BufferedImage imgFire;
    private BufferedImage imgShipReveal;
    private BufferedImage imgShipProwH;
    private BufferedImage imgShipProwV;
    private BufferedImage imgShipBoardH;
    private BufferedImage imgShipBoardV;
    
    
    private int[][] shipsFields = new int[width][width];
    private boolean[][] shotsFields = new boolean[width][width];
    
    public BoardPanel(boolean active, ClientAction clientAction) {
        int realWidth = width * fieldSize + fieldStrokeSize;
        setMinimumSize(new Dimension(realWidth, realWidth));
        setMaximumSize(new Dimension(realWidth, realWidth));
        setPreferredSize(new Dimension(realWidth, realWidth));
        
        try {
            imgCross=ImageIO.read(Client.class.getResource("/gfx/cross.jpeg"));
            imgZero=ImageIO.read(Client.class.getResource("/gfx/zero.png"));

            imgSea = ImageIO.read(Client.class.getResource("/gfx/white_sea.jpg"));
            imgSplash = ImageIO.read(Client.class.getResource("/gfx/splash.png")); 
            imgFire = ImageIO.read(Client.class.getResource("/gfx/fire.png"));
            imgShipReveal = ImageIO.read(Client.class.getResource("/gfx/shipReveal.png"));
            imgShipProwH = ImageIO.read(Client.class.getResource("/gfx/shipProwH.png"));
            imgShipProwV = ImageIO.read(Client.class.getResource("/gfx/shipProwV.png"));
            imgShipBoardH = ImageIO.read(Client.class.getResource("/gfx/shipBoardH.png"));
            imgShipBoardV = ImageIO.read(Client.class.getResource("/gfx/shipBoardV.png"));
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
        
        if (active) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent ev) {
                    int limit = width - 1;
                    int x = Math.min(ev.getX() / fieldSize, limit);
                    int y = Math.min(ev.getY() / fieldSize, limit);
                    clientAction.shot(x, y);
                }
            });
        }
        
        setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int x = j * fieldSize;
                int y = i * fieldSize;
                g2d.setColor(cFieldStroke);
                g2d.setStroke(new BasicStroke(fieldStrokeSize));
                g2d.drawRect(x, y, fieldSize, fieldSize);
                //
//                float[] scales = { 1f, 1f, 1f, 0.5f };
//                float[] offsets = new float[4];
//                RescaleOp rop = new RescaleOp(scales, offsets, null);
                //
                g2d.drawImage(imgSea, x + fieldStrokeSize, y + fieldStrokeSize, 99, 99, this);
//                g2d.drawImage(imgSea,rop, 99, 99,this);


            }
        }
        
        drawShips(g2d);
        drawShots(g2d);
    }
    
    private void drawShips(Graphics2D g2d) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (shipsFields[j][i] != 0) {
                    int x = j * fieldSize;
                    int y = i * fieldSize;
                    BufferedImage imgShip;
                    
                    switch (shipsFields[j][i]) {
                        case Sprite.PROW_H: {
                            imgShip = imgShipProwH;
                            break;
                        }
                        case Sprite.BOARD_H: {
                            imgShip = imgShipBoardH;
                            break;
                        }
                        case Sprite.PROW_V: {
                            imgShip = imgShipProwV;
                            break;
                        }
                        case Sprite.BOARD_V: {
                            imgShip = imgShipBoardV;
                            break;
                        }
                        default: {
                            imgShip = imgShipReveal;
                        }
                    }
                    
                    g2d.drawImage(imgShip, x + fieldStrokeSize, y + fieldStrokeSize, 0, 0, this);
                }
            }
        }
    }
    
    private void drawShots(Graphics2D g2d) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (shotsFields[j][i]) {
                    int x = j * fieldSize;
                    int y = i * fieldSize;
                    BufferedImage imgShot = shipsFields[j][i] != 0 ? imgCross : imgZero;
                    g2d.drawImage(imgShot, x + fieldStrokeSize, y + fieldStrokeSize, 99, 99, this);
                }
            }
        }
    }
    
    public void markShip(int x, int y, int sprite) {
        shipsFields[x][y] = sprite;
    }
    
    public void markShot(int x, int y) {
        shotsFields[x][y] = true;
    }
    
    public void clear() {
        shipsFields = new int[width][width];
        shotsFields = new boolean[width][width];
    }
}