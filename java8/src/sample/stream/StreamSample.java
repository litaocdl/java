package sample.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StreamSample {

	/**
	 * stream works for any class implements the java.util.collections
	 * Map does not support stream, if the method return stream, we can continue to use stream.
	 * 
	 * 
	 * */
	
	public static void main(String[] args) {
		StreamSample sample = new StreamSample() ;
		sample.listMatch().listReduce().listFilter().mapFunctions();
		
	}
	
	private  List<String> arrayList = new ArrayList<String>() ;
	public StreamSample(){
		arrayList.add("home") ;
		arrayList.add("apple") ;
		arrayList.add("banana") ;
		arrayList.add("orage") ;
		arrayList.add("pear") ;
		arrayList.add("tomato") ;
		arrayList.add("yellow") ;
		arrayList.add("red") ;
		arrayList.add("green") ;
		arrayList.add("blue") ;
		arrayList.add("green") ;
		arrayList.add("black") ;
		arrayList.add("white") ;
		arrayList.add("tall") ;
	}
	
	
	public StreamSample listMatch() {
			
		//if there any any value match starting with "g"
		boolean match = arrayList.stream().anyMatch(p -> p.startsWith("g")) ;
		System.out.println("Any match: " + match );
		
		//all match
		match = arrayList.stream().allMatch(p -> p.startsWith("g")) ;
		System.out.println("Any match: " + match );
		
		//none match, all not match
		match = arrayList.stream().noneMatch(p -> p.startsWith("s")) ;
		System.out.println("none match: " + match );
		return this ;
	}
	public StreamSample listFilter() {
		Set<String> color = new HashSet<String>() ;
		color.add("red") ;
		color.add("blue") ;
		color.add("black") ;
		color.add("white") ;
		color.add("red") ;
		color.add("yellow") ;
		color.add("green") ;
		long number = arrayList.stream().filter(p -> color.contains(p)).count() ;
		System.out.println("colors number " + number);
		number = arrayList.stream().filter(p -> p.contains("e")).count() ;
		System.out.println("contains e " + number);
		
		System.out.println("########");
		
		//filter  forEach accept Consumer class, define a Predicate, accept p and check if p starts with g
		arrayList.stream().filter(p -> p.startsWith("g")).forEach(System.out::println); ;
		
		System.out.println("########");
		arrayList.stream().sorted().forEach(System.out::println);
		
		//for each method is using Consumer function, so similar like
		Consumer<String> consumer = (s) -> System.out.println(s);
		arrayList.stream().sorted().forEach(consumer);
		//also the same with
		//==========>
		
		arrayList.stream().sorted().forEach((s) 
				->  System.out.println(s)
		);
		
		
		System.out.println("########");
		//## define a Comparator, accept a and b, return the length difference
		arrayList.stream().sorted((a,b) -> a.length() - b.length()).forEach(System.out::println);	
		
		return this ;
	}
	public StreamSample listReduce() {
		
		long t0 = System.nanoTime();
		// reduce the element in the stream
		Optional<String> result = arrayList.stream().sorted().reduce((s1,s2) -> s1 + "#" + s2);
		long t1 = System.nanoTime() ;
		long timeUsed = TimeUnit.NANOSECONDS.toMillis(t1 - t0) ;
		result.ifPresent(System.out::println);
		System.out.println("time used " + timeUsed);
		
		long t2 = System.nanoTime() ;
		result = arrayList.parallelStream().sorted().reduce((s1,s2) -> s1 + "#" + s2);
		long t3 = System.nanoTime() ;
		timeUsed = TimeUnit.NANOSECONDS.toMillis(t3 - t2) ;
		result.ifPresent(System.out::println);
		System.out.println("time used " + timeUsed);
		
		return this ;
	}
	
	/**
	 * Map does not support stream, but map has new functions. 
	 * */
	public StreamSample mapFunctions() {
		Map<String,String> sampleMap = new HashMap<String,String>() ;
		for(int i=0;i<100;i++) {
			Random random  = new Random() ;
			int r = random.nextInt() ;
			sampleMap.put(String.valueOf(i),"result"+r) ;		
		}
		
		//if value for key 10 exists, return the value, else put the value.
		String result = sampleMap.putIfAbsent("10", "modified") ;
		System.out.println(result);
		
		//update the value if value for the key exists
		result = sampleMap.computeIfPresent("10", (k,v) -> k+v) ;
		System.out.println(result);
		System.out.println(sampleMap.getOrDefault("10","not found"));
		
		//delete the value if it exists
		result = sampleMap.computeIfPresent("20", (k,v) -> null) ;
		System.out.println("value for 20:" + result);
		System.out.println("value for 20:"+sampleMap.getOrDefault("20","not found"));
		
		//if key not exists update the value.
		result = sampleMap.computeIfAbsent("10", k -> "this is new added") ;
		System.out.println("value for 10:"+ sampleMap.get("10"));
		result = sampleMap.computeIfAbsent("101", k -> "this is new added") ;
		System.out.println("value for 101:" + sampleMap.get("101"));
		
		//only the key and value are match then delete
		sampleMap.remove("10","result") ;
		System.out.println("value for 10 after delete:"+ sampleMap.get("10"));
		
		System.out.println("value for 102:"+ sampleMap.getOrDefault("102", "not found"));
		
		// if value for key 10 exists, perform the merge action, otherwise update key
		result = sampleMap.merge("10", "new value", (v,nv) -> nv.concat(v)) ;
		System.out.println("after merge:" + result);
		
		result = sampleMap.merge("103", "new value", (v,nv) -> v.concat(nv)) ;
		System.out.println("after merge:" + result);
		System.out.println("after merge:" + sampleMap.getOrDefault("103","not found"));
		return this ;
	}
}
