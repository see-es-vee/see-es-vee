package io.github.seeesvee;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CSVWriterTest {

    @Test
    public void writeFileTest() throws IOException, NoSuchAlgorithmException {

        TestClass testObject = new TestClass();
        testObject.dad = "dad";
        testObject.mom = "mom";
        testObject.ian = "ian";
        testObject.gennaro = "gennaro";
        testObject._byte = 1;
        testObject._char = 'd';
        testObject._long = 100000L;
        testObject._short = 10000;
        testObject._bool = true;
        testObject._double = 3.14;
        testObject._float = 234.5f;
        testObject._int = 1337;

        ArrayList<TestClass> testArray = new ArrayList<>();
        testArray.add(testObject);

        CSVWriter<TestClass> writer = new CSVWriteBuilder<TestClass>()
                .setClass(TestClass.class)
                .create();


        assertNotNull(writer);

        writer.write(new File("./writeFileTestOutput.csv"), testArray);


        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String hex = checksum("writeFileTestOutput.csv", md);


        assertEquals("3ea2f19bbdfbd73b29e55df257bbfa838563ff53fb1d4e09f5211520ad25505a", hex);

        }

    private static String checksum(String filepath, MessageDigest md) throws IOException {

        // file hashing with DigestInputStream
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }

}
