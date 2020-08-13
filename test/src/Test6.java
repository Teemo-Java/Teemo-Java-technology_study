/** 54 演示Full GC
 *
 */
public class Test6 {
    public static void main(String[] args) throws Exception{
        System.out.println("test6");
        Thread.sleep(30*1000);
        while (true){
            loadData();
        }
    }

    private static void loadData() throws Exception{
        byte[] arr = null;
        for (int i=0;i<4;i++){
            arr = new byte[10*1024*1024];
        }
        arr = null;

        byte[] arr1 = new byte[10*1024*1024];
        byte[] arr2 = new byte[10*1024*1024];
        byte[] arr3 = new byte[10*1024*1024];

        arr3 = new byte[10*1024*1024];

        Thread.sleep(1000);
/*
        堆内存200M，新生代100M ，eden 80M ，S区 10M
        javac -encoding UTF-8  Test6.java
        java -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200 -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:D:\\gc.log Test6

        //这里是会造成Young GC的

        从jstat结果中可以看出，系统开始运行的第一秒就开始了Young GC了，S区有500多K的未知对象，Old区多了30M存活对象，这是因为arr1 arr2 arr3 S区存放不下，直接进入老年代了

        再看老年代这一列，从30M到(60或者)M的时候 然后进行了一次Old GC，对老年代进行了回收，然后腾出空间容纳新生代过来的存活对象。所以回收后又变成了30多M了
        因此可以看出，由于年轻代频繁GC，但是GC后的存活对象S区放不下，导致对象频繁进入老年代，也导致老年代频繁GC，

        再来看看Young GC的耗时和Full GC的耗时，我当前电脑下 Young GC平均2毫秒多耗时，而Old GC平均1毫秒多。
        为什么会这样？ 不是说Old GC比Young GC慢吗？
        原因是Young GC之后，因为存活对象老年代放不下了，启动Old GC，等Old GC完成之后再将存活对象放到Old区中去，这也就是导致了Young GC被Old GC拖累了。
*/


/*      优化：
        将新生代分配200M，老年代100M，Eden:S=2,eden区 100，S区50M
        java -XX:NewSize=209715200 -XX:MaxNewSize=209715200 -XX:InitialHeapSize=314572800 -XX:MaxHeapSize=314572800 -XX:SurvivorRatio=2  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log Test6

        从jstat结果中 可以看出，老年代不进行了Old GC了，每次Young GC之后，最多30M多存活对象，S区可以轻易容纳，因此对象根本不会进入老年代。最终500多K的未知对象进入老年代了

        (在发生Young GC的时候，可能有些工作线程还未执行完，所以这些线程会有对应的存活对象(一般这些线程任务执行完之后 对象都是垃圾，只是就Young GC这个过程来说 还算是存活对象)，
        如果太多的话，就可能触发动态规则，进入老年代，其实这是不必要的，这就是我认为调大S区的重要原因)
 */
    }
}
