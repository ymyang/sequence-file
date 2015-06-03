package com.ymicloud.sequence;


import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * sequence
 * @author yang
 *
 */
public class Sequence {
	
	private static final int SERVER_ID = 001;

	private static final int CACHE_SIZE = 1000;

    private static int count = CACHE_SIZE + 1;
    private static long index = 0;

    private static long getSequenceValue()
    {
    	long seq = 0;
    	try {
    		File file = new File("/opt/ymicloud/.sequence");
    		String str = FileUtils.readFileToString(file);
        	seq = Long.parseLong(str.trim());
        	FileUtils.write(file, String.valueOf(seq + CACHE_SIZE));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
        return seq;
    }

    public synchronized static long getNextId() {
        // 如果发放的序列号小于缓存的个数，则通过缓存发放一个序列号
        if (count < CACHE_SIZE) {
            count++;
            index++;
        } else {
            // 获取一组序列号，缓存
            index = getSequenceValue();
            count = 1;
        }
        long seq = index * 1000 + SERVER_ID;
        return seq;
    }

}
