public class AAA {
/*



-Xms1536M -Xmx1536M
-Xmn512M -Xss256K -XX:SurvivorRatio=5 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
-XX:CMSInitiatingOccupancyFraction=68
-XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -
XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-XX:+PrintHeapAtGC



其实分析到这里，这个案例如何优化已经呼之欲出了！



非常简单，分成两步走



第一步，让开发同学解决代码中的bug，避免一些极端情况下SQL语句里不拼接where条件，务必要拼接上where条件，不允许查询表中全部数据。彻底解决那个时不时有几百MB对象进入老年代的问题。



第二步，年轻代明显过小，Survivor区域空间不够，因为每次Young GC后存活对象在几十MB左右，如果Survivor就70MB很容易触发动态年龄判定，让对象进入老年代中。所以直接调整JVM参数如下：



-Xms1536M -Xmx1536M -Xmn1024M -Xss256K -XX:SurvivorRatio=5 -XX:PermSize=256M -XX:MaxPermSize=256M  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC



直接把年轻代空间调整为700MB左右，每个Surivor是150MB左右，此时Young GC过后就几十MG存活对象，一般不会进入老年代。



反之老年代就留500MB左右就足够了，因为一般不会有对象进入老年代。



而且调整了参数“-XX:CMSInitiatingOccupancyFraction=92”，避免老年代仅仅占用68%就触发GC，现在必须要占用到92%才会触发GC。



最后，就是主动设置了永久代大小为256MB，因为如果不主动设置会导致默认永久代就在几十MB的样子，很容易导致万一系统运行时候采用了反射之类的机制，可能一旦动态加载的类过多，就会频繁触发Full GC。



这几个步骤优化完毕之后，线上系统基本上表现就非常好了，基本上每分钟大概发生一次Young GC，一次在几十毫秒；



Full GC几乎就很少，大概可能要运行至少10天才会发生一次，一次就耗时几百毫秒而已，频率很低。




        */}
