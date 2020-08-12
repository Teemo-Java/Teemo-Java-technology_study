/** 46 - 演示当Young GC之后，如果S区装不下存活对象，那么存活对象进入老年代的现象
 *
 *
 *
 */
public class Test3 {
    public static void main(String[] args) {
        byte[] arr1 = new byte[2*1024*1024];
        arr1 = new byte[2*1024*1024];
        arr1 = new byte[2*1024*1024];

        byte[] arr2 = new byte[128*1024];
        arr2 = null;

        byte[] arr3 = new byte[2*1024*1024];//这里发生GC，存活对象为arr1 arr3 一共4M，from区是放不下的
        System.out.println(11);
//        javac -encoding UTF-8  Test3.java
//        java -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test3


       /* Java HotSpot(TM) 64-Bit Server VM (25.45-b02) for windows-amd64 JRE (1.8.0_45-b14), built on Apr 10 2015 10:34:15 by "java_re" with MS VC++ 10.0 (VS2010)
                Memory: 4k page, physical 16637140k(4498652k free), swap 23054576k(3750136k free)
        CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
        0.060: [GC (Allocation Failure) 0.060: [ParNew: 7300K->646K(9216K), 0.0010605 secs] 7300K->2696K(19456K), 0.0011497 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        Heap
        par new generation   total 9216K, used 2937K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  27% = 2211K used [0x00000000fec00000, 0x00000000fee3c9c8, 0x00000000ff400000)
        from space 1024K,  63% = 645K used [0x00000000ff500000, 0x00000000ff5a1a58, 0x00000000ff600000)
        to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
        concurrent mark-sweep generation total 10240K, used 2050K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        Metaspace       used 2552K, capacity 4486K, committed 4864K, reserved 1056768K
        class space    used 274K, capacity 386K, committed 512K, reserved 1048576K

        ParNew: 7300K->646K(9216K), 0.0010605 secs] 7300K->2696K(19456K), 年轻代回收前使用7300K，回收后剩余646K对象，整个堆回收前使用7300K，回收后剩余2696K
        回收后应该是剩余arr1(2M) + 500多K的未知对象，这些对象肯定from区是放不下的，按照原理 那么这些对象感觉应该放入到老年代吗？部分放进了老年代(arr1) 部分没放进去(未知对象)
        所以回收后 eden占用2M(arr3)，S区存放的时候600多K(这里面就包含了那500K未知对象)，老年代2050K，这就是那进入的arr1对象

        总结，当Young GC之后，如果S区内存比存活对象内存小，那么这些存活对象会进入老年代，但是不是全部进入老年代




        */
    }
}
