package sample.functions;

import java.util.Comparator;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionSample {

	/**
	 * @FunctionalInterface is indicates the function interface
	 * 
	 * function can reference through :: for static method.
	 * */
	
	
	public static void main(String[] args) {
		(new FunctionSample())
		.biFunction()
		.function()
		.consumerAndSupplier()
		.predicates()
		.comparator();
	}
	
	/**
	 * the BiFunction interface usually defined in lamada express which accept two params and have a return value.
	 * By given two params, produce a result. see Function, Supplier
	 * 
	 * */
	public FunctionSample biFunction() {
		// three params, first args, second args and return type.
		BiFunction<String,String,String> bFunction = (T,U) -> T+U ;
		
		String result = bFunction.apply("hello", "world") ;
		System.out.println(result);
		
		BiFunction<Integer,Integer,Integer> bFunction1 = Math::min;
		Integer result1 = bFunction1.apply(1,2) ;
		System.out.println(result1);
		
		return this;
	}
	/**
	 * Function is the function type which accept one params and has a return value.
	 * By give a params, produce a result. 
	 * 
	 * 
	 * */
	public FunctionSample function() {
		Function<String,Integer> function = (T) -> T.length() ;
		
		Integer result = function.apply("hello world") ;
		System.out.println(result);
		
		// we can also use :: to reference a static method
		Function<Integer,String> function1 = String::valueOf;
		String result1 = function1.apply(123) ;
		System.out.println(result1);
		
		return this ;
	}
	
	/**
	 * Consumer like Function, but not accept params
	 * 
	 * The foreach,ifPresent method of Collection,  , is using Consumer
	 * */
	public FunctionSample consumerAndSupplier() {
		//supplier is just like a factory method, the new is refer to the default constructor 
		//accept no param
		Supplier<Person> supplier = Person::new ;
		Person p = supplier.get() ;
		System.out.println(p);
		
		
		
		//Consumer accept params
		Consumer<Person> consumer = (person) -> System.out.println("Person Name is :" + person.name);
		consumer.accept(Person.newPerson("tao",20,"M"));
		
		consumer = System.out::println;
		consumer.accept(Person.newPerson("tao",20,"M"));
		return this ;
			
	}
	
	/**
	 * 
	 * Predicate accept params and return boolean
	 * Predicate can combined to use
	 * */
	public FunctionSample predicates() {
		
		Predicate<String> pred = (s) -> s.length() > 5 ;
		boolean result = pred.test("helloworld") ;
		System.out.println(result); //true
		Predicate<String> pred2 = (s) -> s.length() < 10 ;
		result = pred.and(pred2).test("helloworld") ;
		System.out.println(result); //false
		
		result = pred.negate().test("helloworld") ;
		System.out.println(result); //false
		
		result = pred.or(pred2).test("helloworld") ;
		System.out.println(result); //true
		return this ;
	}
	
	
	/**
	 * java 8 add function interface for it
	 * */
	public FunctionSample comparator() {
		Comparator<Person> cp = (p1,p2) -> p1.age - p2.age ;
		Person p1 = new Person("tao",20,"M") ;
		Person p2 = new Person("mai",7,"F") ;
		int result = cp.compare(p1, p2) ;
		System.out.println(result);
		
		
		return this ;
	}
	
	
	public FunctionSample optional() {
		
		return this;
		
	}
	
}
class Person{
	String name;
	int age;
	String sex;
	
	public static Person newPerson(String name,int age,String sex) {
		return new Person(name,age,sex);
	}
	public Person() {
		this.name = "default" ;
		this.age = 20 ;
		this.sex = "M" ;
	}
	public Person(String name,int age,String sex) {
		this.name = name ;
		this.age = age ;
		this.sex = sex ;
	}
	public String toString() {
		return this.name + this.age + this.sex ;
		
	}
}