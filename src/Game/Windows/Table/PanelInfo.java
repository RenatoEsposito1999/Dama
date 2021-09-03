package Game.Windows.Table;
import javax.swing.*;

import Game.FactoryM.Players.Observer;
import Game.FactoryM.Players.Player;
import Game.Windows.GraphicWindow;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelInfo extends GraphicWindow implements ActionListener, Observer{

    private JPanel panelInfo;
    private ArrayList<JComponent> jComponentList = new ArrayList<>();
    private int nmove = 0;
    private int time=3;


    public PanelInfo(int n, int dim, Color c, LayoutManager lm, Player p1, Player p2, int n_sec){
        time = n_sec;
        panelInfo = addPanel(n, dim, c, lm);
        panelInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jComponentList.add(addLabel("Game Info",new Font("Verdana", Font.BOLD, 17)));
        jComponentList.add(addLabel(p1.getPlayerName()));
        jComponentList.get(1).setForeground(p1.getPlayerColor());
        jComponentList.add(addLabel(p1.getPlayerName() + "'s" + " score: "+ p1.getPlayerScore()));
        jComponentList.add(addLabel(p2.getPlayerName()));
        jComponentList.get(3).setForeground(p2.getPlayerColor());
        jComponentList.add(addLabel(p2.getPlayerName() + "'s" + " score: "+ p2.getPlayerScore()));
        jComponentList.add(addLabel("N° moves: " + nmove));
        jComponentList.add(addLabel("Turn: " + p1.getPlayerName()));
        jComponentList.get(6).setForeground(p1.getPlayerColor());
        jComponentList.add(addLabel("Timer: " + time));
        jComponentList.add(addButton("Give up", this, "1"));
        jComponentList.add(addButton("Restart", this, "2"));


        for (JComponent jComponent : jComponentList) {
            panelInfo.add(jComponent);
        }
        
        panelInfo.setVisible(true);
    }

    public JPanel getpanelInfo(){
        return panelInfo;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String action = arg0.getActionCommand();
        CheckersTable ct= null;
        try {
            ct =  CheckersTable.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (action){
            case "1":
                String name = null;
                name = ct.getActivePlayer().getPlayerName();
                JOptionPane.showMessageDialog(null, "Game over! " + name +" gave up.");
                System.exit(1);
                break;
            case "2":
                ct.returnToStart();
                ct.stopTimer();
                break;
        }
    }

    public  void updateScore(Player p){
        JLabel jl;
        jl = (p.getPlayerColor().equals(Color.RED)) ? (JLabel) jComponentList.get(2) : (JLabel) jComponentList.get(4);
        jl.setText(p.getPlayerName() + "'s" + " score: "+ p.getPlayerScore());
    }

    public void switchTurn(Player p){
        JLabel jl = (JLabel) jComponentList.get(6);
        jl.setText("Turn: " + p.getPlayerName());
        jl.setForeground(p.getPlayerColor());
        increaseNMOVE();
    }

    private void increaseNMOVE(){
        nmove++;
        JLabel jl = (JLabel) jComponentList.get(5);
        jl.setText("N° moves: " + nmove);
    }

    @Override
    public void update(Object obj){
        time--;
        JLabel jl = (JLabel) jComponentList.get(7);
        jl.setText("Timer: " + time);
    }
}