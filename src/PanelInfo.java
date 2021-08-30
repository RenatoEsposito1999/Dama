import javax.swing.*;
import java.awt.*;

import java.awt.event.*;  

import java.awt.event.ActionListener;
import java.util.ArrayList;
public class PanelInfo{

    private JPanel panelInfo;
    private ArrayList<JLabel> jlabelList = new ArrayList<JLabel>();




    public  PanelInfo(int n, int dim, Color c, LayoutManager lm, Player p1, Player p2){
        panelInfo = CGO.addPanel(n, dim, c, lm);
        jlabelList.add(CGO.addLabel("Informazioni di Gioco",new Font("Verdana", Font.PLAIN, 18)));

        jlabelList.add(CGO.addLabel("Player1: " + p1.getPlayerName()));
        jlabelList.add(CGO.addLabel("Punteggio: "+p1.getPlayerScore()));
        jlabelList.add(CGO.addLabel("Player2: " + p2.getPlayerName()));
        jlabelList.add(CGO.addLabel("Punteggio: " + p2.getPlayerScore()));
        
        for (JLabel jb : jlabelList)
            panelInfo.add(jb);
        panelInfo.setVisible(true);
    }

    public JPanel getpanelInfo(){
        return panelInfo;
    }
}