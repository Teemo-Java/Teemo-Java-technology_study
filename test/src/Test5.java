/** 53 使用jstat 查看内存对象情况
 *
 */

public class Test5 {
    public static void main(String[] args) throws Exception{
        Thread.sleep(30*1000);
        while (true){
            loadData();
        }
    }

    private static void loadData() throws Exception{

        byte[] arr1 = null;
        for (int a = 0 ;a<=50;a++){
            arr1 = new byte[100*1024];
        }
        arr1 = null;
        Thread.sleep(1000);
/*
        堆内存20M  新生代100M  eden 80M S 10M
        javac -encoding UTF-8  Test5.java
       java -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test5
       使用jps 找到进程id，使用jstat -gc 打印运行时内存占用情况:jstat -gc Pid 1000 1000  打印Pid的线程内存占用情况  每隔一秒打印一次  一共打印1000次，打印结果列2如下
        S0C(from区大小)    S1C(to区大小)    S0U(from区使用内存大小)    S1U(to区使用内存大小)      EC(eden区大小)       EU(eden 区使用内存大小)        OC(老年代大小)         OU(老年代使用内存大小)
        MC(方法区(元数据，永久代)大小)     MU(方法区使用内存大小)    CCSC(压缩类空间大小)   CCSU(压缩类空间使用大小)
        YGC(系统运行以来Young GC的次数)     YGCT(Young GC耗时)    FGC(Full GC次数)    FGCT(Full GC耗时)     GCT(所有GC总耗时)

        从结果可以看出来系统运行30秒后 每秒增加5M对象，当eden达到80M时 进行一次Young GC，因为基本都是垃圾对象 所以没有对象能进入老年代
        Young GC 的耗时是1毫秒，回收80M只需1毫毛，就算回收10G才10毫秒，这里可以看出来 Young GC是很快的。
        回收后只有600多K对象，S区可以轻松容纳
*/
    }
}
