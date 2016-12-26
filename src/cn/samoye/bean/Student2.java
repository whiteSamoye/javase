package cn.samoye.bean;

public class Student2 {
	private int id;
	private String name;
	private int age;
	public Student2(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public Student2() {
		super();
	}
	@Override
	public String toString() {
		return "Student2 [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	
}
