package com.learn.mvc.excel.easypoi;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExcelHandler extends ExcelDataHandlerDefaultImpl<User> {

	private static final Logger log = LoggerFactory.getLogger(UserExcelHandler.class);


	@Override
	public Object importHandler(User obj, String name, Object value) {
		log.info(name+":"+value);
		return super.importHandler(obj, name, value);
	}

}
