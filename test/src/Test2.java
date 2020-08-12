/**
 * 45 - 演示动态年龄规则

 -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8
 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails
 -XX:+PrintGCTimeStamps -Xloggc:gc.log
 新生代10M，eden8M，S1 1M，S2 1M，大对象为10M 才直接进入老年代

 *
 */
public class Test2 {
    public static void main(String[] args) {
        byte[] arr1 = new byte[2*1024*1024];
        arr1 = new byte[2*1024*1024];
        arr1 = new byte[2*1024*1024];
        arr1 = null;

        byte[] arr2 = new byte[128*1024];
        byte[] arr3 = new byte[2*1024*1024];//这里触发Young GC
        System.out.println(11);
        //第一次在这里执行
//        javac -encoding UTF-8  Test2.java
//        java -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test2


/*Java HotSpot(TM) 64-Bit Server VM (25.45-b02) for windows-amd64 JRE (1.8.0_45-b14), built on Apr 10 2015 10:34:15 by "java_re" with MS VC++ 10.0 (VS2010)
        Memory: 4k page, physical 16637140k(10554736k free), swap 20938984k(13503272k free)
        CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
        0.058: [GC (Allocation Failure) 0.058: [ParNew: 7300K->833K(9216K), 0.0005177 secs] 7300K->833K(19456K), 0.0005924 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        Heap
        par new generation   total 9216K, used 3124K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  27% used [0x00000000fec00000, 0x00000000fee3c9c8, 0x00000000ff400000)
        from space 1024K,  81% used [0x00000000ff500000, 0x00000000ff5d0678, 0x00000000ff600000)
        to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
        concurrent mark-sweep generation total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        Metaspace       used 2552K, capacity 4486K, committed 4864K, reserved 1056768K
        class space    used 274K, capacity 386K, committed 512K, reserved 1048576K
        第一次执行结果分析:
        Young GC 执行前内存消耗为7300K(6M对象+128K对象 + 一些其他JVM为我们创建的对象) ，执行后为833K占用
        回收后整个占用堆为3124K(arr3的2M+128K +一些其他对象)
        eden区占有为 8192K*0.27 = 2211K 大概就是2M多，
        from区占有为 1024*0.81 = 829K(这里面就包含那128K对象)
        到此为止 from区的对象年龄为1岁

        */

        arr3 = new byte[2*1024*1024];
        arr3 = new byte[2*1024*1024];
        arr3 = new byte[128*1024];
        arr3 = null;

        byte[] arr4 = new byte[2*1024*1024];//这里触发第二次GC

//        javac -encoding UTF-8  Test2.java
//        java -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test2


        /*Java HotSpot(TM) 64-Bit Server VM (25.45-b02) for windows-amd64 JRE (1.8.0_45-b14), built on Apr 10 2015 10:34:15 by "java_re" with MS VC++ 10.0 (VS2010)
                Memory: 4k page, physical 16637140k(10073544k free), swap 20938984k(12818648k free)
        CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
        0.060: [GC (Allocation Failure) 0.060: [ParNew: 7300K->838K(9216K), 0.0005678 secs] 7300K->838K(19456K), 0.0006463 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        0.062: [GC (Allocation Failure) 0.062: [ParNew: 7142K->148K(9216K), 0.0012171 secs] 7142K->910K(19456K), 0.0012316 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        Heap
        par new generation   total 9216K, used 2360K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  27% = 2211K used [0x00000000fec00000, 0x00000000fee290e0, 0x00000000ff400000)
        from space 1024K,  14% = 143K used [0x00000000ff400000, 0x00000000ff4250e0, 0x00000000ff500000)
        to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
        concurrent mark-sweep generation total 10240K, used 762K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        Metaspace       used 2552K, capacity 4486K, committed 4864K, reserved 1056768K
        class space    used 274K, capacity 386K, committed 512K, reserved 1048576K

        //这里就发生了两次GC了，第一次和上面的差不多
        第二次7142K->148K 这148对象 可能是JVM创建的未知对象，其进入from区了，总共是剩余910K，还有760K进入老年代了
        进入老年代的原因是超过from区的50%了，因为年龄都是1，所以全进去了(arr2(128K)+jvm为我们创建的500多K对象 所以加起来是700多K)
        */
    }
}
