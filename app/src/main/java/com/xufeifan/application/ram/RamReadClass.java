package com.xufeifan.application.ram;

/*just for read
 * 内存优化
 *
 * Android的内存管理方式
 *
 *
 * APP的内存优化方法
 *
 *
 * OOM的问题优化
 *
 *
 *
 *演示查看APP内存的方法和工具
 *
 *
 *Android的内存管理方式
 * -Android系统的内存分配与回收方式
 * -App内存限制机制
 * -切换应用时后台App清理机制
 * -监管内存的几种方法
 *
 *
 **************App内存优化的方法***************************************
 *
 * -数据结构优化
 * 1、频繁字符串拼接用StringBuffer
 * 2、ArrayMap、SparseArray代替HashMap
 * 3、内存抖动（变量使用不当）
 *
 * -对象复用
 * 1、复用系统自带的资源
 * 2、ListView/GridView的ContentView复用，建议使用RecyclerView
 * 3、不要再onDraw方法里创建对象
 *
 * -避免内存泄露
 * 内存泄露回导致可用的Heap越来越少，频繁的触发GC
 * Activity的内存泄露
 * 引用上下文的时候，可用Application的context，除非特殊的Activity的context
 * Cursor指针对象是否及时关闭
 ***********************************************************************
 *
 * 优化OOM问题的方法
 * 临时Bitmap对象及时回收
 * 加载Bitmap：缩放比例，解码格式，局部加载
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */