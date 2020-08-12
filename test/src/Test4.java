/** 47 - 演示Full GC
 *
 *
 *
 *
 *
 *
 */
public class Test4 {

    public static void main(String[] args) {
        byte[] arr1 = new byte[4 * 1024 *1024];
        arr1 = null;

        byte[] arr2 = new byte[2 * 1024 *1024];
        byte[] arr3 = new byte[2 * 1024 *1024];
        byte[] arr4 = new byte[128 *1024];
        byte[] arr5 = new byte[2 * 1024 *1024];//这里Young GC

        byte[] arr6 = new byte[2 * 1024 *1024];//
/*      javac -encoding UTF-8  Test4.java
        java  -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test4

        这里 PretenureSizeThreshold = 3M 大于3M的对象 视为大对象，直接进入老年代
*/

/*
        Java HotSpot(TM) 64-Bit Server VM (25.45-b02) for windows-amd64 JRE (1.8.0_45-b14), built on Apr 10 2015 10:34:15 by "java_re" with MS VC++ 10.0 (VS2010)
                Memory: 4k page, physical 16637140k(9529920k free), swap 20938984k(11037756k free)
        CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=3145728 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
        0.060: [GC (Allocation Failure) 0.060: [ParNew (promotion failed): 7300K->8083K(9216K), 0.0057428 secs]0.066: [CMS: 8194K->6893K(10240K), 0.0027259 secs] 11396K->6893K(19456K), [Metaspace: 2544K->2544K(1056768K)], 0.0087418 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
        Heap
        par new generation   total 9216K, used 2130K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  26% used [0x00000000fec00000, 0x00000000fee14930, 0x00000000ff400000)
        from space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
        to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
        concurrent mark-sweep generation total 10240K, used 6893K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        Metaspace       used 2551K, capacity 4486K, committed 4864K, reserved 1056768K
        class space    used 274K, capacity 386K, committed 512K, reserved 1048576K


*/

    }
}
