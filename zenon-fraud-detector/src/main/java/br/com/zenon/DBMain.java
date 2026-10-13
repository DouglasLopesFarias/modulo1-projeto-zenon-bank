package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;

public class DBMain {

    void main(){
        ConnectionFactory.getConnection();
        System.out.println("Conexão com o DB criada! :)");

        //TransactionRepository repository = new TransactionSQLRepository();

//        repository.findByOriginName("C1000001")
//                        .ifPresentOrElse(IO::println, () -> IO.println("Transação nao encontrada para: C1000001"));
//        repository.findByOriginName("C2345")
//                .ifPresentOrElse(IO::println, () -> IO.println("Transação nao encontrada para: C2345"));

//        var t1 = new Transaction(1, TransactionType.PAYMENT, new BigDecimal("9838.64"),
//                new TransactionCustomer("C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36") ),
//                new TransactionCustomer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0")),
//                false, false);

//        var t2 = new Transaction(1, TransactionType.PAYMENT, new BigDecimal("9838.64"),
//                new TransactionCustomer("C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0") ),
//                new TransactionCustomer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("0.0")),
//                true, false);
//
//        repository.save(t1);
//        repository.save(t2);
//        IO.println("Transação salva com sucesso!!");



        var repository = new TransactionSQLRepository();
        var transactionIngestor = new TransacionIngestor();
        long startTimeSQL = System.nanoTime();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        IO.println(transactions.size());
        IO.println("Iniciando adição das tarnsacoes no DB...");
        transactions.forEach(repository::save);
        long endTimeSQL = System.nanoTime();
        IO.println("Tempo de Inserção - SQL (ms): " + (endTimeSQL - startTimeSQL) / 1_000_000.0);

        repository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transação nao encontrada para: C1000001"));

    }

}
