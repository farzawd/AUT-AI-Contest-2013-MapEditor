package visualizer;

import game.map.Field;
import io.MapManager;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class Menu extends JPanel
{
	private static final long	serialVersionUID	= -222471052834565867L;

	private JComboBox<Integer> activeTeamNumber;
	private JComboBox<String> activeEntity;

	private GraphicalOutput delegate;
	
	private JLabel activeTileLabel;

	String[] entityKeys = {"Ground", "Wall", "Supplies", "HQs", "Melee Unit", "Ranged Unit"};
	Character[] entityVals = {'.', '#', 'X', '0', 'a', 'A'};

	public Menu(GraphicalOutput delegate)
	{
		this.delegate = delegate;

		this.setSize(200, 600);
		//		this.setLayout(new GridLayout(2, 2));

		Integer[] teams = new Integer[10];
		for(int i = 0 ; i < 10 ; i++)
			teams[i] = i;

		activeTeamNumber = new JComboBox<Integer>(teams);

		activeEntity = new JComboBox<String>(entityKeys);

		activeTileLabel = new JLabel("---(xxx, yyy)---");
		
		this.add(activeTileLabel);
		this.add(new JLabel("Team Number:"));
		this.add(activeTeamNumber);
		this.add(new JLabel("Active Object:"));
		this.add(activeEntity);
		this.add(new NewBtn());
		this.add(new LoadBtn());
		this.add(new SaveBtn());
	}

	public int getActiveTeam()
	{
		return activeTeamNumber.getSelectedIndex();
	}

	public char getActiveEntity()
	{
		return entityVals[activeEntity.getSelectedIndex()];
	}
	
	public void setActiveTile(Point p)
	{
		this.activeTileLabel.setText(String.format("---(%4d, %4d)---", p.x, p.y));
	}

	private class LoadBtn extends JButton
	{
		/**
		 * 
		 */
		private static final long	serialVersionUID	= 4548597054716917331L;

		public LoadBtn()
		{
			this.setText("Load Map");

			this.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					FileFilter filter = new FileFilter()
					{
						
						@Override
						public String getDescription()
						{
							return ".aimap";
						}
						
						@Override
						public boolean accept(File arg0)
						{
							// TODO Auto-generated method stub
							return arg0.getName().endsWith(".aimap") || arg0.isDirectory();
						}
					};
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.addChoosableFileFilter(filter);
					fileChooser.setFileFilter(filter);
					
					//FileNameExtentionFilter?

					int value = fileChooser.showOpenDialog(null);
					String path;

					if(value == JFileChooser.APPROVE_OPTION)
					{
						path = fileChooser.getSelectedFile().getAbsolutePath();

						try
						{
							delegate.update(MapManager.loadMap(path));
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(Menu.this, "Map not supported!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
	}

	private class SaveBtn extends JButton
	{
		/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;

		public SaveBtn()
		{
			this.setText("Save Map");

			this.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					FileFilter filter = new FileFilter()
					{
						
						@Override
						public String getDescription()
						{
							return ".aimap";
						}
						
						@Override
						public boolean accept(File arg0)
						{
							// TODO Auto-generated method stub
							return arg0.getName().endsWith(".aimap") || arg0.isDirectory();
						}
					};
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.addChoosableFileFilter(filter);
					fileChooser.setFileFilter(filter);
					
					int value = fileChooser.showSaveDialog(Menu.this);
					String path;

					if(value == JFileChooser.APPROVE_OPTION)
					{
						path = fileChooser.getSelectedFile().getAbsolutePath();
						
						if(!path.endsWith(fileChooser.getFileFilter().getDescription()))
							path += fileChooser.getFileFilter().getDescription();

						try
						{
							MapManager.saveMap(delegate.map, path);
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(Menu.this, "Cannot write on disk!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
	}

	private class NewBtn extends JButton
	{
		/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;

		public NewBtn()
		{
			this.setText("New Map");

			this.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					int width = -1, height = -1;

					while(width <= 0 || width > 300)
					{
						String input = JOptionPane.showInputDialog("Enter new map's width(1-300):", "30");

						if(input == null)
							return;

						try
						{
							width = Integer.parseInt(input);
						}
						catch(NumberFormatException e)
						{
							JOptionPane.showMessageDialog(Menu.this, "A number is expected!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}

					while(height <= 0 || height > 300)
					{
						String input = JOptionPane.showInputDialog("Enter new map's height(1-300):", "30");

						if(input == null)
							return;

						try
						{
							height = Integer.parseInt(input);
						}
						catch(NumberFormatException e)
						{
							JOptionPane.showMessageDialog(Menu.this, "A number is expected!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}

					delegate.update(new Field(width, height));
				}
			});
		}
	}
}
