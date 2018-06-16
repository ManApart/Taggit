package org.iceburg.taggit.ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;
import org.iceburg.taggit.resources.Resources;

public class TagUIManager {
	private JFrame frame;
	private JPanel mainPane;
	private LibraryPane libraryPane;
	
	public TagUIManager(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Taggit");
//		Image img = new ImageIcon((Starship.getData()).getClass().getResource(shipIcon)).getImage();
		frame.setIconImage((new ImageIcon(Resources.class.getResource("logo.png"))).getImage());
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLocation(400, 200);
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		mainPane.add(new TitlePage());
		frame.setContentPane(mainPane);
		frame.pack();
		
		enforeceMinimumSize();
	}
	
	private void enforeceMinimumSize() {
		Dimension d = frame.getContentPane().getSize();
		if (d.width < 800){
			d.width = 800;
		}
		if (d.height < 500){
			d.height = 500;
		}
		frame.getContentPane().setPreferredSize(d);
		frame.pack();
	}

	public void sendUpdate(TagLibrary update){
		if (libraryPane == null){
			libraryPane = new LibraryPane(update);
			mainPane.add(libraryPane);
		} else{
			libraryPane.sendUpdate(update);
		}
		mainPane.revalidate();
		mainPane.repaint();
	}
	public void sendUpdate(TaggedFile update){
		if (libraryPane != null){
			libraryPane.sendUpdate(update);
		}
	}

}
