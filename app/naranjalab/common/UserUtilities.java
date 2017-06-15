package naranjalab.common;

import org.slf4j.Logger;

import naranjalab.dao.DBAccessFactory;
import naranjalab.dao.DBQueryExecutor;
import naranjalab.descriptors.UserDescriptor;
import naranjalab.descriptors.UserDescriptorStatus;

public class UserUtilities {
	
	public static String userLogin(String username, String pwd, Logger logger){
		if(username == null || username.isEmpty()) return "Usuario Invalido";
		if(pwd == null || pwd.isEmpty()) return "Clave invalida";
		DBQueryExecutor dbexecutor = DBAccessFactory.getExecutor();
		if(dbexecutor == null) return "Invalid db executor";
		UserDescriptor user = dbexecutor.getUserDescriptor(username);
		logger.info("Got result: {}", new Object[]{user});
		if(user == null) return "Usuario Invalido";
		if(user.getStatus() == UserDescriptorStatus.GOOD && user.getPwd().equals(pwd))
			return null;
		else
			return "Usuario Invalido";
	}
}
