package me.iscle.scattertodd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private void run(String inputFile, String scatterName) {
        run(inputFile, scatterName, 1);
    }

    private void run(String inputFileName, String scatterName, int byteSize) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(scatterName));
            ArrayList<String> names = new ArrayList<>();
            ArrayList<Long> addresses = new ArrayList<>();

            while (true) {
                String line = br.readLine();
                int indexOfSpace = line.indexOf(' ');
                String name = line.substring(0, indexOfSpace).toLowerCase();
                long address = Long.valueOf(line.substring(indexOfSpace + 3), 16);

                names.add(name);
                addresses.add(address);

                // Read two useless lines
                br.readLine();
                br.readLine();

                if (name.equals("")) {
                    break;
                }
            }

            for (int i = 0; i < names.size() - 1; i++) {
                String name = names.get(i);
                long skip = addresses.get(i) / byteSize;
                long count = (addresses.get(i + 1) - addresses.get(i)) / byteSize;
                System.out.println("dd if=" + inputFileName + " of=" + name + " bs=" + byteSize + " skip=" + skip + " count=" + count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            System.out.println("WARNING! This tool won't give the correct byte count for the last partition in the scatter (normally BMTPOOL)!");
            new Main().run(args[0], args[1], 2048);
            System.out.println("WARNING! This tool won't give the correct byte count for the last partition in the scatter (normally BMTPOOL)!");
        } else if (args.length == 2){
            System.out.println("WARNING! This tool won't give the correct byte count for the last partition in the scatter (normally BMTPOOL)!");
            System.out.println("WARNING! USING DEFAULT BYTESIZE: 2048");
            new Main().run(args[0], args[1], 2048);
            System.out.println("WARNING! This tool won't give the correct byte count for the last partition in the scatter (normally BMTPOOL)!");
        } else {
            System.out.println("WRONG ARGUMENTS!\nUSAGE: arg1 = inputFile, arg2 = scatterName, arg3 (optional) = byteSize\nEXAMPLE: java -jar scattertodd.jar dump.bin MT6577_Android_scatter_emmc.txt 2048");
        }
    }
}
