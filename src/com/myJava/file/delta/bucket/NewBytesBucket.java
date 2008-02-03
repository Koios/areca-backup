package com.myJava.file.delta.bucket;

import java.io.IOException;
import java.io.InputStream;

import com.myJava.file.delta.Constants;
import com.myJava.file.delta.tools.IOHelper;
import com.myJava.object.ToStringHelper;

/**
 * [NEW_BYTES_SIGNATURE : 8 bytes][SIZE : 4 bytes][DATA : *SIZE* bytes]
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 8290826359148479344
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
public class NewBytesBucket
extends AbstractBucket
implements Bucket, Constants {
    private byte[] tmp = new byte[4];
    private long length;
    private long readOffset = 0;
    
    public long getLength() {
        return length;
    }

    public void init(InputStream in) throws IOException {
        in.read(tmp);
        this.readOffset = 0;
        this.length = IOHelper.get32(tmp, 0);
    }

    public long getSignature() {
        return SIG_NEW;
    }

    public long getReadOffset() {
        return readOffset;
    }

    public void setReadOffset(long readOffset) {
        this.readOffset = readOffset;
    }
    
    public String toString() {
        StringBuffer sb = ToStringHelper.init(this);
        ToStringHelper.append("ReadOffset", readOffset, sb);
        ToStringHelper.append("From", from, sb);
        ToStringHelper.append("Length", length, sb);
        return ToStringHelper.close(sb);
    }
}
