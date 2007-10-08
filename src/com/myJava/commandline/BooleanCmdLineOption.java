package com.myJava.commandline;

/**
 * <BR>
 * @author Ludovic QUESNELLE
 * <BR>
 * <BR>Areca Build ID : 6222835200985278549
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class BooleanCmdLineOption extends CmdLineOption {
	boolean value_ =false;
	
	public BooleanCmdLineOption(boolean mandatory,String name,String comment) {
		super(mandatory,name,CmdLineOption.BOOLEAN,comment);
	}

	public void setValue(boolean value) {
		value_ = value;
		this.setHasBeenSet();
	}
	
	public Object getValue() {
		return new java.lang.Boolean(value_);
	}
}