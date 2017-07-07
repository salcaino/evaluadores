package naranjalab.common;

import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.apache.commons.validator.routines.EmailValidator;

import naranjalab.common.util.EncryptionHelper;
import naranjalab.common.util.Utility;
import naranjalab.dao.DBAccessFactory;
import naranjalab.dao.DBQueryExecutor;
import naranjalab.descriptors.UserDescriptor;
import naranjalab.descriptors.UserDescriptorStatus;
import naranjalab.form.NewUserDescriptor;

public class UserUtilities {
	private static Integer periodoDias = Integer.parseInt(System.getProperty("valido.dias", "30"));

	private static Map<String, UserDescriptor> users = new HashMap<>();

	public static String updateUser(String user, String pwd) {
		if(user == null || user.isEmpty()) return "Usuario Invalido";
		if(pwd == null || pwd.isEmpty()) return "Contraseña invalida";
		DBQueryExecutor dbexecutor = DBAccessFactory.getExecutor();
		if (dbexecutor == null)
			return "Invalid db executor";
		EncryptionHelper helper = new EncryptionHelper();
		try {
			String encrypted = helper.encrypt(pwd);
			pwd = encrypted;
		} catch (GeneralSecurityException e) {
			play.Logger.warn("Cant encrypt pwd", e);
		}
		return dbexecutor.changePwd(user, pwd);
	}
	
	public static UserDescriptor userLogin(String username, String pwd, Logger logger) {
		if (username == null || username.isEmpty())
			return new UserDescriptor("Usuario Invalido");
		if (pwd == null || pwd.isEmpty())
			return new UserDescriptor("Clave invalida");
		DBQueryExecutor dbexecutor = DBAccessFactory.getExecutor();
		if (dbexecutor == null)
			return new UserDescriptor("Invalid db executor");
		UserDescriptor user = dbexecutor.getUserDescriptor(username);
		logger.info("Got result: {}", new Object[] { user });
		if (user == null)
			return new UserDescriptor("Usuario Invalido");
		EncryptionHelper helper = new EncryptionHelper();
		String old = pwd;
		try {
			pwd = helper.encrypt(pwd);
		} catch (Exception e) {
			logger.info("Cant encrypt", e);
		}
		if ((user.getStatus() == UserDescriptorStatus.GOOD || user.getStatus() == UserDescriptorStatus.RESET_PASSWORD) && 
				(user.getPwd().equals(pwd) || user.getPwd().equals(old))) {
			users.put(username, user);
			return user;
		} else {
			users.remove(username);
			return new UserDescriptor("Usuario Invalido");
		}
	}

	public static String getUserRole(String username) {
		if (username == null || username.isEmpty())
			return null;
		UserDescriptor userDescriptor = users.get(username);
		if (userDescriptor != null) {
			return userDescriptor.getRole();
		}
		return null;
	}

	public static String creaUser(NewUserDescriptor newuser, Logger logger) {
		String email = newuser.getEmailinput();
		EmailValidator validator = EmailValidator.getInstance();
		if (email == null || email.isEmpty() || !validator.isValid(email))
			return "Direccion de email invalida";
		String nombre = newuser.getNombreinput();
		if (nombre == null || nombre.isEmpty())
			return "Nombre invalido";
		String apellido = newuser.getPaternoinput();
		if (apellido == null || apellido.isEmpty())
			return "Apellido Paterno invalido";
		String materno = newuser.getPaternoinput();
		if (materno == null || materno.isEmpty())
			return "Apellido Materno invalido";
		String valido = newuser.getValidinput();
		Date hasta = null;
		SimpleDateFormat sdf = new SimpleDateFormat(Utility.dateFormatString);
		if (valido != null && !valido.isEmpty()) {
			try {
				hasta = sdf.parse(valido);
			} catch (ParseException e) {
			}
		}
		if (hasta == null) {
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.DAY_OF_YEAR, periodoDias);
			hasta = instance.getTime();
		}
		DBQueryExecutor executor = DBAccessFactory.getExecutor();
		if (!executor.isAvailable(email)) {
			return "Usuario ya existe";
		}
		UserDescriptor user = new UserDescriptor();
		user.setUsername(email);
		EncryptionHelper helper = new EncryptionHelper();
		String pwd = null;
		try {
			pwd = helper.encrypt(pwd);
		} catch (Exception e) {
			logger.info("Cant encrypt", e);
			pwd = "newpassword";
		}
		user.setPwd(pwd);
		user.setNombre(nombre);
		user.setPaterno(apellido);
		user.setMaterno(materno);
		user.setValid(sdf.format(hasta));
		user.setAssociatedUser(newuser.getAssociatedUser());

		return executor.saveNewUser(user);
	}

	public static NewUserDescriptor getUser(Map<String, String> data) {
		if (data == null || data.isEmpty())
			return null;
		
		NewUserDescriptor ret = new NewUserDescriptor();
		
		for (Map.Entry<String, String> tmp : data.entrySet()) {
			switch (tmp.getKey()) {
			case "nombreinput":
				ret.setNombreinput(tmp.getValue() != null && !tmp.getValue().isEmpty() ? tmp.getValue() : "");
				break;
			case "paternoinput":
				ret.setPaternoinput(tmp.getValue() != null && !tmp.getValue().isEmpty() ? tmp.getValue() : "");
				break;
			case "maternoinput":
				ret.setMaternoinput(tmp.getValue() != null && !tmp.getValue().isEmpty() ? tmp.getValue() : "");
				break;
			case "validinput":
				ret.setValidinput(tmp.getValue() != null && !tmp.getValue().isEmpty() ? tmp.getValue() : "");
				break;
			case "emailinput":
				ret.setEmailinput(tmp.getValue() != null && !tmp.getValue().isEmpty() ? tmp.getValue() : "");
				break;
			}
		}
		return ret;
	}
}
