import com.gennaro.csv.*;

public class Main {

    public static void main(String[] args) {

        /**
         *  return array of "Data" parsed from the .CSV file.
         *  see the "ExampleReader" directory to view how this was done.
        **/
        ArrayList<Data> datas = csvParser.parse(new File("test.csv"));

        // Create a new CSVWriteBuilder with our "Data" class
        CSVWriteBuilder<Data> writeBuilder = new CSVWriteBuilder<>();

        // Set the class to "Data" using the "setClass()" method
        writeBuilder.setClass(Data.class);

        // Create the new CSVWriter using the ".create()" function on your write builder
        CSVWriter<Data> writer = writeBuilder.create();

        // Pass in the file name/location that you want to write to, and the ArrayList of "Data" objects to write from.
        writer.write(new File("ExampleOutput.csv"), datas);

    }

}
