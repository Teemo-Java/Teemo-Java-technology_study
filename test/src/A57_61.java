public class A57_61 {
    private void test1(){}

    //61
    // 不能在代码中随便写 System.gc()，它会连带回收年轻代，老年代，永久代，使用-XX:+DisableExplicitGC 禁止代码中显示的执行System.gc()
}
