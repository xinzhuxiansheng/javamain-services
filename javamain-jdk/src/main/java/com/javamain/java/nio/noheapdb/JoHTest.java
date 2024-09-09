package com.javamain.java.nio.noheapdb;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ebruno
 */
public class JoHTest {
    ArrayList<String> stuff = new ArrayList();
    
    public static void main(String[] args) {
        boolean useHeap = false;
        if ( args != null && args.length > 0 ) {
            if ( args[0].equals("heap") ) {
                useHeap =true;
            }
        }
        JoHTest test = new JoHTest(useHeap);
    }
    
    public JoHTest(boolean useHeap) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        RuntimeMXBean rtBean = ManagementFactory.getRuntimeMXBean();
        List<String> args = rtBean.getInputArguments();
        Runtime rt = Runtime.getRuntime();
        
        HashMap<String, String> map = new HashMap();
        map.put("Eric", "Bruno");
        String val = map.get("Eric");
		
        int count = 1;
        
        NoHeapDB db = new NoHeapDB();
        HashMap<String,String>[] maps = new HashMap[count];

        if ( useHeap ) {
            for ( int i = 0; i < count; i++ ) {
                maps[i] = new HashMap<>();
                testStringsSave(maps[i]);
                testStringsLoad(maps[i], true);
            }
        }
        else {
            try {
                for ( int i = 0; i < count; i++ ) {
                    db.createStore(
                            "TestDB"+i, 
                            DataStore.Storage.IN_MEMORY, 
                            256);

                    testStringsSave(db, "TestDB"+i);
                    testStringsLoad(db, "TestDB"+i, true);
                    
                    db.deleteStore("TestDB"+i);
                }
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("\n--------------------------------------------------");
        sb.append("\n Heap mem: ").append(memBean.getHeapMemoryUsage());
        sb.append("\nOther mem: ").append(memBean.getNonHeapMemoryUsage());
        sb.append("\n--------------------------------------------------");
        sb.append("\nTotal mem: ").append(rt.totalMemory());
        sb.append("\n Free mem: ").append(rt.freeMemory());
        sb.append("\n  Max mem: ").append(rt.maxMemory());
        sb.append("\n***************************************************");
        System.out.println(sb.toString());
    }
    
    int stringRecordCount = 1_500_000;
    long storageTime = 0;
    long retrievalTime = 0;
    
    public void testStringsSave(HashMap map) {
        System.out.println("*** STRING HEAP TEST ****\n");

        double start = System.currentTimeMillis();
        int i = 0;
        for ( i = 0; i < stringRecordCount; i++ ) {
            String key = "Eric"+i;
			map.put(key, "Bruno");
            if ( i % 500_000 == 0 ) {
                System.out.println("Created " + i + " records so far");
            }
        }        
        double end = System.currentTimeMillis();
        double seconds = Math.max((end/1000 - start/1000), 1);
        storageTime = (long)end - (long)start;
        long perSec = (long)((double)i / (double)seconds);
        System.out.println(
                "Seconds: "+ seconds +
                ", Msgs per second written: " + perSec);
    }
    
    public void testStringsSave(NoHeapDB db, String storeName) {
        System.out.println("*** STRING PERSIST TEST ****\n");

        double start = System.currentTimeMillis();
        int i = 0;
        for ( i = 0; i < stringRecordCount; i++ ) {
            String key = "Eric"+i;
			db.putString(storeName, key, "Bruno");
            if ( i % 500_000 == 0 ) {
                System.out.println("Created " + i + " records so far");
            }
        }        
        double end = System.currentTimeMillis();
        double seconds = Math.max((end/1000 - start/1000), 1);
        long perSec = (long)((double)i / (double)seconds);
        System.out.println(
                "Seconds: "+ seconds +
                ", Msgs per second written: " + perSec);
        System.out.println("Collisions: " + db.getCollisions(storeName) );

        long empty = db.getEmptyCount(storeName);
        long active = db.getRecordCount(storeName);
        db.outputStats(storeName);
        System.out.println("Empty records: " + empty);
        System.out.println("Active records: " + active);
    }
    
    public void testStringsLoad(HashMap<String, String> map, boolean displayError) {
        System.out.println("*** STRING LOAD FROM HEAP TEST ****\n");
    
        int failedGets = 0;
		double start = System.currentTimeMillis();
        int i = 0;
        for ( i = 0; i < stringRecordCount; i++ ) {
            String key = "Eric"+i;
            String obj = map.get(key);
            if ( obj == null ) {
                failedGets++;
                if ( displayError ) {
                    System.out.println("Object is null for item " + i);
                }
            }
        }
        double end = System.currentTimeMillis();
        retrievalTime = (long)end - (long)start;
        double seconds = Math.max((end/1000 - start/1000), 1);
        long perSec = (long)((double)i / (double)seconds);
        System.out.println( "Seconds: "+ seconds +
                            ", Msgs per second read: " + perSec);
        System.out.println("Failed gets: "+ failedGets);
        
        System.out.println("Object storage time: " + storageTime);
        System.out.println("Object retrieval time: " + retrievalTime);
    }

    public void testStringsLoad(NoHeapDB db, String storeName, boolean displayError) {
        System.out.println("*** STRING LOAD TEST ****\n");
    
        int failedGets = 0;
		double start = System.currentTimeMillis();
        int i = 0;
        for ( i = 0; i < stringRecordCount; i++ ) {
            String key = "Eric"+i;
            String obj = db.getString(storeName, key);
            if ( obj == null ) {
                failedGets++;
                if ( displayError ) {
                    System.out.println("Object is null for item " + i);
                    System.exit(i);
                }
            }
        }
        double end = System.currentTimeMillis();
        double seconds = Math.max((end/1000 - start/1000), 1);
        long perSec = (long)((double)i / (double)seconds);
        System.out.println( "Seconds: "+ seconds +
                            ", Msgs per second read: " + perSec);
        System.out.println("Failed gets: "+ failedGets);
        
        System.out.println("Object storage time: " + 
                            db.getObjectStorageTime(storeName));
        System.out.println("Object retrieval time: " + 
                            db.getObjectRetrievalTime(storeName));
    }
}
