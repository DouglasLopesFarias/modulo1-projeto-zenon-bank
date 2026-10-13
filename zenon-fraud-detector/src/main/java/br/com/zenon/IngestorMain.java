package br.com.zenon;

import java.util.List;

public class IngestorMain {

    void main(){

        var repository = new TransactionSQLRepository();

        var transactionIngestor = new EfficientTransacionIngestor();

        long startTimeSQL = System.nanoTime();
        transactionIngestor.readAsBatch("data/PS_20174392719_1491204439457_log.csv", repository::saveAll);

        long endTimeSQL = System.nanoTime();
        IO.println("Tempo de Inserção - SQL (ms): " + (endTimeSQL - startTimeSQL) / 1_000_000.0);

    }

}
