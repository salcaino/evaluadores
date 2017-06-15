package naranjalab.dao;

public class DBAccessFactory {
	
	private static DBQueryExecutor executor = new DBQueryExecutor();
	public static DBQueryExecutor getExecutor(){
		return executor;
		
	}
}
