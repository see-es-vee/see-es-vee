import io.github.ceeesvee.*;

public class Main {

    public static void main(String[] args) {

        // Instantiation of new parse builder of our "Data" class
        CSVParseBuilder<Data> parsebuilder = new CSVParseBuilder<>();

        // Set the parse builder to use the "Data" class
        parsebuilder.setClass(Data.class);

        // Create the new parser using the "create()" function on the the parse builder
        CSVParser<Data> csvParser = parsebuilder.create();

        // return array of "Data" parsed from the .CSV file.
        ArrayList<Data> datas = csvParser.parse(new File("ExampleInput.csv"));

    }

}
