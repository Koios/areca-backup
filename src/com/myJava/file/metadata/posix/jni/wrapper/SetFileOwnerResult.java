package com.myJava.file.metadata.posix.jni.wrapper;

import com.myJava.object.ToStringHelper;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 5570316944386086207
 */

 /*
 Copyright 2005-2009, Olivier PETRUCCI.

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
public class SetFileOwnerResult extends AbstractMethodResult {
	public String toString() {
		StringBuffer sb = ToStringHelper.init(this);
		ToStringHelper.append("returnCode", returnCode, sb);
		ToStringHelper.append("errorNumber", errorNumber, sb);
		ToStringHelper.append("transcodedErrorNumber", transcodedErrorNumber, sb);
		return ToStringHelper.close(sb);
	}
}