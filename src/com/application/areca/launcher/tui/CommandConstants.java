package com.application.areca.launcher.tui;


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
public interface CommandConstants {  
    public static UserCommand COMMAND_BACKUP = new UserCommand("backup");
    public static UserCommand COMMAND_RECOVER = new UserCommand("recover");
    public static UserCommand COMMAND_MERGE = new UserCommand("merge");
    public static UserCommand COMMAND_DESCRIBE = new UserCommand("describe");
    public static UserCommand COMMAND_INFOS = new UserCommand("infos");
    public static UserCommand COMMAND_DELETE = new UserCommand("delete");  
    public static UserCommand COMMAND_CHECK = new UserCommand("check");
    public static UserCommand COMMAND_NEWTARGET = new UserCommand("newtarget");
    
    public static SimpleUserOption OPTION_CONFIG = new SimpleUserOption("-config");
    public static SimpleUserOption OPTION_DESTINATION = new SimpleUserOption("-destination");
    public static SimpleUserOption OPTION_TARGET = new SimpleUserOption("-target");
    public static SimpleUserOption OPTION_DELAY = new SimpleUserOption("-delay");
    public static SimpleUserOption OPTION_FROM = new SimpleUserOption("-from");
    public static SimpleUserOption OPTION_TO = new SimpleUserOption("-to");
    public static SimpleUserOption OPTION_DATE = new SimpleUserOption("-date");
    public static UserFlag OPTION_CHECK_FILES = new UserFlag("-c");
    public static UserFlag OPTION_OVERWRITE = new UserFlag("-o");
    public static UserFlag OPTION_NO_SUBDIR = new UserFlag("-nosubdir");
    public static SimpleUserOption OPTION_SPEC_LOCATION = new SimpleUserOption("-wdir");
    public static UserFlag OPTION_SYNC = new UserFlag("-s");
    public static UserFlag OPTION_RESUME = new UserFlag("-resume"); 
    public static SimpleUserOption OPTION_RESUME_CONDITIONAL = new SimpleUserOption("-cresume"); 
    public static UserFlag OPTION_CHECK_ALL = new UserFlag("-a"); 
    public static UserFlag OPTION_FULL_BACKUP = new UserFlag("-f");
    public static UserFlag OPTION_KEEP_DELETED_ENTRIES = new UserFlag("-k");
    public static UserFlag OPTION_DIFFERENTIAL_BACKUP = new UserFlag("-d");
    public static SimpleUserOption OPTION_TITLE = new SimpleUserOption("-title");
    public static SimpleUserOption OPTION_TEMPLATE = new SimpleUserOption("-template");
    public static SimpleUserOption OPTION_WORKSPACE = new SimpleUserOption("-workspace");
    public static SimpleUserOption OPTION_NAME = new SimpleUserOption("-name");
    public static DoubleDashOption OPTION_DOUBLEDASH = new DoubleDashOption("--");
}
