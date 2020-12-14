
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.*;

public class GameEndPanel extends JPanel
{
    private ArrayList<Integer> top10;
    private String finalTime; 
    private int finalPoints;
    private Setup config;
    private JLabel timelabel;
    private JLabel pointlabel;
    private JPanel labels;
    private JTextArea top10area;
    

    public GameEndPanel(Setup config, int points, String time, ArrayList<Integer> top10)
    {
        Font endFont = new Font("Monaco", Font.BOLD, 20);
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        //setup the class variables
        this.config = config;
        this.finalPoints = points;
        this.finalTime = time;
        this.top10 = top10;

        top10.add(points);
        Collections.sort(top10);
        if(top10.size() > 10)
        {
            top10.remove(0);
        }
        top10area = new JTextArea();
        top10area.setEditable(false);
        top10area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        top10area.setBackground(Color.GRAY);
        top10area.setSize(new Dimension(400,400));
        top10area.setFont(endFont);
        add(top10area, BorderLayout.CENTER);
        top10area.append("TOP SCORES\n");
        for(int i = 1; i <= top10.size(); i++)
        {
            top10area.append(i + ". " + top10.get(top10.size() - i) + "\n");
        }
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        labels = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pointlabel = new JLabel();
        pointlabel.setText("FINAL POINTS: " + points);
        timelabel = new JLabel();
        timelabel.setText(time);
        pointlabel.setFont(endFont);
        timelabel.setFont(endFont);
        pointlabel.setBorder(raisedetched);
        timelabel.setBorder(raisedetched);
        labels.add(pointlabel);
        labels.add(timelabel);
        add(labels, BorderLayout.NORTH);

    }

    @Override
	public Dimension getPreferredSize() {
		return new Dimension(750, 750);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(750, 750);
	}

}
