package service;

public class personServerImpl implements personServer{
	@Override
	public Person save(String uname,int age) throws Exception {
		int a=0;
		try {
			age= age/a;//���������б����ɴ����쳣֪ͨ
		}catch (Exception e){
			throw new Exception("123");
		}

		System.out.println("come in personServerImpl save method...");
		Person p=new Person();
		p.setAge(1);
		p.setName("땂�");
		return p;
	}
}