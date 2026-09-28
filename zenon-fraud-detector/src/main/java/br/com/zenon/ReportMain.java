package br.com.zenon;

import br.com.zenon.TransactionReport.Statistics;

public class ReportMain {

    void main(){
        var transactionReport = new TransactionReport();
        //transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        //long totalTransactions = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        //IO.println(("Total de Transações: %d".formatted(totalTransactions)));

        Statistics statistics = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        IO.println("""                
                Total de linhas: %d 
                Total de fraudes: %d
                Valor total transacionado: %.2f
                """.formatted(statistics.totalTransactions(), statistics.totalFrauds(), statistics.totalAmount()));
    }
}
