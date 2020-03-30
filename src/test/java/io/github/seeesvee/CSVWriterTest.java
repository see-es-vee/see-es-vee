package io.github.seeesvee;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

//        System.out.println("\n\n\nDelete writeFileTestOutput.csv\n\n\n");
//
//        ls();
//
//        Files.deleteIfExists(Paths.get("writeFileTestOutput.csv"));
//
//        System.out.println("\n\n\nShould be gone\n\n\n");
//
//
//        ls();

        ArrayList<TestClass> testArray = new ArrayList<>();
        testArray.add(testObject);

        CSVWriter<TestClass> writer = new CSVWriteBuilder<TestClass>()
                .setClass(TestClass.class)
                .create();


//        File file = new File("./writeFileTestOutput.csv");
//
//        System.out.println(file);
//        System.out.println(testArray);
//
//        try{
//            writer.write(file, testArray);
//        }
//        catch(NullPointerException e ){
//            e.printStackTrace();
//        }
//
//
//        System.out.println("\n\n\n");
//
//        ls();
//
//        System.out.println("\n\n\n");

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

    private static void ls(){
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("ls -aF");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}

    }

}
