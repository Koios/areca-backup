package com.application.areca.launcher.tui;

import java.util.Iterator;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 *
 */

 /*
 Copyright 2005-2015, Olivier PETRUCCI.

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
public abstract class UserOption<Value> {
    private String name;

    public UserOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public class ParseResult {
    	private String currentArgument;
        private Value value;
        
        public ParseResult(String currentArgument, Value value) {
        	this.currentArgument = currentArgument;
        	this.value = value;
        }
        
        public String getCurrentArgument() { return currentArgument; }
        public Value getValue() { return value; }
    }
    
    public abstract ParseResult parse(Iterator<String> iArgs) throws InvalidCommandException;
}