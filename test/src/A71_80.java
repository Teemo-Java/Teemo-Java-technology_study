public class A71_80 {
/*  71  OOM介绍
    out of memory(OOM) :内存溢出，指程序无法再申请到足够的内存
    memory leak        :内存泄露，指程序无法释放自己申请的内存，内存泄露多次后 会导致OOM
*/

/*
    72 发生OOM的区域
    1 metaspace(元空间，永久代)，这块内存用于存储类信息的，当加载的类越来越多 这里是有可能发生OOM的
    2 虚拟机栈，虚拟机栈一般设置了1M，用于存储局部变量等等，这里也是有可能发生OOM的
    3 堆内存，这里肯定有可能OOM的
*/

/*
    73 metaspace溢出
    元空间溢出会触发Full GC，其溢出的情况主要有两种
    1 未设置元空间内存大小，默认的元空间大小才几十M，如果系统比较复杂，各种引用jar，就有可能放不下。 从参数 -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m 设置元空间
    2 程序员在写代码的时候动态生成类(比如cglib)，如果代码没控制好，有可能生成的类过多，造成元空间OOM
*/

/*  74 栈溢出
    每一个线程对对应一个虚拟机栈，这个栈的大小一般就设置为1M，用于存储局部变量等信息
    比如：下面执行后，就是main线程，首先把main方法栈帧压入栈内，然后把test方法栈帧,test方法执行完之后，其方法内的变量销毁，栈帧出栈
    一般来说就算栈内存只有128K 也基本足够了，如果栈溢出，那基本就是代码有bug了。
    比如下面的test1()方法，无限递归调用，这里因为栈帧是占用一定大小的(即使是空方法)，所以就发生StackOverflowError
*/

    public static void main(String[] args) {
//            test();
            test1();
    }
    public static void test(){
        String msg = "test";
        System.out.println(msg);
    }

    public static void test1(){
        test1();
    }


































}
