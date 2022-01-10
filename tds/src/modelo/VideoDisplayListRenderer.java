package modelo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

@SuppressWarnings("serial")
public class VideoDisplayListRenderer extends DefaultListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (value != null && value instanceof VideoDisplay) {
				VideoDisplay ele = (VideoDisplay) value;
				if(isSelected)
				{
					ele.setBackground(new Color(113,169,236));
				}
				else{
					ele.setBackground(list.getBackground());
					ele.setForeground(list.getForeground());
				}
				return ele;
			}			
			else
			{
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
	}
}

