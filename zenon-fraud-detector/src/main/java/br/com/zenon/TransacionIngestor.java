package br.com.zenon;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TransacionIngestor {


    public List<Transaction> read(String filename){

        Path path  = Path.of(filename);
        try{
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        }catch (Exception ex){
            throw  new RuntimeException("Erro ao ler o arquivo: "+ filename, ex);
        }



    }

    public List<Transaction> readOld(String filename){

        List<Transaction> transactions = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(filename);
            Scanner scanner = new Scanner(fis)){
                int lineCount = 0;

                while (scanner.hasNext()){
                    String line = scanner.nextLine();
                    lineCount++;

                    if(lineCount == 1){
                        continue;
                    }

                    if (lineCount > 1000){
                        break;
                    }
                    IO.println(line);

                    var transaction = parseTransaction(line).get();
                    transactions.add(transaction);

                }

        }catch (Exception ex){
            new RuntimeException("Erro ao ler o arquivo: " + filename, ex);
        }

        return transactions;
    }

    public Optional<Transaction> parseTransaction(String line ){

        try{
            String[]  chunks = line.split(",");
            int step = Integer.parseInt(chunks[0]);
            TransactionType type = TransactionType.valueOf(chunks[1]);
            if(chunks[2] == null || chunks[2].trim().isEmpty()) throw new IllegalArgumentException("O valor de amount nao pode ser nulo nem vazio");
            BigDecimal amount = new BigDecimal(chunks[2]);

            IO.println(amount);

            var origin    = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5]));
            var recipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8]));

            boolean isFraud = "1".equals(chunks[9]);
            boolean isFlaggedFraud = "1".equals(chunks[10]);

            return Optional.of( new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud ));

        }catch (Exception e){
            System.err.println("Erro ao fazer o parse: "+ line + " | " + e);
            //e.printStackTrace();
            return Optional.empty();
        }

    }

}
