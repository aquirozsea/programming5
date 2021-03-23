/*
 * JDateField.java
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *This class is a swing text field which incorporates methods to parse and format date strings and return the associated 
 *date objects. It uses the standard java utility Canlendar and DateFormat implementations, so a format string following 
 *the conventions of SimpleDateFormat must be specified for the object to work. There is also an awt counterpart.
 *@see DateField
 *@see java.text.SimpleDateFormat
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class JDateField extends JTextField {
	
	protected SimpleDateFormat formatter;
	
	/**
         *Creates a date field that parses according to the given date format string
         */
        public JDateField(String dateFormat) {
		super();
		formatter = new SimpleDateFormat(dateFormat);
	}
	
	/**
         *Creates a date field that parses according to the given date format string. The field is initialized showing the format pattern.
         */
        public JDateField(String dateFormat, boolean showPattern) {
		super();
		formatter = new SimpleDateFormat(dateFormat);
		if (showPattern) {
			this.setText(dateFormat);
		}
	}
	
	/**
         *Sets the date format with the given format string
         */
        public void setFormat(String dateFormat) {
		formatter.applyPattern(dateFormat);
	}
	
	/**
         *@return a Calendar object with the date parsed from the text field
         */
        public Calendar getDate() throws ParseException {
		Calendar ret = new GregorianCalendar();
		ret.setTime(formatter.parse(this.getText()));
		return ret;
	}
	
	/**
         *Sets the text of the field with the date from the given Calendar object.
         */
        public void setDate(Calendar c) {
		this.setText(formatter.format(c.getTime()));
	}
	
	/**
         *Sets the text of the field with the current date.
         */
        public void setCurrentDate() {
		this.setText(formatter.format(Calendar.getInstance().getTime()));
	}
	
}
