import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 *
 * @authors Anton Nagornyi
 * on 20.02.2018.
 */
public class FifoFileBufferTest extends TestCase {
    private Timestamp timestamp;
    private String testString;
    private Integer integer;
    private FifoFileBuffer bufferTimestamp;
    private FifoFileBuffer<String> bufferString;
    private FifoFileBuffer<Integer> bufferInteger;

    @Before
    protected void setUp() {
        bufferTimestamp = new FifoFileBuffer<>();
        bufferString = new FifoFileBuffer<>();
        bufferInteger = new FifoFileBuffer<>();
        timestamp = new Timestamp(System.currentTimeMillis());
        testString = "TestString";
        integer = 1789;
    }

    @Test
    public void testPutTimestamp() {
        bufferTimestamp.put(timestamp);

        try {
            assertEquals(bufferTimestamp.take(), timestamp);
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @Test
    public void testPutString() {
        bufferString.put(testString);

        try {
            assertEquals(bufferString.take(), testString);
        }catch(Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testPutInteger() {
        bufferInteger.put(integer);

        try {
            assertEquals(bufferInteger.take(), integer);
        }catch(Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testFifo() {
       List<String> stringsList = new ArrayList<>();

       for(int i = 0; i < 20; i++) {
           stringsList.add(i, "String " + i);
       }

       for(String str : stringsList) {
           bufferString.put(str);
       }

       try {
           for(String str : stringsList) {
               assertEquals(str + " is in incorrect order.", str, bufferString.take());
           }
       }catch(Exception e) {
           System.err.println(e.getMessage());
       }
    }

    @After
    public void tearDown() {
        bufferTimestamp = null;
        bufferString = null;
        bufferInteger = null;
        timestamp = null;
        testString = null;
        integer = null;
    }

}
