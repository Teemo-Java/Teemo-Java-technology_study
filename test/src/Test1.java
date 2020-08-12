/** 44 - 演示Young GC
  -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760
  -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC 
  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:com/test/JvmCode/chapter7/test1/gc.log

 执行步骤，CMD下执行：
 javac -encoding UTF-8  Test1.java
 java -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test1

 * 
 * 新生代5M，堆10M，新生代比例8:1:1，当对象大于10M时视为大对象，直接进入老年代
 * 新生代使用ParNew，老年代使用CMS
 * 打印GC细节，及每次GC发生的时间到gc.log文件下
 *
 * 可以计算出 Eden区为4M,s1 s2 各为0.5M
 */

public class Test1 {
	public static void main(String[] args) {
		//先分配3个1M对象，然后再分配1个2M对象，Eden区放不下，因此会触发Young GC
		byte[] arr1 = new byte[1024*1024];
		arr1 = new byte[1024*1024];
		arr1 = new byte[1024*1024];
		arr1 = null;
		
		byte[] arr2 = new byte[2*1024*1024];
		System.out.println(11);
	}
}

/*Java HotSpot(TM) 64-Bit Server VM (25.45-b02) for windows-amd64 JRE (1.8.0_45-b14), built on Apr 10 2015 10:34:15 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16637140k(10531248k free), swap 20938984k(13852856k free)
CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
0.058: [GC (Allocation Failure) 0.058: [ParNew: 4087K->512K(4608K), 0.0011343 secs] 4087K->692K(9728K), 0.0012256 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
Heap
 par new generation   total 4608K, used 2681K [0x00000000ff600000, 0x00000000ffb00000, 0x00000000ffb00000)
  eden space 4096K,  52% used [0x00000000ff600000, 0x00000000ff81e678, 0x00000000ffa00000)
  from space 512K, 100% used [0x00000000ffa80000, 0x00000000ffb00000, 0x00000000ffb00000)
  to   space 512K,   0% used [0x00000000ffa00000, 0x00000000ffa00000, 0x00000000ffa80000)
 concurrent mark-sweep generation total 5120K, used 180K [0x00000000ffb00000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 2551K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 274K, capacity 386K, committed 512K, reserved 1048576K
  
  打印结果解释
  CommandLine flags:就是我们本次运行程序所设置的JVM参数，有些参数我们没有设置，是程序给我们设置的默认值
  0.058 是指程序过了86毫秒后开始发生GC
  [GC (Allocation Failure) 0.058: [ParNew: 4087K->512K(4608K), 0.0011343 secs] 4087K->692K(9728K), 0.0012256 secs]
  (Allocation Failure) 指GC原因是分配内存失败，因为Eden区只有4M，但是我们是5M，所以触发了GC
  [ParNew: 4087K->512K(4608K), 0.0011343 secs] 指使用ParNew进行了年轻代的Young GC，年轻代总共可使用内存为4608,约为4.5M(Eden + 一个S区嘛)，
      回收前内存使用为4087K，回收后 512K(这个是JVM未我们创建的未知对象)，整个回收时长是0.0011343秒，(这里为什么是4087，而不是3072K(3M) 因为还有其他一些的对象，比如数组的描述信息，jvm新建的一些其他对象等等，所以大于3M)
  4087K->692K(9728K), 0.0012256 secs:指整个Java堆内存总可用空间9728KB(约9.5M，Eden(4M) + S(0.5M) + 老年代(5M)) 回收前使用了4087K内存，回收后使用了692KK，整个回收时长是0.0012256秒
  
  [Times: user=0.00 sys=0.00, real=0.00 secs] 指本次gc消耗的时间，单位是秒，我们这里才几十毫秒，所以约等于0

  Heap下面的是GC之后的内存占用情况
     年轻代可用内存是4608K(4.5M)，使用了2681K(2.5M多)，和之前相比+了2M，其中512在S区，是gc之后剩余存活对象，eden有2M，2/4M 约为50% 。from区 512K被占满了，所以100%

  这里为什么from到了100%，都没触发动态年龄判断而进入老年代呢？因为那是下一次GC才开始判断的

  concurrent mark-sweep generation total 5120K, used 180K
    老年代使用CMS收集器，总共5M，占用了1.2M内存
    
  Metaspace       used 2849K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 305K, capacity 386K, committed 512K, reserved 1048576K
  Metaspace元数据空间和Class空间，存放一些类信息、常量池之类的东西，此时他们的总容量，使用内存，等等。
  */

