/*
 * ComponentTools.java
 *
 * Copyright 2004 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.ui;

import javax.swing.*;
import java.awt.*;

/**
 *Utility class for UI component manipulation
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public abstract class ComponentTools {
	
	/**
	 *Enables all components contained in the given JPanel
	 */
	public static final void setPanelEnable(JPanel p, boolean ind) {
		Component[] compList = p.getComponents();
		for (Component comp : compList) {
			if (comp instanceof JPanel)
				setPanelEnable((JPanel)comp, ind);
			else
				comp.setEnabled(ind);
		}
	}

	/**
	 *Enables all components contained in the given Panel
	 */
	public static final void setPanelEnable(Panel p, boolean ind) {
		Component[] compList = p.getComponents();
		for (Component comp : compList) {
			if (comp instanceof Panel)
				setPanelEnable((Panel)comp, ind);
			else
				comp.setEnabled(ind);
		}
	}	
}
