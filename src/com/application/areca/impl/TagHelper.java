package com.application.areca.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;

import com.application.areca.context.ProcessContext;
import com.myJava.file.FileSystemManager;
import com.myJava.util.CalendarUtils;
import com.myJava.util.Utilitaire;
import com.myJava.util.log.Logger;
import com.myJava.util.os.OSTool;

/**
 * Helper class for dynamic tags processing
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : -1700699344456460829
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
public class TagHelper {
    public static final String PARAM_ARCHIVE = "%ARCHIVE%";
    public static final String PARAM_ARCHIVE_NAME = "%ARCHIVE_NAME%";
    public static final String PARAM_TARGET_UID = "%TARGET_UID%";
    public static final String PARAM_TARGET_NAME = "%TARGET_NAME%";
    public static final String PARAM_COMPUTER_NAME = "%COMPUTER_NAME%";
    public static final String PARAM_USER_NAME = "%USER_NAME%";
    public static final String PARAM_DATE = "%DATE%";
    public static final String PARAM_TIME = "%TIME%";
    
    public static String replaceParamValues(String param, ProcessContext context) {
        if (param == null) {
            return null;
        }
        
        String value = param;
        
        value = Utilitaire.replace(value, PARAM_ARCHIVE, FileSystemManager.getAbsolutePath(context.getFinalArchiveFile()));
        value = Utilitaire.replace(value, PARAM_ARCHIVE_NAME, FileSystemManager.getName(context.getFinalArchiveFile()));
        
        value = Utilitaire.replace(value, PARAM_TARGET_UID, context.getReport().getTarget().getUid());
        value = Utilitaire.replace(value, PARAM_TARGET_NAME, context.getReport().getTarget().getTargetName());
        
        try {
            String localHost = InetAddress.getLocalHost().getHostName();
            value = Utilitaire.replace(value, PARAM_COMPUTER_NAME, localHost);
        } catch (UnknownHostException e) {
            Logger.defaultLogger().error("Error detecting local host name", e);
        }
        
        GregorianCalendar now = new GregorianCalendar();
        value = Utilitaire.replace(value, PARAM_DATE, CalendarUtils.getDateToString(now));
        value = Utilitaire.replace(value, PARAM_TIME, CalendarUtils.getTimeToString(now));
        value = Utilitaire.replace(value, PARAM_USER_NAME, OSTool.getUserName());
        
        return value;
    }
}