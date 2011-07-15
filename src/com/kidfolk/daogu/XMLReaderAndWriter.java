package com.kidfolk.daogu;

import java.util.List;
/**
 * xml文件操作接口
 * @author kidfolk
 *
 * @param <T>
 */
public interface XMLReaderAndWriter<T> {
	
	public List<T> reader();
	
	public void writer(List<T> list);

}
