package com.application.areca.launcher.tui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * Classe implementant une commande utilisateur 
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
public class UserCommandLine implements CommandConstants {

    private static Map<String, UserCommand> ACCEPTED_COMMANDS;
    
    static {
        Launcher.COMMAND_BACKUP.addMandatoryArgument(Launcher.OPTION_CONFIG);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_TARGET);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_FULL_BACKUP);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_DIFFERENTIAL_BACKUP);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_CHECK_FILES);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_SPEC_LOCATION);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_SYNC);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_RESUME);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_RESUME_CONDITIONAL);
        Launcher.COMMAND_BACKUP.addOptionalArgument(Launcher.OPTION_TITLE);
   
        Launcher.COMMAND_MERGE.addMandatoryArgument(Launcher.OPTION_CONFIG);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_TARGET);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_DATE);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_DELAY);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_FROM);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_TO);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_KEEP_DELETED_ENTRIES);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_TITLE);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_SPEC_LOCATION);
        Launcher.COMMAND_MERGE.addOptionalArgument(Launcher.OPTION_CHECK_FILES);
        
        Launcher.COMMAND_RECOVER.addMandatoryArgument(Launcher.OPTION_CONFIG);
        Launcher.COMMAND_RECOVER.addMandatoryArgument(Launcher.OPTION_DESTINATION);
        Launcher.COMMAND_RECOVER.addOptionalArgument(Launcher.OPTION_TARGET);
        Launcher.COMMAND_RECOVER.addOptionalArgument(Launcher.OPTION_DATE);
        Launcher.COMMAND_RECOVER.addOptionalArgument(Launcher.OPTION_CHECK_FILES);
        Launcher.COMMAND_RECOVER.addOptionalArgument(Launcher.OPTION_OVERWRITE);
        Launcher.COMMAND_RECOVER.addOptionalArgument(Launcher.OPTION_NO_SUBDIR);

        Launcher.COMMAND_CHECK.addMandatoryArgument(Launcher.OPTION_CONFIG);
        Launcher.COMMAND_CHECK.addOptionalArgument(Launcher.OPTION_TARGET);
        Launcher.COMMAND_CHECK.addOptionalArgument(Launcher.OPTION_DATE);
        Launcher.COMMAND_CHECK.addOptionalArgument(Launcher.OPTION_DESTINATION);
        Launcher.COMMAND_CHECK.addOptionalArgument(Launcher.OPTION_SPEC_LOCATION);
        Launcher.COMMAND_CHECK.addOptionalArgument(Launcher.OPTION_CHECK_ALL);
        
        Launcher.COMMAND_DESCRIBE.addMandatoryArgument(Launcher.OPTION_CONFIG);

        Launcher.COMMAND_DELETE.addMandatoryArgument(Launcher.OPTION_CONFIG);
        Launcher.COMMAND_DELETE.addOptionalArgument(Launcher.OPTION_TARGET);
        Launcher.COMMAND_DELETE.addOptionalArgument(Launcher.OPTION_DATE);
        Launcher.COMMAND_DELETE.addOptionalArgument(Launcher.OPTION_DELAY);
        
        Launcher.COMMAND_NEWTARGET.addMandatoryArgument(Launcher.OPTION_WORKSPACE);
        Launcher.COMMAND_NEWTARGET.addMandatoryArgument(OPTION_TEMPLATE);
        Launcher.COMMAND_NEWTARGET.addOptionalArgument(Launcher.OPTION_NAME);
        Launcher.COMMAND_NEWTARGET.addMandatoryArgument(Launcher.OPTION_DOUBLEDASH);
        
        ACCEPTED_COMMANDS = new HashMap<String, UserCommand>();
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_INFOS.getName(), Launcher.COMMAND_INFOS);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_BACKUP.getName(), Launcher.COMMAND_BACKUP);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_MERGE.getName(), Launcher.COMMAND_MERGE);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_RECOVER.getName(), Launcher.COMMAND_RECOVER);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_DESCRIBE.getName(), Launcher.COMMAND_DESCRIBE);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_DELETE.getName(), Launcher.COMMAND_DELETE);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_CHECK.getName(), Launcher.COMMAND_CHECK);
        ACCEPTED_COMMANDS.put(Launcher.COMMAND_NEWTARGET.getName(), Launcher.COMMAND_NEWTARGET);
    }
    
    private String[] args;
    private UserCommand command;
    private HashMap<UserOption, Object> options;
    
    public UserCommandLine(String[] args) {
        this.args = rebuildArguments(args);
        this.options = new HashMap<>();
    }
    
    public void parse() throws InvalidCommandException {
        if (args.length == 0) {
            throw new InvalidCommandException("a command must be provided");
        }
        
        List<String> argsList = Arrays.asList(args);
        Iterator<String> iArg = argsList.iterator();
        
        final String commandName = iArg.next();
        if (! validateCommand(commandName)) {
            throw new InvalidCommandException("invalid command : " + commandName);
        }
        command = ACCEPTED_COMMANDS.get(commandName);
        
        if(iArg.hasNext()) {
        	String option = iArg.next();
            while (true) {
                if (option.length() == 0) { continue; }
                
                UserOption userOption = command.getArgument(option);
                if (userOption == null) {
                    throw new InvalidCommandException("invalid option : " + option);
                }
                
                UserOption.ParseResult res = userOption.parse(iArg);
                options.put(userOption, res.getValue());
                
                if(res.getCurrentArgument() != null) {
                	option = res.getCurrentArgument();
                }else if(iArg.hasNext()) {
                	option = iArg.next();
                }else {
                	break;
                }
            }
        }
        
        command.validateArguments(options);
        this.validateStructure(command);
    }
    
    /**
     * Checks that the command is valid.
     */
    protected static boolean validateCommand(String command) {
        return ACCEPTED_COMMANDS.containsKey(command);
    }
    
    /**
     * Validates the mandatory options.
     */
    protected void validateStructure(UserCommand command) throws InvalidCommandException {
        if (command == Launcher.COMMAND_DELETE) {
        	if(! (   this.options.containsKey(Launcher.OPTION_DELAY)
                  || this.options.containsKey(Launcher.OPTION_TO)
                  || this.options.containsKey(Launcher.OPTION_DATE)
                 )
            ) {
                throw new InvalidCommandException("the " +
                        Launcher.OPTION_TO.getName() + " or " + Launcher.OPTION_DATE.getName() + " option is mandatory");
        	}
        }
    }
    
    public UserCommand getCommand() {
        return this.command;
    }
    
    public <T> T getOption(UserOption<T> option) {
        return (T)this.options.get(option);
    }
    
    public boolean hasOption(UserOption option) {
        return this.options.containsKey(option);
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<args.length; i++) {
            if (args[i].trim().length() != 0) {
            	String arg = args[i];
            	if (arg.indexOf(' ') != -1) {
            		arg = "\"" + arg + "\"";
            	}
                sb.append(arg).append(" ");
            }
        }
        return sb.toString().trim();
    }
    
    private static String[] rebuildArguments(String[] args) {
        List<String> ret = new ArrayList<String>();
        boolean isStarted = false;
        String currentPart = "";
        for (int i=0; i<args.length; i++) {
            String data = args[i].trim();
            if (data.length() != 0) {
                if (isFirstPart(data)) {
                    if (isLastPart(data)) {
                        add(ret, data.substring(1, data.length() - 1));
                    } else {
                        isStarted = true;
                        currentPart = data.substring(1);
                    }
                } else if (isLastPart(data)) {
                    isStarted = false;
                    currentPart += " ";
                    currentPart += data.substring(0, data.length() - 1);
                    add(ret, currentPart);
                    currentPart = "";
                } else if (isStarted) {
                    currentPart += " ";
                    currentPart += data;
                } else {
                    add(ret, data);
                }
            }
        }
        
        return (String[])ret.toArray(new String[ret.size()]);
    }
    
    
    private static void add(List<String> l, String v) {
        if (v.trim().length() != 0) {
            l.add(v);
        }
    }
    
    private static boolean isFirstPart(String s) {
        return 
        	s.charAt(0) == '\"' 
        	|| s.charAt(0) == '['
        ;
    }
    
    private static boolean isLastPart(String s) {
        return s.endsWith("\"") || s.endsWith("]");
    }
    
    public static void main(String[] args) {
        show(rebuildArguments(args));
    }
    
    private static void show(String[] args) {
        System.out.println("----------------------------------");
        for (int i=0; i<args.length; i++) {
            System.out.println("<" + args[i] + ">");
        }
    }
}