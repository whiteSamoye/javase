package cn.samoye.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import cn.samoye.bean.Student;
import cn.samoye.bean.Student2;

/**
 * 需要的jar：
 * 	1. 引入commons-beanutils-1.8.3.jar核心包
 *	2. 引入日志支持包: commons-logging-1.1.3.jar
 * @author samoye
 * 当bean中没有setter或者是getter方法时，也可以设置其属性
 */
public class _BeanUtils {
	//1.实现对象属性的拷贝
	@Test
	public void testBeanUtils()throws Exception{
//		Student stu1 = new Student(1, "samoye",24);
		Student stu = new Student();
		BeanUtils.copyProperty(stu, "id", "2");
		BeanUtils.copyProperty(stu, "name", "samoye");
		BeanUtils.copyProperty(stu, "age", 24);
		System.out.println(stu);
	}
	/**
	 * 实现对象的拷贝
	 * @throws Exception
	 */
	@Test
	public void testBeanUtils2() throws Exception {
		Student stu = new Student(2, "samoye2", 24);
		Student stu2 = new Student();
		BeanUtils.copyProperties(stu2, stu);
		System.out.println(stu2);
	}
	
	/**
	 * 把map集合中的键值对设置到bean中，
	 * 注意：bean的属性名必须和map的键名相同
	 * @throws Exception
	 */
	@Test
	public void testBeanUtils3() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", 3);
		map.put("name", "samoye3");
		map.put("age", 23);

		Student stu = new Student();
		BeanUtils.populate(stu, map);
		System.out.println(stu);
	}
	
	@Test
	public void testBeanUtils4() throws Exception {
		//无set和get方法
		Student2 stu = new Student2();
		BeanUtils.setProperty(stu, "id", 2);
		BeanUtils.setProperty(stu, "name", "samoye");
		BeanUtils.setProperty(stu, "age", 24);
		System.out.println(stu);
	}
}
