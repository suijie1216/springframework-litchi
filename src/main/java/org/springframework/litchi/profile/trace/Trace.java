package org.springframework.litchi.profile.trace;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

/**
 * @author: suijie
 * @date: 2018/5/27 14:54
 * @description:
 */
public class Trace {

    /**
     * 4个空格符
     */
    private static final String SPACE_STR = "    ";
    /**
     * trace索引，进出索引一致
     */
    private static ThreadLocal<Integer> index = ThreadLocal.withInitial(() -> -1);
    /**
     * trace日志信息
     */
    private static ThreadLocal<StringBuilder> log = ThreadLocal.withInitial(() -> new StringBuilder("\n"));
    /**
     * trace节点栈
     */
    private static ThreadLocal<Stack<TraceNode>> traceStack = ThreadLocal.withInitial(new Supplier<Stack<TraceNode>>() {
        @Override
        public Stack<TraceNode> get() {
            return new Stack<>();
        }
    });
    /**
     * 每个方法调用时间
     */
    private static ThreadLocal<Map<String, Long>> costMap = ThreadLocal.withInitial(new Supplier<Map<String, Long>>() {
        @Override
        public Map<String, Long> get() {
            return Maps.newHashMap();
        }
    });

    /**
     * 进入方法
     * @param name
     */
    public static void traceIn(String name) {
        TraceNode node = new TraceNode();
        node.setName(name);
        node.setTime(System.currentTimeMillis());
        int idx = index.get() + 1;
        node.setIndex(idx);
        index.set(idx);
        traceStack.get().push(node);
        for (int i = 0; i < node.getIndex(); i++) {
            log.get().append(SPACE_STR);
        }
        log.get().append(node.getName()).append("--->begin:").append(node.getFormatTime()).append("\n");
    }

    /**
     * 跳出方法
     * @param name
     */
    public static void traceOut(String name) {
        TraceNode node = new TraceNode();
        node.setName(name);
        node.setTime(System.currentTimeMillis());
        int idx = index.get();
        node.setIndex(idx);
        index.set(idx - 1);
        StringBuilder sb = log.get();
        if (traceStack.get().isEmpty()) {
            sb.append(node.getName()).append("--->end:").append(node.getFormatTime()).append(" not find bigin").append("\n");
            return;
        }
        TraceNode begin = traceStack.get().pop();
        for (int i = 0; i < node.getIndex(); i++) {
            sb.append(SPACE_STR);
        }
        if (begin.getIndex() != node.getIndex()) {
            sb.append(node.getName()).append("--->end:").append(node.getFormatTime()).append(" begin not match").append("\n");
            return;
        }
        long cost = node.getTime() - begin.getTime();
        sb.append(node.getName()).append("--->").append("end:").append(node.getFormatTime()).append(",cost:").append(cost).append("\n");
        costMap.get().put(node.getName(), cost);
    }

    /**
     * 获取方法的调用时间
     * @param name
     * @return
     */
    public static long getCost(String name) {
        Long cost = costMap.get().get(name);
        return cost == null ? 0 : cost;
    }

    /**
     * 清空线程的trace信息
     */
    public static void reset() {
        traceStack.remove();
        index.remove();
        log.remove();
        costMap.remove();
    }

    /**
     * 打印trace信息，打印完清空trace
     * @return
     */
    public static String printTrace() {
        String trace = log.get().toString();
        reset();
        return trace;
    }
}
