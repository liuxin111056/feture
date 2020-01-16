package service;

public class personServerImpl implements personServer{
	@Override
	public Person save(String uname,int age) throws Exception {
		int a=0;
		try {
			age= age/a;//打开上面两行报错，可触发异常通知
		}catch (Exception e){
			throw new Exception("123");
		}

		System.out.println("come in personServerImpl save method...");
		Person p=new Person();
		p.setAge(1);
		p.setName("霑傘");
		return p;
	}
}