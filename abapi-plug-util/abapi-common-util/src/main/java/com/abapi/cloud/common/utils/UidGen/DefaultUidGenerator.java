package com.abapi.cloud.common.utils.UidGen;

import cn.hutool.core.util.RandomUtil;
import com.abapi.cloud.common.utils.DateUtils;
import com.abapi.cloud.common.utils.StringUtils;
import com.google.common.base.Joiner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author ldx
 * @Date 2019/11/21 16:38
 * @Description
 * @Version 1.0.0
 */
public class DefaultUidGenerator implements UidGenerator{

    /** Bits allocate */
    protected int timeBits = 28;
    protected int workerBits = 22;
    protected int seqBits = 13;

    /** Customer epoch, unit as second. For example 2016-05-20 (ms: 1463673600000)*/
    protected String epochStr = "2016-05-20";
    protected long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(1463673600000L);

    /** Stable fields after spring bean initializing */
    protected BitsAllocator bitsAllocator;
    protected long workerId;

    /** Volatile fields caused by nextId() */
    protected long sequence = 0L;
    protected long lastSecond = -1L;

    /** Spring property */
    protected WorkerIdAssigner workerIdAssigner;

    public DefaultUidGenerator(Long workerId){
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);

        // initialize worker id
        this.workerId = workerId;
        if (workerId > bitsAllocator.getMaxWorkerId()) {
            throw new RuntimeException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId());
        }
    }

    @Override
    public String getUID(String code) {
        try {
            return nextId(code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String parseUID(long uid) {
        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (uid << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (uid << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = uid >>> (workerIdBits + sequenceBits);

        Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
        String thatTimeStr = DateUtils.formatByDateTimePattern(thatTime);

        // format as string
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                uid, thatTimeStr, workerId, sequence);
    }

    /**
     * Get UID
     *
     * @return UID
     * @throws UidGenerateException in the case: Clock moved backwards; Exceeds the max timestamp
     */
    protected synchronized String nextId(String code) {
        long currentSecond = getCurrentSecond();

        // Clock moved backwards, refuse to generate uid
        if (currentSecond < lastSecond) {
            long refusedSeconds = lastSecond - currentSecond;
            throw new UidGenerateException("Clock moved backwards. Refusing for %d seconds", refusedSeconds);
        }

        // At the same second, increase sequence
        if (currentSecond == lastSecond) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate uid
            if (sequence == 0) {
                currentSecond = getNextSecond(lastSecond);
            }

            // At the different second, sequence restart from zero
        } else {
            sequence = 0L;
        }

        lastSecond = currentSecond;

        // Allocate bits for UID
        long uid = bitsAllocator.allocate(currentSecond - epochSeconds, workerId, sequence);
        return Joiner.on("").join(StringUtils.isEmpty(code) ? "" : code,uid,new SimpleDateFormat("ssSSS").format(new Date()),RandomUtil.randomInt(100000,999999));
    }

    /**
     * Get next millisecond
     */
    private long getNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }

        return timestamp;
    }

    /**
     * Get current second
     */
    private long getCurrentSecond() {
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (currentSecond - epochSeconds > bitsAllocator.getMaxDeltaSeconds()) {
            throw new UidGenerateException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
        }

        return currentSecond;
    }

    /**
     * Setters for spring property
     */
    public void setWorkerIdAssigner(WorkerIdAssigner workerIdAssigner) {
        this.workerIdAssigner = workerIdAssigner;
    }

    public void setTimeBits(int timeBits) {
        if (timeBits > 0) {
            this.timeBits = timeBits;
        }
    }

    public void setWorkerBits(int workerBits) {
        if (workerBits > 0) {
            this.workerBits = workerBits;
        }
    }

    public void setSeqBits(int seqBits) {
        if (seqBits > 0) {
            this.seqBits = seqBits;
        }
    }

    public void setEpochStr(String epochStr) {
        if (StringUtils.isNotBlank(epochStr)) {
            this.epochStr = epochStr;
            this.epochSeconds = TimeUnit.MILLISECONDS.toSeconds(DateUtils.parseByDayPattern(epochStr).getTime());
        }
    }

    /*public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int concurrentNum = 20000;
        CyclicBarrier c = new CyclicBarrier(concurrentNum);        // 让线程同时开始
        CountDownLatch l = new CountDownLatch(concurrentNum);
        ExecutorService es = Executors.newCachedThreadPool();
        Set<String> set = ConcurrentHashMap.newKeySet();        // S
        for(int i=0;i<concurrentNum;i++){
            //循环创建现成
            es.submit(new Demo(set,c,l));
            //new Thread(new Demo(flowingWaterUtil,set,c,l)).start();
            //每添加一个现成发令器数量减少一次
        }
        es.shutdown();
        l.await();
        System.out.println(set.size());
        long end = (System.currentTimeMillis()-start)/1000;
        System.out.println(end);
    }*/
}

/*class Demo implements Runnable{

    private Set<String> set;
    private CyclicBarrier c;
    private CountDownLatch l;

    public Demo(Set<String> set, CyclicBarrier c, CountDownLatch l) {
        super();
        this.set = set;
        this.c = c;
        this.l = l;
    }

    @Override
    public void run() {
        try {
            System.out.println("---加入线程---");
            c.await();    // 等待，直到有足够的线程
        } catch (InterruptedException | BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long a = UidGenerateService.getIn().getUID();
        String id20 = a+""+new SimpleDateFormat("HHmmssSSS").format(new Date())+""+ RandomUtil.randomInt(100,999);
        System.out.println(id20);
        set.add(id20);
        l.countDown();    // 每当有线程结束就减1
    }
}*/
